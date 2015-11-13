package com.android.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * 图片异步下载类，包括图片的软应用缓存以及将图片存放到SDCard或者文件中
 * 
 * 
 */
public class ImageDownloader {
	public static int flag = 0;
	private static final String TAG = "info";
	private HashMap<String, MyAsyncTask> map = new HashMap<String, MyAsyncTask>();
	private Map<String, SoftReference<Bitmap>> imageCaches = new HashMap<String, SoftReference<Bitmap>>();

	/**	
	 * 
	 * @param url
	 *            该mImageView对应的url
	 * @param mImageView
	 * @param path
	 *            文件存储路径
	 * @param mActivity
	 * @param download
	 *            OnImageDownload回调接口，在onPostExecute()中被调用
	 */
	public void imageDownload(String url, ImageView mImageView, String path,
			Activity mActivity, OnImageDownload download) {
		SoftReference<Bitmap> currBitmap = imageCaches.get(url);
		Bitmap softRefBitmap = null;
		if (currBitmap != null) {
			softRefBitmap = currBitmap.get();
		}
		String imageName = "";
		if (url != null) {
			imageName = getImageName(url);
		}
		// 先从软引用中拿数据
		if (currBitmap != null && mImageView != null && softRefBitmap != null
				&& url.equals(mImageView.getTag())) {
			System.out.println("从软引用中拿数据--imageName==" + imageName);
			mImageView.setImageBitmap(softRefBitmap);
		} else if (mImageView != null && url.equals(mImageView.getTag())) {
			// 软引用中没有，从文件中拿数据
			Bitmap bitmap = getBitmapFromFile(mActivity, imageName, path);
			if (bitmap != null) {
				mImageView.setImageBitmap(bitmap);
				// 将读取的数据放入到软引用中
				imageCaches.put(url, new SoftReference<Bitmap>(bitmap));
			}
			// 文件中也没有，此时根据mImageView的tag，即url去判断该url对应的task是否已经在执行，如果在执行，本次操作不创建新的线程，否则创建新的线程。
			else if (url != null && needCreateNewTask(mImageView)) {
				MyAsyncTask task = new MyAsyncTask(url, mImageView, path,
						mActivity, download);
				if (mImageView != null) {
					Log.i(TAG, "执行MyAsyncTask --> " + flag);
					flag++;
					Log.i(TAG, "下载IMG --> " + url);
					task.execute();
					// 将对应的url对应的任务存起来
					map.put(url, task);
				}
			}
		}
	}

	/**
	 * 根据url得到图片名
	 * 
	 * @param url
	 * @return
	 */
	private String getImageName(String url) {
		String imageName = "";
		if (url != null) {
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}

	/**
	 * 判断是否需要重新创建线程下载图片，如果需要，返回值为true。
	 * 
	 * @param url
	 * @param mImageView
	 * @return
	 */
	private boolean needCreateNewTask(ImageView mImageView) {
		boolean b = true;
		if (mImageView != null) {
			String curr_task_url = (String) mImageView.getTag();
			if (isTasksContains(curr_task_url)) {
				b = false;
			}
		}
		return b;
	}

	/**
	 * 检查该url（最终反映的是当前的ImageView的tag，tag会根据position的不同而不同）对应的task是否存在
	 * 
	 * @param url
	 * @return
	 */
	private boolean isTasksContains(String url) {
		boolean b = false;
		if (map != null && map.get(url) != null) {
			b = true;
		}
		return b;
	}

	/**
	 * 删除map中该url的信息，这一步很重要，不然MyAsyncTask的引用会“一直”存在于map中
	 * 
	 * @param url
	 */
	private void removeTaskFormMap(String url) {
		if (url != null && map != null && map.get(url) != null) {
			map.remove(url);
			System.out.println("当前map的大小==" + map.size());
		}
	}

	/**
	 * 从文件中拿图片
	 * 
	 * @param mActivity
	 * @param imageName
	 *            图片名字
	 * @param path
	 *            图片路径
	 * @return
	 */
	private Bitmap getBitmapFromFile(Activity mActivity, String imageName,
			String path) {
		Bitmap bitmap = null;
		if (imageName != null) {
			File file = null;
			try {

				file = new File(Const.FILEPATH_IMG, imageName);
				if (file.exists())
					bitmap = BitmapFactory.decodeStream(new FileInputStream(
							file));
			} catch (Exception e) {
				e.printStackTrace();
				bitmap = null;
			}
		}
		return bitmap;
	}

	/**
	 * 将下载好的图片存放到文件中
	 * 
	 * @param path
	 *            图片路径
	 * @param mActivity
	 * @param imageName
	 *            图片名字
	 * @param bitmap
	 *            图片
	 * @return
	 */
	private boolean setBitmapToFile(String path, Activity mActivity,
			String imageName, Bitmap bitmap) {
		File file = null;
		file = new File(Const.FILEPATH_IMG, imageName);
		if (!file.exists()) {
			File file2 = new File(Const.FILEPATH_IMG);
			file2.mkdirs();
		}
		try {
			file.createNewFile();
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (imageName != null
					&& (imageName.contains(".png") || imageName
							.contains(".PNG"))) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
			} else {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			}
			fos.flush();
			if (fos != null) {
				fos.close();
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 辅助方法，一般不调用
	 * 
	 * @param path
	 * @param mActivity
	 * @param imageName
	 */
	private void removeBitmapFromFile(String path, Activity mActivity,
			String imageName) {
		File file = null;
		try {

			file = new File(Const.FILEPATH_IMG, imageName);
			if (file != null)
				file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 异步下载图片的方法
	 * 
	 * @author yanbin
	 * 
	 */
	private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
		private ImageView mImageView;
		private String url;
		private OnImageDownload download;
		private String path;
		private Activity mActivity;

		public MyAsyncTask(String url, ImageView mImageView, String path,
				Activity mActivity, OnImageDownload download) {
			this.mImageView = mImageView;
			this.url = url;
			this.path = path;
			this.mActivity = mActivity;
			this.download = download;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap data = null;
			if (url != null) {
				try {
					URL c_url = new URL(url);
					InputStream bitmap_data = c_url.openStream();
					data = BitmapFactory.decodeStream(bitmap_data);
					String imageName = getImageName(url);
					if (!setBitmapToFile(path, mActivity, imageName, data)) {
						removeBitmapFromFile(path, mActivity, imageName);
					}
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);

					writeToSD(path, imageName, client.execute(get).getEntity()
							.getContent());
					imageCaches.put(
							url,
							new SoftReference<Bitmap>(data.createScaledBitmap(
									data, 100, 100, true)));
				} catch (Exception e) {
					LogUtil.i("ErrorUrl:"+url);
					e.printStackTrace();
				}
			}
			return data;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			LogUtil.i("下载完毕：---------》"+url);
			// 回调设置图片
			if (download != null) {
				download.onDownloadSucc(result, url, mImageView);
				// 该url对应的task已经下载完成，从map中将其删除
				removeTaskFormMap(url);
			}
			super.onPostExecute(result);
		}

	}

	public void writeToSD(String path, String imageName, InputStream fis) {

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		File imgFile = new File(path + imageName);
		try {
			File temp = new File(imgFile.getAbsolutePath());
			if (temp.exists()) {
				temp.delete();
			}
			FileOutputStream fos = new FileOutputStream(temp);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			// temp.renameTo(imgFile.getAbsoluteFile());
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public  interface OnImageDownload {
		public void onDownloadSucc(Bitmap bitmap, String c_url, ImageView imageView);
	}

}

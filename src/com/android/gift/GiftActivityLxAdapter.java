package com.android.gift;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.GiftObject;
import com.android.tools.ImageDownloader;
import com.android.tools.ImageDownloader.OnImageDownload;

public class GiftActivityLxAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GiftObject> gfList;
	private ViewHolder myholder;
	private ImageDownloader downloader;
	private View myConvertView;
	private ListView lv;

	public GiftActivityLxAdapter(Context context, ArrayList<GiftObject> gfList,
			ListView lv) {
		this.context = context;
		if (downloader == null) {
			downloader = new ImageDownloader();
		}
		if (gfList != null) {
			this.gfList = gfList;
		}
		this.lv = lv;
	}

	@Override
	public int getCount() {
		return gfList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return gfList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			myholder = holder;
			convertView = LayoutInflater.from(context).inflate(
					R.layout.giftactivity_lvadapter, null);
			myConvertView = convertView;
			holder.ivIcon = (ImageView) convertView
					.findViewById(R.id.iv_giftactivitylxadapter_gift);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_giftactivitylxadapter_name);
			holder.tvNum = (TextView) convertView
					.findViewById(R.id.tv_giftactivitylxadapter_num);
			holder.tvMeaning = (TextView) convertView
					.findViewById(R.id.tv_giftactivitylxadapter_meaning);
			holder.tvPrice = (TextView) convertView
					.findViewById(R.id.tv_giftactivitylxadapter_price);
			holder.tvPoint = (TextView) convertView
					.findViewById(R.id.tv_giftactivitylxadapter_point);
			holder.tvCharm = (TextView) convertView
					.findViewById(R.id.tv_giftactivitylxadapter_charm);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(gfList.get(position).getName());
		holder.tvNum.setText("我的仓库剩余：" + gfList.get(position).getIntegral());
		holder.tvMeaning.setText(gfList.get(position).getRemark());
		holder.tvPrice.setText("售价：" + gfList.get(position).getPrice());
		holder.tvPoint.setText("积分：" + gfList.get(position).getIntegral());
		holder.tvCharm.setText("TA魅力" + gfList.get(position).getCharm());
		holder.ivIcon.setImageBitmap(null);
		holder.ivIcon.setImageResource(R.drawable.gift);
		try {
			holder.ivIcon.setTag(gfList.get(position).getPic());
			downloader.imageDownload(gfList.get(position).getPic(),
					holder.ivIcon, Const.FILEPATH_IMG, (Activity) context,
					new OnImageDownload() {

						@Override
						public void onDownloadSucc(Bitmap bitmap, String c_url,
								ImageView imageView) {
							ImageView iv = (ImageView) lv
									.findViewWithTag(c_url);
							if (iv != null) {
								iv.setImageBitmap(bitmap);
								iv.setTag("");
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				GiftNumDialog dialog = new GiftNumDialog(context, gfList
						.get(position));
				dialog.show();
			}
		});
		return convertView;
	}
	class ViewHolder {
		ImageView ivIcon;
		TextView tvName;
		TextView tvNum;
		TextView tvMeaning;
		TextView tvPrice;
		TextView tvPoint;
		TextView tvCharm;
	}
}

package com.android.qingshu;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

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

import com.android.fragmentbase.MyApplication;
import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.Friend;
import com.android.tools.ImageDownloader;
import com.android.tools.ImageDownloader.OnImageDownload;
import com.android.tools.LogUtil;
import com.android.tools.SNetTools;
import com.android.tools.SNetTools.ReponseGetCallBack;
import com.android.tools.STools;

public class GZLVAdapter extends BaseAdapter {

	private ArrayList<Friend> qsList;
	private Context context;
	private ImageDownloader downloader;
	private ViewHolder myholder;
	private ListView lv;

	public GZLVAdapter(Context context, ArrayList<Friend> qsList, ListView lv) {
		this.context = context;
		this.lv = lv;
		if (downloader == null) {
			downloader = new ImageDownloader();
		}
		if (qsList != null) {
			this.qsList = qsList;
		}
	}

	@Override
	public int getCount() {
		return qsList.size();
	}

	@Override
	public Object getItem(int position) {
		return qsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			myholder = holder;
			convertView = LayoutInflater.from(context).inflate(
					R.layout.qingshu_gz_adapter_fragment, null);
			holder.ivIcon = (ImageView) convertView
					.findViewById(R.id.iv_gzlvadapter_icon);
			holder.tvNickName = (TextView) convertView
					.findViewById(R.id.tv_gzlvadapter_nickname);
			holder.tvProvince = (TextView) convertView
					.findViewById(R.id.tv_gzlvadapter_province);
			holder.tvCity = (TextView) convertView
					.findViewById(R.id.tv_gzlvadapter_city);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_gzlvadapter_time);
			holder.tvMsgNum = (TextView) convertView
					.findViewById(R.id.tv_gzlvadapter_smsnum);
			holder.tvInfo = (TextView) convertView
					.findViewById(R.id.tv_gzlvadapter_info);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvNickName.setText(qsList.get(position).getNickname());
		holder.tvProvince.setText(qsList.get(position).getProvince() + " ");
		holder.tvCity.setText(qsList.get(position).getCity());
		holder.tvInfo.setText(qsList.get(position).getAge() + "岁/"
				+ qsList.get(position).getHeight() + "cm");
		holder.tvTime.setText(STools.getLastTime(qsList.get(position)
				.getLastTime()));
		LogUtil.i("newNum:"+qsList.get(position).getNewNum());
		if (qsList.get(position).getNewNum().equals("0")) {
			holder.tvMsgNum.setVisibility(View.GONE);
		} else {
			holder.tvMsgNum.setVisibility(View.VISIBLE);
			holder.tvMsgNum.setText(qsList.get(position).getNewNum());
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyApplication.closeID=qsList.get(position).getId();
				SNetTools.reponseGet(
						Const.URL_MESSAGE_READ
								+ qsList.get(position).getFriendID(),
						new ReponseGetCallBack() {

							@Override
							public void resGetCallBack(String resGetStr) {
								LogUtil.i(qsList.get(position).getFriendID()
										+ resGetStr);
							}
						});

				// RongIM.getInstance()
				// .refreshUserInfoCache(
				// new UserInfo(
				// "10002",
				// "啊明",
				// Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png")));
				RongIM.getInstance().startConversation(context,
						Conversation.ConversationType.PRIVATE,
						qsList.get(position).getFriendID(),
						qsList.get(position).getNickname());
			}
		});
		holder.ivIcon.setTag(qsList.get(position).getPortarait());
		downloader.imageDownload(qsList.get(position).getPortarait(),
				holder.ivIcon, Const.FILEPATH_IMG, (Activity) context,
				new OnImageDownload() {
					@Override
					public void onDownloadSucc(Bitmap bitmap, String c_url,
							ImageView ivImg) {
						ImageView iv = (ImageView) lv.findViewWithTag(c_url);
						if (iv != null) {
							iv.setImageBitmap(bitmap);
							iv.setTag("");
						}
					}
				});
		return convertView;
	}

	class ViewHolder {
		ImageView ivIcon;
		TextView tvNickName;
		TextView tvCity;
		TextView tvArea;
		TextView tvProvince;
		TextView tvInfo;
		TextView tvTime;
		TextView tvMsgNum;
	}

	public void updata(ArrayList<Friend> friends) {
		if (qsList == null && friends != null) {
			qsList = new ArrayList<Friend>();
		}
		qsList = (ArrayList<Friend>) friends.clone();
		notifyDataSetChanged();

	}
}

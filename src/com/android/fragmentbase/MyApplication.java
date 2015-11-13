package com.android.fragmentbase;

import java.io.File;
import java.util.ArrayList;

import com.android.gift.GiftActivity;
import com.android.qingshu.GZFragemnt;
import com.android.qingshu.HFFragemnt;
import com.android.qingshu.LXFragemnt;
import com.android.tools.Const;
import com.android.tools.Friend;
import com.android.tools.GiftObject;
import com.android.tools.LogUtil;

import io.rong.imkit.RongIM;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class MyApplication extends Application {
	public static Handler handler;
	private static MyApplication context;
	public static String closeID;
	@Override
	public void onCreate() {
		super.onCreate();

		LogUtil.i("MyApplication:--->oncreate");
		RongIM.init(this);
		createFile();
		initHandler();
		context = this;
		
	}

	private void createFile() {
		File fileImg = new File(Const.FILEPATH_IMG);
		if (!fileImg.exists()) {
			fileImg.mkdirs();
		}
	}

	private void initHandler() {
		if (handler == null) {
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case Const.UPDATA_QINGSHU_LIANXI_ADAPTER:
						LXFragemnt.updata((ArrayList<Friend>) msg.obj);
						break;
					case Const.UPDATA_QINGSHU_HUIFU_ADAPTER:
						HFFragemnt.updata((ArrayList<Friend>) msg.obj);
						break;
					case Const.UPDATA_QINGSHU_GUANZHU_ADAPTER:
						GZFragemnt.updata((ArrayList<Friend>) msg.obj);
						break;
					case Const.UPDATA_GITFACTIVITY_ADAPTER:
						GiftActivity.updata((ArrayList<GiftObject>) msg.obj);
						break;
					case Const.UPDATA_QINGSHU_LIANXI_MESSAGE:
						LogUtil.i("------>MESSAGE_GET_UPDATA_LIANXI");
						LXFragemnt.newInstance(Const.INDEX_QINGSHU_LIANXI);
						LXFragemnt.updata();
						break;

					}
				}
			};
		}

	}

	public static Context getContext() {
		return context;
	}

}

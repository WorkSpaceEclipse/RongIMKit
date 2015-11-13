package com.android.gift;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;

import com.android.fragmentbase.MyApplication;
import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.GiftObject;
import com.android.tools.JsonDownLoad;
import com.android.tools.JsonDownLoad.JsonCallBack;
import com.android.tools.JsonTools;

public class GiftActivity extends Activity {

	private static Context context;
	private static ListView lv;
	public static String targetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.giftactivity);
		this.context = this;
		targetId = getIntent().getExtras().getString("targetId");
		initView();
		initGiftJson();
	}

	private void initGiftJson() {
		final SharedPreferences sp = getSharedPreferences(
				Const.SharedPreferences_GIFT, Activity.MODE_PRIVATE);
		// Editor editor=sp.edit();
		String jsonGift = sp.getString(Const.SharedPreferences_GIFT_JSON, "");
		if (jsonGift.equals("")) {
			JsonDownLoad.getJson(Const.URL_GITF_GET, new JsonCallBack() {
				@Override
				public void jsonBack(String jsonstr) {
					Editor editor = sp.edit();
					editor.putString(Const.SharedPreferences_GIFT_JSON, jsonstr);
					editor.commit();
					ArrayList<GiftObject> gifts = new ArrayList<>();
					gifts = JsonTools.getObjects(jsonstr, gifts);
					Message msg = Message.obtain();
					msg.what = Const.UPDATA_GITFACTIVITY_ADAPTER;
					msg.obj = gifts;
					MyApplication.handler.sendMessage(msg);
				}
			});
		} else {
			ArrayList<GiftObject> gifts = null;
			gifts = JsonTools.getObjects(jsonGift, gifts);
			Message msg = Message.obtain();
			msg.what = Const.UPDATA_GITFACTIVITY_ADAPTER;
			msg.obj = gifts;
			MyApplication.handler.sendMessage(msg);
		}
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.listView_giftactivity);
	}

	public static void close() {
		((Activity) context).finish();
	}

	public static void updata(ArrayList<GiftObject> obj) {
		GiftActivityLxAdapter adapter = new GiftActivityLxAdapter(context, obj,lv);
		lv.setAdapter(adapter);
	}


}

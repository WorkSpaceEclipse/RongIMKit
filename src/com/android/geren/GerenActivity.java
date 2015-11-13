package com.android.geren;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.NetTools;
import com.android.tools.PeopleInfo;
import com.android.tools.STools;
import com.android.tools.Tools;

public class GerenActivity extends Activity {
	static PeopleInfo peopleInfo;
	static Context context;
	final static int CHANGE_INFO = 0;
	GerenActivity gerenActivity;

	static Handler gahandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case CHANGE_INFO:
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		gerenActivity = this;
		context = this;
		peopleInfo = gereninfo.peopleInfo;
		setContentView(R.layout.geren_act);
		getview();
		getinfo();
		super.onCreate(savedInstanceState);
	}

	private void getview() {

	}

	Thread infoThread = new Thread() {
		@Override
		public void run() {
			try {
				String peoples_json = NetTools.GetJson(Const.People_infos + peopleInfo.getId());
				peopleInfo = Tools.Resolve_GerenActivity_info_Json(peoples_json, peopleInfo);
				if (peopleInfo != null)
					gahandler.sendEmptyMessage(CHANGE_INFO);
			} catch (Exception e) {
			}
			super.run();
		}
	};

	private void getinfo() {
		infoThread.start();
	}

	public void ChangeGerenInfo(PeopleInfo peopleInfo) {
		GerenActivity.peopleInfo = peopleInfo;
	}
}

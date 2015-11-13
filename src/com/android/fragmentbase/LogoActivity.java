package com.android.fragmentbase;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.OnSendMessageListener;
import io.rong.imkit.RongIM.SentMessageErrorCode;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.OnReceiveMessageListener;
import io.rong.imlib.model.Message;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.JsonDownLoad;
import com.android.tools.JsonDownLoad.JsonCallBack;
import com.android.tools.LogUtil;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logo);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				RongIM.getInstance().setSendMessageListener(
						new OnSendMessageListener() {

							@Override
							public boolean onSent(Message msg,
									SentMessageErrorCode arg1) {
								LogUtil.i("--------->MessageonSent"
										+ msg.getSenderUserId());
								JsonDownLoad.getJson(Const.URL_MESSAGE_SAY
										+ "?uid=" + msg.getSenderUserId()
										+ "&fid=" + msg.getTargetId(),
										new JsonCallBack() {

											@Override
											public void jsonBack(String jsonstr) {
												LogUtil.i("FriendSay:"
														+ jsonstr);
											}
										});
								return false;
							}

							@Override
							public Message onSend(Message msg) {
								LogUtil.i("--------->MessageonSend"
										+ msg.getSenderUserId());

								return msg;
							}
						});
				RongIM.setOnReceiveMessageListener(new OnReceiveMessageListener() {

					@Override
					public boolean onReceived(Message msg, int left) {
						MyApplication.handler
								.sendEmptyMessage(Const.UPDATA_QINGSHU_LIANXI_MESSAGE);
						return false;
					}
				});
				RongIM.connect(
						//"GMzBh8+aaCKiwp+NJxA8Jc52xkS90RG3vEVefe6WXOACbrcf53RdvsQyo0KY32Lo99AUFemSVi+zqVoPgPmqtQ==",
						 "SH5vnt5V42RhaQ+Truipvs52xkS90RG3vEVefe6WXOC0kjSv1MgyCU0Ulvbgy3UW99AUFemSVi+SIaLVIzuBEA==",
						new ConnectCallback() {

							@Override
							public void onSuccess(String arg0) {
								startActivity(new Intent(LogoActivity.this,
										HomeFragment.class));
								finish();
							}

							@Override
							public void onError(
									io.rong.imlib.RongIMClient.ErrorCode arg0) {
								Toast.makeText(LogoActivity.this,
										"connect onError", Toast.LENGTH_SHORT)
										.show();
							}

							@Override
							public void onTokenIncorrect() {
								Toast.makeText(LogoActivity.this,
										"onTokenIncorrect", Toast.LENGTH_SHORT)
										.show();

							}
						});

			}
		}, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

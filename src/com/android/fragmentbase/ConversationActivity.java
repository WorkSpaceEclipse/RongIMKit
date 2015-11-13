package com.android.fragmentbase;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.RongIMClient.SendMessageCallback;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gift.GiftActivity;
import com.android.qingshu.LXFragemnt;
import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.JsonDownLoad;
import com.android.tools.JsonDownLoad.JsonCallBack;
import com.android.tools.LogUtil;
import com.android.tools.SNetTools;
import com.android.tools.SNetTools.ReponseGetCallBack;

public class ConversationActivity extends FragmentActivity implements
		OnClickListener {
	private static final String TAG = ConversationActivity.class
			.getSimpleName();
	private String targetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.conversation);
		targetId = getIntent().getData().getQueryParameter("targetId");
		LogUtil.i(TAG);
		initView();

	}

	private void initView() {
		ImageView ivGift = (ImageView) findViewById(R.id.iv_ConversationActivity_gift);
		ImageView ivSendmsg = (ImageView) findViewById(R.id.iv_ConversationActivity_sendmsg);
		TextView tvNickName = (TextView) findViewById(R.id.tv_ConversationActivity_nickname);
		tvNickName.setText(getIntent().getData().getQueryParameter("title"));
		RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.rl_ConversationActivity_back);
		rlBack.setOnClickListener(this);
		ivGift.setOnClickListener(this);
		ivSendmsg.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_ConversationActivity_gift:
			Intent it=new Intent(ConversationActivity.this,
					GiftActivity.class);
			it.putExtra("targetId", targetId);
			startActivity(it);
			break;
		case R.id.rl_ConversationActivity_back:
			finish();
			break;
		case R.id.iv_ConversationActivity_sendmsg:
			SNetTools.reponseGet(Const.URL_MESSAGE_SEND + "uid="+Const.UserID+"&fid="+targetId,
					new ReponseGetCallBack() {

						@Override
						public void resGetCallBack(String resGetStr) {
							if (resGetStr.contains("Error")) {
								Toast.makeText(ConversationActivity.this,
										"同一个用户一天只能打一次招呼~", Toast.LENGTH_SHORT)
										.show();
							} else if (resGetStr.contains("Success")) {
								String msg = resGetStr.substring(resGetStr
										.indexOf(":") + 1);
								RongIM.getInstance()
										.getRongIMClient()
										.sendMessage(
												Conversation.ConversationType.PRIVATE,
												targetId,
												TextMessage.obtain(msg),
												"", "",
												new SendMessageCallback() {

													@Override
													public void onSuccess(
															Integer arg0) {
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onError(
															Integer arg0,
															ErrorCode arg1) {
														// TODO Auto-generated
														// method stub

													}
												});
							}
						}
					});
			break;
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		JsonDownLoad.getJson(Const.URL_MESSAGE_CLOSE+MyApplication.closeID, new JsonCallBack() {
			
			@Override
			public void jsonBack(String jsonstr) {
				MyApplication.closeID="";
				LogUtil.i("FriendClose:"+jsonstr);
				MyApplication.handler.sendEmptyMessage(Const.UPDATA_QINGSHU_LIANXI_MESSAGE);
			}
		});
		LogUtil.i("ConversationActivityonDestroy");
	}
}

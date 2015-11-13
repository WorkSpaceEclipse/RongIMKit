package com.android.gift;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.GiftObject;
import com.android.tools.LogUtil;
import com.android.tools.Rong;
import com.android.tools.STools;

public class GiftNumDialog extends Dialog implements
		android.view.View.OnClickListener, Formatter, OnValueChangeListener,
		OnScrollListener {

	private NumberPicker numberPicker;
	private TextView tvConfirm;
	private String giftNum = "1";
	private boolean isRolling = true;
	private Context context;
	private String[] values = {};
	private GiftObject gift;

	public GiftNumDialog(Context context) {
		super(context);
		this.context = context;
	}

	public GiftNumDialog(Context contextm, GiftObject gift) {
		super(contextm);
		this.context = contextm;
		if(gift!=null){
			this.gift = gift;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.giftactivity_numpicker_dialog);
		setTitle("请选择数量");
		initView();
	}

	private void initView() {
		numberPicker = (NumberPicker) findViewById(R.id.numberPicker_giftactivity);
		values = "1, 10, 30, 66, 99, 188, 520, 1314".split(",");
		// { "1", "10", "30", "66", "99", "188", "520", "1314" };
		numberPicker.setDisplayedValues(values);
		numberPicker.setOnClickListener(this);
		numberPicker.setOnValueChangedListener(this);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(values.length - 1);
		numberPicker.setValue(0);
		numberPicker
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		tvConfirm = (TextView) findViewById(R.id.tv_giftnumdialog_confirm);
		tvConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_giftnumdialog_confirm:
			this.dismiss();
			AlertDialog.Builder builder = new Builder(context);
			builder.setTitle("赠送礼物");
			builder.setMessage("确认赠送\""+gift.getName()+"\"" + "x" + giftNum + "个");
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					dismiss();
				}
			});
			builder.setPositiveButton("确认", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				File file=new File(Const.FILEPATH_IMG+STools.getImgName(gift.getPic()));
				LogUtil.i("giftPic:"+gift.getPic());
				FileInputStream in = null;
				try {
					in = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Rong.sendImg(in, GiftActivity.targetId, " ");
					GiftActivity.close();
				}
			});
			builder.create().show();
			break;

		}
	}

	@Override
	public String format(int value) {
		return values[value];
	}

	@Override
	public void onScrollStateChange(NumberPicker arg0, int arg1) {
		Log.i("info", "arg1:" + arg1);
	}

	@Override
	public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
		giftNum = values[newVal];
		Log.i("info", "newVal:" + newVal);
	}

}

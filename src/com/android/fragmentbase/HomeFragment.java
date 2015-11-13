package com.android.fragmentbase;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.GetChars;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.geren.GerenActivity;
import com.android.geren.gereninfo;
import com.android.guangchang.GCFragemnt;
import com.android.mine.MineFragemnt;
import com.android.qingshu.QSFragemnt;
import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.PeopleInfo;
import com.android.tuijian.TJFragment;

@SuppressLint("NewApi")
public class HomeFragment extends FragmentActivity implements OnTouchListener {

	private ImageView ivTJ;
	private ImageView ivGC;
	private ImageView ivQS;
	private ImageView ivMine;
	private MyViewPage myViewPage;
	private long mExitTime;
	private ArrayList<ImageView> ivs;
	private View tvQSNum;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homefragmet);
		initView();
		initLinster();

	}

	private void initLinster() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		ivTJ = (ImageView) findViewById(R.id.iv_tuijian_homefragment);
		ivGC = (ImageView) findViewById(R.id.iv_guangchang_homefragment);
		ivQS = (ImageView) findViewById(R.id.iv_qingshu_homefragment);
		tvQSNum = findViewById(R.id.tv_homefragment_qsnum);
		ivMine = (ImageView) findViewById(R.id.iv_mine_homefragment);

		ivTJ.setOnTouchListener(this);
		ivGC.setOnTouchListener(this);
		ivQS.setOnTouchListener(this);
		ivMine.setOnTouchListener(this);
		tvQSNum.setVisibility(View.GONE);

		TJFragment tjFragemnt = TJFragment.newInstance(Const.INDEX_TUIJIAN);
		GCFragemnt gcFragemnt = GCFragemnt.newInstance(Const.INDEX_GUANGCHANG);
		QSFragemnt qsFragemnt = QSFragemnt.newInstance(Const.INDEX_QINGSHU);
		MineFragemnt mineFragemnt = MineFragemnt.newInstance(Const.INDEX_MINE);

		ArrayList<Fragment> fragList = new ArrayList<Fragment>();
		fragList.add(tjFragemnt);
		fragList.add(gcFragemnt);
		fragList.add(qsFragemnt);
		fragList.add(mineFragemnt);

		MyViewPageAdapter pageAdapter = new MyViewPageAdapter(
				getSupportFragmentManager(), fragList);
		myViewPage = (MyViewPage) findViewById(R.id.viewpager_homefragment);
		myViewPage.setAdapter(pageAdapter);
		turnToPressOn(ivTJ);
	}

	private void turnToPressOn(ImageView ivOn) {
		if (ivs == null) {
			ivs = new ArrayList<ImageView>();
			ivs.add(ivTJ);
			ivs.add(ivGC);
			ivs.add(ivQS);
			ivs.add(ivMine);
		}
		for (ImageView iv : ivs) {
			if (iv == ivOn) {
				iv.setPressed(true);
			} else {
				iv.setPressed(false);
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.iv_tuijian_homefragment:
				turnToPressOn(ivTJ);
				myViewPage.setCurrentItem(Const.INDEX_TUIJIAN);
				break;
			case R.id.iv_guangchang_homefragment:
				turnToPressOn(ivGC);
				myViewPage.setCurrentItem(Const.INDEX_GUANGCHANG);
				break;
			case R.id.iv_qingshu_homefragment:
				turnToPressOn(ivQS);
				myViewPage.setCurrentItem(Const.INDEX_QINGSHU);
				break;
			case R.id.iv_mine_homefragment:
				turnToPressOn(ivMine);
				myViewPage.setCurrentItem(Const.INDEX_MINE);
				break;

			}
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public static void ShowGeren(PeopleInfo peopleInfo) {
		Intent bintent = new Intent(MyApplication.getContext(),
				GerenActivity.class);
		bintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		gereninfo.peopleInfo = peopleInfo;
		// Bundle bundle = new Bundle();
		// bundle.putInt("id", peopleInfo.getId());
		// bintent.putExtras(bundle);
		MyApplication.getContext().startActivity(bintent);
	}
}

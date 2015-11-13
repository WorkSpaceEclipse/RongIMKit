package com.android.qingshu;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.fragmentbase.BaseFragment;
import com.android.fragmentbase.MyViewPage;
import com.android.fragmentbase.MyViewPageAdapter;
import com.android.tongcheng.R;
import com.android.tools.Const;

public class QSFragemnt extends BaseFragment implements OnTouchListener {
	private boolean isPrepared;
	private View view;
	private MyViewPage qsMyViewPage;
	private TextView tvXin;
	private TextView tvXinNum;
	private TextView tvHuifu;
	private TextView tvHuifuNum;
	private TextView tvGuanzhu;
	private TextView tvGuanzhuNum;
	private ArrayList<TextView> tvList;
	private static boolean mHasLoadedOnce = false;

	public static QSFragemnt newInstance(int index) {
		Bundle bundle = new Bundle();
		bundle.putInt(Const.index, index);
		QSFragemnt fragment = new QSFragemnt();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater
					.inflate(R.layout.qingshu_fragment, container, false);
			Bundle bundle = getArguments();
			if (bundle != null) {
				// mCurIndex = bundle.getInt(Const.INDEX);
			}
		}
		isPrepared = true;
		lazyLoad();
		initview();
		return view;
	}

	private void initview() {

		tvXin = (TextView) view.findViewById(R.id.tv_xin_qingshufragment);
		tvXinNum = (TextView) view
				.findViewById(R.id.tv_xin_num_qingshufragment);

		tvHuifu = (TextView) view.findViewById(R.id.tv_hf_qingshufragment);
		tvHuifuNum = (TextView) view
				.findViewById(R.id.tv_hf_num_qingshufragment);

		tvGuanzhu = (TextView) view.findViewById(R.id.tv_gz_qingshufragment);
		tvGuanzhuNum = (TextView) view
				.findViewById(R.id.tv_gz_num_qingshufragment);

		tvXinNum.setVisibility(View.GONE);
		tvHuifuNum.setVisibility(View.GONE);
		tvGuanzhuNum.setVisibility(View.GONE);

		RelativeLayout rlXin = (RelativeLayout) view
				.findViewById(R.id.relativelayout_xin_qingshufragment);
		RelativeLayout rlHuifu = (RelativeLayout) view
				.findViewById(R.id.relativelayout_hf_qingshufragment);
		RelativeLayout rlGuanzhu = (RelativeLayout) view
				.findViewById(R.id.relativelayout_guanzhu_qingshufragment);
		// gzRL.setVisibility(View.GONE);

		RelativeLayout rlIcon = (RelativeLayout) view
				.findViewById(R.id.rl_qingshu_icon);
		// rlIcon.setVisibility(View.GONE);

		rlXin.setOnTouchListener(this);
		rlHuifu.setOnTouchListener(this);
		rlGuanzhu.setOnTouchListener(this);

		if (tvList == null) {
			tvList = new ArrayList<>();
			tvList.add(tvXin);
			tvList.add(tvHuifu);
			tvList.add(tvGuanzhu);
		}
		turnTvOn(tvXin);

	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		} else {
			initFragments();
		}

	}

	private void initFragments() {
		mHasLoadedOnce = true;
		LXFragemnt lxfragemnt = LXFragemnt
				.newInstance(Const.INDEX_QINGSHU_LIANXI);
		LXFragemnt hfFragemnt = LXFragemnt
				.newInstance(Const.INDEX_QINGSHU_HUIFU);
		GZFragemnt gzFragemnt = GZFragemnt
				.newInstance(Const.INDEX_QINGSHU_GUANZHU);

		qsMyViewPage = (MyViewPage) view
				.findViewById(R.id.viewpage_qingshufragment);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(lxfragemnt);
		fragments.add(hfFragemnt);
		fragments.add(gzFragemnt);
		MyViewPageAdapter adapter = new MyViewPageAdapter(getFragmentManager(),
				fragments);
		qsMyViewPage.setAdapter(adapter);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.relativelayout_xin_qingshufragment:
				qsMyViewPage.setCurrentItem(Const.INDEX_QINGSHU_LIANXI);
				turnTvOn(tvXin);
				break;

			case R.id.relativelayout_hf_qingshufragment:
				qsMyViewPage.setCurrentItem(Const.INDEX_QINGSHU_HUIFU);
				turnTvOn(tvHuifu);
				break;
			case R.id.relativelayout_guanzhu_qingshufragment:
				qsMyViewPage.setCurrentItem(Const.INDEX_QINGSHU_GUANZHU);
				turnTvOn(tvGuanzhu);
				break;
			}
		}
		return false;
	}
	private void turnTvOn(TextView tvOn) {
		for (TextView tv : tvList) {
			if (tv == tvOn) {
				tv.setTextColor(Color.RED);
			} else {
				tv.setTextColor(Color.BLACK);
			}
		}
	}

}

package com.android.tuijian;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.fragmentbase.BaseFragment;
import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.NetTools;
import com.android.tools.PeopleInfo;
import com.android.tools.Tools;

public class TJFragment extends BaseFragment {
	private boolean isPrepared;
	private static boolean mHasLoadedOnce = false;
	View view = null;
	TJFragment tjFragemnt;

	final static int CHANGE_SHOWCANG = 3;
	final static int CHANGE_ADAPTER = 2;
	static ArrayList<PeopleInfo> peopleInfos = new ArrayList<PeopleInfo>();
	static TJListAdapter tjListAdapter;

	Thread infoThread = new Thread() {
		@Override
		public void run() {
			try {
				mHasLoadedOnce=true;
				String peoples_json = NetTools.GetJson(Const.Tuijian_host + URLEncoder.encode("女", "UTF-8"));
				peopleInfos = Tools.Resolve_Tuijian_host_Json(peoples_json);
				Log.i("info", "peopleInfos.s=" + peopleInfos.size());
				handler.sendEmptyMessage(CHANGE_ADAPTER);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			super.run();
		}
	};

	static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case CHANGE_SHOWCANG:
				ImageView shoucang = (ImageView) msg.obj;
				if (msg.arg1 == 0) {
					shoucang.setImageResource(R.drawable.home_h_);
				} else {
					shoucang.setImageResource(R.drawable.home_h);
				}
				break;
			case CHANGE_ADAPTER:
				if (tjListAdapter != null)
					tjListAdapter.changepeople(peopleInfos);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	public static TJFragment newInstance(int index) {
		Bundle bundle = new Bundle();
		bundle.putInt(Const.index, index);
		TJFragment fragment = new TJFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	private void getinfos() {
		infoThread.start();
	}

	private void getview() {
		ImageView imgbanner = (ImageView) view.findViewById(R.id.imgbanner);
		final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.tuijian_SwipeRefreshLayout);

		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
				android.R.color.black);

		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				(new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() {
						if (!swipeRefreshLayout.isRefreshing())
							swipeRefreshLayout.setRefreshing(false);
					}
				}, 5000);
			}
		});

		imgbanner.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(tjFragemnt.getActivity(), "点击banner", Toast.LENGTH_SHORT).show();
			}
		});

		ListView listView = (ListView) view.findViewById(R.id.tuijian_listview);
		tjListAdapter = new TJListAdapter(this.getActivity(), peopleInfos);
		listView.setAdapter(tjListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		tjFragemnt = this;
		if (view == null) {
			view = inflater.inflate(R.layout.tuijian_fragment, container, false);
			Bundle bundle = getArguments();
			if (bundle != null) {
				// mCurIndex = bundle.getInt(Const.INDEX);
			}
		}
		isPrepared = true;
		lazyLoad();
		getview();
		return view;
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		} else {
			getinfos();
		}
	}

	public static void changeSC(ImageView shoucang, int i) {
		Message msg = new Message();
		msg.what = CHANGE_SHOWCANG;
		msg.arg1 = i;
		msg.obj = shoucang;
		handler.sendMessage(msg);
	}

}

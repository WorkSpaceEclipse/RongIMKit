package com.android.qingshu;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.fragmentbase.BaseFragment;
import com.android.fragmentbase.MyApplication;
import com.android.tongcheng.R;
import com.android.tools.Const;
import com.android.tools.Friend;
import com.android.tools.JsonDownLoad;
import com.android.tools.LogUtil;
import com.android.tools.JsonDownLoad.JsonCallBack;
import com.android.tools.JsonTools;

public class HFFragemnt extends BaseFragment {
	private boolean isPrepared;
	private static View view;
	private static HFFragemnt context;
	private static ListView lv;
	private static LXLVAdapter adapter;
	private static boolean mHasLoadedOnce = false;

	public static HFFragemnt newInstance(int index) {
		Bundle bundle = new Bundle();
		bundle.putInt(Const.index, index);
		HFFragemnt fragment = new HFFragemnt();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = null;
		if (view == null) {
			view = inflater.inflate(R.layout.qingshu_huifu_fragment, container,
					false);
			Bundle bundle = getArguments();
			if (bundle != null) {
				// mCurIndex = bundle.getInt(Const.INDEX);
			}
		}
		this.context = this;
		initView();
		isPrepared = true;
		lazyLoad();
		return view;
	}


	private static void initView() {
		lv = (ListView) view.findViewById(R.id.listView_lianxi_qingshu);
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		} else {
			initData();
		}

	}

	public static void initData() {
		JsonDownLoad.getJson(Const.URL_FRIEND_OLD + Const.UserID,
				new JsonCallBack() {
					@Override
					public void jsonBack(String jsonstr) {
						mHasLoadedOnce = true;
						ArrayList<Friend> friends = JsonTools
								.getObjects(jsonstr);
						Message message = Message.obtain();
						message.what = Const.UPDATA_QINGSHU_HUIFU_ADAPTER;
						message.obj = friends;
						MyApplication.handler.sendMessage(message);
					}
				});
	}

	public static void updata(ArrayList<Friend> friends) {
		if (adapter == null) {
			adapter = new LXLVAdapter(context.getActivity(), friends, lv);
			lv.setAdapter(adapter);
		} else {
			adapter.updata(friends);
		}
	}

	public static void updata() {
		if (null != lv) {
			initView();
			initData();
		}
	}

}

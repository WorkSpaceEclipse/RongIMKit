package com.android.guangchang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fragmentbase.BaseFragment;
import com.android.tongcheng.R;
import com.android.tools.Const;

public class GCFragemnt extends BaseFragment {
	private boolean isPrepared;
	private static boolean mHasLoadedOnce = false;

	public static GCFragemnt newInstance(int index) {
		Bundle bundle = new Bundle();
		bundle.putInt(Const.index, index);
		GCFragemnt fragment = new GCFragemnt();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		if (view == null) {
			view = inflater.inflate(R.layout.guangchang_fragment, container, false);
			Bundle bundle = getArguments();
			if (bundle != null) {
				// mCurIndex = bundle.getInt(Const.INDEX);
			}
		}
		isPrepared = true;
		lazyLoad();
		return view;
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		} else {
		}

	}

}

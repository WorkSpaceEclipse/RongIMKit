package com.android.tuijian;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fragmentbase.HomeFragment;
import com.android.tongcheng.R;
import com.android.tools.PeopleInfo;

public class TJListAdapter extends BaseAdapter {
	Context context;
	ArrayList<PeopleInfo> peoples = new ArrayList<PeopleInfo>();;
	private LayoutInflater mInflater;

	public TJListAdapter(Context context, ArrayList<PeopleInfo> peoples) {
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.peoples = peoples;
	}

	@Override
	public int getCount() {
		return peoples.size() / 9 + 1;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			if (position % 2 == 0) {
				convertView = mInflater.inflate(R.layout.tuijian_item_a, null);
			} else {
				convertView = mInflater.inflate(R.layout.tuijian_item_b, null);
			}
			holder.view = convertView;
			holder.peoples9 = new ArrayList<PeopleInfo>();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.peoples9.clear();
		try {
			for (int i = 0; i < 9; i++) {
				holder.peoples9.add(peoples.get(i + position * 9));
			}
		} catch (Exception e) {
		}
		if (holder.peoples9.size() != 0)
			setView(holder.view, holder.peoples9, position);
		return convertView;
	}

	private void setView(View view, ArrayList<PeopleInfo> arrayList, int position) {
		RelativeLayout layout1 = (RelativeLayout) view.findViewById(R.id.item_a_1);
		RelativeLayout layout2 = (RelativeLayout) view.findViewById(R.id.item_a_2);
		RelativeLayout layout3 = (RelativeLayout) view.findViewById(R.id.item_a_3);
		RelativeLayout layout4 = (RelativeLayout) view.findViewById(R.id.item_a_4);
		RelativeLayout layout5 = (RelativeLayout) view.findViewById(R.id.item_a_5);
		RelativeLayout layout6 = (RelativeLayout) view.findViewById(R.id.item_a_6);
		RelativeLayout layout7 = (RelativeLayout) view.findViewById(R.id.item_a_7);
		RelativeLayout layout8 = (RelativeLayout) view.findViewById(R.id.item_a_8);
		RelativeLayout layout9 = (RelativeLayout) view.findViewById(R.id.item_a_9);
		switch (arrayList.size()) {
		case 9:
			addview(layout9, arrayList.get(8), 9, position);
		case 8:
			addview(layout8, arrayList.get(7), 8, position);
		case 7:
			addview(layout7, arrayList.get(6), 7, position);
		case 6:
			addview(layout6, arrayList.get(5), 6, position);
		case 5:
			addview(layout5, arrayList.get(4), 5, position);
		case 4:
			addview(layout4, arrayList.get(3), 4, position);
		case 3:
			addview(layout3, arrayList.get(2), 3, position);
		case 2:
			addview(layout2, arrayList.get(1), 2, position);
		case 1:
			addview(layout1, arrayList.get(0), 1, position);
		default:
			break;
		}
	}

	private void addview(RelativeLayout layout, final PeopleInfo peopleInfo, final int no, final int ye) {
		ImageView touxiang = (ImageView) layout.findViewById(R.id.tuijian_geren_touxiang);
		if (no == 1) {
			touxiang.setImageResource(R.drawable.aaaaa);
		} else {
			touxiang.setImageResource(R.drawable.bbbbb);
		}

		ImageView vip = (ImageView) layout.findViewById(R.id.tuijian_geren_vip);
		if (peopleInfo.getVip() == 1) {
			vip.setVisibility(View.VISIBLE);
		} else {
			vip.setVisibility(View.GONE);
		}

		final ImageView shoucang = (ImageView) layout.findViewById(R.id.tuijian_geren_shoucang);
		if (peopleInfo.isShoucang()) {
			shoucang.setImageResource(R.drawable.home_h_);
		} else {
			shoucang.setImageResource(R.drawable.home_h);
		}

		ImageView renzheng = (ImageView) layout.findViewById(R.id.tuijian_geren_renzheng);
		if (peopleInfo.isRenzheng()) {
			renzheng.setVisibility(View.VISIBLE);
		} else {
			renzheng.setVisibility(View.GONE);
		}

		TextView name = (TextView) layout.findViewById(R.id.tuijian_geren_name);
		name.setText(peopleInfo.getName());

		TextView age = (TextView) layout.findViewById(R.id.tuijian_geren_age);
		age.setText(peopleInfo.getAge());

		TextView city = (TextView) layout.findViewById(R.id.tuijian_geren_city);
		city.setText(peopleInfo.getCity());

		touxiang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "您点击了" + (no + 9 * ye) + "号女士", Toast.LENGTH_SHORT).show();
				HomeFragment.ShowGeren(peopleInfo);
			}
		});

		shoucang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (peopleInfo.isShoucang()) {
					Toast.makeText(context, "点击收藏", Toast.LENGTH_SHORT).show();
					peopleInfo.setShoucang(false);
					TJFragment.changeSC(shoucang, 1);
				} else {
					Toast.makeText(context, "点击收藏", Toast.LENGTH_SHORT).show();
					peopleInfo.setShoucang(true);
					TJFragment.changeSC(shoucang, 0);
				}
			}
		});
	}

	public class ViewHolder {
		View view;
		ArrayList<PeopleInfo> peoples9;
	}

	public void changepeople(ArrayList<PeopleInfo> peopleInfos) {
		peoples = peopleInfos;
		Log.i("info", "peoples.s=" + peoples.size());
		notifyDataSetChanged();
	}
}

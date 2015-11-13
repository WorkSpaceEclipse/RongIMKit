package com.android.tools;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTools {

	public static ArrayList<Friend> getObjects(String jsonstr) {
		ArrayList<Friend> objs = null;
		try {
			objs = new ArrayList<Friend>();
			JSONArray jsonArray = new JSONArray(jsonstr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Iterator iterator = jsonObject.keys();
				Friend obj = new Friend();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					if (key.equals("id")) {
						obj.setId(jsonObject.getString(key));
					} else if (key.equals("friend_id")) {
						obj.setFriendID(jsonObject.getString(key));
					} else if (key.equals("nickname")) {
						obj.setNickname(jsonObject.getString(key));
					} else if (key.equals("portrait")) {
						obj.setPortarait(jsonObject.getString(key));
					} else if (key.equals("portrait_status")) {
						obj.setPortaraitStatus(jsonObject.getString(key));
					} else if (key.equals("age")) {
						obj.setAge(jsonObject.getString(key));
					} else if (key.equals("height")) {
						obj.setHeight(jsonObject.getString(key));
					} else if (key.equals("province")) {
						obj.setProvince(jsonObject.getString(key));
					} else if (key.equals("city")) {
						obj.setCity(jsonObject.getString(key));
					} else if (key.equals("last_time")) {
						obj.setLastTime(jsonObject.getString(key));
					} else if (key.equals("new_num")) {
						obj.setNewNum(jsonObject.getString(key));
					}
				}
				LogUtil.i(obj.getNickname());
				objs.add(obj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return objs;
	}

	public static ArrayList<GiftObject> getObjects(String jsonstr,
			ArrayList<GiftObject> objs) {
		if (objs == null) {
			objs = new ArrayList<GiftObject>();
		}
		try {
			objs = new ArrayList<GiftObject>();
			JSONArray jsonArray = new JSONArray(jsonstr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Iterator iterator = jsonObject.keys();
				GiftObject obj = new GiftObject();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					if (key.equals("id")) {
						obj.setId(jsonObject.getString(key));
					} else if (key.equals("name")) {
						obj.setName(jsonObject.getString(key));
					} else if (key.equals("remark")) {
						obj.setRemark(jsonObject.getString(key));
					} else if (key.equals("price")) {
						obj.setPrice(jsonObject.getString(key));
					} else if (key.equals("integral")) {
						obj.setIntegral(jsonObject.getString(key));
					} else if (key.equals("charm")) {
						obj.setCharm(jsonObject.getString(key));
					} else if (key.equals("pic")) {
						obj.setPic(jsonObject.getString(key));
					}
				}
				objs.add(obj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return objs;
	}

}

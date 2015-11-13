package com.android.tools;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Tools {

	public static ArrayList<PeopleInfo> Resolve_Tuijian_host_Json(String peoples_json) {
		ArrayList<PeopleInfo> peopleInfos = new ArrayList<PeopleInfo>();
		try {
			JSONArray jsonArray = new JSONArray(peoples_json);
			for (int i = 0; i < jsonArray.length(); i++) {
				PeopleInfo peopleInfo = new PeopleInfo();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				try {
					peopleInfo.setId(jsonObject.getInt("id"));
					peopleInfo.setName(jsonObject.getString("nickname"));
					peopleInfo.setVip(jsonObject.getInt("is_vip"));
					peopleInfo.setAge(jsonObject.getString("age"));
					peopleInfo.setCity(jsonObject.getString("province"));
					peopleInfo.setIcon(jsonObject.getString("portrait"));
					Log.i("info", "解析成功=" + jsonObject);
				} catch (Exception e) {
					Log.i("info", "解析失败=" + jsonObject);
				}

				peopleInfos.add(peopleInfo);
			}
			return peopleInfos;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PeopleInfo Resolve_GerenActivity_info_Json(String peoples_json, PeopleInfo peopleInfo) {
		try {
			JSONObject jsonObject = new JSONObject(peoples_json);
			try {
				peopleInfo.setManifesto(jsonObject.getString("manifesto"));
				peopleInfo.setJob(jsonObject.getString("job"));
				peopleInfo.setIs_having_car(jsonObject.getString("is_having_car"));
				peopleInfo.setIs_having_house(jsonObject.getString("is_having_house"));
				peopleInfo.setIs_marriage(jsonObject.getString("is_marriage"));
				peopleInfo.setIs_want_baby(jsonObject.getString("is_want_baby"));
				peopleInfo.setIs_remote_love(jsonObject.getString("is_remote_love"));
				peopleInfo.setLike_type(jsonObject.getString("like_type"));
				peopleInfo.setPremarital_sex(jsonObject.getString("premarital_sex"));
				peopleInfo.setParent_house(jsonObject.getString("parent_house"));
				peopleInfo.setCharm_part(jsonObject.getString("charm_part"));
				peopleInfo.setCharacter(jsonObject.getString("character"));
				peopleInfo.setInterest(jsonObject.getString("interest"));
				peopleInfo.setMobile(jsonObject.getString("mobile"));
				peopleInfo.setQq(jsonObject.getString("qq"));
				peopleInfo.setWeixin(jsonObject.getString("weixin"));
				peopleInfo.setEmail(jsonObject.getString("email"));

			} catch (Exception e) {
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return peopleInfo;
	}
}

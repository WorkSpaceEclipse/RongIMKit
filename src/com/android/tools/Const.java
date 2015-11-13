package com.android.tools;

import android.os.Environment;

public class Const {
	public final static String index = "index";
	public final static String UserID = "10002";
	
	public final static int INDEX_TUIJIAN = 0;
	public final static int INDEX_GUANGCHANG = 1;
	public final static int INDEX_QINGSHU = 2;
	public final static int INDEX_MINE = 3;

	public final static int INDEX_QINGSHU_LIANXI = 0;
	public final static int INDEX_QINGSHU_HUIFU = 1;
	public final static int INDEX_QINGSHU_GUANZHU = 2;

	public final static int UPDATA_QINGSHU_LIANXI_ADAPTER = 1000;
	public final static int UPDATA_QINGSHU_LIANXI_MESSAGE = 1001;
	public final static int UPDATA_GITFACTIVITY_ADAPTER = 1002;
	public final static int UPDATA_QINGSHU_GUANZHU_ADAPTER = 1003;
	
	public final static int UPDATA_QINGSHU_HUIFU_ADAPTER = 2000;

	public final static String FILEPATH_IMG = Environment
			.getExternalStorageDirectory() + "/chat/icon/";
	public final static String SharedPreferences_GIFT = "SharedPreferences_GIFT";
	public final static String SharedPreferences_GIFT_JSON = "SharedPreferences_GIFT_JSON";

	public final static String BaseUrl = "http://122.195.244.87:8299/";
	
	public final static String URL_FRIEND_NEW = BaseUrl
			+ "friend/get_new_friends?uid=";
	public final static String URL_FRIEND_OLD = BaseUrl
			+ "friend/get_old_friends?uid=";
	public final static String URL_FRIEND_FOCUS = BaseUrl
			+ "friend/get_focus_friends?uid=";
	
	public final static String URL_MESSAGE_SAY = BaseUrl + "message/say";
	//?uid=10001&fid=10002
	public final static String URL_MESSAGE_CLOSE = BaseUrl + "message/close?fid=";
	public final static String URL_MESSAGE_READ = BaseUrl + "message/read?fid=";
	public final static String URL_MESSAGE_SEND = BaseUrl + "message/send?";
	public final static String URL_GITF_GET = BaseUrl + "system/get_gift";

	public final static String URL_TOKEN_GET= BaseUrl + "user/get_token?uid=";

	
	
	public final static String Hosturl = "http://122.195.244.87:8299/";

	public final static String Tuijian_host = Hosturl + "system/index?sex="; // 推荐主页
	public final static String Yonghu_infos = Hosturl + "user/get_user?uid="; // 用户ID
	public final static String People_infos = Hosturl + "user/get_info?uid="; // 通过ID获取个人信息

}

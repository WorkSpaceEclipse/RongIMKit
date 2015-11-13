package com.android.tools;


public class STools {

	public static void messageRead(String id) {

	}

	public static CharSequence getLastTime(String lastTime) {
		String result = "";
		long last=(System.currentTimeMillis()/1000-Long.parseLong(lastTime));
		System.out.println(last);
		int year = (int)last/3600/24/365;
		int month =(int) last/3600/24/31;
		int day= (int)last/3600/24;
		int hour= (int)last/3600;
		int minute=(int) last/60;
		int second= (int)last;
		if (year != 0) {
			result=year+"年之前";
		} else if (month != 0) {
			result=month+"个月之前";
		} else if (day != 0) {
			result=day+"天之前";
		} else if (hour != 0) {
			result=hour+"个小时之前";
		} else if (minute != 0) {
			result=minute+"分钟之前";
		} else if (second != 0) {
			result=second+"秒之前";
		}else{
			result="1秒之前";
		}
		return result;

	}

	public static String getImgName(String url) {
		String imageName = "";
		if (url != null) {
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}
}

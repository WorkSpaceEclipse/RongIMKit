package com.android.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class NetTools {

	/**
	 * 从URL 获取JSON
	 * 
	 * @param url
	 *            访问地址
	 * @return json 返回JSON字符串
	 */
	public static String GetJson(String url) {
		if (url == null || url.equals(""))
			return null;
		BufferedReader reader = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url.trim());
		try {
			Log.i("info", "url=" + url.trim());
			HttpResponse response = client.execute(get);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder builder = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			if (builder != null) {
				reader.close();
				Log.i("info", "builder.toString()=" + builder.toString());
				return builder.toString();
			}
		} catch (UnsupportedEncodingException e) {
			Log.i("info", "UnsupportedEncodingException");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.i("info", "ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("info", "IOException");
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}

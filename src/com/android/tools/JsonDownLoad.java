package com.android.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class JsonDownLoad {

	public static void getJson(final String josnStr, final boolean barSwitch,
			final JsonCallBack jsonCallBack) {
		LogUtil.i(josnStr);
		if (barSwitch) {
		}
		ExecutorService executorService = SNetTools.getExeService();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// HttpClient client = new DefaultHttpClient();
				try {
					URL url = new URL(josnStr);
					URI uri = new URI(url.getProtocol(), url.getHost(),
							url.getPath(), url.getQuery(), null);
					HttpClient client = SNetTools.getSaveHttpClient();
					HttpGet get = new HttpGet(uri);
					HttpResponse response;
					response = client.execute(get);

					BufferedReader br = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent()));
					String line = "";
					StringBuilder sb = new StringBuilder();
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					get.abort();
					jsonCallBack.jsonBack(sb.toString());
					if (barSwitch) {
					}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		executorService.execute(runnable);
	}

	public interface JsonCallBack {
		public void jsonBack(String jsonstr);
	}

	public static void getJson(final String josnStr,
			final JsonCallBack jsonCallBack) {
		LogUtil.i(josnStr);
		ExecutorService executorService = SNetTools.getExeService();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// HttpClient client = new DefaultHttpClient();
				HttpClient client = SNetTools.getSaveHttpClient();
				HttpGet get = new HttpGet(josnStr);
				HttpResponse response;
				try {
					response = client.execute(get);

					BufferedReader br = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent()));
					String line = "";
					StringBuilder sb = new StringBuilder();
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					get.abort();
					jsonCallBack.jsonBack(sb.toString());
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		executorService.execute(runnable);
	}

}

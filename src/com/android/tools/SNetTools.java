package com.android.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class SNetTools {
	private static DefaultHttpClient client;
	private static ExecutorService executorService;

	public static synchronized DefaultHttpClient getSaveHttpClient() {
		if (client == null) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUseExpectContinue(params, true);
			ConnManagerParams.setTimeout(params, 1000);
			HttpConnectionParams.setConnectionTimeout(params, 2000);
			HttpConnectionParams.setSoTimeout(params, 4000);
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			client = new DefaultHttpClient(conMgr, params);
		}
		return client;
	}

	public static ExecutorService getExeService() {
		if (executorService == null) {
			executorService = Executors.newCachedThreadPool();
		}
		return executorService;
	}

	public static void reponseGet(final String url,
			final ReponseGetCallBack reponseGetCallBack) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				client = getSaveHttpClient();
				HttpGet get = new HttpGet(url);
				try {
					HttpResponse response = client.execute(get);
					String line = "";
					StringBuilder sb = new StringBuilder();
					InputStreamReader is = new InputStreamReader(response
							.getEntity().getContent());
					BufferedReader br = new BufferedReader(is);
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					get.abort();
					reponseGetCallBack.resGetCallBack(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		getExeService().execute(runnable);
	}

	public interface ReponseGetCallBack {
		public void resGetCallBack(String resGetStr);
	}
}

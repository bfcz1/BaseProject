package com.me.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.mysql.jdbc.StringUtils;

/***
 * 
 * 无需代理的包
 * 
 * 
 * 
 * **/



@SuppressWarnings("deprecation")
public class PostRequest {

	private static PoolingHttpClientConnectionManager connMgr;
	@SuppressWarnings("unused")
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}


	//post请求
	public static String post(String url, Map<String, String> param, Map<String, String> head, String json) {

		String resp = "";
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);

		for (String key : head.keySet()) {

			post.setHeader(key, head.get(key));

		}

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		for (String key : param.keySet()) {

			params.add(new BasicNameValuePair(key, param.get(key)));

			// try {
			// post.setEntity(new UrlEncodedFormEntity(Arrays.asList(new
			// NameValuePair[]{new BasicNameValuePair(key, param.get(key))})));
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}
		if (!StringUtils.isEmptyOrWhitespaceOnly(json)) {

			StringEntity strEntit = new StringEntity(json, "utf-8");
			post.setEntity(strEntit);
		} else {

			try {
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			HttpResponse response = client.execute(post);
			resp = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resp;
	}
	//get请求
	public static String Get(String url, Map<String, String> head) throws ClientProtocolException, IOException {
		String resp = "";
		HttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);

		for (String key : head.keySet()) {

			requestGet.setHeader(key, head.get(key));

		}

		HttpResponse response = client.execute(requestGet);
		resp = EntityUtils.toString(response.getEntity());

		return resp;
	}
	public static HttpResponse GetReturnHttpResponse(String url, Map<String, String> head) throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);

		for (String key : head.keySet()) {

			requestGet.setHeader(key, head.get(key));

		}

		HttpResponse response = client.execute(requestGet);

		return response;
	}
	//返回responseEntity
	public static HttpResponse postReturnEntity(String url, Map<String, String> param, Map<String, String> head) {

		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);

		for (String key : head.keySet()) {

			post.setHeader(key, head.get(key));

		}

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		for (String key : param.keySet()) {

			params.add(new BasicNameValuePair(key, param.get(key)));

			// try {
			// post.setEntity(new UrlEncodedFormEntity(Arrays.asList(new
			// NameValuePair[]{new BasicNameValuePair(key, param.get(key))})));
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}
		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = client.execute(post);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}

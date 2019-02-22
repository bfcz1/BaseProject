package com.me.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.mysql.jdbc.StringUtils;

public class PostRequestWithProxy {
	
	//传入代理即可
	public static String get(String url, Map<String, String> head, String proxyIp, int port, String proxyProtocol) throws SocketTimeoutException, ConnectTimeoutException {
		String resp = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		// 依次是目标请求地址，端口号,协议类型
		// HttpHost target = new HttpHost("119.75.213.61", 443,"https");
		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost(proxyIp, port, proxyProtocol);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectionRequestTimeout(8000).setConnectTimeout(8000).setSocketTimeout(8000)
				.build();
		HttpGet requestGet = new HttpGet(url);
		requestGet.setConfig(config);

		for (String key : head.keySet()) {
			requestGet.setHeader(key, head.get(key));
		}

		CloseableHttpResponse response = null;
		try {
			response = closeableHttpClient.execute(requestGet);
			resp = EntityUtils.toString(response.getEntity());
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
//				response.getEntity().getContent().close();
				requestGet.releaseConnection();
				closeableHttpClient.close();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		// 释放get
		return resp;
	}
	//传入代理即可
	public static String postWithProxy(String url, Map<String, String> param, Map<String, String> head, String json) {

		String resp = "";
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpHost proxy = new HttpHost("1.199.226.180", 10240, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(8000).setSocketTimeout(8000)
				.build();
		HttpPost post = new HttpPost(url);
		post.setConfig(config);

		for (String key : head.keySet()) {

			post.setHeader(key, head.get(key));

		}

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		for (String key : param.keySet()) {

			params.add(new BasicNameValuePair(key, param.get(key)));

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

			HttpResponse response = closeableHttpClient.execute(post);
			resp = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resp;
	}
}

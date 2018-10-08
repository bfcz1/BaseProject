package com.me.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.me.net.PostRequest;
import com.me.net.PostRequestWithProxy;

public abstract class BaseModle extends Thread {

	public abstract void spider(String Url);
	public String ip;
	public int port;

	public String postRequestGet(String url, Map<String, String> header) {
		String rstPageSource = null;
		for (int i = 0; i < 3; i++) {

			try {
				rstPageSource = PostRequest.Get(url, header);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
				return rstPageSource;
			}
			if(!StringUtils.isBlank(rstPageSource)){
				break;
			}
		}
		return rstPageSource;
	}
	public String postRequestGetWithProxy(String url, Map<String, String> header,String failKeyword) {
		String rstPageSource = null;
		for (int i = 0; i < 3; i++) {

			try {
				rstPageSource = PostRequest.Get(url, header);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				setProxy();
				e.printStackTrace();
			}
			if(!StringUtils.isBlank(rstPageSource)){
				break;
			}
			if(StringUtils.contains(rstPageSource, failKeyword)){
				setProxy();
			}
		}
		return rstPageSource;
	}
	public void setProxy() {

		List<String> readLines = null;
		try {
			readLines = IOUtils
					.readLines(new FileInputStream(System.getProperty("user.dir") + File.separator + "proxyurl.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String url = readLines.get(0);
		// 请求代理，如果代理有问题，继续请求
		while (true) {
			// 获取代理中
			System.out.println("获取代理中");
			String get = null;
			try {
				get = PostRequest.Get(url, new HashMap<String, String>());
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (StringUtils.isBlank(get)) {
				try {
					BaseModle.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("代理为空，请检查代理配置文件");
				continue;
			}
			if (StringUtils.contains(get, "请按规定10秒提取一次")) {
				try {
					BaseModle.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			get = get.replaceAll("\\s+", "");
			System.out.println(get);
			String tempip = get.replaceAll(":.*", "");
			String tempport = get.replaceAll(".*:", "");
			if (checkIP(tempip, tempport)) {
				this.ip = tempip;
				this.port = Integer.parseInt(tempport);
				break;
			}
			try {
				BaseModle.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private boolean checkIP(String ip, String port) {

		String str = null;
		try {
			str = PostRequestWithProxy.get("http://httpbin.org/ip", new HashMap<String, String>(), ip,
					Integer.parseInt(port), "http");
			System.out.println("真实ip为：" + str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(str)) {
			return true;
		}
		return false;

	}

}

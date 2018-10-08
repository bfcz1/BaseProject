package com.me.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
	public static String url = null;
	public static String driver = null;
	public static String user = null;
	public static String password = null;

	static {
		// 利用配置文件设置数据库
		Properties propertiesFile = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(System.getProperty("user.dir") + File.separator + "DataBase.properties");
			propertiesFile.load(in);

			url = propertiesFile.getProperty("url");
			driver = propertiesFile.getProperty("driver");
			user = propertiesFile.getProperty("username");
			password = propertiesFile.getProperty("password");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Connection conn = null;

	public static Connection getConn() {

		if (conn != null)
			return conn;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

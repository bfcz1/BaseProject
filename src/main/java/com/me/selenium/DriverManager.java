package com.me.selenium;

import java.io.File;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.me.utils.UseragentUtil;

public class DriverManager {
	String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);

	public WebDriver getFirefoxDriver() {

		// 實例化帶有代理ip，不加載圖片的firefoxdriver
		FirefoxProfile profile = new FirefoxProfile();

		// profile.setPreference("network.proxy.type", 1);
		// profile.setPreference("network.proxy.http", proxy.getIp());
		// profile.setPreference("network.proxy.ftp", proxy.getIp());
		// profile.setPreference("network.proxy.socks", proxy.getIp());
		// profile.setPreference("network.proxy.ssl", proxy.getIp());
		// profile.setPreference("network.proxy.http_port", proxy.getPort());
		// profile.setPreference("network.proxy.ftp_port", proxy.getPort());
		// profile.setPreference("network.proxy.socks_port", proxy.getPort());
		// profile.setPreference("network.proxy.ssl_port", proxy.getPort());
		profile.setPreference("general.useragent.override", UseragentUtil.getRandomUA());
		// profile.setPreference("permissions.default.image", 1);
		FirefoxDriver driver = new FirefoxDriver(profile);
		// driver.manage().window().setSize(new Dimension(1000, 800));

		return driver;
	}

	public WebDriver getChromeDriver() {

		// 實例化帶有代理ip，不加載圖片的driver
		if (StringUtils.contains(OS_NAME, "lin")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "chromedriver");

		} else {
			// System.setProperty("webdriver.chrome.bin","D:/Google/Chrome/Application/chrome.exe");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "chromedriver.exe");

		}

		ChromeOptions option = new ChromeOptions();
		// option.addArguments("--headless");
		option.addArguments("disable-infobars");
		option.addArguments("--user-agent=" + UseragentUtil.getRandomUA());
		ChromeDriver driver = new ChromeDriver(option);
		return driver;
	}

	public WebDriver getChromeDriverByUA(String useragent) {

		// 實例化帶有代理ip，不加載圖片的driver
		if (StringUtils.contains(OS_NAME, "lin")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "chromedriver");

		} else {
			// System.setProperty("webdriver.chrome.bin","D:/Google/Chrome/Application/chrome.exe");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "chromedriver.exe");

		}

		ChromeOptions option = new ChromeOptions();
		// option.addArguments("--headless");
		option.addArguments("disable-infobars");
		option.addArguments("--user-agent=" + useragent);
		ChromeDriver driver = new ChromeDriver(option);
		return driver;
	}

	public DriverManager() {

	}

}

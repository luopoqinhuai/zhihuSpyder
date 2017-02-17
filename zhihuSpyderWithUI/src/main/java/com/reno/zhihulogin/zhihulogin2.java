package com.reno.zhihulogin;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class zhihulogin2 {
	static String _xsrf = "";
	static OkHttpClient okHttpClient = new OkHttpClient();
	/*
	 * static { CookieManager cookieManager = new CookieManager();
	 * cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL); okHttpClient =
	 * new OkHttpClient.Builder().cookieJar( new
	 * JavaNetCookieJar(cookieManager)).build(); }
	 */
	static String email = "qxl19920213@163.com";

	static String passwd = "88947635qxl";

	static void login() throws IOException {
		// 请求https://www.zhihu.com/#signin 获取隐藏字段 xsrf
		Request request = new Request.Builder().url(
				"https://www.zhihu.com/#signin").build();
		Response response = okHttpClient.newCall(request).execute();
		String resp = response.body().string();
		Document parse = Jsoup.parse(resp);
		Elements select = parse.select("input[type=hidden]");
		Element element = select.get(0);
		_xsrf = element.attr("value");

		// 请求https://www.zhihu.com/captcha.gif?r= 获取验证码 并保存到文件
		String codeUrl = "https://www.zhihu.com/captcha.gif?r=";
		codeUrl += System.currentTimeMillis() + "&type=login";
		Request getcode = new Request.Builder()
				.url(codeUrl)
				.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36")
				.build();
		Response code = okHttpClient.newCall(getcode).execute();
		byte[] bytes = code.body().bytes();
		saveCode(bytes, "code2.png");

		// 肉眼识别并输入验证码
		System.out.println(_xsrf);
		Scanner sc = new Scanner(System.in);
		String randCode = sc.next();
		System.out.println(randCode);
		// 请求https://www.zhihu.com/login/email 并post关键的四个字段 xsrf 用户名 密码 验证码
		// 以及可有可无的记住密码
		FormBody formBody = new FormBody.Builder().add("_xsrf", _xsrf)
				.add("password", passwd).add("remember_me", "true")
				.add("email", email).add("captcha", randCode).build();

		Request request2 = new Request.Builder().post(formBody)
				.url("https://www.zhihu.com/login/email").build();

		System.out.println("ceshiing      " + request2.headers().toString());

		Response loginres = okHttpClient.newCall(request2).execute();
		System.out.println(decode("google_lenve_fb" + " onResponse: "
				+ loginres.body().string().toString()));
	}

	static void getInfoFromElement(Element ele){
		System.out.println(ele.select("a.UserLink-link").get(0));
		System.out.println("\n\n\n");
	}
	
	
	public static void main(String[] args) throws IOException {
		login();
		
		String followers = "https://www.zhihu.com/people/LocTa/followers";
		Request request_followers = new Request.Builder().url(followers)
				.build();
		Response response_followers = okHttpClient.newCall(request_followers)
				.execute();
		String doc = response_followers.body().string();
		Document parse2 = Jsoup.parse(doc);
		// Elements select = parse.select("input[type=hidden]");
		// Element element = select.get(0);
		Elements Lists=parse2.select("div.List-item");
		System.out.println(Lists.size());
		
		for(Element el:Lists){
			getInfoFromElement(el);
			
		}

	}

	public static void saveCode(byte[] bfile, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = new File(fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}
}

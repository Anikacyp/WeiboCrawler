package crawl.sina;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicNameValuePair;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import crawl.common.WebClientDevWrapper;

public class SinaClientLogin {

	/**
	 * @param args
	 */
	public DefaultHttpClient login(String id,String psd) {
		
		DefaultHttpClient client = new DefaultHttpClient();
		new WebClientDevWrapper();
		client=(DefaultHttpClient) WebClientDevWrapper.wrapClient(client);
		client.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		client.getParams().setParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
		//post information:
		try {
			HttpPost post = new HttpPost(
					"http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.3.19)");
			String data = getServerTime();
			String nonce = makeNonce(6);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("entry", "weibo"));
			nvps.add(new BasicNameValuePair("gateway", "1"));
			nvps.add(new BasicNameValuePair("from", ""));
			nvps.add(new BasicNameValuePair("savestate", "7"));
			nvps.add(new BasicNameValuePair("useticket", "1"));
			nvps.add(new BasicNameValuePair("ssosimplelogin", "1"));
			nvps.add(new BasicNameValuePair("su",
					encodeAccount(id)));
			nvps.add(new BasicNameValuePair("service", "miniblog"));
			nvps.add(new BasicNameValuePair("servertime", data));
			nvps.add(new BasicNameValuePair("nonce", nonce));
			nvps.add(new BasicNameValuePair("pwencode", "wsse"));
			nvps.add(new BasicNameValuePair("sp", new SinaSSOEncoder().encode(
					psd, data, nonce)));

			nvps.add(new BasicNameValuePair(
							"url",
							"http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack"));
			nvps.add(new BasicNameValuePair("returntype", "META"));
			nvps.add(new BasicNameValuePair("encoding", "UTF-8"));
			nvps.add(new BasicNameValuePair("vsnval", ""));

			//configuration the post information
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			System.out.println("post information:"+EntityUtils.toString(post.getEntity()));
			HttpResponse response = client.execute(post);
			String entity = EntityUtils.toString(response.getEntity());
			System.out.println("\n\r"+entity);
			String url = entity.substring(entity.indexOf("http://weibo.com/ajaxlogin.php?"), entity.indexOf("retcode=0") + 12);
			//System.out.println("+++:"+url);
			HttpGet getMethod = new HttpGet(url);		
			response = client.execute(getMethod);
			entity = EntityUtils.toString(response.getEntity());
			//System.out.println("---:"+entity);
			entity = entity.substring(entity.indexOf("userdomain") + 13, entity
					.lastIndexOf("\""));
			//System.out.println("***:"+entity);
			return client;
			/*
			getMethod = new HttpGet("https://api.weibo.com/2/friendships/followers/ids.json?source=2041255924&count=190&page=3&uid=1266321801");
			response = client.execute(getMethod);
			entity = EntityUtils.toString(response.getEntity());
			// Document doc =
			// Jsoup.parse(EntityUtils.toString(response.getEntity()));
			System.out.println(entity);
			*/

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	//preprocessing the username
	private static String encodeAccount(String account) {
		String userName = "";
		try {
			userName = Base64.encodeBase64String(URLEncoder.encode(account,
					"UTF-8").getBytes());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userName;
	}

	private static String makeNonce(int len) {
		String x = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String str = "";
		for (int i = 0; i < len; i++) {
			str += x.charAt((int) (Math.ceil(Math.random() * 1000000) % x
					.length()));
		}
		return str;
	}

	private static String getServerTime() {
		long servertime = new Date().getTime() / 1000;
		//System.out.println("TIME:"+servertime);
		return String.valueOf(servertime);
	}
}
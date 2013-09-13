package crawl.tencent;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import org.apache.http.util.EntityUtils;

public class TencentClientLogin {

	/**
	 * @param args
	 */
	public DefaultHttpClient login(String uin, String p) {
		@SuppressWarnings("deprecation")
		DefaultHttpClient client = new DefaultHttpClient(
				new ThreadSafeClientConnManager());
		try {
			HttpGet get = new HttpGet("http://check.ptlogin2.qq.com/check?uin="
					+ uin + "&appid=46000101&ptlang=2052&r=" + Math.random());
			System.out.println(get.toString());
			get.setHeader("Host", "check.ptlogin2.qq.com");
			get.setHeader("Referer", "http://t.qq.com/?from=11");

			get.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 5.1; rv:13.0) Gecko/20100101 Firefox/13.0");
			HttpResponse response = client.execute(get);

			String entity = EntityUtils.toString(response.getEntity());
			String[] checkNum = entity
					.substring(entity.indexOf("(") + 1, entity.lastIndexOf(")"))
					.replace("'", "").split(",");
			/*
			 * System.out.println(checkNum[0]); System.out.println(checkNum[1]);
			 * System.out.println(checkNum[2].trim());
			 * System.out.println(checkNum[2].trim().replace("\\x", ""));
			 */
			String pass = "";

			/******************** *锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷 ***************************/
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("javascript");

			String jsFileName = "src/md5.js";
			FileReader reader;

			reader = new FileReader(jsFileName);
			engine.eval(reader);

			if (engine instanceof Invocable) {
				Invocable invoke = (Invocable) engine;

				pass = invoke.invokeFunction("QXWEncodePwd",
						checkNum[2].trim(), p, checkNum[1].trim()).toString();
				// System.out.println("c = " + pass);
			}
			reader.close();

			/************************* 锟斤拷锟斤拷锟斤拷 ****************************/
			get = new HttpGet(
					"http://ptlogin2.qq.com/login?ptlang=2052&u="
							+ uin
							+ "&p="
							+ pass
							+ "&verifycode="
							+ checkNum[1]
							+ "&aid=46000101&u1=http%3A%2F%2Ft.qq.com&ptredirect=1&h=1&from_ui=1&dumy=&fp=loginerroralert&action=4-12-14683&g=1&t=1&dummy=");
			get.setHeader("Connection", "keep-alive");
			get.setHeader("Host", "ptlogin2.qq.com");
			get.setHeader("Referer", "http://t.qq.com/?from=11");
			get.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 5.1; rv:13.0) Gecko/20100101 Firefox/13.0");
			response = client.execute(get);
			entity = EntityUtils.toString(response.getEntity());
			// System.out.println(entity);
			if (entity.indexOf("鐧婚檰鎴愬姛") > -1) {
				System.out.println("login success");
				return client;
				/*
				 * get = new HttpGet(
				 * "https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=801252645&response_type=token&redirect_uri=http://www.aiqingda.me"
				 * ); response = client.execute(get); entity =
				 * EntityUtils.toString(response.getEntity());
				 * entity=entity.substring(entity.indexOf(
				 * "https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=801252645&response_type=token"
				 * ), entity.indexOf("checkType=showAuth")+18);
				 * //System.out.println(entity); get=new HttpGet(entity);
				 * response=client.execute(get);
				 * entity=EntityUtils.toString(response.getEntity());
				 * //System.out.println(entity);
				 * entity=entity.substring(entity.indexOf("access_token"),
				 * entity.indexOf("&name=")); //System.out.println(entity);
				 * Map<String, String> map=new HashMap<String, String>();
				 * for(String param : entity.split("&")){
				 * System.out.println(param); map.put(param.split("=")[0],
				 * param.split("=")[1]); }
				 */
				/*
				 * Document doc = Jsoup.parse(entty);
				 * 
				 * /*Document doc = Jsoup.parse(entity); Element es =
				 * doc.getElementById("topNav1"); String displayName = ""; if
				 * (es != null) { Elements e = es.getElementsByTag("u"); if (e
				 * != null && e.size() > 0) displayName = e.get(0).text(); }
				 */
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
}

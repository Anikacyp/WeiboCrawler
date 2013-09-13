package crawl.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class AppkeySet {
	Map<String, Integer> appkeyMap = new HashMap<String, Integer>();

	public AppkeySet() {
		initialAPPkey();
	}

	// initialize the appkey map.
	public void initialAPPkey() {
		appkeyMap.put("2041255924", 150);
		appkeyMap.put("2429583087", 150);
		appkeyMap.put("1006349126", 150);
		appkeyMap.put("4207614057", 150);
		appkeyMap.put("4105523946", 150);
		appkeyMap.put("687241926", 150);
		appkeyMap.put("110432990", 150);
		appkeyMap.put("3551946331", 150);
		appkeyMap.put("1977026121", 150);
		appkeyMap.put("3088787102", 150);

	}

	public String getAppKey(String oldKey, HttpClient client) {
		HttpResponse response;
		HttpGet get;
		String limit = "http://api.t.sina.com.cn/account/rate_limit_status.json?source=";
		String entity;
		get = new HttpGet(limit + oldKey);
		try {
			response = client.execute(get);
			entity = EntityUtils.toString(response.getEntity());
			Map<String, Object> map = JacksonUtils.getMapFromJsonStr(entity);
			float remain_hits = Float.parseFloat(map.get("remaining_hits")
					.toString());
			float total_hits = Float.parseFloat(map.get("hourly_limit")
					.toString());
			if(total_hits-remain_hits>=appkeyMap.get(oldKey)-10)
			{
				return getCurrentHits(client);
			}
			else{
				return oldKey;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("static-access")
	public String getCurrentHits(HttpClient client) {
		//System.err.println("Here to get another appkey!");
		int waitSeconds;
		HttpResponse response;
		HttpGet get;
		String entity;
		String limit = "http://api.t.sina.com.cn/account/rate_limit_status.json?source=";
		while (true) {
			waitSeconds = 3600;
			for (String obj : appkeyMap.keySet()) {
				//System.out.println("obj:" + obj);
				get = new HttpGet(limit + obj);
				try {
					response = client.execute(get);
					entity = EntityUtils.toString(response.getEntity());
					Map<String, Object> map = JacksonUtils
							.getMapFromJsonStr(entity);
					float remain_hits = Float.parseFloat(map.get(
							"remaining_hits").toString());
					float total_hits = Float.parseFloat(map.get("hourly_limit")
							.toString());
					int second = Integer.parseInt(map.get(
							"reset_time_in_seconds").toString());
					if(total_hits-remain_hits>=appkeyMap.get(obj)-10)
					{
						if(waitSeconds>second)
							waitSeconds=second;
					}
					else{
						return obj;
					}
				} catch (ClientProtocolException e) {
					System.out.println("ERROR1!");
					System.out.println(e);
				} catch (IOException e) {
					System.out.println("ERROR2!");
					System.out.println(e);
				}
			}
			// sleep when all the appkeys are in the limitation.
			try {
				System.out.println("sleep " + waitSeconds + " seconds.");
				Thread.currentThread().sleep(waitSeconds * 1000);
				continue;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			continue;
		}
	}
}

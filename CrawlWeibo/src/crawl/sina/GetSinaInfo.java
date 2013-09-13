package crawl.sina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import sqloperation.LastPosition;
import sqloperation.SqlConnection;
import sqloperation.connection;
import crawl.common.AppkeySet;
import crawl.common.JacksonUtils;

public class GetSinaInfo {
	String sourceKey;
	Map<String, String> infomap;
	DefaultHttpClient client;
	HttpGet getmethod;
	HttpResponse response;
	int count;
	String entity;
	// WriteInfo write;
	// ReadInfo reader;
	int deeplevel;
	AppkeySet appkeySet;
	String oldkey = "4028615622";
	SqlConnection sql = new connection().conn();

	public GetSinaInfo(String id, String psd) {
		client = new SinaClientLogin().login(id, psd);
		sourceKey = "?source=" + oldkey;
		entity = null;
		count = 1000;
		deeplevel = 5;
		appkeySet = new AppkeySet();
		infomap = new APIFunction().InitialAPIs();
	}

	public String getUserInfo(String id) {
		String query;
		oldkey = appkeySet.getAppKey(oldkey, client);
		sourceKey = "?source=" + oldkey;
		getmethod = new HttpGet(infomap.get("user_info") + sourceKey + "&uid="
				+ id);
		entity = execute(getmethod);
		String idstr = "";
		String screen_name = "";
		String name = "";
		String province = "";
		String city = "";
		String location = "";
		String description = "";
		String url = "";
		String profile_image_url = "";
		String profile_url = "";
		String domain = "";
		String gender = "";
		String followers_count = "";
		String friends_count = "";
		String statuses_count = "";
		String favourites_count = "";
		String created_at = "";
		String remark = "";
		String avatar_large = "";
		String lang = "";
		String star = "";
		String bi_followers_count = "";
		String mbtype = "";
		String mbrank = "";
		String block_word = "";

		if (!entity.contains("User does not exists")) {
			Map<String, Object> map = JacksonUtils.getMapFromJsonStr(entity);
			if (map != null) {
				if (map.get("idstr") != null)
					idstr = map.get("idstr").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("screen_name") != null)
					screen_name = map.get("screen_name").toString()
							.replace("\\", "\\\\").replaceAll("\"", "\\\"");
				if (map.get("name") != null)
					name = map.get("name").toString().replace("\\", "\\\\")
							.replaceAll("\"", "\\\"");
				if (map.get("province") != null)
					province = map.get("province").toString()
							.replace("\\", "\\\\").replaceAll("\"", "\\\"");
				if (map.get("city") != null)
					city = map.get("city").toString().replace("\\", "\\\\")
							.replaceAll("\"", "\\\"");
				if (map.get("location") != null)
					location = map.get("location").toString()
							.replace("\\", "\\\\").replaceAll("\"", "\\\"");
				if (map.get("description") != null)
					description = map.get("description").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("url") != null)
					url = map.get("url").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("profile_image_url") != null)
					profile_image_url = map.get("profile_image_url").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("profile_url") != null)
					profile_url = map.get("profile_url").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("domain") != null)
					domain = map.get("domain").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("gender") != null)
					gender = map.get("gender").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("followers_count") != null)
					followers_count = map.get("followers_count").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("friends_count") != null)
					friends_count = map.get("friends_count").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("statuses_count") != null)
					statuses_count = map.get("statuses_count").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("favourites_count") != null)
					favourites_count = map.get("favourites_count").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("created_at") != null)
					created_at = map.get("created_at").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("remark") != null)
					remark = map.get("remark").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("avatar_large") != null)
					avatar_large = map.get("avatar_large").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
				if (map.get("lang") != null)
					lang = map.get("lang").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("star") != null)
					star = map.get("star").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("bi_followers_count") != null)
					bi_followers_count = map.get("bi_followers_count")
							.toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("mbtype") != null)
					mbtype = map.get("mbtype").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("mbrank") != null)
					mbrank = map.get("mbrank").toString().replace("\\", "\\\\")
							.replace("\"", "\\\"");
				if (map.get("block_word") != null)
					block_word = map.get("block_word").toString()
							.replace("\\", "\\\\").replace("\"", "\\\"");
			}
		}
		query = "insert into UserInformation values(\"" + id + "\",\"" + idstr
				+ "\",\"" + screen_name + "\",\"" + name + "\",\"" + province
				+ "\",\"" + city + "\",\"" + location + "\",\"" + description
				+ "\",\"" + url + "\",\"" + profile_image_url + "\",\""
				+ profile_url + "\",\"" + domain + "\",\"" + gender + "\",\""
				+ followers_count + "\",\"" + friends_count + "\",\""
				+ statuses_count + "\",\"" + favourites_count + "\",\""
				+ created_at + "\",\"" + remark + "\",\"" + avatar_large
				+ "\",\"" + lang + "\",\"" + star + "\",\""
				+ bi_followers_count + "\",\"" + mbtype + "\",\"" + mbrank
				+ "\",\"" + block_word + "\");";
		return query;
	}

	// get local user's follower's id Arraylist
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getBilateraID(String id) {
		oldkey = appkeySet.getAppKey(oldkey, client);
		sourceKey = "?source=" + oldkey;
		getmethod = new HttpGet(infomap.get("user_bilateral_id_list")
				+ sourceKey + "&uid=" + id + "&count=" + 1000 + "&sort=0");
		entity = execute(getmethod);
		Map<String, Object> map = JacksonUtils.getMapFromJsonStr(entity);
		ArrayList<Object> array = (ArrayList<Object>) map.get("ids");
		map.clear();
		return array;
	}

	// get weibo information from me.
	public void GetStarted() {
		// 获取用户信息
		// getUserInformation();
		// 获取用户发表的微博
		Scanner scanner = new Scanner(System.in);
		System.out.println("Current POS:");
		int num = scanner.nextInt();
		getWeibo(num);
		// 获取用户的关系
		// getRelationsFUN();
		sql.disconnectMySQL();
	}

	public void getRelationsFUN() {
		ArrayList<Object> initialList = new ArrayList<Object>();
		LastPosition getList = new LastPosition();
		initialList = getList.getArray();
		getRelations(initialList, getList.lastLevel);
	}

	public void getUserInformation() {
		int total = 5052053;
		int looptime = total / 10;
		for (int i = 0; i <= looptime; i++) {
			String query = "select id from userID order by id limit " + i * 100
					+ "," + 100 + ";";
			ResultSet rs = sql.executeQuery(query);
			try {
				while (rs.next()) {
					String currentId = rs.getString("id");
					query = "select count(*) from UserInformation where id=\""
							+ currentId + "\";";
					if (!(sql.getCount(query) == 1)) {
						query = getUserInfo(currentId);
						if (query != null)
							sql.execute(query);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void getRelations(ArrayList<Object> curlist, int level) {
		System.out.println("current level:" + level);
		ArrayList<Object> innerArray = new ArrayList<Object>();
		if (level == deeplevel) {
			return;
		} else {
			for (Object obj : curlist) {
				ArrayList<Object> followers = new ArrayList<Object>();
				followers = getBilateraID(obj.toString());
				String query = "select count(*) from bilateralRelation where id=\""
						+ obj + "\";";
				if (!(sql.getCount(query) >= followers.size() * 0.8)) {
					for (Object element : followers) {
						sql.execute("insert into bilateralRelation values(\""
								+ obj + "\",\"" + element + "\"," + level
								+ ");");
						if (!innerArray.contains(element))
							innerArray.add(element);
					}
					followers.clear();
				}
			}
		}
		getRelations(innerArray, ++level);
	}

	public String execute(HttpGet get) {
		try {
			response = client.execute(get);
			entity = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			entity = "";
		}
		return entity;
	}

	public void getWeibo(int num) {
		int total = 5052053;
		int looptime = total / 100;
		// original number 2700
		// original i<looptime
		for (int i = num; i < num + 1000; i++) {
			if (i % 10 == 0) {
				System.out.println(i);
			}
			String query = "select id from userID order by id limit " + i * 100
					+ "," + 100 + ";";
			ResultSet rs = sql.executeQuery(query);
			try {
				while (rs.next()) {
					String currentId = rs.getString("id");
					////////////////////////////////////////////////
					System.out.println("Current ID is: "+currentId);
					query = "select count(*) from weiboInfo where id=\""
							+ currentId + "\";";
					if (!(sql.getCount(query) >= 5)) {
						//System.out.println(currentId);
						String[] id = getWeiboID(currentId);
						if (id != null) {
							for (int j = 0; j < id.length; j++) {
								query = getWeibo(currentId, id[j]);
								if (query != null){
									System.out.println(query);
									sql.execute(query);
									System.out.println(query);
								}
							}
						}
					} else
						continue;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// change count from 10 to 1000
	public String[] getWeiboID(String id) {
		oldkey = appkeySet.getAppKey(oldkey, client);
		sourceKey = "?source=" + oldkey;
		getmethod = new HttpGet(infomap.get("user_timeline_ids") + sourceKey
				+ "&uid=" + id + "&count=10");
		entity = execute(getmethod);
		if (!entity.contains("User does not exists")) {
			Map<String, Object> map = JacksonUtils.getMapFromJsonStr(entity);
			String ids = map.get("statuses").toString().replace("[", "")
					.replace("]", "").replace(" ", "");
			return ids.split(",");
		} else {
			return null;
		}
	}

	public String getWeibo(String uid, String id) {
		oldkey = appkeySet.getAppKey(oldkey, client);
		sourceKey = "?source=" + oldkey;
		getmethod = new HttpGet(infomap.get("show_id") + sourceKey + "&id="
				+ id);
		entity = execute(getmethod);
		Map<String, Object> map = JacksonUtils.getMapFromJsonStr(entity);
		String matchText = "";
		String weiboText = "";
		if (map.get("text") != null) {
			weiboText = map.get("text").toString().replace("\\", "\\\\")
					.replace("\"", "\\\"");
		}
		if (entity.contains("retweeted_status")) {
			String text = map.get("retweeted_status").toString();
			if (text != null) {
				while ((matchText = match("text=.*?, source=", text)) != null) {
					String t = matchText.substring(5, matchText.length() - 9)
							.replace("\\", "\\\\").replace("\"", "\\\"");
					weiboText += "&" + t;
					text = text.replace(matchText, "");
				}
			}
			return "insert into weiboInfo values(\"" + uid + "\",\"" + id
					+ "\",\"" + weiboText + "\");";
		}
		return "insert into weiboInfo values(\"" + uid + "\",\"" + id + "\",\""
				+ weiboText + "\");";
	}

	public static String match(String pattern, String inputString) {
		Pattern patterner = Pattern.compile(pattern);
		Matcher matcher = patterner.matcher(inputString);
		if (matcher.find()) {
			return (matcher.group());
		}
		return null;
	}
}

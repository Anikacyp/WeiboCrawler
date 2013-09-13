package crawl.tencent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class GetTencentInfo {
	String sourceKey;
	String headurl;
	DefaultHttpClient client;
	HttpGet get;
	HttpResponse response;
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> qqmap = new HashMap<String, String>();
	String entity;
	String access_token;
	String openkey;
	String openid;
	String appkey="801252526";
	String appsecret="25c4c7782ca4ac65413ef022beb4843b";
	public GetTencentInfo(String username, String psd) {
		System.out.println("Begin!");
		client = new TencentClientLogin().login(username, psd);
		try {
			map=getloginInfo();
			access_token=map.get("access_token");
			openkey=map.get("openkey");
			openid=map.get("openid");
			headurl="http://open.t.qq.com/api/user/info?format=json,";
			qqmap.put("user_info",headurl+ "&oauth_consumer_key="+appkey+"&openid="+openid+"&access_token="+access_token+"&oauth_version="+"2.a");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//get basic login info
	public Map<String, String> getloginInfo() throws ClientProtocolException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		get = new HttpGet(
				"https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=801252526&response_type=token&redirect_uri=http://www.sarow.me");
		entity = execute(get);
		entity = entity
				.substring(
						entity.indexOf("https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=801252526&response_type=token"),
						entity.indexOf("checkType=showAuth") + 18);
		get = new HttpGet(entity);
		entity = execute(get);
		if(entity.indexOf("access_token")==-1){
			int l1=entity.indexOf("<input type=\"hidden\" name=\"g_tk\" value=\"");
			int l2=entity.indexOf("<input type=\"hidden\" name=\"sessionKey\" value=\"")+"<input type=\"hidden\" name=\"sessionKey\" value=\"".length()+36;
			entity=entity.substring(l1,l2);
			String pattern1 = "name=\"g_tk\".*?\" ";
			String pattern2 = "name=\"sessionKey\".*?\" ";
			String pattern3 = "(?<=value=\").\\w*";
			String g_tk;
			String sessionKey;
			g_tk=GetTencentInfo.match(pattern3,GetTencentInfo.match(pattern1, entity));
			System.out.println("g_tk:::"+g_tk);
			sessionKey=GetTencentInfo.match(pattern3,GetTencentInfo.match(pattern2, entity));
			System.out.println(g_tk+"======="+sessionKey);
			
			HttpPost post = new HttpPost("https://open.t.qq.com/cgi-bin/oauth2/authorize");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("client_id", appkey));
			nvps.add(new BasicNameValuePair("checkStatus", "checked"));
			nvps.add(new BasicNameValuePair("response_type", "token"));
			nvps.add(new BasicNameValuePair("checkType", "authorize"));
			nvps.add(new BasicNameValuePair("redirect_uri", "http://www.sarow.me"));
			nvps.add(new BasicNameValuePair("appfrom", ""));
			nvps.add(new BasicNameValuePair("g_tk", g_tk));
			nvps.add(new BasicNameValuePair("sessionKey",sessionKey));
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = client.execute(post);
			entity = EntityUtils.toString(response.getEntity());
			//System.out.println(entity);
			
		}
		entity = entity.substring(entity.indexOf("access_token"),
				entity.indexOf("&name="));
		for (String param : entity.split("&")) {
			map.put(param.split("=")[0], param.split("=")[1]);
		}
		return map;
	}
	public void getUserInfo(){
		get=new HttpGet(qqmap.get("user_info"));
		entity=execute(get);
		System.out.println(entity);
	}
	
	public String execute(HttpGet get) {
		String result = "";
		try {
			response = client.execute(get);
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String match(String pattern,String inputString){
		Pattern patterner1 = Pattern.compile(pattern);
		Matcher matcher = patterner1.matcher(inputString);
		if(matcher.find()){
			System.out.println(matcher.group());
			return (matcher.group());
		}
		return null;
	}
}

package crawl.sina;

import java.util.HashMap;
import java.util.Map;

public class APIFunction {
	Map<String, String> infomap ;
	public APIFunction() {
		infomap=new HashMap<String,String>();
	}
	public Map<String, String> InitialAPIs()
	{
		infomap.put("user_info", "https://api.weibo.com/2/users/show.json");
		infomap.put("user_friends_list",
				"https://api.weibo.com/2/friendships/friends.json");
		infomap.put("user_friends_id_list",
				"https://api.weibo.com/2/friendships/friends/ids.json");
		infomap.put("user_bilateral_list",
				"https://api.weibo.com/2/friendships/friends/bilateral.json");
		infomap.put("user_bilateral_id_list",
				"https://api.weibo.com/2/friendships/friends/bilateral/ids.json");
		infomap.put("user_followers",
				"https://api.weibo.com/2/friendships/followers.json");
		infomap.put("user_followers_id",
				"https://api.weibo.com/2/friendships/followers/ids.json");
		infomap.put("user_followers_active",
				"https://api.weibo.com/2/friendships/followers/active.json");
		infomap.put("user_friends_chain_followers",
				"https://api.weibo.com/2/friendships/friends_chain/followers.json");
		infomap.put("user_friendsship_show",
				"https://api.weibo.com/2/friendships/show.json");
		infomap.put("get_privacy",
				"https://api.weibo.com/2/account/get_privacy.json");
		// 获取用户微博信息
		
		infomap.put("user_timeline",
				"https://api.weibo.com/2/statuses/user_timeline.json");
		infomap.put("user_timeline_ids", "https://api.weibo.com/2/statuses/user_timeline/ids.json");
		infomap.put("repost_by_me",
				"https://api.weibo.com/2/statuses/repost_by_me.json");
		infomap.put("me_mentioned_news",
				"https://api.weibo.com/2/statuses/mentions.json");
		infomap.put("show_id", "https://api.weibo.com/2/statuses/show.json");
		return infomap;
	}
}

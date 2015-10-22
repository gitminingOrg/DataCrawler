package userInfoFetch;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import utility.HttpDeal;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class FetchUserPage {
	private static String FETCH_USER_URL = "https://api.github.com/users?since=";
	
	public void fetchUserPage(Channel channel,int start, int end, String queue_name) {
		int last_id = start;
		boolean effect = true;
		try {
			while(effect && last_id < end){
				String uri = FETCH_USER_URL+last_id;
				System.out.println(uri);
				String userJsons = HttpDeal.getResponse(uri);
				
				Gson gson = new Gson();
				JsonArray jsonArray = new JsonParser().parse(userJsons).getAsJsonArray();
				if (jsonArray.size() == 0) {
					effect = false;
				}else{
					for (JsonElement jsonElement : jsonArray) {
						int id = jsonElement.getAsJsonObject().get("id").getAsInt();
						String name = jsonElement.getAsJsonObject().get("login").getAsString();
						String infos = "user_info;"+jsonElement.getAsJsonObject().get("url").getAsString();
						String repos = "repo;"+jsonElement.getAsJsonObject().get("repos_url").getAsString();
						String followers = "follower;"+id+","+name + "," + jsonElement.getAsJsonObject().get("followers_url").getAsString();
						last_id = id;
						System.out.println(id);
						channel.basicPublish("", queue_name,MessageProperties.PERSISTENT_TEXT_PLAIN,infos.getBytes("UTF-8"));	
						channel.basicPublish("", queue_name,MessageProperties.PERSISTENT_TEXT_PLAIN,followers.getBytes("UTF-8"));
						//channel.basicPublish("", queue_name,MessageProperties.PERSISTENT_TEXT_PLAIN,repos.getBytes("UTF-8"));
					}				
				}
				//Thread.sleep(1000);
				
			
			}

		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

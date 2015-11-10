package userInfoFetch;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import utility.HttpDeal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FetchUserFollowers {
	/**
	 * get followers of a user and insert into mongo
	 * @param message id,name,url
	 * @return
	 */
	public List<Document> insertFollowers(String message){
		System.out.println("fetch followers");
		List<Document> result = new ArrayList<Document>();
		int page = 1;
		boolean effect = true; 
		try {
			//parse message
			String[] urlItems = message.split(",");
			String id = urlItems[0];
			String name = urlItems[1];
			String url = urlItems[2];
			
			while (effect) {
				String searchUrl = url+"?page="+page;
				System.out.println(searchUrl);
				page++;
				//get response str
				String followJson = HttpDeal.getResponse(searchUrl);

				//parse to json array
				JsonParser parser = new JsonParser();
				JsonArray followersArray = parser.parse(followJson).getAsJsonArray();
				
				if (followersArray.size() == 0) {
					effect = false;
				}else{
					//parse array , generate follower info (add user,id)
					for (JsonElement jsonElement : followersArray) {
						String follower = jsonElement.toString();
						
						Document document = Document.parse(follower);
						document.append("follows_id", id);
						document.append("follows_name", name);
						result.add(document);
					}							
				}
			}

			return result;
		} catch (Exception e) {
			return result;
		}
	}
}

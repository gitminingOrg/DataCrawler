package userInfoFetch;

import org.bson.Document;

import utility.HttpDeal;
import utility.MongoInfo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FetchUserFollowers {
	/**
	 * get followers of a user and insert into mongo
	 * @param message id,name,url
	 * @return
	 */
	public boolean insertFollowers(String message){
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
				page++;
				
				//get response str
				String followJson = HttpDeal.getResponse(searchUrl);

				//parse to json array
				JsonParser parser = new JsonParser();
				JsonArray followersArray = parser.parse(followJson).getAsJsonArray();
				
				if (followersArray.size() == 0) {
					effect = false;
				}else{
					//start mongo
					MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
					MongoDatabase db = mongoClient.getDatabase("testUser2");
					Gson gson = new Gson();
					MongoCollection<Document> collection = db.getCollection("follower");
					//parse array , generate follower info (add user,id)
					for (JsonElement jsonElement : followersArray) {
						String follower = jsonElement.toString();
						
						Document document = Document.parse(follower);
						document.append("follows_id", id);
						document.append("follows_name", name);
						collection.insertOne(document);
					}
					
					mongoClient.close();							
				}
		
				
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

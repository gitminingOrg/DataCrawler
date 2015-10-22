package userInfoFetch;

import org.bson.Document;

import utility.HttpDeal;
import utility.MongoInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FetchUserRepos {
	public boolean insertUserRepos(String message){
		int page = 1;
		boolean effect = true; 
		try {
			//parse message
			String url = message;
			
			while (effect) {
				//get auth account
				
				String searchUrl = url+"?page="+page;
				page++;
				
				//get response str
				String followJson = HttpDeal.getResponse(searchUrl);

				//parse to json array
				JsonParser parser = new JsonParser();
				JsonArray reposArray = parser.parse(followJson).getAsJsonArray();
				
				if (reposArray.size() == 0) {
					effect = false;
				}else{
					//start mongo
					MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
					MongoDatabase db = mongoClient.getDatabase("testUser2");

					MongoCollection<Document> collection = db.getCollection("userRepo");
					//parse array , generate follower info (add user,id)
					for (JsonElement jsonElement : reposArray) {
						String userRepo = jsonElement.toString();						
						Document document = Document.parse(userRepo);
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

package userInfoFetch;

import org.bson.Document;

import utility.HttpDeal;
import utility.MongoInfo;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FetchUserInfo {
	/**
	 * fetch user info insert into mongo
	 * 
	 * @param message
	 *            user_url
	 * @return
	 */
	public boolean fecthUserInfo(String message) {
		try {

			String url = message;

			String userJson = HttpDeal.getResponse(url);
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(userJson);
			//start mongo
			MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
			MongoDatabase db = mongoClient.getDatabase("testUser2");
			if (element.getAsJsonObject().get("id").getAsInt() > 0) {
				MongoCollection<Document> collection = db.getCollection("user");
				Document document = Document.parse(userJson);
				collection.insertOne(document);
			}
			mongoClient.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}

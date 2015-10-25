package historyDownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;

import org.bson.Document;

import utility.MongoInfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class HistoryFilter {
	private static HashSet<Integer> repoIdSet = new HashSet<Integer>();
	public static void init(){
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
		MongoDatabase db = mongoClient.getDatabase("ghcrawler");
		FindIterable<Document> exist = db.getCollection("repolist").find();
		for (Document document : exist) {
			String json = document.toJson();
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			int repo_id = element.getAsJsonObject().get("id").getAsInt();
			repoIdSet.add(repo_id);
		}
		mongoClient.close();
	}
	
	public static void hashFilter(File file){
		try{
			MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
			MongoDatabase db = mongoClient.getDatabase("historyevents");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				JsonParser parser = new JsonParser();
				JsonObject event = parser.parse(line).getAsJsonObject();
				int repoId = 0;
				if(event.has("repo")){
					repoId = event.get("repo").getAsJsonObject().get("id").getAsInt();
				}else if(event.has("repository")){
					repoId = event.get("repository").getAsJsonObject().get("id").getAsInt();
				}
				
				if(repoId!=0 && repoIdSet.contains(repoId)){
					db.getCollection("spec_events").insertOne(Document.parse(line));
				}
			}
			br.close();
			fr.close();
			mongoClient.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args){
		File file = new File("/Users/owenchen/Documents/2014-12-30-0.json");
		init();
		hashFilter(file);
	}
}

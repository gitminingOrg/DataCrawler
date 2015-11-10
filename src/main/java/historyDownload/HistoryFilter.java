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
	
	public static boolean validate(String hour){
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
		MongoDatabase db = mongoClient.getDatabase("historyevents");
		FindIterable<Document> iterable = db.getCollection("hour").find(new Document("hour", hour));
		if (iterable.first() != null) {
			System.out.println(hour + " exists!");
			mongoClient.close();
			return false;
		}
		mongoClient.close();
		return true;
	}
	
	public static void hashFilter(File file,String hour){
		try{
			MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
			MongoDatabase db = mongoClient.getDatabase("historyevents");
			
			Document document = new Document();
			document.append("hour", hour);
			document.append("finish", false);
			db.getCollection("hour").insertOne(document);
			
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
			db.getCollection("hour").updateOne(new Document("hour",hour),new Document("$set",new Document("finish","true")));
			br.close();
			fr.close();
			mongoClient.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
}

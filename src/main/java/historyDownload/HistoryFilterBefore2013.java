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

public class HistoryFilterBefore2013 {
	private static HashSet<String> repoSet = new HashSet<String>();
	public static void init(){
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
		MongoDatabase db = mongoClient.getDatabase("NewProject");
		FindIterable<Document> exist = db.getCollection("repo").find();
		for (Document document : exist) {
			String json = document.toJson();
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			String repo = element.getAsJsonObject().get("full_name").getAsString();
			repoSet.add(repo);
		}
		System.out.println(repoSet.size()+"repos to filter");
		mongoClient.close();
	}
	
	public static boolean validate(String hour){
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
		MongoDatabase db = mongoClient.getDatabase("historyevents2");
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
			MongoDatabase db = mongoClient.getDatabase("historyevents2");
			
			Document document = new Document();
			document.append("hour", hour);
			document.append("finish", false);
			db.getCollection("hour").insertOne(document);
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			boolean quote = false;
			int level = 0;
			int start =0;
			while ((line = br.readLine()) != null) {
				for(int i=0; i< line.length(); i++){
					if(line.charAt(i)=='"'){
						quote = quote?false:true;
					}
					if(!quote){
						if(line.charAt(i)=='{'){
							level++;
						}
						if(line.charAt(i)=='}'){
							level--;
						}						
					}

					if(level == 0){
						String jsonString = line.substring(start, i+1);
						start=i+1;
						JsonParser parser = new JsonParser();
						JsonObject event = parser.parse(jsonString).getAsJsonObject();
						String full_name = null;
						if(event.has("repo")){
//							String eventString = event.toString();
//							String id = eventString.split(":")[3].split(",")[0];
//							repoId = Integer.parseInt(id);
							System.out.println(event.get("repo").toString());
							String owner = event.get("repo").getAsJsonObject().get("owner").getAsString();
							String name = event.get("repo").getAsJsonObject().get("name").getAsString();
							full_name = owner+"/"+name;
						}else if(event.has("repository")){
							String owner = event.get("repository").getAsJsonObject().get("owner").getAsString();
							String name = event.get("repository").getAsJsonObject().get("name").getAsString();
							full_name = owner+"/"+name;
						}
						
						if(full_name!=null && repoSet.contains(full_name)){
							db.getCollection("spec_events").insertOne(Document.parse(jsonString).append("full_name", full_name));
						}
					}
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

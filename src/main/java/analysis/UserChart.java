package analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.bson.Document;

import utility.MongoInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class UserChart {
	public static void main(String[] args){
		try{
			MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),27017);
			MongoDatabase database = mongoClient.getDatabase("testUser2");
			FindIterable<Document> doIterable = database.getCollection("user").find();
			
//			File file = new File("userData.txt");
//			FileWriter fw = new FileWriter(file,true);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write("[");
			boolean start = false;
			for (Document document : doIterable) {
				String item = "";
				JsonParser parser = new JsonParser();
				JsonObject json = parser.parse(document.toJson()).getAsJsonObject();
				int repo_count = json.get("public_repos").getAsInt();
				int follow_count = json.get("followers").getAsInt();
				String login = json.get("login").getAsString();
				String type = json.get("type").getAsString();
				int id = json.get("id").getAsInt();
				if(start){
					item = ",";
				}
				item +="["+repo_count+","+follow_count+"]";
				if(repo_count != 0 && follow_count != 0){
//					bw.write(item);
					start=true;
				}
				
				if(follow_count > 1000){
					System.out.println(login + "\t" + id + "\t" + follow_count + "\t" + repo_count+"\t"+type);
				}
				
			}
			
//			bw.write("]");
//			bw.flush();
//			bw.close();
//			fw.close();
			mongoClient.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

package userInfoFetch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

import utility.MongoInfo;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;

public class TestUser {
	private static Log log = LogFactory.getLog(TestUser.class.getName());
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),27017);
		MongoDatabase database = mongoClient.getDatabase("ttttest");
		List<Document> documents = new ArrayList<Document>();
		for(int i=0 ;i<10000; i++){
			Document document = new Document();
			document.append("id", i);
			document.append("value", i+"papapa");
			System.out.println(i);
			documents.add(document);
		}
		database.getCollection("doTest").insertMany(documents);
		System.out.println("end");
		mongoClient.close();
	}

}

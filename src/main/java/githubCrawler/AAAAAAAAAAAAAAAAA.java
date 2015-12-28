package githubCrawler;

import utility.MessageSender;
import utility.MongoInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCallback;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class AAAAAAAAAAAAAAAAA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		/*DBCollection cache = db.getCollection("WIN-8IEVE9HL0DJcommitscache");
		DBCollection commit = db.getCollection("commitsbyapi");
		DBCursor cursor = cache.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		
		while(cursor.hasNext()){
			commit.save(cursor.next());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/
		DBCollection repo = db.getCollection("repository");
		DBCollection commitNumber = db.getCollection("commitnumberbyapi");
		DBCursor cursor = repo.find();
		DBCursor cursor2 = commitNumber.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		int count = 0;
		
		while(cursor.hasNext()){
			DBObject object = new BasicDBObject();
			object.put("fn", cursor.next().get("full_name").toString());
			if(commitNumber.find(object).size() == 0){
				MessageSender sender = new MessageSender();
				sender.sendMessage(object.get("fn").toString());
				count ++;
			}
		}
		System.out.println(count);
	}

}

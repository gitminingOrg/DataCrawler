package utility;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class ValidateMongoConnection {
	
	public static int validateMongoConnection(){
		try {
			Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
			DB db = mongo.getDB("ghcrawler");
			DBCollection repository = db.getCollection("repository");
			DBCursor repoCursor = repository.find();
			return repoCursor.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return -1;
		}
	}
}

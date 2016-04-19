package hehe;

import org.bson.Document;

import userInfoFetch.UserDeal;
import utility.GetHostName;
import utility.MongoInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.client.MongoCollection;

public class Test {
	public static void main(String[] args){
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		
		DBCollection oldCollection = db.getCollection("assignees");
		DBCollection user = db.getCollection("user");
		int index = 0;
		
		DBCursor cursor = oldCollection.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		while(cursor.hasNext()){
			DBObject object = cursor.next();
			String login = object.get("login").toString();
			//System.out.println(reponame);
			DBObject temp = new BasicDBObject();
			temp.put("login", login);
			index ++;
			if(user.find(temp).size() == 1){
				//System.out.print(index + " ");
			}else{
				System.err.println(login);
			}
		}
	}
}
/*owenchen
19930301owenchen*/

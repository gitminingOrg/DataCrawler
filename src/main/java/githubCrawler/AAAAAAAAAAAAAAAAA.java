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
		/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection cache = db.getCollection("WIN-8IEVE9HL0DJcommitscache");
		DBCollection commit = db.getCollection("commitsbyapi");
		DBCursor cursor = cache.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		
		while(cursor.hasNext()){
			commit.save(cursor.next());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/
		
		/*DBCollection repo = db.getCollection("repository");
		DBCollection commitNumber = db.getCollection("commitnumberbyapi");
		DBCursor cursor = repo.find();
		DBCursor cursor2 = commitNumber.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		cursor2.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);*/
		/*int count = 0;
		
		while(cursor.hasNext()){
			DBObject object = new BasicDBObject();
			object.put("fn", cursor.next().get("full_name").toString());
			if(commitNumber.find(object).size() == 0){
				MessageSender sender = new MessageSender();
				sender.sendMessage(object.get("fn").toString());
				count ++;
			}
		}
		System.out.println(count);*/
		
		/*while(cursor2.hasNext()){
			DBObject object = cursor2.next();
			DBObject object2 = new BasicDBObject();
			object2.put("fn", object.get("fn").toString());
			
			if(commitNumber.find(object2).size() > 1){
				System.out.println(object.get("fn"));
			}
		}*/
		
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("Experiment");
		DBCollection pulls = db.getCollection("pulls");
		DBCursor cursor = pulls.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		PullCommitCrawler pullCommitCrawler = new PullCommitCrawler();
		int judge = 0;
		
		/*while(cursor.hasNext()){
			DBObject object = cursor.next();
			if(object.get("commits_url").toString().equals("https://api.github.com/repos/ControlSystemStudio/cs-studio/pulls/486/commits")){
				judge = 1;
			}
			if(judge == 1){
				pullCommitCrawler.crawlCommits(object.get("commits_url").toString());
			}
		}*/
		pullCommitCrawler.crawlCommits("https://api.github.com/repos/openmicroscopy/openmicroscopy/pulls/89/commits");
	}

}

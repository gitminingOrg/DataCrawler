package githubCrawler;

import utility.GetHostName;
import utility.MongoInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class CrawlPullAndIssue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection issues = db.getCollection("issuescp");
		DBCollection pulls = db.getCollection("pullscp");
		DBCollection issuecache = db.getCollection(GetHostName.getHostName() + "issuecache");
		DBCollection pullcache = db.getCollection(GetHostName.getHostName() + "pullcache");
		DBCollection repository = db.getCollection("repository");
		DBCursor cursor = repository.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		
		while(cursor.hasNext()){
			DBObject object = cursor.next();
			DBObject object2 = new BasicDBObject();
			object2.put("fn", object.get("full_name").toString());
			if(issues.find(object2).limit(1).size() == 0 && pulls.find(object2).limit(1).size() == 0){
				System.out.println(object.get("full_name").toString() + "---------------------------------------");
				
				issuecache.drop();
				pullcache.drop();
				
				IssueCrawler issueCrawler = new IssueCrawler();
				PullCrawler pullCrawler = new PullCrawler();
				
				issueCrawler.crawlIssues(object.get("full_name").toString());
				pullCrawler.crawlPulls(object.get("full_name").toString());
				
				System.out.println("start saving---------------------------");
				
				DBCursor issuecursor = issuecache.find();
				issuecursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
				while (issuecursor.hasNext()) {
					issues.save(issuecursor.next());
				}
				issuecursor.close();
				
				DBCursor pullcursor = pullcache.find();
				pullcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
				while (pullcursor.hasNext()) {
					pulls.save(pullcursor.next());
				}
				pullcursor.close();
				
				System.out.println("complete-------------------------------------------");
			}
		}
		
	}

}

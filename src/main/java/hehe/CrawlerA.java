package hehe;

import utility.MongoInfo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class CrawlerA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] repo = {"rails/rails","vslavik/poedit"};
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection pullcache = db.getCollection("pullcacheA");
		DBCollection issuecache = db.getCollection("issuecacheA");
		DBCollection pulls = db.getCollection("pullscp");
		DBCollection issues = db.getCollection("issuescp");
		
		for(int i = 0 ; i < repo.length ; i ++){
			pullcache.drop();
			issuecache.drop();
			
			PullCrawlerA pullCrawler = new PullCrawlerA();
			IssueCrawlerA issueCrawler = new IssueCrawlerA();
			issueCrawler.crawlIssues(repo[i]);
			pullCrawler.crawlPulls(repo[i]);
			
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
		}
	}

}

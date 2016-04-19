package hehe;

import utility.MongoInfo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class CrawlerB {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] repo = {"c9s/App-gh","joshsh/sesametools","jbr/sibilant","r1k0/kigen"};//"pouchdb/pouchdb"
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection pullcache = db.getCollection("pullcacheB");
		DBCollection issuecache = db.getCollection("issuecacheB");
		DBCollection pulls = db.getCollection("pullscp");
		DBCollection issues = db.getCollection("issuescp");
		
		for(int i = 0 ; i < repo.length ; i ++){
			pullcache.drop();
			issuecache.drop();
			
			PullCrawlerB pullCrawler = new PullCrawlerB();
			IssueCrawlerB issueCrawler = new IssueCrawlerB();
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

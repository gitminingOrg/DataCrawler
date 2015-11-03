package githubCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

import utility.GetAuthorization;
import utility.GetURLConnection;
import utility.MongoInfo;
import utility.ValidateInternetConnection;

public class TTTTTTTTTTTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*IssueCrawler issueCrawler = new IssueCrawler();
		System.out.println(issueCrawler.crawlIssues("Komodo/KomodoEdit").size());*/
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlV1.0");
		DBCollection commitscache = db.getCollection("commitscache");
		
		commitscache.drop();
		commitscache = db.getCollection("commitscache");
		
		/*TreeCrawler treeCrawler = new TreeCrawler();
		System.out.println(treeCrawler.crawlTree("charliesome/jsos").toString());*/
		
		/*CommitCrawler commitCrawler = new CommitCrawler();
		commitCrawler.crawlCommits("gitminingOrg/DataCrawler");*/
		
		/*DBCursor cursor = commitscache.find();
		while(cursor.hasNext()){
			System.out.println(cursor.next().get("commit").toString());
		}*/

		GitCrawler gitCrawler = new GitCrawler();
		gitCrawler.crawl("gitminingOrg/DataCrawler");
	}

}

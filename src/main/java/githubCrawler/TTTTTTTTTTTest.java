package githubCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;

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
		/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV1");
		DBCollection repository = db.getCollection("repository");
		DBCollection language = db.getCollection("languages");
		
		DBCursor cursor = repository.find();
		while(cursor.hasNext()){
			LanguageCrawler languageCrawler = new LanguageCrawler();
			DBObject object = languageCrawler.crawlLanguages(cursor.next().get("full_name").toString());
			if(object != null){
			language.save(object);
			}
		}*/
		
		//commitscache.drop();
		
		/*DBObject object = new BasicDBObject();
		object.put("sda", "sda");
		System.out.println(commitscache.find(object).count());*/
		/*TreeCrawler treeCrawler = new TreeCrawler();
		System.out.println(treeCrawler.crawlTree("charliesome/jsos").toString());*/
		
		/*CommitCrawler commitCrawler = new CommitCrawler();
		commitCrawler.crawlCommits("gitminingOrg/DataCrawler");*/
		
		/*DBCursor cursor = commitscache.find();
		while(cursor.hasNext()){
			System.out.println(cursor.next().get("commit").toString());
		}*/

		/*GitCrawler gitCrawler = new GitCrawler();
		gitCrawler.crawl("gitminingOrg/DataCrawler");*/
		
		
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection("https://github.com/mbostock/d3/pulls");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
			String response = "";
			String result = "";
			while((response = reader.readLine()) != null){
				result = result + response;
			}
			//System.out.println(result);
			if(result.contains("<span class=\"octicon octicon-check\"></span>")){
				String str = result.split("<span class=\"octicon octicon-git-pull-request\"></span>")[2].split(" Open")[0].replace(" ", "");
				if(str.contains(",")){
					str = str.replace(",", "");
				}
				System.out.println("open:" + str);
				System.out.println("closed:" + result.split("<span class=\"octicon octicon-check\"></span>")[1].split(" Closed")[0].replace(" ", ""));
			}else{
				System.out.println("open:0");
				System.out.println("closed:0");
			}
			
			//System.out.println(result.split("<span class=\"octicon octicon-check\"></span>")[1].split(" Closed")[0].replace(" ", ""));
			//System.out.println(result.contains("<span class=\"octicon octicon-check\"></span>"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV1");
		DBCollection test = db.getCollection("test");
		DBObject object = test.find().next();
		DBObject object2 = new BasicDBObject();
		DBObject object3 = new BasicDBObject();
		object2.put("id", 20000);
		object3.put("$set", object2);
		
		test.update(object, object3);*/
	}

}

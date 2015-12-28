package githubCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCallback;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

import utility.GetAuthorization;
import utility.GetURLConnection;
import utility.MessageSender;
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
		
		
		/*HttpURLConnection urlConnection = GetURLConnection.getUrlConnection("https://github.com/mbostock/d3/pulls");
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
		}*/
		
		/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV1");
		DBCollection test = db.getCollection("test");
		DBObject object = test.find().next();
		DBObject object2 = new BasicDBObject();
		DBObject object3 = new BasicDBObject();
		object2.put("id", 20000);
		object3.put("$set", object2);
		
		test.update(object, object3);*/
		
		/*DownloadRepository downloadRepository = new DownloadRepository();
		downloadRepository.downloadRepository("moment/moment");*/
		/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ttttest");
		DBCollection abc = db.getCollection("abc");
		DBObject commit = new BasicDBObject();
		ArrayList<DBObject> files = new ArrayList<DBObject>();
		String message = "";
		int temp = 0;
		try {
			Process process = Runtime.getRuntime().exec("git log --stat",null,new File("H:\\GitRepo\\highcharts"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"utf-8"));
			String str = "";
			while((str = reader.readLine()) != null){
				if(str.startsWith("commit")){
					if(temp != 0){
						if(files.size() > 0){
							commit.put("files", files);
						}
						abc.save(commit);
					}
					temp ++;
					message = "";
					files.clear();
					commit = new BasicDBObject();
					commit.put("sha", str.split("commit ")[1]);
				}else if(str.startsWith("Merge")){
					commit.put("merge", str.split("Merge: ")[1]);
				}else if(str.startsWith("Author")){
					DBObject committer = new BasicDBObject();
					committer.put("name", str.split("Author: ")[1].split(" <")[0]);
					committer.put("email", str.split("<")[1].split(">")[0]);
					commit.put("committer", committer);
				}else if(str.startsWith("Date")){
					commit.put("Date", str.split("Date:   ")[1]);
				}else if(str == ""){
			
				}else if(str.contains("|") && (str.endsWith("+") || str.endsWith("-"))){
					DBObject file = new BasicDBObject();
					System.out.println(str.split(" ").length);
					file.put("filename", str.split(" ")[1]);
					if(str.contains("+") && str.endsWith("-")){
						file.put("status", "modified");
					}else if (str.endsWith("+")) {
						file.put("status", "added");
					}else if (str.endsWith("-")) {
						file.put("status", "removed");
					}
					file.put("changes", str.split(" ")[str.split(" ").length - 2]);
					files.add(file);
				}else if (str.contains("|") && str.endsWith("bytes")) {
					DBObject file = new BasicDBObject();
					file.put("filename", str.split(" ")[1]);
					int start = Integer.parseInt(str.split(" -> ")[0].split("Bin ")[1]);
					int end = Integer.parseInt(str.split(" -> ")[1].split(" bytes")[0]);
					if(start == 0 && end > 0){
						file.put("status", "added");
					}else if(end == 0 && start > 0){
						file.put("status", "removed");
					}else{
						file.put("status", "modified");
					}
					file.put("changes", 0);
					files.add(file);
				}else if(str.contains("file changed,") || str.contains("files changed,")){
					DBObject stats = new BasicDBObject();
					stats.put("filenumber", str.split(" file")[0].split(" ")[1]);
					if(str.contains("+") && str.contains("-")){
						stats.put("additions", str.split(", ")[1].split(" insertion")[0]);
						stats.put("deletions", str.split(", ")[2].split(" deletion")[0]);
						stats.put("total", (Integer.parseInt(str.split(", ")[1].split(" insertion")[0]) + Integer.parseInt(str.split(", ")[2].split(" deletion")[0]))+"");
					}else if(str.contains("+") && !str.contains("-")){
						stats.put("additions", str.split(", ")[1].split(" insertion")[0]);
						stats.put("deletions", "0");
						stats.put("total", str.split(", ")[1].split(" insertion")[0]);
					}else{
						System.out.println(str);
						stats.put("additions", "0");
						stats.put("deletions", str.split(", ")[1].split(" deletion")[0]);
						stats.put("total", str.split(", ")[1].split(" deletion")[0]);
					}
					commit.put("stats", stats);
				}else{
					message = message + str.replace("    ", "") + " ";
					commit.put("message", message);
				}
			}
			commit.put("message", message);
			if(files.size() > 0){
				commit.put("files", files);
			}
			abc.save(commit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ttttest");
		DBCollection abc = db.getCollection("abc");
		ArrayList<DBObject> files = new ArrayList<DBObject>();
		DBObject object1 = new BasicDBObject();
		object1.put("value", "");
		DBObject object2 = new BasicDBObject();
		object2.put("value", "456");
		files.add(object1);
		files.add(object2);
		
		DBObject object3 = new BasicDBObject();
		object3.put("files", files);
		abc.save(object3);*/
		
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection("https://github.com/checkstyle/checkstyle");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
			String response = "";
			String result = "";
			
			while((response = reader.readLine()) != null){
				result = result + response;
			}
			
			//System.out.println(result.split(("<span class=\"num text-emphasized\">"))[4].split("</span>")[0]);
			System.out.println(result.split("<div class=\"repository-lang-stats-graph js-toggle-lang-stats\" title=\"Click for language details\">")[1].split("</div>")[0].split("aria-label=\"Java ")[1].split("\" ")[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

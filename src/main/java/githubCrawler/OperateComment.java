package githubCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import utility.GetURLConnection;
import utility.MongoInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class OperateComment {
	public static void main(String[] args){
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection repo = db.getCollection("repo");
		DBCollection comment = db.getCollection("comment");
		DBCollection commentcondition = db.getCollection("commentcondition");
		DBCursor cursor = repo.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		CommentCrawler commentCrawler = new CommentCrawler();
		Boolean judge = false;
		
		//crawl the comment first.
		/*while(cursor.hasNext()){
			DBObject repository = cursor.next();
			if(repository.get("full_name").toString().equals("rust-lang/rust")){
				judge = true;
			}
			
			if(judge && !repository.get("full_name").toString().equals("rust-lang/rust")){
				ArrayList<DBObject> commentArray = commentCrawler.crawlComments(cursor.next().get("full_name").toString());
				for(int i = 0; i < commentArray.size() ; i ++){
					comment.save(commentArray.get(i));
				}
			}
			ArrayList<DBObject> commentArray = commentCrawler.crawlComments(cursor.next().get("full_name").toString());
			comment.insert(commentArray);
		}*/
		
		while(cursor.hasNext()){
			DBObject cc = new BasicDBObject();
			DBObject repository = cursor.next();
			
			HttpURLConnection urlConnection = GetURLConnection.getUrlConnection("https://github.com/" + repository.get("full_name").toString());
			try {
				/*get the commit number from the webpage*/
				BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
				String response = "";
				String result = "";
				while((response = reader.readLine()) != null){
					result = result + response;
				}
				String str = result.split("<span class=\"num text-emphasized\">")[1].split("</span>")[0].replace(" ", "");
				if(str.contains(",")){
					str = str.replace(",", "");
				}
				cc.put("commitNumber", Integer.parseInt(str));
				
				DBObject commenttt = new BasicDBObject();
				int count = 0;
				
				/*count the comment number of each repo*/
				commenttt.put("fn", repository.get("full_name").toString());
				DBCursor cursor2 = comment.find(commenttt);
				cc.put("commentNumber", cursor2.size());
				
				/*count the comment number by line of each repo*/
				while(cursor2.hasNext()){
					if(cursor2.next().get("position") != null){
						count ++;
					}
				}
				cc.put("commentByline", count);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			cc.put("fn", repository.get("full_name").toString());
			System.out.println(repository.get("full_name").toString());
			commentcondition.save(cc);
		}
	}
}

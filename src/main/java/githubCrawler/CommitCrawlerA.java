package githubCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import utility.GetHostName;
import utility.GetURLConnection;
import utility.MongoInfo;
import utility.ValidateInternetConnection;
import utility.ValidateMongoConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class CommitCrawlerA {
	private Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
	private DB db = mongo.getDB("ghcrawlerV3");
	private DBCollection commitscache = db.getCollection(GetHostName.getHostName() + "commitscache");
	//private DBCollection commitscache = db.getCollection("pullcommits");
	public static int commitNumber = 0;
	public static int commitNumberByAPI = 0;

	public void crawlCommits(String fullName){
		System.out.println("Start crawl commits------------------------");
		int index = 1;
		String commitsURL = "https://api.github.com/repos/" + fullName + "/commits?page=";
		//String commitsURL = fullName + "?page=";
		//int pn = Integer.parseInt(fullName.split("pulls/")[1].split("/")[0]);
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(commitsURL + index);
		BufferedReader reader = null;
		String response = "";
		int responseCode = 200;
		ArrayList<DBObject> commitsArray = new ArrayList<DBObject>();
		
		try {
			responseCode = urlConnection.getResponseCode();
		} catch (Exception e) {
			// TODO: handle exception
			while(ValidateInternetConnection.validateInternetConnection() == 0){
				System.out.println("Wait for connecting the internet---------------");
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println("The internet is connected------------");
			
			urlConnection = GetURLConnection.getUrlConnection(commitsURL + index);
			try {
				responseCode = urlConnection.getResponseCode();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(responseCode == 200){
			try {
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
				response = reader.readLine();
			} catch (Exception e) {
				// TODO: handle exception
				while(ValidateInternetConnection.validateInternetConnection() == 0){
					System.out.println("Wait for connecting the internet---------------");
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("The internet is connected------------");
				try{
					urlConnection = GetURLConnection.getUrlConnection(commitsURL + index);
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			while (response != null && !response.equals("[]")){
				try{
					System.out.println(commitsURL + index);
					JsonArray jsonArray = new JsonParser().parse(response)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						String concreteURL = jsonArray.get(i).getAsJsonObject()
								.get("url").toString();
						System.out.println(concreteURL);
						urlConnection = GetURLConnection.getUrlConnection(concreteURL.substring(1,concreteURL.length() - 1));
						
						try {
							reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
							response = reader.readLine();
						} catch (Exception e) {
							// TODO: handle exception
							while(ValidateInternetConnection.validateInternetConnection() == 0){
								System.out.println("Wait for connecting the internet---------------");
								try {
									Thread.sleep(30000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							System.out.println("The internet is connected------------");
							try{
								urlConnection = GetURLConnection.getUrlConnection(concreteURL.substring(1,concreteURL.length() - 1));
								reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
								response = reader.readLine();
							}catch(Exception e2){
								e2.printStackTrace();
							}
						}
						
						try {
							DBObject object = (BasicDBObject) JSON.parse(response);
							object.put("fn", fullName);
							//object.put("pn", pn);
							commitsArray.add(object);
							commitNumberByAPI ++;
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("can not translate it to json----------------------------");
						}
					}
					try{
						commitscache.insert(commitsArray);
					}catch(Exception e){
						while(ValidateInternetConnection.validateInternetConnection() == 0){
							System.out.println("Wait for connecting the internet---------------");
							try {
								Thread.sleep(30000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						System.out.println("The internet is connected------------");
						
						while(ValidateMongoConnection.validateMongoConnection() <= 0){
							System.out.println("Wait for connecting the mongo---------------");
							try {
								Thread.sleep(30000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						System.out.println("The mongo is connected------------");
						commitscache.insert(commitsArray);
					}finally{
						commitsArray.clear();
					}
				}catch(Exception e){
					System.out.println("can not translate it to json----------------------------");
				}
				
				index = index + 1;
				urlConnection = GetURLConnection.getUrlConnection(commitsURL + index);
				
				try {
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
				} catch (Exception e) {
					// TODO: handle exception
					while(ValidateInternetConnection.validateInternetConnection() == 0){
						System.out.println("Wait for connecting the internet---------------");
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					System.out.println("The internet is connected------------");
					try{
						urlConnection = GetURLConnection.getUrlConnection(commitsURL + index);
						reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
						response = reader.readLine();
					}catch(Exception e2){
						e2.printStackTrace();
					}
				}
			}
		}
	}
	
	public void crawlCommitsByLog(String fullName){
		System.out.println(fullName);
		DBObject commit = new BasicDBObject();
		ArrayList<DBObject> files = new ArrayList<DBObject>();
		String message = "";
		int temp = 0;
		try {
			String string = fullName.split("/")[0] + "@" + fullName.split("/")[1];
			Process process = Runtime.getRuntime().exec("git log --stat",null,new File("H:\\GitRepo\\" + string));
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"utf-8"));
			String str = "";
			while((str = reader.readLine()) != null){
				if(str.startsWith("commit")){
					if(temp != 0){
						if(files.size() > 0){
							commit.put("files", files);
						}
						commit.put("fn", fullName);
						commitscache.save(commit);
						commitNumber ++;
					}
					temp ++;
					message = "";
					files.clear();
					commit = new BasicDBObject();
					commit.put("sha", str.split("commit ")[1]);
				}else if(str.startsWith("Merge")){
					commit.put("merge", str.split("Merge: ")[1]);
				}else if(str.startsWith("Author")){
					System.out.println(str);
					DBObject committer = new BasicDBObject();
					committer.put("name", str.split("Author: ")[1].split(" <")[0]);
					if(str.split("<")[1].startsWith(">")){
						committer.put("email", "");
					}else{
						committer.put("email", str.split("<")[1].split(">")[0]);
					}
					commit.put("committer", committer);
				}else if(str.startsWith("Date")){
					commit.put("Date", str.split("Date:   ")[1]);
				}else if(str == ""){
			
				}else if(str.contains("|") && (str.endsWith("+") || str.endsWith("-"))){
					DBObject file = new BasicDBObject();
					//System.out.println(str.split(" ").length);
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
			commit.put("fn", fullName);
			commitscache.save(commit);
			commitNumber ++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

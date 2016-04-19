package githubCrawler;

import java.io.BufferedReader;
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
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class PullCommitCrawler {
	private Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
	private DB db = mongo.getDB("Experiment");
	private DBCollection commitscache = db.getCollection(GetHostName.getHostName() + "pullcommitscache");
	private DBCollection commits = db.getCollection("pullcommits");
	//private DBCollection commitscache = db.getCollection("pullcommits");

	//https://api.github.com/repos/guardianproject/ChatSecureAndroid/pulls/712/commits
	public void crawlCommits(String pullURL){
		System.out.println("Start crawl commits------------------------");
		commitscache.drop();
		int index = 1;
		//String commitsURL = "https://api.github.com/repos/" + fullName + "/commits?page=";
		String commitsURL = pullURL + "?page=";
		int pn = Integer.parseInt(pullURL.split("pulls/")[1].split("/")[0]);
		String fn = pullURL.split("repos/")[1].split("/pulls")[0];
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
							object.put("fn", fn);
							object.put("pn", pn);
							commitsArray.add(object);
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
		
		DBCursor cursor = commitscache.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		while (cursor.hasNext()) {
			commits.save(cursor.next());
		}
		cursor.close();
	}
}

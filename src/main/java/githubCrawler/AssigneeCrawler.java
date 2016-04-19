package githubCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import userInfoFetch.UserDeal;
import utility.GetAuthorization;
import utility.GetHostName;
import utility.GetURLConnection;
import utility.MongoInfo;
import utility.ValidateInternetConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class AssigneeCrawler {
	
	public void crawlAssignees(String fullName){
		System.out.println("Start crawl assignees------------------------");
		int index = 1;
		String assigneesURL = "https://api.github.com/repos/" + fullName + "/assignees?page=";
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(assigneesURL + index);
		BufferedReader reader = null;
		String response = "";
		int responseCode = 200;
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("Experiment");
		DBCollection assigneecache = db.getCollection(GetHostName.getHostName() + "assigneecache");
		
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
			
			urlConnection = GetURLConnection.getUrlConnection(assigneesURL + index);
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
					urlConnection = GetURLConnection.getUrlConnection(assigneesURL + index);
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			while (response != null && !response.equals("[]")){
				try{
					JsonArray jsonArray = new JsonParser().parse(response)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						assigneecache.save(object);
						
						UserDeal.fetchUser(object.get("login").toString(), Integer.parseInt(object.get("id").toString()));
					}
				}catch(Exception e){
					System.out.println("can not translate it to json----------------------------");
				}
				
				System.out.println(assigneesURL + index);
				index = index + 1;
				urlConnection = GetURLConnection.getUrlConnection(assigneesURL + index);				
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
						urlConnection = GetURLConnection.getUrlConnection(assigneesURL + index);
						reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
						response = reader.readLine();
					}catch(Exception e2){
						e2.printStackTrace();
					}
				}
			}
		}
		
	}

}

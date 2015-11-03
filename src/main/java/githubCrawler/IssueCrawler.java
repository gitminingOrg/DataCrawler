package githubCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import utility.GetAuthorization;
import utility.GetURLConnection;
import utility.ValidateInternetConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class IssueCrawler {

	public ArrayList<DBObject> crawlIssues(String fullName){
		System.out.println("Start crawl issues------------------------");
		int index = 1;
		String issuesURL = "https://api.github.com/repos/" + fullName + "/issues?page=";
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(issuesURL + index);
		BufferedReader reader = null;
		String response = "";
		int responseCode = 200;
		ArrayList<DBObject> issuesArray = new ArrayList<DBObject>();
		
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
			
			urlConnection = GetURLConnection.getUrlConnection(issuesURL + index);
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
					urlConnection = GetURLConnection.getUrlConnection(issuesURL + index);
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
						String concreteURL = jsonArray.get(i).getAsJsonObject()
								.get("url").toString();
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
							issuesArray.add(object);
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("can not translate it to json----------------------------");
						}
						
					}
				}catch(Exception e){
					System.out.println("can not translate it to json----------------------------");
				}
				
				System.out.println(issuesURL + index);
				index = index + 1;
				urlConnection = GetURLConnection.getUrlConnection(issuesURL + index);
				
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
						urlConnection = GetURLConnection.getUrlConnection(issuesURL + index);
						reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
						response = reader.readLine();
					}catch(Exception e2){
						e2.printStackTrace();
					}
				}
			}
		}
		
		return issuesArray;
	}
}

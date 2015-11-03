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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class TreeCrawler {
	private static String treeStructure = "";
	private static int fileNumber = 0;
	private static int repoSize = 0;
	
	public DBObject crawlTree(String fullName){
		System.out.println("Start crawl tree------------------------");
		String treeURL = "https://api.github.com/repos/" + fullName + "/contents";
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(treeURL);
		BufferedReader reader = null;
		String response = "";
		int responseCode = 200;
		DBObject tree = null;
		
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
			
			urlConnection = GetURLConnection.getUrlConnection(treeURL);
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
					urlConnection = GetURLConnection.getUrlConnection(treeURL);
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			if (response != null && !response.equals("[]")){
				try{
					JsonArray jsonArray = new JsonParser().parse(response)
							.getAsJsonArray();
					treeStructure = "{'name':" + "'" + fullName + "'"
							+ ",'children':[";
					for (int i = 0; i < jsonArray.size(); i++) {
						if (jsonArray.get(i).getAsJsonObject().get("type")
								.getAsString().equals("file")) {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "'}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "'},";
							}
							fileNumber = fileNumber + 1;
							repoSize = repoSize + jsonArray.get(i).getAsJsonObject().get("size").getAsInt();
						} else {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("_links") != null && jsonArray.get(i).getAsJsonObject().get("_links").getAsJsonObject().get("git") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("_links").getAsJsonObject()
											.get("git").getAsString());
								}
								treeStructure = treeStructure + "]}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("_links") != null && jsonArray.get(i).getAsJsonObject().get("_links").getAsJsonObject().get("git") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("_links").getAsJsonObject()
											.get("git").getAsString());
								}
								treeStructure = treeStructure + "]},";
							}
						}
					}
					treeStructure = treeStructure + "]}";

					DBObject object = new BasicDBObject();
					object.put("tree", treeStructure);
					object.put("filenumber", fileNumber);
					object.put("reposize", repoSize);
					object.put("fn", fullName);
					tree = object;
				}catch(Exception e){
					System.out.println("can not translate it to json----------------------------");
				}
			}
		}
		
		return tree;
	}
	
	public void analyseTree(String treeURL){
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(treeURL);
		BufferedReader reader = null;
		String response = "";
		int responseCode = 200;
		
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
			
			urlConnection = GetURLConnection.getUrlConnection(treeURL);
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
					urlConnection = GetURLConnection.getUrlConnection(treeURL);
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			if (response != null && !response.equals("[]")){
				try{
					JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
					JsonArray jsonArray = new JsonParser().parse(jsonObject.get("tree").toString()).getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						if (jsonArray.get(i).getAsJsonObject().get("type")
								.getAsString().equals("blob")) {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "'}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "'},";
							}
							fileNumber = fileNumber + 1;
							repoSize = repoSize + jsonArray.get(i).getAsJsonObject().get("size").getAsInt();
						} else {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("url") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("url").getAsString());
								}
								treeStructure = treeStructure + "]}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("url") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("url").getAsString());
								}
								treeStructure = treeStructure + "]},";
							}
						}
					}
				}catch(Exception e){
					System.out.println("can not translate it to json----------------------------");
				}
			}
		}
	}
}

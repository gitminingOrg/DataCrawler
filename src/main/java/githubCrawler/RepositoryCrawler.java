package githubCrawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import userInfoFetch.UserDeal;
import utility.GetAuthorization;
import utility.GetURLConnection;
import utility.ValidateInternetConnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class RepositoryCrawler {

	public DBObject crawlRepository(String fullName){
		System.out.println("Start crawl repository------------------------");
		String repositoryURL = "https://api.github.com/repos/" + fullName;
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(repositoryURL);
		BufferedReader reader = null;
		String response = "";
		DBObject repo = null;
		
		try{
			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
			response = reader.readLine();
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
			try{
				urlConnection = GetURLConnection.getUrlConnection(repositoryURL);
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
				response = reader.readLine();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
		try {
			repo = (BasicDBObject) JSON.parse(response);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("can not translate it to json----------------------------");
		}
		
		DBObject owner = (BasicDBObject)repo.get("owner");
		UserDeal.fetchUser(owner.get("login").toString(), Integer.parseInt(owner.get("id").toString()));
		
		return repo;
	}
}

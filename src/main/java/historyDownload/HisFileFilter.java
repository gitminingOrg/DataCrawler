package historyDownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import utility.MongoInfo;
import utility.MysqlInfo;

public class HisFileFilter {

	public static Set<String> repos = new HashSet<String>();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		initRepos();
		HisFileFilter hisFileFilter = new HisFileFilter();
		hisFileFilter.handleDateFormat();
//		for (int i = 2013; i < 2016; i++) {
//			File file = new File("/Users/owenchen/Desktop/"+i+"_FWMevent.res");
//			hisFileFilter.filterHistory(file);
//		}
		
	}
	
	public static void initRepos(){
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
		MongoDatabase db = mongoClient.getDatabase("Experiment");
		FindIterable<Document> exist = db.getCollection("repocondition").find();
		for (Document document : exist) {
			String json = document.toJson();
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			String repo = element.getAsJsonObject().get("fn").getAsString();
			repos.add(repo);
		}
		System.out.println(repos.size()+"repos to filter");
		mongoClient.close();
	}
	
	public void handleDateFormat() throws Exception{
		Connection connection = MysqlInfo.getMysqlConnection();
		String search = "select id,time from HistoryEvent";
		
		String sql = "update HistoryEvent set time = ? where id = ?";
		PreparedStatement stmt = connection.prepareStatement(search);
		ResultSet resultSet = stmt.executeQuery();
		while(resultSet.next()){
			String time = resultSet.getString("time");
			int id = resultSet.getInt("id");
			if(time.contains("/") || time.length() > 19){
				System.out.println(time);
				time = time.substring(0, 19).replaceAll("/", "-");
				stmt = connection.prepareStatement(sql);
				stmt.setString(1, time);
				stmt.setInt(2, id);
				stmt.execute();
			}
		}
		resultSet.close();
		stmt.close();
		connection.close();
		
	}
	
	public void filterHistory(File file) throws Exception{
		Connection connection = MysqlInfo.getMysqlConnection();
		String sql = "insert into HistoryEvent(full_name,type,time,person,action) values(?,?,?,?,?);";
		PreparedStatement stmt = connection.prepareStatement(sql);
		connection.setAutoCommit(false);
		
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		String line = "";
		while ((line=br.readLine()) != null) {
			String[] items = line.split(",");
			if(items.length < 5){
				line = line + br.readLine();
				items = line.split(",");
			}
			String full_name = items[0];
			String type = items[1];
			String time = items[2].replaceAll("T", " ").replaceAll("Z", "");
			System.out.println(time);
			if(repos.contains(full_name)){
				String person = items[3];
				String action = items[4];
				System.out.println("-------------------------------------------");
				stmt.setString(1, full_name);
				stmt.setString(2, type);
				stmt.setString(3, time);
				stmt.setString(4, person);
				stmt.setString(5, action);
				stmt.execute();
			}
			
		}
		connection.commit();
		stmt.close();
		connection.close();
	}

}

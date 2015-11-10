package analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import utility.MongoInfo;
import utility.MysqlInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class RepoInfo {
	public static void main(String[] args) throws Exception {
		RepoInfo repoInfo = new RepoInfo();
		repoInfo.getContribution();
	}

	/**
	 * fetch the repo info from mongo to mysql
	 * 
	 * @throws Exception
	 */
	public void getRepo() throws Exception {
		// fetch from mongo
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),
				27017);
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV1");
		FindIterable<Document> repoIterable = database.getCollection(
				"repository").find();

		// get mysql connection
		Connection connection = MysqlInfo.getMysqlConnection();
		// refresh update time
		String updateSql = "update updatetime set repo_update_time = ?";
		PreparedStatement updateStmt = connection.prepareStatement(updateSql);
		Date time = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		updateStmt.setString(1, sdf.format(time));
		updateStmt.execute();
		
		connection.setAutoCommit(false);
		String sql = "replace into repotest(id,full_name,description,fork,owner_id,owner_name,owner_type,create_time,push_time,update_time,stargazers,subscribers,fork_num) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);

		JsonParser parser = new JsonParser();
		for (Document document : repoIterable) {
			String json = document.toJson();
			JsonObject repoJsonObject = parser.parse(json).getAsJsonObject();
			int id = repoJsonObject.get("id").getAsInt();
			stmt.setInt(1, id);
			
			String full_Name = repoJsonObject.get("full_name").getAsString();
			System.out.println(full_Name);


			stmt.setString(2, full_Name);
			String description = "";
			if(repoJsonObject.has("description") && !repoJsonObject.get("description").isJsonNull()){
				description = repoJsonObject.get("description")
						.getAsString();
			}
			stmt.setString(3, description);
			
			boolean fork = repoJsonObject.get("fork").getAsBoolean();
			int forkNum = fork ? 1 : 0;
			stmt.setInt(4, forkNum);
			
			int owner_id = repoJsonObject.get("owner").getAsJsonObject()
					.get("id").getAsInt();
			stmt.setInt(5, owner_id);
			
			String[] items = full_Name.split("/");
			String owner_name = items[0];
			stmt.setString(6, owner_name);
			
			String ownerType = repoJsonObject.get("owner").getAsJsonObject()
					.get("type").getAsString();
			int ot_num = 1;
			if (ownerType.equals("Organization")) {
				ot_num = 2;
			}
			stmt.setInt(7, ot_num);
			
			String createTime = repoJsonObject.get("created_at").getAsString();
			stmt.setString(8, createTime);
			
			String pushTime = "";
			if (repoJsonObject.has("pushed_at")
					&& !repoJsonObject.get("pushed_at").isJsonNull()) {
				pushTime = repoJsonObject.get("pushed_at").getAsString();
			}
			stmt.setString(9, pushTime);
			
			String updateTime = repoJsonObject.get("updated_at").getAsString();
			stmt.setString(10, updateTime);
			
			int starCount = repoJsonObject.get("stargazers_count").getAsInt();
			stmt.setInt(11, starCount);
			
			int subscriber = repoJsonObject.get("subscribers_count").getAsInt();
			stmt.setInt(12, subscriber);
			
			int forksCount = repoJsonObject.get("forks_count").getAsInt();
			stmt.setInt(13, forksCount);
			
			stmt.execute();
		}
		connection.commit();
		stmt.close();
		connection.close();
		mongoClient.close();
	}

	public void getContribution() throws Exception {
		//get mysql connection
		Connection connection = MysqlInfo.getMysqlConnection();
		connection.setAutoCommit(false);
		String conSql = "insert into contribution(user_id,repo_id) values(?,?);";
		PreparedStatement conStmt = connection.prepareStatement(conSql);
		String repoSql = "update repotest set contributor = ? where id = ?";
		PreparedStatement repoStmt = connection.prepareStatement(repoSql);
		
		//get repos from mongo
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),
				27017);
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV1");
		FindIterable<Document> repoIterable = database
				.getCollection("repository").find();
		JsonParser parser = new JsonParser();
		Map<String, Integer> repoMap = new HashMap<String, Integer>();
		for (Document document : repoIterable) {
			String json = document.toJson();
			JsonObject repoJsonObject = parser.parse(json).getAsJsonObject();
			int id = repoJsonObject.get("id").getAsInt();
			String full_name = repoJsonObject.get("full_name").getAsString();
			System.out.println(id);
			repoMap.put(full_name, id);
		}

		Map<Integer,Integer> contributorMap = new HashMap<Integer, Integer>();
		
		FindIterable<Document> contributeIterable = database.getCollection(
				"contributors").find();
		for (Document document : contributeIterable) {
			String json = document.toJson();
			JsonObject contriJsonObject = parser.parse(json).getAsJsonObject();
			int id = contriJsonObject.get("id").getAsInt();
			String repoName = contriJsonObject.get("fn").getAsString();
			int repo_id = repoMap.get(repoName);
			conStmt.setInt(1, id);
			conStmt.setInt(2, repo_id);
			conStmt.execute();
			
			if(contributorMap.containsKey(repo_id)){
				contributorMap.put(repo_id, contributorMap.get(repo_id)+1);
			}else{
				contributorMap.put(repo_id, 1);
			}
		}
		
		Set<Integer> keySet = contributorMap.keySet();
		for (Integer repoId : keySet) {
			int contri_count = contributorMap.get(repoId);
			repoStmt.setInt(1, contri_count);
			repoStmt.setInt(2, repoId);
			repoStmt.execute();
		}
		
		mongoClient.close();
		connection.commit();
		conStmt.close();
		repoStmt.close();
		connection.close();
	}

	public void analyseContributors() {
		Map<String, Integer> repoMap = new HashMap<String, Integer>();
		JsonParser parser = new JsonParser();
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),
				27017);
		MongoDatabase database = mongoClient.getDatabase("ghcrawler");
		FindIterable<Document> contiIterable = database.getCollection(
				"contributors").find();
		for (Document document : contiIterable) {
			String json = document.toJson();
			JsonObject contributor = parser.parse(json).getAsJsonObject();
			String repoName = contributor.get("fn").getAsString();
			if (!repoMap.containsKey(repoName)) {
				repoMap.put(repoName, 1);
			} else {
				int conNum = repoMap.get(repoName);
				repoMap.put(repoName, conNum + 1);
			}
		}

		Set<String> keySet = repoMap.keySet();
		int index = 0;
		for (String key : keySet) {
			int num = repoMap.get(key);
			if (num > 1) {
				System.out.println(key + "\t" + num);
				index++;
			}
		}
		System.out.println(index);
		mongoClient.close();
	}

}

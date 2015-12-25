package analysis;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.bson.Document;

import userInfoFetch.FetchOrgMembers;
import utility.MongoInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class UserInfo {
	public static void main(String[] args) throws Exception{
		UserInfo info = new UserInfo();
		info.getUserBasicInfo();
	}
	public void getUserBasicInfo() {
		try {
			Connection connection = getMysqlConnection();
			connection.setAutoCommit(false);
			MongoClient mongoClient = new MongoClient(
					MongoInfo.getMongoServerIp(), 27017);
			MongoDatabase database = mongoClient.getDatabase("ghcrawlerV3");
			FindIterable<Document> userIterable = database
					.getCollection("user").find();

			JsonParser parser = new JsonParser();
			for (Document document : userIterable) {
				String json = document.toJson();
				JsonObject user = parser.parse(json).getAsJsonObject();
				String login = user.get("login").getAsString();
				int id = user.get("id").getAsInt();
				String type = user.get("type").getAsString();
				boolean admin = user.get("site_admin").getAsBoolean();
				int admin_num = admin ? 1 : 0;
				String name = null;
				if (!user.get("name").isJsonNull()) {
					name = user.get("name").getAsString();
				}

				String company = null;
				if (!user.get("company").isJsonNull()) {
					company = user.get("company").getAsString();
				}

				String blog = null;
				if (!user.get("blog").isJsonNull()) {
					blog = user.get("blog").getAsString();
				}

				String location = null;
				if (!user.get("location").isJsonNull()) {
					location = user.get("location").getAsString();
				}

				String email = null;
				if (!user.get("email").isJsonNull()) {
					email = user.get("email").getAsString();
				}

				String hireable = null;
				if (!user.get("hireable").isJsonNull()) {
					hireable = user.get("hireable").getAsString();
				}

				String bio = null;
				if (!user.get("bio").isJsonNull()) {
					bio = user.get("bio").getAsString();
				}

				int repos = user.get("public_repos").getAsInt();
				int gists = user.get("public_gists").getAsInt();

				int followers = user.get("followers").getAsInt();
				int following = user.get("following").getAsInt();

				String createDate = user.get("created_at").getAsString();
				String updateDate = user.get("updated_at").getAsString();
				

				
				String sql = "insert into usertest values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE hireable='true';";
				PreparedStatement stmt = connection.prepareStatement(sql);
				System.out.println(id);
				stmt.setInt(1, id);
				stmt.setString(2, login);
				stmt.setString(3, type);
				stmt.setInt(4, admin_num);
				stmt.setString(5, name);
				stmt.setString(6, company);
				stmt.setString(7, blog);
				stmt.setString(8, location);
				stmt.setString(9, email);
				stmt.setString(10, hireable);
				stmt.setString(11, bio);
				stmt.setInt(12, repos);
				stmt.setInt(13, gists);
				stmt.setInt(14, followers);
				stmt.setInt(15, following);
				stmt.setString(16, createDate);
				stmt.setString(17, updateDate);
				
				stmt.execute();

			}
			mongoClient.close();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOrgs(){
		try {
			Connection connection = getMysqlConnection();
			String sql = "select * from usertest where type = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, "Organization");
			ResultSet resultSet = stmt.executeQuery();
			int index = 0;
			while(resultSet.next()){
				index++;
				int id = resultSet.getInt("id");
				String login = resultSet.getString("login");
				String name = resultSet.getString("name");
				String company = resultSet.getString("company");
				System.out.println(id + "\t" + login+"\t"+name+"\t"+company);
				
				String sqlInsert = "INSERT INTO orgtest VALUES (?,?,?,?)";
				PreparedStatement stmtInsert = connection.prepareStatement(sqlInsert);
				stmtInsert.setInt(1, id);
				stmtInsert.setString(2, login);
				stmtInsert.setString(3, name);
				stmtInsert.setString(4, company);
				stmtInsert.execute();
			}
			System.out.println(index);
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void calOrgRepoCount() throws Exception{
		Map<Integer,Integer> orgMap = new HashMap<Integer, Integer>();
		Connection connection = getMysqlConnection();
		String sql = "select * from orgtest";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();
		while(resultSet.next()){
			int org_id = resultSet.getInt("id");
			orgMap.put(org_id, 0);
		}
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),27017);
		MongoDatabase database = mongoClient.getDatabase("testUser2");
		JsonParser parser = new JsonParser();
		FindIterable<Document> doIterable = database.getCollection("userRepo").find();
		for (Document document : doIterable) {
			JsonObject repo = parser.parse(document.toJson()).getAsJsonObject();
			
			int id = repo.get("owner").getAsJsonObject().get("id").getAsInt();
			if(orgMap.containsKey(id)){
				System.out.println(id);
				orgMap.put(id, orgMap.get(id)+1);
			}
		}
		
		String sqlUpdate = "update orgtest set repo_count = ? where id = ?";
		
		PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdate);
		
		Set<Integer> orgIntegers = orgMap.keySet();
		for (Integer orgId : orgIntegers) {
			stmtUpdate.setInt(1, orgMap.get(orgId));
			stmtUpdate.setInt(2, orgId);
			stmtUpdate.execute();
		}
		mongoClient.close();
		connection.close();
		
	}
	
	public void calOrgMemberCount() throws Exception{
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),27017);
		MongoDatabase database = mongoClient.getDatabase("testUser2");
		FindIterable<Document> doIterable = database.getCollection("orgMember").find();
		
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		JsonParser parser = new JsonParser();
		for (Document document : doIterable) {
			JsonObject member = parser.parse(document.toJson()).getAsJsonObject();
			int org_id = member.get("org_id").getAsInt();
			if(map.containsKey(org_id)){
				map.put(org_id, map.get(org_id)+1);
			}else{
				map.put(org_id, 1);
			}
		}
		
		Connection connection = getMysqlConnection();
		String sql = "update orgtest set member_count = ? where id = ?";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		Set<Integer> orgIntegers = map.keySet();
		for (Integer orgId : orgIntegers) {
			stmt.setInt(1, map.get(orgId));
			stmt.setInt(2, orgId);
			stmt.execute();
		}
		
		connection.close();
		mongoClient.close();
	}
	
	public void fetchOrgMembers(){

		try {
			FetchOrgMembers fetchOrgMembers = new FetchOrgMembers();
			MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),27017);
			MongoDatabase database = mongoClient.getDatabase("testUser2");
			Connection connection = getMysqlConnection();
			String sql = "SELECT * FROM OrgTest";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String login = resultSet.getString("login");
				List<Document> documents = fetchOrgMembers.insertOrgMembers(id, login);
				if (documents != null && documents.size() > 0) {
					database.getCollection("orgMember").insertMany(documents);
				}
				
				
			}
			mongoClient.close();
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getMysqlConnection() throws Exception{
		//get items from resources/config.properties
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
		properties.load(new FileInputStream(new File(path)));
		
		String mysqlIP = properties.getProperty("mysql.server.ip");
		String mysqlPort =  properties.getProperty("mysql.server.port");
		String dbName =  properties.getProperty("mysql.db.data");
		String loginName =  properties.getProperty("mysql.login.name");
		String password =  properties.getProperty("mysql.login.password");
		String url = "jdbc:mysql://"+mysqlIP+":"+mysqlPort+"/"+dbName+"?"
                + "user="+loginName+"&password="+password+"&useUnicode=true&characterEncoding=UTF8";
		
		//load class driver
		Class.forName("com.mysql.jdbc.Driver");
		
		//return connection
		Connection connection = DriverManager.getConnection(url);
		return connection;
	}
}
// login
// id
// "type" : "Organization",
// "site_admin" : false,
// "name" : null,
// "company" : null,
// "blog" : null,
// "location" : null,
// "email" : null,
// "hireable" : null,
// "bio" : null,
// "public_repos" : 6,
// "public_gists" : 0,
// "followers" : 0,
// "following" : 0,
// "created_at" : "2013-06-01T00:43:49Z",
// "updated_at" : "2014-11-01T16:19:07Z"

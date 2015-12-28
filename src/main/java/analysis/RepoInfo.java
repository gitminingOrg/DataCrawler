package analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
//		repoInfo.getRepo();
//		System.out.println("repo!!!!!!!!!");
		repoInfo.getContribution();
		System.out.println("getContribution!!");
		repoInfo.getCollaborators();
		System.out.println("getCollaborators!!");
		repoInfo.analyseLanguage();
		repoInfo.getIssueAndPull();
		repoInfo.analyseContributors();
		repoInfo.analyseCollaborators();
		repoInfo.getCommitCount();
		repoInfo.updateScore();
		repoInfo.generateTag();
		repoInfo.generateRepoTagStub();
		repoInfo.calculateRepoSimilarity();
	}
	
	public void getIssueAndPull() throws Exception{
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),27017);
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV3");
		FindIterable<Document> issueIterable = database.getCollection(
				"issueandpull").find();
		Connection connection = MysqlInfo.getMysqlConnection();
		connection.setAutoCommit(false);
		String sql = "update repotest set open_issues = ?,closed_issues = ?,open_pull=?,closed_pull=? where full_name = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		JsonParser parser = new JsonParser();
		for (Document document : issueIterable) {
			String json = document.toJson();
			JsonObject repoIssue = parser.parse(json).getAsJsonObject();
			int openIssue = repoIssue.get("openissue").getAsInt();
			int closedIssue = repoIssue.get("closedissue").getAsInt();
			int openPull = repoIssue.get("openpull").getAsInt();
			int closedPull = repoIssue.get("closedpull").getAsInt();
			String repoName = repoIssue.get("fn").getAsString();
			System.out.println(repoName);
			stmt.setInt(1, openIssue);
			stmt.setInt(2, closedIssue);
			stmt.setInt(3, openPull);
			stmt.setInt(4, closedPull);
			stmt.setString(5, repoName);
			
			stmt.execute();
		}
		connection.commit();
		connection.close();
		mongoClient.close();
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
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV3");
		FindIterable<Document> repoIterable = database.getCollection(
				"repo").find();

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
		String sql = "insert into repotest(id,full_name,description,fork,owner_id,owner_name,owner_type,create_time,push_time,update_time,stargazers,subscribers,fork_num,size,hot,mature,popular,nb) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);

		JsonParser parser = new JsonParser();
		for (Document document : repoIterable) {
			String json = document.toJson();
			JsonObject repoJsonObject = parser.parse(json).getAsJsonObject();
			int id = repoJsonObject.get("id").getAsInt();
			System.out.println(id);
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
			
			int size = repoJsonObject.get("size").getAsInt();
			stmt.setInt(14, size);		
			
			int hot = (int)(Math.log10(starCount)*2.5);
			if(hot>10){
				hot=10;
			}
			stmt.setInt(15, hot);	
			int mature = (int)(Math.log10(forksCount)*2.5);
			if(mature>10){
				mature = 10;
			}
			stmt.setInt(16, mature);	
			
			int popular = (int)(Math.log10(subscriber)*2.5);
			if(popular>10){
				popular = 10;
			}
			stmt.setInt(17, popular);	
			
			int nb = (int)(Math.log10(size)*2.5);
			if(nb>10){
				nb = 10;
			}
			stmt.setInt(18, nb);	
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
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV3");
		FindIterable<Document> repoIterable = database
				.getCollection("repo").find();
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

	public void getCollaborators() throws Exception {
		//get mysql connection
		Connection connection = MysqlInfo.getMysqlConnection();
		connection.setAutoCommit(false);
		String conSql = "insert into collaborator(user_id,repo_id) values(?,?);";
		PreparedStatement conStmt = connection.prepareStatement(conSql);
		String repoSql = "update repotest set collaborator = ? where id = ?";
		PreparedStatement repoStmt = connection.prepareStatement(repoSql);
		
		//get repos from mongo
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),
				27017);
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV3");
		FindIterable<Document> repoIterable = database
				.getCollection("repo").find();
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

		Map<Integer,Integer> collaboratorMap = new HashMap<Integer, Integer>();
		
		FindIterable<Document> collaboratorIterable = database.getCollection(
				"assignees").find();
		for (Document document : collaboratorIterable) {
			String json = document.toJson();
			JsonObject contriJsonObject = parser.parse(json).getAsJsonObject();
			int id = contriJsonObject.get("id").getAsInt();
			String repoName = contriJsonObject.get("fn").getAsString();
			int repo_id = repoMap.get(repoName);
			conStmt.setInt(1, id);
			conStmt.setInt(2, repo_id);
			conStmt.execute();
			
			if(collaboratorMap.containsKey(repo_id)){
				collaboratorMap.put(repo_id, collaboratorMap.get(repo_id)+1);
			}else{
				collaboratorMap.put(repo_id, 1);
			}
		}
		
		Set<Integer> keySet = collaboratorMap.keySet();
		for (Integer repoId : keySet) {
			int contri_count = collaboratorMap.get(repoId);
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
	
	
	public void analyseLanguage() throws Exception {
		//get mysql connection
		Connection connection = MysqlInfo.getMysqlConnection();
		connection.setAutoCommit(false);
		String lanSql = "insert into language(repo_id,language,count) values(?,?,?);";
		PreparedStatement lanStmt = connection.prepareStatement(lanSql);
		String repoSql = "update repotest set language = ? where id = ?";
		PreparedStatement repoStmt = connection.prepareStatement(repoSql);
		
		//get repos from mongo
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),
				27017);
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV3");
		FindIterable<Document> repoIterable = database
				.getCollection("repo").find();
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

		Map<Integer,String> languageMap = new HashMap<Integer, String>();
		//the most language line of each repo
		Map<Integer,Integer> lanNumMap = new HashMap<Integer, Integer>();
		
		FindIterable<Document> collaboratorIterable = database.getCollection(
				"languages").find();
		for (Document document : collaboratorIterable) {
			String json = document.toJson();
			String[] items = json.split(",")[1].split(":");
			String language = items[0].trim().replaceAll("\"", "");
			int num = Integer.parseInt(items[1].trim());
			
			System.out.println(language +"\t" + num);
			JsonObject lanJsonObject = parser.parse(json).getAsJsonObject();
			String repoName = lanJsonObject.get("fn").getAsString();
			int repo_id = repoMap.get(repoName);
			
			if(lanNumMap.containsKey(repo_id)){
				if(num>=lanNumMap.get(repo_id)){
					languageMap.put(repo_id, language);
					lanNumMap.put(repo_id, num);
				}
			}else{
				languageMap.put(repo_id, language);
				lanNumMap.put(repo_id, num);				
			}
			lanStmt.setInt(1, repo_id);
			lanStmt.setString(2, language);
			lanStmt.setInt(3, num);
			lanStmt.execute();
		}
		
		Set<Integer> keySet = languageMap.keySet();
		for (Integer repoId : keySet) {
			String language = languageMap.get(repoId);
			repoStmt.setString(1, language);
			repoStmt.setInt(2, repoId);
			repoStmt.execute();
		}
		
		mongoClient.close();
		connection.commit();
		lanStmt.close();
		repoStmt.close();
		connection.close();
	}
	public void analyseContributors() throws Exception {
		Connection connection = MysqlInfo.getMysqlConnection();
		
		String groupSql = "select repo_id,count(*) AS contributors from contribution group by `repo_id`;";
		PreparedStatement stmt = connection.prepareStatement(groupSql);
		ResultSet resultSet = stmt.executeQuery();
		connection.setAutoCommit(false);
		while (resultSet.next()) {
			int repo_id = resultSet.getInt("repo_id");
			int contributors = resultSet.getInt("contributors");
			String sql = "update repotest set contributor = ? where id =?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, contributors);
			stmt.setInt(2, repo_id);
			stmt.execute();
		}
		connection.commit();
		stmt.close();
		connection.close();
	}
	
	public void analyseCollaborators() throws Exception {
		Connection connection = MysqlInfo.getMysqlConnection();
		
		String groupSql = "select repo_id,count(*) AS collaborators from collaborator group by `repo_id`;";
		PreparedStatement stmt = connection.prepareStatement(groupSql);
		ResultSet resultSet = stmt.executeQuery();
		connection.setAutoCommit(false);
		while (resultSet.next()) {
			int repo_id = resultSet.getInt("repo_id");
			int collaborators = resultSet.getInt("collaborators");
			String sql = "update repotest set collaborator = ? where id =?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, collaborators);
			stmt.setInt(2, repo_id);
			stmt.execute();
		}
		
		connection.commit();
		stmt.close();
		connection.close();
	}
	
	
	public void getCommitCount() throws Exception{

		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),27017);
		MongoDatabase database = mongoClient.getDatabase("ghcrawlerV3");
		FindIterable<Document> issueIterable = database.getCollection(
				"commitnumber").find();
		Connection connection = MysqlInfo.getMysqlConnection();
		connection.setAutoCommit(false);
		JsonParser parser = new JsonParser();
		for (Document document : issueIterable) {
			String json = document.toJson();
			JsonObject repoJsonObject = parser.parse(json).getAsJsonObject();
			int commit = repoJsonObject.get("commitnumber").getAsInt();
			String full_name = repoJsonObject.get("fn").getAsString();
			System.out.println(full_name);
			String sql = "update repotest set commit = ? where full_name = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, commit);
			stmt.setString(2, full_name);
			stmt.execute();
		}
		
		connection.commit();
		connection.close();
		mongoClient.close();
	}
	
	public void updateScore() throws Exception{
		Connection connection = MysqlInfo.getMysqlConnection();
		String sql = "select * from repotest";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();
		connection.setAutoCommit(false);
		while (resultSet.next()) {
			int repo_id = resultSet.getInt("id");
			int hot = resultSet.getInt("hot");
			int mature = resultSet.getInt("mature");
			int popular = resultSet.getInt("popular");
			int nb = resultSet.getInt("nb");
			int contributor = (int) Math.min(10, Math.log10(resultSet.getInt("contributor")) *2.5);	
			int size = (int) Math.min(10, Math.log10(resultSet.getInt("size")) *2.5);
			int update = 10;
			int release = 10;
			int total = (hot+mature+popular+nb+contributor+size+update+release)/8;
			
			String insertSql = "insert into reposcore values(?,?,?,?,?,?,?,?,?,?)";
			stmt = connection.prepareStatement(insertSql);
			stmt.setInt(1, repo_id);
			stmt.setInt(2, hot);
			stmt.setInt(3, mature);
			stmt.setInt(4, popular);
			stmt.setInt(5, nb);
			stmt.setInt(6, contributor);
			stmt.setInt(7, size);
			stmt.setInt(8, update);
			stmt.setInt(9, release);
			stmt.setInt(10, total);
			stmt.execute();
		}
		connection.commit();
		connection.close();
	}
	
	public void generateTag() throws Exception{
		String sql = "insert into tag(node_id,name,weight) values(?,?,?);";
		Connection connection = MysqlInfo.getMysqlConnection();
		PreparedStatement stmt = connection.prepareStatement(sql);
		char init = 'A'-1;
		for (int i = 0; i <= 25; i++) {
			init++;
			String typeName = "type"+init;
			int node_id = i;
			char subInit = 'a'-1;
			int weight = (int) (Math.random()*10);
			
			stmt.setString(1, Integer.toString(node_id));
			stmt.setString(2, typeName);
			stmt.setInt(3, weight);
			stmt.execute();
			for (int j = 0; j <= 10; j++) {
				subInit++;
				String subType = typeName+subInit;
				String node = i+"."+j;
				weight = (int) (Math.random()*10);
				
				stmt.setString(1, node);
				stmt.setString(2, subType);
				stmt.setInt(3, weight);
				stmt.execute();
			}
		}
		stmt.close();
		connection.close();
	}
	
	public void generateRepoTagStub() throws Exception{
		String sql = "select id from repotest";
		Connection connection = MysqlInfo.getMysqlConnection();
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();
		connection.setAutoCommit(false);
		String insertSql = "insert into repo_tag(repo_id,tag_id) values(?,?);";
		stmt = connection.prepareStatement(insertSql);
		
		while(resultSet.next()){
			int repo_id = resultSet.getInt("id");
			for (int i = 0; i < 10; i++) {
				int random = (int)(Math.random() * 312);
				stmt.setInt(1, repo_id);
				stmt.setInt(2, random);
				stmt.execute();
			}
			
		}
		connection.commit();
		stmt.close();
		connection.close();
	}
	
	public void calculateRepoSimilarity() throws Exception{
		String sql = "select * from repo_tag order by repo_id,tag_id";
		ArrayList<RepoTagPair> repoTagPairs = new ArrayList<RepoTagPair>();
		Connection connection = MysqlInfo.getMysqlConnection();
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			int repo_id = resultSet.getInt("repo_id");
			int tag_id = resultSet.getInt("tag_id");
			RepoTagPair repoTagPair = new RepoTagPair();
			repoTagPair.setId(id);
			repoTagPair.setRepo_id(repo_id);
			repoTagPair.setTag_id(tag_id);
			repoTagPairs.add(repoTagPair);
		}
		Map<Integer,List<Integer>> repoTagMap = new HashMap<Integer, List<Integer>>();
		for (RepoTagPair repoTagPair : repoTagPairs) {
			if(!repoTagMap.containsKey(repoTagPair.getRepo_id())){
				List<Integer> list = new ArrayList<Integer>();
				list.add(repoTagPair.getTag_id());
				repoTagMap.put(repoTagPair.getRepo_id(), list);
			}else{
				List<Integer> list = repoTagMap.get(repoTagPair.getRepo_id());
				list.add(repoTagPair.getTag_id());
			}
		}

		//compare tags of different repos
		HashMap<Integer,ArrayList<RepoPairRelation>> matrix = new HashMap<Integer, ArrayList<RepoPairRelation>>();
		Set<Integer> keySet = repoTagMap.keySet();
		int c = 0;
		for (Integer integer : keySet) {
			List<Integer> values1 = repoTagMap.get(integer);
			matrix.put(integer, new ArrayList<RepoPairRelation>());
			for (Integer integer2 : keySet) {
				RepoPairRelation repoPairRelation=new RepoPairRelation(integer, integer2, 0);
				int count = 0;
				List<Integer> values2 = repoTagMap.get(integer2);
				int index1 = 0, index2 = 0;
				while(index1<values1.size() && index2<values2.size()){
					if(values1.get(index1).equals(values2.get(index2))){
						count++;
						index1++;index2++;
					}else if(values1.get(index1)<values2.get(index2)){
						index1++;
					}else{
						index2++;
					}
				}
				repoPairRelation.setRelation_score(count);
				matrix.get(integer).add(repoPairRelation);
			}
		}	
		connection.setAutoCommit(false);
		keySet = matrix.keySet();
		System.out.println("haha");
		FileWriter fw = new FileWriter("relate.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		for (Integer id : keySet) {
			ArrayList<RepoPairRelation> relations = matrix.get(id);
			for (RepoPairRelation repoPairRelation : relations) {
				int repo_id = repoPairRelation.getRepo_id();
				int relate_repo_id = repoPairRelation.getRepo_relate_id();
				System.out.println(repo_id +" " + relate_repo_id);
				if(repo_id == relate_repo_id){
					continue;
				}
				int similar = repoPairRelation.getRelation_score();
				bw.write(repo_id+"\t"+relate_repo_id+"\t"+similar+"\n");
//				String insertsql = "insert into repo_similar values(?,?,?)";
//				stmt = connection.prepareStatement(insertsql);
//				stmt.setInt(1, repo_id);
//				stmt.setInt(2, relate_repo_id);
//				stmt.setInt(3, similar);
//				stmt.execute();
			}
		}
		bw.flush();
		bw.close();
		fw.close();
		connection.commit();
		stmt.close();
		connection.close();
	}

}

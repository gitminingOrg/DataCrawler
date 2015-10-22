package userInfoFetch;
import java.util.List;

import org.bson.Document;

import utility.MongoInfo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UserDeal {
	public static void fetchUser(String login, int id) {
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(), 27017);
		MongoDatabase db = mongoClient.getDatabase("testUser2");
		FindIterable<Document> exist = db.getCollection("user").find(new Document("id",id));
		if (exist.first() != null) {
			System.out.println(id + " exists!");
			mongoClient.close();
			return;
		}
		
		String infos ="https://api.github.com/users/" + login;
		String repos ="https://api.github.com/users/" + login + "/repos";
		String followers = id + "," + login + ","
				+ "https://api.github.com/users/" + login
				+ "/followers";
		
		FetchUserInfo fetchUserInfo = new FetchUserInfo();
		Document user = fetchUserInfo.fecthUserInfo(infos);
		
		FetchUserFollowers fetchUserFollowers = new FetchUserFollowers();
		List<Document> followerList = fetchUserFollowers.insertFollowers(followers);
		
		FetchUserRepos fetchUserRepos = new FetchUserRepos();
		List<Document> repoList =fetchUserRepos.insertUserRepos(repos);	
		
		MongoCollection<Document> collection = db.getCollection("user");
		MongoCollection<Document> collectionFollow = db.getCollection("follower");
		MongoCollection<Document> collectionRepo = db.getCollection("userRepo");
		
		collection.insertOne(user);
		collectionFollow.insertMany(followerList);
		collectionRepo.insertMany(repoList);
		
		mongoClient.close();
	}
}

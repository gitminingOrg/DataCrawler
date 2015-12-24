package zwqneed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class SubmitterDataFetch {
	public List<Document> fetchPullPersonalCommits(String repo_name, String user_login){
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("fn", repo_name);
		filters.put("user.login", user_login);
		List<Document> commits = mongoQuery.search(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.COMMIT_COLLECTION, filters);
		return commits;
	}
	
	public List<Document> fetchPersonalIssues(String repo_name, String user_login){
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("fn", repo_name);
		filters.put("user.login", user_login);
		List<Document> commits = mongoQuery.search(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.ISSUE_COLLECTION, filters);
		return commits;
	}
	
	public List<Document> fetchPersonalClosedPR(String repo_name, String user_login){
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("fn", repo_name);
		filters.put("user.login", user_login);
		filters.put("state", "closed");
		List<Document> commits = mongoQuery.search(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.PULL_COLLECTION, filters);
		return commits;
	}
}

package zwqneed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class ProjectDataFetch {
	
	public static void main(String[] args){
		ProjectDataFetch projectDataFetch = new ProjectDataFetch();
		projectDataFetch.fetchProjectInfo(27);
		projectDataFetch.fetchProjectStarEvent("mojombo/grit");
		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		pullRequestFetch.fetchClosedPullRequest(27);
	}
	
	public List<Document> fetchProjectMemberEvent(int repo_id){
		System.out.println("fetchProjectMemberEvent");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("type", "MemberEvent");
		filters.put("repo.id", repo_id);
		List<Document> result = mongoQuery.search(DBCollectionInfo.EVENT_DB, DBCollectionInfo.EVENT_COLLECTION, filters);
		return result;
	}
	
	public List<Document> fetchProjectForkEvent(String repo_name){
		System.out.println("fetchProjectForkEvent");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("type", "ForkEvent");
		filters.put("repo.name", repo_name);
		List<Document> result = mongoQuery.search(DBCollectionInfo.EVENT_DB, DBCollectionInfo.EVENT_COLLECTION, filters);
		return result;
	}
	
	public List<Document> fetchProjectStarEvent(String repo_name){
		System.out.println("fetchProjectStarEvent");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("type", "WatchEvent");
		filters.put("repo.name", repo_name);
		List<Document> result = mongoQuery.search(DBCollectionInfo.EVENT_DB, DBCollectionInfo.EVENT_COLLECTION, filters);
		return result;
	}
	
	public List<Document> fetchProjectMemberEvent(String repo_name){
		System.out.println("fetchProjectMemberEvent");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("type", "MemberEvent");
		filters.put("repo.full_name", repo_name);
		List<Document> result = mongoQuery.search(DBCollectionInfo.EVENT_DB, DBCollectionInfo.EVENT_COLLECTION, filters);
		return result;
	}
	
	public Document fetchProjectInfo(int repo_id){
		System.out.println("fetchProjectInfo");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("id", repo_id);
		Document result = mongoQuery.searchOne(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.REPO_COLLECTION, filters);

		return result;
	}
	
	public Document fetchProjectInfo(String repo_name){
		System.out.println("fetchProjectInfo");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("full_name", repo_name);
		Document result = mongoQuery.searchOne(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.REPO_COLLECTION, filters);
		return result;
	}
}

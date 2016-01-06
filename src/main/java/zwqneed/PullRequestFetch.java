package zwqneed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class PullRequestFetch {
	public static void main(String[] args){
		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		pullRequestFetch.fetchPullCommits("mojombo/grit", 1);
	}
	public void fetchClosedPullRequest(int repo_id){
		System.out.println("fetchClosedPullRequest");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("base.repo.id", repo_id);
		filters.put("state", "closed");
		List<Document> pulls = mongoQuery.search(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.PULL_COLLECTION, filters);
		System.out.println(pulls.size());
	}
	
	public List<Document> fetchClosedPullRequest(String repo_name){
		System.out.println("fetchClosedPullRequest");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("base.repo.full_name", repo_name);
		filters.put("state", "closed");
		List<Document> pulls = mongoQuery.search(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.PULL_COLLECTION, filters);
		System.out.println(pulls.size());
		return pulls;
	}
	
	public List<Document> fetchALLPullRequest(String repo_name){
		System.out.println("fetchALLPullRequest");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("base.repo.full_name", repo_name);
		List<Document> pulls = mongoQuery.search(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.PULL_COLLECTION, filters);
		System.out.println(pulls.size());
		return pulls;
	}
	
	public List<Document> fetchPullCommits(String repo_name, int pull_id){
		System.out.println("fetchPullCommits");
		MongoQuery mongoQuery = new MongoQuery();
		Map<String, Object> filters = new HashMap<String, Object>();
		String fn = "https://api.github.com/repos/"+repo_name+"/pulls/"+pull_id+"/commits";
		System.out.println(fn);
		System.out.println(pull_id);
		//filters.put("fn", repo_name);
		filters.put("pn", pull_id);
		List<Document> commits = mongoQuery.search(DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.PULL_COMMIT_COLLECTION, filters);
		return commits;
	}
}

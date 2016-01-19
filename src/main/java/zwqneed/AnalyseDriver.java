package zwqneed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.EndianUtils;
import org.bson.Document;

import utility.MongoInfo;
import utility.MysqlInfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class AnalyseDriver {

	public static String ownerType;
	public static String ownerLogin;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		AnalyseDriver analyseDriver = new AnalyseDriver();
		List<String> repoStrings =analyseDriver.getRepos();
		
		int start = MysqlInfo.startRepo() - 1;
		for (int i=start; i<repoStrings.size(); i++) {
			String projectName = repoStrings.get(i);
			System.err.println("repo no." + i + " " + projectName + "start");
			analyseDriver.analyseRepoPull(projectName);
			System.err.println("repo no." + i + " " + projectName + "finish");
		}
	}
	
	public List<String> getRepos(){
		List<String> repos = new ArrayList<String>();
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
		
		return repos;
	}
	
	public void analyseRepoPull(String projectName) throws Exception{
		//test-------------------------------------
		//projectName = "pockethub/PocketHub";
		//test-------------------------------------
		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		ProjectDataFetch projectDataFetch = new ProjectDataFetch();

		List<Document> pulls = pullRequestFetch.fetchClosedPullRequest(projectName);
		Document project = projectDataFetch.fetchProjectInfo(projectName);
		ownerType = project.get("owner",Document.class).getString("type");
		ownerLogin = project.get("owner",Document.class).getString("login");
		
		System.out.println(project);
		AnalyseDriver analyseDriver = new AnalyseDriver();
		analyseDriver.anaylseOpenPull(pulls, project);
		analyseDriver.analyseProjectHistoryData(pulls, project);
		
		for (Document document : pulls) {
//test-------------------------------------
//			if(document.getInteger("number") != 851){
//				continue;
//			}
//---------------------------------------------
			analyseDriver.analyseSubmitterCommitsData(document, project);
			analyseDriver.analyseSubmitterLevelData(document);
			PullRequest pullRequest = analyseDriver.pr_info(document);
			analyseDriver.analyseProjectLevelData(pullRequest, project);
			MongoQuery mongoQuery = new MongoQuery();
			mongoQuery.insert(pullRequest, DBCollectionInfo.CRAWLER_DB, DBCollectionInfo.RESULT_COLLECTION);
		}
	}
	
	/**
	 * PR提交前当前open的PR数
	 * @param pulls
	 * @param project
	 * @throws ParseException
	 */
	public void anaylseOpenPull(List<Document> pulls,Document project) throws ParseException{
//		当前仍为open么有------------------------------
		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(project.toJson()).getAsJsonObject();
		String repo_name = jsonObject.get("full_name").getAsString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<Document> allPulls = pullRequestFetch.fetchALLPullRequest(repo_name);
		
		int pull_index = 0;
		int all_index = 0;
		List<Document> unendPulls = new ArrayList<Document>();
		while(pull_index < pulls.size() && all_index <allPulls.size()){
			Document pull = pulls.get(pull_index);	
			Date pull_time = sdf.parse(pull.getString("created_at").replaceAll("T", " ").replaceAll("Z", ""));
			
			Document allPull = allPulls.get(all_index);
			Date all_time = sdf.parse(allPull.getString("created_at").replaceAll("T", " ").replaceAll("Z", ""));
			if(all_time.before(pull_time)){
				unendPulls.add(allPull);
				all_index++;
			}else{
				for (int i=0; i<unendPulls.size(); i++) {
					Document document = unendPulls.get(i);
					if(document.getString("closed_at") != null){
						Date end_time = sdf.parse(document.getString("closed_at").replaceAll("T", " ").replaceAll("Z", ""));
						if(end_time.before(pull_time)){
							unendPulls.remove(i);
							i--;
						}
					}	
				}
				pull.append("open_pull", unendPulls.size());
				pull_index++;
			}	
		}
	}
	
	public void analyseProjectHistoryData(List<Document> pulls,Document project) throws Exception{
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(project.toJson()).getAsJsonObject();
		String repo_name = jsonObject.get("full_name").getAsString();
		
		ProjectDataFetch projectDataFetch = new ProjectDataFetch();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HistoryEventFetcher historyEventFetcher = new HistoryEventFetcher();
		List<Event> forkEvents = historyEventFetcher.getEvents(repo_name, "ForkEvent");
		
		forkEvents.add(new Event("", "", new Date(), "", ""));
		List<Event> starEvents = historyEventFetcher.getEvents(repo_name, "WatchEvent");
		starEvents.add(new Event("", "", new Date(), "", ""));
		
		//if owner himself is a user ,add it to insiders
		List<Event> memberEvents = historyEventFetcher.getEvents(repo_name, "MemberEvent");
		if(!ownerType.equals("Organization")){
			memberEvents.add(0,new Event(repo_name, "MemberEvent", new Date(0), ownerLogin, "Member"));
		}
		memberEvents.add(new Event("", "", new Date(), "", ""));
		
		HashSet<String> stargazers = new HashSet<String>();
		int pull_index = 0;
		int fork_index = 0;
		int star_index = 0;
		int member_index = 0;
		int member_count = 0;
		int start_count = 0;
		HashSet<String> members = new HashSet<String>();
		
		while(pull_index<pulls.size()){
			Document pull = pulls.get(pull_index);
			String pull_login = pull.get("user", Document.class).getString("login");
			Date pull_time = sdf.parse(pull.getString("created_at").replaceAll("T", " ").replaceAll("Z", ""));
			
			Event forkEvent = forkEvents.get(fork_index);
			Date fork_time = forkEvent.getTime();
			
			Event starEvent = starEvents.get(star_index);	
			Date star_time = starEvent.getTime();
			String stargazer = starEvent.getPerson();
			
			Event memberEvent = memberEvents.get(member_index);
			Date member_time = memberEvent.getTime();
			String login = memberEvent.getPerson();
			
			//all events after pull time , calculate once
			if(pull_time.before(member_time) && pull_time.before(star_time) && pull_time.before(fork_time)){
				pull.append("fork", fork_index);
				pull.append("star", start_count);
				pull.append("insider", member_count);
				System.out.println("History Event !!!!!!!!!"+fork_index+"!!!!"+start_count+"!!!!!!!!"+member_count+"!!!!!!!!!!!");
				if(members.contains(pull_login)){
					pull.append("is_insider", true);
				}else{
					pull.append("is_insider", false);
				}
				pull_index++;
			}else{
				if(!pull_time.before(member_time)){
					if(!members.contains(login)){
						member_count++;
						members.add(login);
					}
					member_index++;
				}
				
				if(!pull_time.before(star_time)){
					if(!stargazers.contains(stargazer)){
						start_count++;
						stargazers.add(stargazer);
					}
					star_index++;
				}
				
				if(!pull_time.before(fork_time)){
					fork_index++;
				}
			}
			
		}
		
	}
	
	/**
	 * calculate pr level data
	 * @param pullDocument
	 * @return
	 * @throws ParseException 
	 */
	public PullRequest pr_info(Document pullDocument) throws ParseException{
		PullRequest pullRequest = new PullRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//analyse pr base info
		String fn = pullDocument.getString("fn");
		int pull_id = pullDocument.getInteger("number",0);
		String login = pullDocument.get("user", Document.class).getString("login");
		String createString = pullDocument.getString("created_at");
		String closeString = pullDocument.getString("closed_at");
		boolean merged = pullDocument.getBoolean("merged");
		Date create = sdf.parse(createString.replaceAll("T", " ").replaceAll("Z", ""));
		Date close = sdf.parse(closeString.replaceAll("T", " ").replaceAll("Z", ""));
		double closeTime = 1.0 *(close.getTime()-create.getTime()) / 1000 / 60 /60/24;
		
		
		int commitNum = pullDocument.getInteger("commits");
		int finalChangeSize = pullDocument.getInteger("additions")+pullDocument.getInteger("deletions");
		int finalChangeFile = pullDocument.getInteger("changed_files");
		
		
		//anaylse related commits
		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		List<Document> commits = pullRequestFetch.fetchPullCommits(fn, pull_id);
		HashSet<String> committers = new HashSet<String>();
		
		int codeChangeMax = 0;
		int codeChangeTotal = 0;
		int fileChangeTotal = 0;
		for (Document commit : commits) {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(commit.toJson()).getAsJsonObject();
			int add = jsonObject.get("stats").getAsJsonObject().get("additions").getAsInt();
			int delete = jsonObject.get("stats").getAsJsonObject().get("deletions").getAsInt();
			int codeChange = add+delete;
			codeChangeTotal+=codeChange;
			codeChangeMax =codeChangeMax>codeChange?codeChangeMax:codeChange;
			
			int change_file = jsonObject.get("files").getAsJsonArray().size();			
			fileChangeTotal+=change_file;
			if(!jsonObject.get("committer").isJsonNull() && jsonObject.get("committer").getAsJsonObject().has("login")){
				String commiter  = jsonObject.get("committer").getAsJsonObject().get("login").getAsString();
				if(!committers.contains(commiter)){
					committers.add(commiter);
				}
			}
		}
		int committer_num = committers.size();
		//get other items analysed by other funcs
		int fork = pullDocument.getInteger("fork");
		int star = pullDocument.getInteger("star");
		int insider = pullDocument.getInteger("insider");
		boolean is_insider = pullDocument.getBoolean("is_insider");
		int open_pull = pullDocument.getInteger("open_pull");
		
		int submitter_commits_num = pullDocument.getInteger("submitter_commits_num");
		int submitter_changed_line = pullDocument.getInteger("submitter_changed_line");
		int submitter_issue = pullDocument.getInteger("submitter_issue");
		int submitter_closed_PR = pullDocument.getInteger("submitter_closed_PR");
		int submitter_merged_PR = pullDocument.getInteger("submitter_merged_PR");
		double submitter_PR_close_avg_time = pullDocument.getDouble("submitter_PR_close_avg_time");
		
		pullRequest.setFn(fn);
		pullRequest.setId(pull_id);
		pullRequest.setCommitter(login);
		pullRequest.setCreated_time(createString);
		pullRequest.setIs_merged(merged);
		pullRequest.setTime_close(closeTime);
		
		pullRequest.setCommitter_num(committer_num);
		pullRequest.setCommit_num(commitNum);
		pullRequest.setChurn_final_size(finalChangeSize);
		pullRequest.setChurn_total_size(codeChangeTotal);
		pullRequest.setChurn_max_size(codeChangeMax);
		pullRequest.setChurn_file_num(fileChangeTotal);
		pullRequest.setFinal_churn_file_num(finalChangeFile);
		
		pullRequest.setProj_fork(fork);
		pullRequest.setProj_star(star);
		pullRequest.setProj_insider(insider);
		pullRequest.setIs_insider(is_insider);
		pullRequest.setProj_open_pr(open_pull);
		
		pullRequest.setScommit_num(submitter_commits_num);
		pullRequest.setSchurn_num(submitter_changed_line);
		pullRequest.setSissue_num(submitter_issue);
		pullRequest.setSclosedPR_num(submitter_closed_PR);
		pullRequest.setSmergedPR_num(submitter_merged_PR);
		pullRequest.setSclosedPR_avgtime(submitter_PR_close_avg_time);
		
		
		return pullRequest;
	}

	public void analyseProjectLevelData(PullRequest pullRequest, Document project){
		ProjectDataFetch projectDataFetch = new ProjectDataFetch();
		//get repo_create_time
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(project.toJson()).getAsJsonObject();
		
		//calculate days from repo to pull
		String repo_create = jsonObject.get("created_at").getAsString().replaceAll("T", " ").replaceAll("Z", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double from_days = 0;
		try {
			from_days = (sdf.parse(pullRequest.getCreated_time().replaceAll("T", " ").replaceAll("Z", "")).getTime()-sdf.parse(repo_create).getTime()) / 1000 / 60 / 60 /24;
			pullRequest.setProj_age(from_days);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 分析submiiter级别的PR数据
	 * @param pulls
	 * @param project
	 * @throws ParseException 
	 */
	public void analyseSubmitterCommitsData(Document pull,Document project) throws ParseException{
		String userName = pull.get("user", Document.class).getString("login");
		String fn = pull.getString("fn");
		int pull_id = pull.getInteger("number");
		boolean merged = (pull.containsKey("merged_at") && pull.get("merged_at") != null)?true:false;
		JsonParser parser = new JsonParser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int commits_num = 0;
		int changed_line = 0;
		
		
		JsonObject jsonObject = parser.parse(pull.toJson()).getAsJsonObject();
		String pull_create = jsonObject.get("created_at").getAsString().replaceAll("T", " ").replaceAll("Z", "");	
		String submitter = jsonObject.get("user").getAsJsonObject().get("login").getAsString();
		
		Date pullTime = sdf.parse(pull_create);	

		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		List<Document> pullCommits = pullRequestFetch.fetchPullCommits(fn, pull_id);
		//////////////////////////////
		String early_time = "";
		for (Document commit : pullCommits) {
			JsonObject commitJson = parser.parse(commit.toJson()).getAsJsonObject();
			String commit_create = commitJson.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString().replaceAll("T", " ").replaceAll("Z", "");
			if(!commitJson.get("committer").isJsonNull()  && commitJson.get("committer").getAsJsonObject().has("login")){
				String commiter = commitJson.get("committer").getAsJsonObject().get("login").getAsString();
				if(commiter.equals(submitter)){
					if(early_time.equals("") || early_time.compareTo(commit_create) > 0){
						early_time = commit_create;
					}
				}
			}

		}
		if(!early_time.equals("")){
			pullTime = sdf.parse(early_time.replaceAll("T", " ").replaceAll("Z", ""));
		}
		
		SubmitterDataFetch submitterDataFetch = new SubmitterDataFetch();
		List<Document> commits = submitterDataFetch.fetchPullPersonalCommits(fn, userName);
		for (Document commit : commits) {
			JsonObject commitJson = parser.parse(commit.toJson()).getAsJsonObject();
			String commit_create = commitJson.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString().replaceAll("T", " ").replaceAll("Z", "");
			Date commitTime = sdf.parse(commit_create);
			
			String loginName = commitJson.get("committer").getAsJsonObject().get("login").getAsString();
			if(loginName.equals(userName) && commitTime.before(pullTime)){
				commits_num++;
				int additions = commitJson.get("stats").getAsJsonObject().get("additions").getAsInt();
				int deletions = commitJson.get("stats").getAsJsonObject().get("deletions").getAsInt();
				
				changed_line = changed_line + additions + deletions;
			}
		}
		
		pull.append("submitter_commits_num", commits_num);
		pull.append("submitter_changed_line", changed_line);
	}
	
	public void analyseSubmitterLevelData(Document pull) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userName = pull.get("user", Document.class).getString("login");
		String fn = pull.getString("fn");
		Date pull_time = sdf.parse(pull.getString("created_at").replaceAll("T", " ").replaceAll("Z", ""));
		System.out.println(pull_time+"pull_time");
		SubmitterDataFetch submitterDataFetch = new SubmitterDataFetch();
		
		int issue = 0;
		List<Document> personalIssues = submitterDataFetch.fetchPersonalIssues(fn, userName);
		for (Document issueDocument : personalIssues) {
			Date create_time = sdf.parse(issueDocument.getString("created_at").replaceAll("T", " ").replaceAll("Z", ""));
			System.out.println(create_time+"create_time");
			if (create_time.before(pull_time)) {
				issue++;
			}else{
				
			}
		}
		
		int closedPR = 0;
		int mergedPR = 0;
		double closeTotalTime=0;
		double closeAvgTime = 0;
		List<Document> personalPulls = submitterDataFetch.fetchPersonalClosedPR(fn, userName);
		for (Document personalPull : personalPulls) {
			/////////////////////////////////////////////
			Date closeTime = sdf.parse(personalPull.getString("closed_at").replaceAll("T", " ").replaceAll("Z", ""));
			Date create_time = sdf.parse(personalPull.getString("created_at").replaceAll("T", " ").replaceAll("Z", ""));
			if (closeTime.before(pull_time)) {
				closedPR++;
				if(personalPull.getBoolean("merged")){
					mergedPR++;
				}		
				double closeHour = 1.0 *(closeTime.getTime()-create_time.getTime()) /1000 / 60 /60;
				closeTotalTime+=closeHour;
			}
		}
		if(closedPR > 0){
			closeAvgTime = closeTotalTime / closedPR;
		}
		
		pull.append("submitter_issue", issue);
		pull.append("submitter_closed_PR", closedPR);
		pull.append("submitter_merged_PR", mergedPR);
		pull.append("submitter_PR_close_avg_time", closeAvgTime);
		
	}
}

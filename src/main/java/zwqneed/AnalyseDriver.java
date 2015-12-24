package zwqneed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.bson.Document;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AnalyseDriver {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		ProjectDataFetch projectDataFetch = new ProjectDataFetch();

		String projectName = "mojombo/grit";
		List<Document> pulls = pullRequestFetch.fetchClosedPullRequest(projectName);
		Document project = projectDataFetch.fetchProjectInfo(projectName);
		
		AnalyseDriver analyseDriver = new AnalyseDriver();
		analyseDriver.anaylseOpenPull(pulls, project);
		analyseDriver.analyseProjectHistoryData(pulls, project);
		
		for (Document document : pulls) {
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
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(project.toJson()).getAsJsonObject();
		String repo_name = jsonObject.get("full_name").getAsString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
		
		int create_index = 0;
		List<Document> unendPulls = new ArrayList<Document>();
		while(create_index < pulls.size()){
			Document pull = pulls.get(create_index);
			Date pull_time = sdf.parse(pull.getString("created_at"));
			
			for (int i=0; i<unendPulls.size(); i++) {
				if(pull.getString("closed_at") != null){
					Date close_time = sdf.parse(pull.getString("closed_at"));
					if (close_time.before(pull_time)) {
						unendPulls.remove(i);
						i--;
					}
				}
			}
			unendPulls.add(pull);
			pull.append("open_pull", unendPulls.size());
			create_index++;
		}
	}
	
	public void analyseProjectHistoryData(List<Document> pulls,Document project) throws ParseException{
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(project.toJson()).getAsJsonObject();
		String repo_name = jsonObject.get("full_name").getAsString();
		
		ProjectDataFetch projectDataFetch = new ProjectDataFetch();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
		List<Document> forkEvents = projectDataFetch.fetchProjectForkEvent(repo_name);
		List<Document> starEvents = projectDataFetch.fetchProjectStarEvent(repo_name);
		List<Document> memberEvents = projectDataFetch.fetchProjectMemberEvent(repo_name);
		HashSet<String> stargazers = new HashSet<String>();
		int pull_index = 0;
		int fork_index = 0;
		int star_index = 0;
		int member_index = 0;
		int member_count = 0;
		int start_count = 0;
		
		while(pull_index<pulls.size()){
			Document pull = pulls.get(pull_index);
			Date pull_time = sdf.parse(pull.getString("created_at"));
			
			Document forkEvent = forkEvents.get(fork_index);
			Date fork_time = sdf.parse(forkEvent.getString("created_at"));
			
			Document starEvent = starEvents.get(star_index);	
			Date star_time = sdf.parse(starEvent.getString("created_at"));
			String stargazer = starEvent.get("actor", Document.class).getString("login");
			
			Document memberEvent = memberEvents.get(member_index);
			Date member_time = sdf.parse(memberEvent.getString("created_at"));
			boolean add = memberEvent.get("payload", Document.class).getString("action").equals("added");
			
			//all events after pull time , calculate once
			if(pull_time.before(member_time) && pull_time.before(star_time) && pull_time.before(fork_time)){
				pull.append("fork", fork_index);
				pull.append("star", start_count);
				pull.append("insider", member_count);
				pull_index++;
			}else{
				if(!pull_time.before(member_time)){
					if(add){
						member_count++;
					}else{
						member_count--;
					}
					member_index++;
				}
				
				if(pull_time.before(star_time)){
					if(!stargazers.contains(stargazer)){
						start_count++;
						stargazers.add(stargazer);
					}
					star_index++;
				}
				
				if(pull_time.before(fork_time)){
					fork_index++;
				}
			}
			
		}
		
	}
	
	/**
	 * calculate pr level data
	 * @param pullDocument
	 * @return
	 */
	public PullRequest pr_info(Document pullDocument){
		PullRequest pullRequest = new PullRequest();
		
		//analyse pr base info
		String fn = pullDocument.getString("fn");
		int pull_id = pullDocument.getInteger("pull_id",0);
		int commitNum = pullDocument.getInteger("commits");
		int finalChangeSize = pullDocument.getInteger("additions")+pullDocument.getInteger("deletions");
		int finalChangeFile = pullDocument.getInteger("changed_files");
		String login = pullDocument.get("user", Document.class).getString("login");
		String createString = pullDocument.getString("created_at");
		
		//anaylse related commits
		PullRequestFetch pullRequestFetch = new PullRequestFetch();
		List<Document> commits = pullRequestFetch.fetchPullCommits(fn, pull_id);
		
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
			codeChangeMax =codeChangeMax>codeChangeTotal?codeChangeMax:codeChangeTotal;
			
			int change_file = jsonObject.get("files").getAsJsonArray().size();			
			fileChangeTotal+=change_file;
		}
		
		//get other items analysed by other funcs
		int fork = pullDocument.getInteger("fork");
		int star = pullDocument.getInteger("star");
		int insider = pullDocument.getInteger("insider");
		int open_pull = pullDocument.getInteger("open_pull");
		
		int submitter_commits_num = pullDocument.getInteger("submitter_commits_num");
		int submitter_changed_line = pullDocument.getInteger("submitter_changed_line");
		int submitter_issue = pullDocument.getInteger("submitter_issue");
		int submitter_closed_PR = pullDocument.getInteger("submitter_closed_PR");
		int submitter_merged_PR = pullDocument.getInteger("submitter_merged_PR");
		int submitter_PR_close_avg_time = pullDocument.getInteger("submitter_PR_close_avg_time");
		
		pullRequest.setFn(fn);
		pullRequest.setId(pull_id);
		pullRequest.setCommitter(login);
		pullRequest.setCreated_time(createString);
		
		pullRequest.setCommit_num(commitNum);
		pullRequest.setChurn_final_size(finalChangeSize);
		pullRequest.setChurn_total_size(codeChangeTotal);
		pullRequest.setChurn_max_size(codeChangeMax);
		pullRequest.setChurn_file_num(fileChangeTotal);
		pullRequest.setFinal_churn_file_num(finalChangeFile);
		
		pullRequest.setProj_fork(fork);
		pullRequest.setProj_star(star);
		pullRequest.setProj_insider(insider);
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
		String repo_create = jsonObject.get("created_at").getAsString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
		int from_days = 0;
		try {
			from_days = (int) ((sdf.parse(pullRequest.getCreated_time()).getTime()-sdf.parse(repo_create).getTime()) / 1000 / 60 / 60 /24);
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
		int pull_id = pull.getInteger("id");
		boolean merged = (pull.containsKey("merged_at") && pull.get("merged_at") != null)?true:false;
		JsonParser parser = new JsonParser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
		
		int commits_num = 0;
		int changed_line = 0;
		
		JsonObject jsonObject = parser.parse(pull.toJson()).getAsJsonObject();
		String pull_create = jsonObject.get("created_at").getAsString();		
		Date pullTime = sdf.parse(pull_create);	
		if(merged){
			PullRequestFetch pullRequestFetch = new PullRequestFetch();
			List<Document> pullCommits = pullRequestFetch.fetchPullCommits(fn, pull_id);
			Document commit = pullCommits.get(0);
			JsonObject commitJson = parser.parse(commit.toJson()).getAsJsonObject();
			String commit_create = commitJson.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString();
			Date commitTime = sdf.parse(commit_create);
			pullTime = commitTime;
		}
		
		SubmitterDataFetch submitterDataFetch = new SubmitterDataFetch();
		List<Document> commits = submitterDataFetch.fetchPullPersonalCommits(fn, userName);
		for (Document commit : commits) {
			JsonObject commitJson = parser.parse(commit.toJson()).getAsJsonObject();
			String commit_create = commitJson.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString();
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
		String userName = pull.get("user", Document.class).getString("login");
		String fn = pull.getString("fn");
		Date pull_time = sdf.parse(pull.getString("created_at"));
		
		SubmitterDataFetch submitterDataFetch = new SubmitterDataFetch();
		
		int issue = 0;
		List<Document> personalIssues = submitterDataFetch.fetchPersonalIssues(fn, userName);
		for (Document issueDocument : personalIssues) {
			Date create_time = sdf.parse(issueDocument.getString("created_at"));
			if (create_time.before(pull_time)) {
				issue++;
			}else{
				break;
			}
		}
		
		int closedPR = 0;
		int mergedPR = 0;
		int closeTotalTime=0;
		int closeAvgTime = 0;
		List<Document> personalPulls = submitterDataFetch.fetchPersonalClosedPR(fn, userName);
		for (Document personalPull : personalPulls) {
			Date create_time = sdf.parse(personalPull.getString("created_at"));
			if (create_time.before(pull_time)) {
				closedPR++;
				if(personalPull.getBoolean("merged")){
					mergedPR++;
				}
				Date closeTime = sdf.parse(personalPull.getString("closed_at"));
				int closeHour = (int) ((closeTime.getTime()-create_time.getTime()) /1000 / 60 /60);
				closeTotalTime+=closeHour;
			}else{
				break;
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

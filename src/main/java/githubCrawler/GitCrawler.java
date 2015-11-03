package githubCrawler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.conn.tsccm.WaitingThread;

import userInfoFetch.UserDeal;
import utility.AccountUtil;
import utility.MessageSender;
import utility.MongoInfo;
import utility.ValidateInternetConnection;
import utility.ValidateMongoConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

public class GitCrawler {
	private static Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
	//private static Mongo mongo = new Mongo("localhost", 27017);
	private static DB db = mongo.getDB("fuck");
	private static DBCollection repository = db.getCollection("repository");
	private static DBCollection forks = db.getCollection("forks");
	private static DBCollection assignees = db.getCollection("assignees");
	private static DBCollection languages = db.getCollection("languages");
	private static DBCollection stargazers = db.getCollection("stargazers");
	private static DBCollection contributors = db.getCollection("contributors");
	private static DBCollection subscribers = db.getCollection("subscribers");
	private static DBCollection tags = db.getCollection("tags");
	private static DBCollection branches = db.getCollection("branches");
	private static DBCollection gitrefs = db.getCollection("gitrefs");
	private static DBCollection comments = db.getCollection("comments");
	private static DBCollection issue_comment = db
			.getCollection("issuecomment");
	private static DBCollection issue_events = db.getCollection("issueevents");
	private static DBCollection events = db.getCollection("events");
	private static DBCollection contents = db.getCollection("contents");
	private static DBCollection commits = db.getCollection("commits");
	private static DBCollection issues = db.getCollection("issues");
	private static DBCollection pulls = db.getCollection("pulls");
	private static DBCollection commitscache = db.getCollection("commitscache");
	private static DBCollection tree = db.getCollection("tree");
	private static DBCollection repolist = db.getCollection("repolist");
	private static DBObject repositoryArray = null;
	private static ArrayList<DBObject> forksArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> assigneesArray = new ArrayList<DBObject>();
	private static DBObject languagesArray = null;
	private static ArrayList<DBObject> stargazersArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> contributorsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> subscribersArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> tagsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> branchesArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> gitrefsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> commentsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> issuecommentArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> issueeventsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> eventsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> contentsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> commitsArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> issuesArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> pullsArray = new ArrayList<DBObject>();
	private static DBObject treeArray = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*RepoCrawler repoCrawler = new RepoCrawler();
		for (int i = 0; i < 6600; i++) {
			int id = (int) (Math.random() * 44012900 + 1);
			DBCollection repolist = db.getCollection("repolist");
			DBObject idObject = new BasicDBObject();
			idObject.put("id", id);
			String fullName = repoCrawler.getRepoFullName(id);
			if (!fullName.equals("Fail")) {
				repoCrawler.crawl(fullName);
			} else {
				i = i - 1;
			}
		}*/
		/*MessageSender messageSender = new MessageSender();
		DBCursor repoCursor = repolist.find();
		while(repoCursor.hasNext()){
			DBObject repo = repoCursor.next();
			if(repo.get("state").equals("uncompleted")){
				messageSender.sendMessage(repo.get("full_name").toString());
				DBObject before = new BasicDBObject();
				before.put("id", Integer.parseInt(repo.get("id").toString()));
				DBObject after = new BasicDBObject();
				after.put("id", Integer.parseInt(repo.get("id").toString()));
				after.put("full_name", repo.get("full_name").toString());
				after.put("state", "completed");
				repolist.update(before, after);
			}
		}*/
	}

	public void crawl(String fullName) {

		commitscache.drop();
		commitscache = db.getCollection("commitscache");
		
		RepositoryCrawler repositoryCrawler = new RepositoryCrawler();
		ForkCrawler forkCrawler = new ForkCrawler();
		AssigneeCrawler assigneeCrawler = new AssigneeCrawler();
		LanguageCrawler languageCrawler = new LanguageCrawler();
		StargazerCrawler stargazerCrawler = new StargazerCrawler();
		ContributorCrawler contributorCrawler = new ContributorCrawler();
		SubscriberCrawler subscriberCrawler = new SubscriberCrawler();
		TagCrawler tagCrawler = new TagCrawler();
		BranchCrawler branchCrawler = new BranchCrawler();
		GitrefCrawler gitrefCrawler = new GitrefCrawler();
		CommentCrawler commentCrawler = new CommentCrawler();
		IssueCommentCrawler issueCommentCrawler = new IssueCommentCrawler();
		IssueEventCrawler issueEventCrawler = new IssueEventCrawler();
		//EventCrawler eventCrawler = new EventCrawler();
		ContentCrawler contentCrawler = new ContentCrawler();
		CommitCrawler commitCrawler = new CommitCrawler();
		IssueCrawler issueCrawler = new IssueCrawler();
		PullCrawler pullCrawler = new PullCrawler();
		TreeCrawler treeCrawler = new TreeCrawler();
		//DownloadRepository downloadRepository = new DownloadRepository();
		
		repositoryArray = repositoryCrawler.crawlRepository(fullName);
		forksArray = forkCrawler.crawlForks(fullName);
		assigneesArray = assigneeCrawler.crawlAssignees(fullName);
		languagesArray = languageCrawler.crawlLanguages(fullName);
		stargazersArray = stargazerCrawler.crawlStargazers(fullName);
		contributorsArray = contributorCrawler.crawlContributors(fullName);
		subscribersArray = subscriberCrawler.crawlSubscribers(fullName);
		tagsArray = tagCrawler.crawlTags(fullName);
		branchesArray = branchCrawler.crawlBranches(fullName);
		gitrefsArray = gitrefCrawler.crawlGitrefs(fullName);
		commentsArray = commentCrawler.crawlComments(fullName);
		issuecommentArray = issueCommentCrawler.crawlIssueComments(fullName);
		issueeventsArray = issueEventCrawler.crawlIssueEvents(fullName);
		//eventsArray = eventCrawler.crawlEvents(fullName);
		contentsArray = contentCrawler.crawlContents(fullName);
		commitCrawler.crawlCommits(fullName);
		issuesArray = issueCrawler.crawlIssues(fullName);
		pullsArray = pullCrawler.crawlPulls(fullName);
		treeArray = treeCrawler.crawlTree(fullName);
		//downloadRepository.downloadRepository(fullName);
		
		try{
			if(repositoryArray != null){
				repository.save(repositoryArray);
			}
			for (int i = 0; i < forksArray.size(); i++) {
				forks.save(forksArray.get(i));
			}
			for (int i = 0; i < assigneesArray.size(); i++) {
				assignees.save(assigneesArray.get(i));
			}
			if(languagesArray != null){
				languages.save(languagesArray);
			}
			for (int i = 0; i < stargazersArray.size(); i++) {
				stargazers.save(stargazersArray.get(i));
			}
			for (int i = 0; i < contributorsArray.size(); i++) {
				contributors.save(contributorsArray.get(i));
			}
			for (int i = 0; i < subscribersArray.size(); i++) {
				subscribers.save(subscribersArray.get(i));
			}
			for (int i = 0; i < tagsArray.size(); i++) {
				tags.save(tagsArray.get(i));
			}
			for (int i = 0; i < branchesArray.size(); i++) {
				branches.save(branchesArray.get(i));
			}
			for (int i = 0; i < gitrefsArray.size(); i++) {
				gitrefs.save(gitrefsArray.get(i));
			}
			for (int i = 0; i < commentsArray.size(); i++) {
				comments.save(commentsArray.get(i));
			}
			for (int i = 0; i < issuecommentArray.size(); i++) {
				issue_comment.save(issuecommentArray.get(i));
			}
			for (int i = 0; i < issueeventsArray.size(); i++) {
				issue_events.save(issueeventsArray.get(i));
			}
			/*for (int i = 0; i < eventsArray.size(); i++) {
				events.save(eventsArray.get(i));
			}*/
			for (int i = 0; i < contentsArray.size(); i++) {
				contents.save(contentsArray.get(i));
			}
			for (int i = 0; i < issuesArray.size(); i++) {
				issues.save(issuesArray.get(i));
			}
			for (int i = 0; i < pullsArray.size(); i++) {
				pulls.save(pullsArray.get(i));
			}
			if(treeArray != null){
				tree.save(treeArray);
			}
			
			DBCursor cursor = commitscache.find();
			cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (cursor.hasNext()) {
				commits.save(cursor.next());
			}
			cursor.close();
		}catch(Exception e){
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter("log.txt",true);
				fileWriter.write(fullName + "\n");
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while(ValidateInternetConnection.validateInternetConnection() == 0){
				System.out.println("Wait for connecting the internet---------------");
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println("The internet is connected------------");
			
			while(ValidateMongoConnection.validateMongoConnection() <= 0){
				System.out.println("Wait for connecting the mongo---------------");
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println("The mongo is connected------------");
		}

		repositoryArray = null;
		forksArray.clear();
		assigneesArray.clear();
		languagesArray = null;
		stargazersArray.clear();
		contributorsArray.clear();
		subscribersArray.clear();
		tagsArray.clear();
		branchesArray.clear();
		gitrefsArray.clear();
		commentsArray.clear();
		issuecommentArray.clear();
		issueeventsArray.clear();
		eventsArray.clear();
		contentsArray.clear();
		commitsArray.clear();
		issuesArray.clear();
		pullsArray.clear();
		treeArray = null;

		/*crawlTree(fullName);
		downloadRepository(fullName);
		for (int i = 0; i < treeArray.size(); i++) {
			tree.save(treeArray.get(i));
		}*/
		
		System.out.println("complete crawl!");
	}

	/*need exception opertion later*/
	/*public String getRepoFullName(int id){
		String urlString = indexURL + (id - 1);
		try {
			url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String string11 = reader.readLine();
			System.out.println(id);
			JsonArray jsonArray = new JsonParser().parse(string11)
					.getAsJsonArray();
			if (jsonArray.get(0).getAsJsonObject().get("id").getAsInt() == id) {
				DBObject object = new BasicDBObject();
				object.put("id", id);
				if (repository.find(object).size() != 0) {
					return "Fail";
				} else {
					String str = jsonArray.get(0).getAsJsonObject().get("url")
							.toString();
					String string = str.substring(1, str.length() - 1);
					url = new URL(string);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					if (urlConnection.getResponseCode() == 403) {
						return "Fail";
					} else {
						String result = jsonArray.get(0).getAsJsonObject()
								.get("full_name").toString();
						return result.substring(1, result.length() - 1);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "Fail";
		}
		return "Fail";

	}*/
}

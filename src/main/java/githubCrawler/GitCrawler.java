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
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.conn.tsccm.WaitingThread;

import userInfoFetch.UserDeal;
import utility.AccountUtil;
import utility.GetHostName;
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
	private static DB db = mongo.getDB("Experiment");
	
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
	private static DBCollection tree = db.getCollection("tree");
	private static DBCollection repolist = db.getCollection("repolist");
	private static DBCollection complete = db.getCollection("complete");
	private static DBCollection commitnumber = db.getCollection("commitnumber");
	private static DBCollection user = db.getCollection("user");
	
	private static DBCollection usercache = db.getCollection(GetHostName.getHostName() + "usercache");
	//private static DBCollection followercache = db.getCollection("followercache");
	//private static DBCollection userRepocache = db.getCollection("userRepocache");
	//private static DBCollection follower = db.getCollection("follower");
	//private static DBCollection userRepo = db.getCollection("userRepo");
	private static DBCollection forkcache = db.getCollection(GetHostName.getHostName() + "forkcache");
	private static DBCollection assigneecache = db.getCollection(GetHostName.getHostName() + "assigneecache");
	private static DBCollection stargazercache = db.getCollection(GetHostName.getHostName() + "stargazercache");
	private static DBCollection contributorcache = db.getCollection(GetHostName.getHostName() + "contributorcache");
	private static DBCollection subscribercache = db.getCollection(GetHostName.getHostName() + "subscribercache");
	private static DBCollection tagcache = db.getCollection(GetHostName.getHostName() + "tagcache");
	private static DBCollection branchcache = db.getCollection(GetHostName.getHostName() + "branchcache");
	private static DBCollection gitrefcache = db.getCollection(GetHostName.getHostName() + "gitrefcache");
	private static DBCollection commentcache = db.getCollection(GetHostName.getHostName() + "commentcache");
	private static DBCollection issuecommentcache = db.getCollection(GetHostName.getHostName() + "issuecommentcache");
	private static DBCollection issueeventcache = db.getCollection(GetHostName.getHostName() + "issueeventcache");
	private static DBCollection contentcache = db.getCollection(GetHostName.getHostName() + "contentcache");
	private static DBCollection commitscache = db.getCollection(GetHostName.getHostName() + "commitscache");
	private static DBCollection issuecache = db.getCollection(GetHostName.getHostName() + "issuecache");
	private static DBCollection pullcache = db.getCollection(GetHostName.getHostName() + "pullcache");
	
	private static DBObject repositoryArray = null;
	private static DBObject languagesArray = null;
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

		usercache.drop();
		forkcache.drop();
		assigneecache.drop();
		stargazercache.drop();
		contributorcache.drop();
		subscribercache.drop();
		tagcache.drop();
		branchcache.drop();
		gitrefcache.drop();
		commentcache.drop();
		issuecommentcache.drop();
		issueeventcache.drop();
		contentcache.drop();
		commitscache.drop();
		issuecache.drop();
		pullcache.drop();
		//followercache.drop();
		//userRepocache.drop();
		//followercache = db.getCollection("followercache");
		//userRepocache = db.getCollection("userRepocache");
		
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
		DownloadRepository downloadRepository = new DownloadRepository();
		
		//downloadRepository.downloadRepository(fullName);
		repositoryArray = repositoryCrawler.crawlRepository(fullName);
		forkCrawler.crawlForks(fullName);
		//assigneeCrawler.crawlAssignees(fullName);
		languagesArray = languageCrawler.crawlLanguages(fullName);
		stargazerCrawler.crawlStargazers(fullName);
		//contributorCrawler.crawlContributors(fullName);
		subscriberCrawler.crawlSubscribers(fullName);
		tagCrawler.crawlTags(fullName);
		branchCrawler.crawlBranches(fullName);
		gitrefCrawler.crawlGitrefs(fullName);
		commentCrawler.crawlComments(fullName);
		//issueCommentCrawler.crawlIssueComments(fullName);
		//issueEventCrawler.crawlIssueEvents(fullName);
		//eventsArray = eventCrawler.crawlEvents(fullName);
		contentCrawler.crawlContents(fullName);
		commitCrawler.crawlCommits(fullName);;
		issueCrawler.crawlIssues(fullName);
		//pullCrawler.crawlPulls(fullName);
		treeArray = treeCrawler.crawlTree(fullName);
		
		try{
			if(repositoryArray != null){
				repository.save(repositoryArray);
			}
			
			DBCursor forkcursor = forkcache.find();
			forkcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (forkcursor.hasNext()) {
				forks.save(forkcursor.next());
			}
			forkcursor.close();
			
			/*DBCursor assigneecursor = assigneecache.find();
			assigneecursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (assigneecursor.hasNext()) {
				assignees.save(assigneecursor.next());
			}
			assigneecursor.close();*/
			
			if(languagesArray != null){
				languages.save(languagesArray);
			}
			
			DBCursor stargazercursor = stargazercache.find();
			stargazercursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (stargazercursor.hasNext()) {
				stargazers.save(stargazercursor.next());
			}
			stargazercursor.close();
			
			/*DBCursor contributorcursor = contributorcache.find();
			contributorcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (contributorcursor.hasNext()) {
				contributors.save(contributorcursor.next());
			}
			contributorcursor.close();*/
			
			DBCursor subscribercursor = subscribercache.find();
			subscribercursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (subscribercursor.hasNext()) {
				subscribers.save(subscribercursor.next());
			}
			subscribercursor.close();
			
			DBCursor tagcursor = tagcache.find();
			tagcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (tagcursor.hasNext()) {
				tags.save(tagcursor.next());
			}
			tagcursor.close();
			
			DBCursor branchcursor = branchcache.find();
			branchcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (branchcursor.hasNext()) {
				branches.save(branchcursor.next());
			}
			branchcursor.close();
			
			DBCursor gitrefcursor = gitrefcache.find();
			gitrefcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (gitrefcursor.hasNext()) {
				gitrefs.save(gitrefcursor.next());
			}
			gitrefcursor.close();
			
			/*for (int i = 0; i < commentsArray.size(); i++) {
				comments.save(commentsArray.get(i));
			}*/
			
			DBCursor commentcursor = commentcache.find();
			commentcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (commentcursor.hasNext()) {
				comments.save(commentcursor.next());
			}
			commentcursor.close();
			
			/*DBCursor issuecommentcursor = issuecommentcache.find();
			issuecommentcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (issuecommentcursor.hasNext()) {
				issue_comment.save(issuecommentcursor.next());
			}
			issuecommentcursor.close();
			
			DBCursor issueeventcursor = issueeventcache.find();
			issueeventcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (issueeventcursor.hasNext()) {
				issue_events.save(issueeventcursor.next());
			}
			issueeventcursor.close();*/
			
			/*for (int i = 0; i < eventsArray.size(); i++) {
				events.save(eventsArray.get(i));
			}*/
			
			DBCursor contentcursor = contentcache.find();
			contentcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (contentcursor.hasNext()) {
				contents.save(contentcursor.next());
			}
			contentcursor.close();
			
			DBCursor issuecursor = issuecache.find();
			issuecursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (issuecursor.hasNext()) {
				issues.save(issuecursor.next());
			}
			issuecursor.close();
			
			/*DBCursor pullcursor = pullcache.find();
			pullcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (pullcursor.hasNext()) {
				pulls.save(pullcursor.next());
			}
			pullcursor.close();*/
			
			if(treeArray != null){
				tree.save(treeArray);
			}
			
			DBObject commitNum = new BasicDBObject();
			commitNum.put("commitnumber", commitCrawler.commitNumber);
			commitNum.put("fn", fullName);
			commitnumber.save(commitNum);
			commitCrawler.commitNumber = 0;
			
			DBCursor cursor = commitscache.find();
			cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while (cursor.hasNext()) {
				commits.save(cursor.next());
			}
			cursor.close();
			
			DBCursor userCursor = usercache.find();
			userCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while(userCursor.hasNext()){
				DBObject object = userCursor.next();
				DBObject judge = new BasicDBObject();
				judge.put("login", object.get("login").toString());
				if(user.find(judge).limit(1).size() == 0){
					user.save(object);
				}
			}
			userCursor.close();
			
			/*DBCursor followerCursor = followercache.find();
			followerCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while(followerCursor.hasNext()){
				follower.save(followerCursor.next());
			}
			followerCursor.close();
			
			DBCursor userRepoCursor = userRepocache.find();
			userRepoCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
			while(userRepoCursor.hasNext()){
				userRepo.save(userRepoCursor.next());
			}
			userRepoCursor.close();*/
			
			DBObject object = new BasicDBObject();
			object.put("full_name", fullName);
			object.put("state", "completed");
			object.put("date", Calendar.getInstance().getTime());
			complete.save(object);
		}catch(Exception e){
			e.printStackTrace();
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
					// TODO Auto-generated catch blockk
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
		languagesArray = null;
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

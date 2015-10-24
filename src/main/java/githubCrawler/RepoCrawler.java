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

import javax.swing.plaf.basic.BasicOptionPaneUI;

import org.apache.commons.codec.binary.Base64;

import userInfoFetch.UserDeal;
import utility.AccountUtil;
import utility.MessageSender;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class RepoCrawler {
	private static URL url;
	private static HttpURLConnection urlConnection;
	private static BufferedReader reader;
	private static int rateCounter = 0;
	private static String rootURL = "https://api.github.com/repos/";
	private static String indexURL = "https://api.github.com/repositories?since=";
	private static String authorization = "";
	private static Mongo mongo = new Mongo("121.41.118.191", 27017);
	private static DB db = mongo.getDB("ghcrawler");
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
	private static ArrayList<DBObject> repositoryArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> forksArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> assigneesArray = new ArrayList<DBObject>();
	private static ArrayList<DBObject> languagesArray = new ArrayList<DBObject>();
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
	private static ArrayList<DBObject> treeArray = new ArrayList<DBObject>();
	private static String treeStructure = "";
	private static int fileNumber = 0;

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

		treeStructure = "";
		fileNumber = 0;
		commitscache.drop();
		commitscache = db.getCollection("commitscache");

		crawlRepository(fullName);
		crawlForks(fullName);
		crawlAssignees(fullName);
		crawlLanguages(fullName);
		crawlStargazers(fullName);
		crawlContributors(fullName);
		crawlSubscribers(fullName);
		crawlTags(fullName);
		crawlBranches(fullName);
		crawlGitrefs(fullName);
		crawlComments(fullName);
		crawlIssueComment(fullName);
		crawlIssueEvents(fullName);
		crawlEvents(fullName);
		crawlContents(fullName);
		crawlCommits(fullName);
		crawlIssues(fullName);
		crawlPulls(fullName);

		try{
			for (int i = 0; i < repositoryArray.size(); i++) {
				repository.save(repositoryArray.get(i));
			}
			for (int i = 0; i < forksArray.size(); i++) {
				forks.save(forksArray.get(i));
			}
			for (int i = 0; i < assigneesArray.size(); i++) {
				assignees.save(assigneesArray.get(i));
			}
			for (int i = 0; i < languagesArray.size(); i++) {
				languages.save(languagesArray.get(i));
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
			for (int i = 0; i < eventsArray.size(); i++) {
				events.save(eventsArray.get(i));
			}
			for (int i = 0; i < contentsArray.size(); i++) {
				contents.save(contentsArray.get(i));
			}
			for (int i = 0; i < issuesArray.size(); i++) {
				issues.save(issuesArray.get(i));
			}
			for (int i = 0; i < pullsArray.size(); i++) {
				pulls.save(pullsArray.get(i));
			}
			DBCursor cursor = commitscache.find();
			while (cursor.hasNext()) {
				commits.save(cursor.next());
			}
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
			System.exit(0);
		}

		repositoryArray.clear();
		forksArray.clear();
		assigneesArray.clear();
		languagesArray.clear();
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

		/*crawlTree(fullName);
		downloadRepository(fullName);
		for (int i = 0; i < treeArray.size(); i++) {
			tree.save(treeArray.get(i));
		}*/
		
		System.out.println("complete crawl!");
	}

	public void downloadRepository(String fullName){
		System.out.println("Start download repository------------------------");
		try {
			String downloadURL = "https://github.com/" + fullName + ".git";
			//Runtime.getRuntime().exec("git clone " + downloadURL);
			Runtime.getRuntime().exec("git clone " + downloadURL, null, new File("H:\\GitRepo"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void crawlTree(String fullName) {
		System.out.println("Start crawl tree------------------------");
		String treeURL = rootURL + fullName + "/contents";
		String resultString = "";
		try {
			url = new URL(treeURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				if (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					treeStructure = "{'name':" + "'" + fullName + "'"
							+ ",'children':[";
					for (int i = 0; i < jsonArray.size(); i++) {
						if (jsonArray.get(i).getAsJsonObject().get("type")
								.getAsString().equals("file")) {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "'}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "'},";
							}
							fileNumber = fileNumber + 1;
						} else {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("_links") != null && jsonArray.get(i).getAsJsonObject().get("_links").getAsJsonObject().get("git") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("_links").getAsJsonObject()
											.get("git").getAsString());
								}
								treeStructure = treeStructure + "]}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("name").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("_links") != null && jsonArray.get(i).getAsJsonObject().get("_links").getAsJsonObject().get("git") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("_links").getAsJsonObject()
											.get("git").getAsString());
								}
								treeStructure = treeStructure + "]},";
							}
						}
					}
					treeStructure = treeStructure + "]}";

					DBObject object = new BasicDBObject();
					object.put("tree", treeStructure);
					object.put("filenumber", fileNumber);
					object.put("fn", fullName);
					treeArray.add(object);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void analyseTree(String treeURL) {
		String resultString = "";
		try {
			url = new URL(treeURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				if (resultString != null && !resultString.equals("[]")) {
					JsonObject jsonObject = new JsonParser()
							.parse(resultString).getAsJsonObject();
					JsonArray jsonArray = new JsonParser().parse(
							jsonObject.get("tree").toString()).getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						if (jsonArray.get(i).getAsJsonObject().get("type")
								.getAsString().equals("blob")) {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "'}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "'},";
							}
							fileNumber = fileNumber + 1;
						} else {
							if (i == jsonArray.size() - 1) {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("url") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("url").getAsString());
								}
								treeStructure = treeStructure + "]}";
							} else {
								treeStructure = treeStructure
										+ "{'name':"
										+ "'"
										+ jsonArray.get(i).getAsJsonObject()
												.get("path").getAsString()
										+ "','children':[";
								if(jsonArray.get(i).getAsJsonObject().get("url") != null){
									analyseTree(jsonArray.get(i).getAsJsonObject()
											.get("url").getAsString());
								}
								treeStructure = treeStructure + "]},";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlPulls(String fullName) {
		System.out.println("Start crawl pulls------------------------");
		String pullsURL = rootURL + fullName + "/pulls?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(pullsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						String concreteURL = jsonArray.get(i).getAsJsonObject()
								.get("url").toString();
						url = new URL(concreteURL.substring(1,
								concreteURL.length() - 1));
						urlConnection = (HttpURLConnection) url
								.openConnection();
						rateCounter = rateCounter + 1;
						setAuthorization(rateCounter);
						urlConnection.setRequestProperty("Authorization",
								authorization);
						reader = new BufferedReader(new InputStreamReader(
								urlConnection.getInputStream()));
						DBObject object = (BasicDBObject) JSON.parse(reader
								.readLine());
						object.put("fn", fullName);
						pullsArray.add(object);
					}
					index = index + 1;
					System.out.println(pullsURL + index);
					url = new URL(pullsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlIssues(String fullName) {
		System.out.println("Start crawl issues------------------------");
		String issuesURL = rootURL + fullName + "/issues?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(issuesURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						String concreteURL = jsonArray.get(i).getAsJsonObject()
								.get("url").toString();
						url = new URL(concreteURL.substring(1,
								concreteURL.length() - 1));
						urlConnection = (HttpURLConnection) url
								.openConnection();
						rateCounter = rateCounter + 1;
						setAuthorization(rateCounter);
						urlConnection.setRequestProperty("Authorization",
								authorization);
						reader = new BufferedReader(new InputStreamReader(
								urlConnection.getInputStream()));
						DBObject object = (BasicDBObject) JSON.parse(reader
								.readLine());
						object.put("fn", fullName);
						issuesArray.add(object);
					}
					index = index + 1;
					System.out.println(issuesURL + index);
					url = new URL(issuesURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlCommits(String fullName) {
		System.out.println("Start crawl commits------------------------");
		String commitsURL = rootURL + fullName + "/commits?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(commitsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						commitscache.save(object);
					}
					index = index + 1;
					System.out.println(commitsURL + index);
					url = new URL(commitsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlContents(String fullName) {
		System.out.println("Start crawl contents------------------------");
		String contentsURL = rootURL + fullName + "/contents";
		String resultString = "";
		try {
			url = new URL(contentsURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				if (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						contentsArray.add(object);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlEvents(String fullName) {
		System.out.println("Start crawl events------------------------");
		String eventsURL = rootURL + fullName + "/events?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(eventsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			while (urlConnection.getResponseCode() != 422
					&& urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				if (resultString == null || resultString.equals("[]")) {
					break;
				} else {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						eventsArray.add(object);
					}
					index = index + 1;
					System.out.println(eventsURL + index);
					url = new URL(eventsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlIssueEvents(String fullName) {
		System.out.println("Start crawl issue_events------------------------");
		String issueeventsURL = rootURL + fullName + "/issues/events?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(issueeventsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						issueeventsArray.add(object);
					}
					index = index + 1;
					System.out.println(issueeventsURL + index);
					url = new URL(issueeventsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlIssueComment(String fullName) {
		System.out.println("Start crawl issue_comment------------------------");
		String issuecommentURL = rootURL + fullName + "/issues/comments?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(issuecommentURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						issuecommentArray.add(object);
					}
					index = index + 1;
					System.out.println(issuecommentURL + index);
					url = new URL(issuecommentURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlComments(String fullName) {
		System.out.println("Start crawl comments------------------------");
		String commentsURL = rootURL + fullName + "/comments?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(commentsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						commentsArray.add(object);
					}
					index = index + 1;
					System.out.println(commentsURL + index);
					url = new URL(commentsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlGitrefs(String fullName) {
		System.out.println("Start crawl git_refs------------------------");
		String gitrefsURL = rootURL + fullName + "/git/refs?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(gitrefsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						gitrefsArray.add(object);
					}
					index = index + 1;
					System.out.println(gitrefsURL + index);
					url = new URL(gitrefsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlBranches(String fullName) {
		System.out.println("Start crawl branches------------------------");
		String branchesURL = rootURL + fullName + "/branches?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(branchesURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						branchesArray.add(object);
					}
					index = index + 1;
					System.out.println(branchesURL + index);
					url = new URL(branchesURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlTags(String fullName) {
		System.out.println("Start crawl tags------------------------");
		String tagsURL = rootURL + fullName + "/tags?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(tagsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						tagsArray.add(object);
					}
					index = index + 1;
					System.out.println(tagsURL + index);
					url = new URL(tagsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlSubscribers(String fullName) {
		System.out.println("Start crawl subscribers------------------------");
		String subscribersURL = rootURL + fullName + "/subscribers?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(subscribersURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						subscribersArray.add(object);
					}
					index = index + 1;
					System.out.println(subscribersURL + index);
					url = new URL(subscribersURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlContributors(String fullName) {
		System.out.println("Start crawl contributors------------------------");
		String contributorsURL = rootURL + fullName + "/contributors?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(contributorsURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						contributorsArray.add(object);
						
						UserDeal.fetchUser(object.get("login").toString(), Integer.parseInt(object.get("id").toString()));
					}
					index = index + 1;
					System.out.println(contributorsURL + index);
					url = new URL(contributorsURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlStargazers(String fullName) {
		System.out.println("Start crawl stargazers------------------------");
		String stargazersURL = rootURL + fullName + "/stargazers?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(stargazersURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						stargazersArray.add(object);
					}
					index = index + 1;
					System.out.println(stargazersURL + index);
					url = new URL(stargazersURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlLanguages(String fullName) {
		System.out.println("Start crawl languages------------------------");
		String languagesURL = rootURL + fullName + "/languages";
		try {
			url = new URL(languagesURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				String resultString = reader.readLine();
				if (resultString != null && !resultString.equals("{}")) {
					DBObject object = (BasicDBObject) JSON.parse(resultString);
					object.put("fn", fullName);
					languagesArray.add(object);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlAssignees(String fullName) {
		System.out.println("Start crawl assignees------------------------");
		String assigneesURL = rootURL + fullName + "/assignees?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(assigneesURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						assigneesArray.add(object);
						
						UserDeal.fetchUser(object.get("login").toString(), Integer.parseInt(object.get("id").toString()));
					}
					index = index + 1;
					System.out.println(assigneesURL + index);
					url = new URL(assigneesURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlForks(String fullName) {
		System.out.println("Start crawl forks------------------------");
		String forksURL = rootURL + fullName + "/forks?page=";
		String resultString = "";
		int index = 1;
		try {
			url = new URL(forksURL + index);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			if (urlConnection.getResponseCode() != 409
					&& urlConnection.getResponseCode() != 404) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				resultString = reader.readLine();
				while (resultString != null && !resultString.equals("[]")) {
					JsonArray jsonArray = new JsonParser().parse(resultString)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						object.put("fn", fullName);
						forksArray.add(object);
					}
					index = index + 1;
					System.out.println(forksURL + index);
					url = new URL(forksURL + index);
					urlConnection = (HttpURLConnection) url.openConnection();
					rateCounter = rateCounter + 1;
					setAuthorization(rateCounter);
					urlConnection.setRequestProperty("Authorization",
							authorization);
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream()));
					resultString = reader.readLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crawlRepository(String fullName) {
		System.out.println("Start crawl repository------------------------");
		String repositoryURL = rootURL + fullName;
		try {
			url = new URL(repositoryURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			rateCounter = rateCounter + 1;
			setAuthorization(rateCounter);
			urlConnection.setRequestProperty("Authorization", authorization);
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			DBObject object = (BasicDBObject) JSON.parse(reader.readLine());
			repositoryArray.add(object);
			
			DBObject owner = (BasicDBObject)object.get("owner");
			UserDeal.fetchUser(owner.get("login").toString(), Integer.parseInt(owner.get("id").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRepoFullName(int id){
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

	}

	public void setAuthorization(int counter) {
		Charset charset = Charset.forName("UTF-8");
		String account = "";
		if (counter >= 0 && counter <= 4900) {
			account = AccountUtil.getLoginPassword();
			authorization = "Basic "
					+ new String(
							Base64.encodeBase64(account.getBytes(charset)),
							charset);
		} else if (counter > 4900 && counter <= 9800) {
			account = AccountUtil.getLoginPassword();
			authorization = "Basic "
					+ new String(
							Base64.encodeBase64(account.getBytes(charset)),
							charset);
		} else if (counter > 9800 && counter <= 14700) {
			account = AccountUtil.getLoginPassword();
			authorization = "Basic "
					+ new String(
							Base64.encodeBase64(account.getBytes(charset)),
							charset);
		} else if (counter > 14700 && counter <= 19600) {
			account = AccountUtil.getLoginPassword();
			authorization = "Basic "
					+ new String(
							Base64.encodeBase64(account.getBytes(charset)),
							charset);
		} else {
			account = AccountUtil.getLoginPassword();
			authorization = "Basic "
					+ new String(
							Base64.encodeBase64(account.getBytes(charset)),
							charset);
			rateCounter = 0;
		}
	}
}

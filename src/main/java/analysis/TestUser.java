package analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

import utility.MongoInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class TestUser {
	private static Log log = LogFactory.getLog(TestUser.class.getName());
	private static String[] types = { "CommitCommentEvent", "CreateEvent",
			"DeleteEvent", "DeploymentEvent", "DeploymentStatusEvent",
			"DownloadEvent", "FollowEvent", "ForkEvent", "ForkApplyEvent",
			"GistEvent", "GollumEvent", "IssueCommentEvent", "IssueEvent",
			"MemberEvent", "MembershipEvent", "PageBuildEvent", "PublicEvent",
			"PullRequestEvent", "PullRequestReviewCommentEvent", "PushEvent",
			"ReleaseEvent", "RepositoryEvent", "StatusEvent", "TeamAddEvent",
			"WatchEvent" };
	//get the active time of all repositories,25 types of events

	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient(MongoInfo.getMongoServerIp(),
				27017);
		MongoDatabase eventsDatabase = mongoClient.getDatabase("historyevents");
		FindIterable<Document> eventFindIterable = eventsDatabase
				.getCollection("spec_events").find();
		Map<Integer,int[]> items = new HashMap<Integer,int[]>();
		for (Document document : eventFindIterable) {
			String json = document.toJson();
			JsonParser parser = new JsonParser();
			JsonObject event = parser.parse(json).getAsJsonObject();
			String type = event.get("type").getAsString();
			int repoId = 0;
			if(event.has("repo")){
				repoId = event.get("repo").getAsJsonObject().get("id").getAsInt();
			}else if(event.has("repository")){
				repoId = event.get("repository").getAsJsonObject().get("id").getAsInt();
			}
			int index = 0;
			for (int i = 0; i < types.length; i++) {
				if(type.equals(types[i])){
					index = i;
					System.out.println(type);
				}
			}
			if(!items.containsKey(repoId)){
				System.out.println(repoId);
				int[] numbers = new int[types.length];
				numbers[index] = 1;
				items.put(repoId, numbers);
			}else{
				int[] numbers = items.get(repoId);
				numbers[index]++;
			}

		}

		try{
			FileWriter fw = new FileWriter("event_data",true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			Set<Integer> keys = items.keySet();
			for (Integer key : keys) {
				String line = ""+key+"\t";
				int[] nums = items.get(key);
				for (int i : nums) {
					line += i +"\t";
				}
				line+="\n";
				bw.write(line);
			}
			bw.flush();
			bw.close();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		mongoClient.close();
//		IssueCommentEvent
//		PullRequestReviewCommentEvent
//		GollumEvent
//		WatchEvent
//		MemberEvent
	}

}

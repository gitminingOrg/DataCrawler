package hehe;

import githubCrawler.AssigneeCrawler;
import githubCrawler.ContributorCrawler;
import githubCrawler.PullCrawler;
import utility.GetHostName;
import utility.MongoInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class RepoReFilter {
	
	public static void main(String[] args){
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("NewProject");
		DB db1 = mongo.getDB("Experiment");
		DBCollection repocondition = db.getCollection("repocondition");
		DBCursor rc = repocondition.find();
		rc.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		
		AssigneeCrawler assigneeCrawler = new AssigneeCrawler();
		ContributorCrawler contributorCrawler = new ContributorCrawler();
		PullCrawler pullCrawler = new PullCrawler();
		
		DBCollection assigneecache = db1.getCollection(GetHostName.getHostName() + "assigneecache");
		DBCollection contributorcache = db1.getCollection(GetHostName.getHostName() + "contributorcache");
		DBCollection pullcache = db1.getCollection(GetHostName.getHostName() + "pullcache");
		DBCollection usercache = db1.getCollection(GetHostName.getHostName() + "usercache");
		DBCollection assignee = db1.getCollection("assignees");
		DBCollection contributor = db1.getCollection("contributors");
		DBCollection pull = db1.getCollection("pulls");
		DBCollection user = db1.getCollection("user");
		DBCollection repocondition1 = db1.getCollection("repocondition");
		DBCollection mergedpr = db1.getCollection("mergedpr");
		
		DBCursor assCursor = assigneecache.find();
		assCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		int count = 0;
		int insider = 0;
		int outsider = 0;
		int contributornum = 0;
		int mpr = 0;
		int judge1 = 0;
		
		while(rc.hasNext()){
			DBObject object = rc.next();
			if(object.get("fn").equals("Activiti/Activiti")){
				judge1 = 1;
			}
			if(judge1 == 1){
				if(Integer.parseInt(object.get("closepull").toString()) != Integer.parseInt(object.get("closeissue").toString())){
					assigneecache.drop();
					contributorcache.drop();
					pullcache.drop();
					usercache.drop();
					count = 0;
					
					assigneeCrawler.crawlAssignees(object.get("fn").toString());
					contributorCrawler.crawlContributors(object.get("fn").toString());
					pullCrawler.crawlPulls(object.get("fn").toString());
					
					assCursor = assigneecache.find();
					while(assCursor.hasNext()){
						DBObject test = new BasicDBObject();
						test.put("login", assCursor.next().get("login"));
						if(contributorcache.find(test).size() > 0){
							count ++;
							//System.out.println(count);
						}
					}
					insider = assigneecache.find().size();
					outsider = contributorcache.find().size() - count;
					contributornum = contributorcache.find().size();
					
					if(outsider >= insider){
						DBObject test1 = new BasicDBObject();
						test1.put("merged", true);
						
						mpr = pullcache.find(test1).size();
						if(mpr / 1.0 / pullcache.find().size() >= 0.1){
							DBObject rc1 = new BasicDBObject();
							rc1.put("java", object.get("java"));
							rc1.put("closepull", object.get("closepull"));
							rc1.put("closeissue", object.get("closeissue"));
							rc1.put("mpr", mpr);
							rc1.put("outsider", outsider);
							rc1.put("insider", insider);
							rc1.put("contributor", contributornum);
							rc1.put("fn", object.get("fn"));
							repocondition1.save(rc1);
							
							DBCursor assigneecursor = assigneecache.find();
							assigneecursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
							while (assigneecursor.hasNext()) {
								assignee.save(assigneecursor.next());
							}
							assigneecursor.close();
							
							DBCursor contributorcursor = contributorcache.find();
							contributorcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
							while (contributorcursor.hasNext()) {
								contributor.save(contributorcursor.next());
							}
							contributorcursor.close();
							
							DBCursor userCursor = usercache.find();
							userCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
							while(userCursor.hasNext()){
								DBObject object111 = userCursor.next();
								DBObject judge = new BasicDBObject();
								judge.put("login", object111.get("login").toString());
								if(user.find(judge).limit(1).size() == 0){
									user.save(object111);
								}
							}
							userCursor.close();
							
							DBCursor pullcursor = pullcache.find();
							pullcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
							while (pullcursor.hasNext()) {
								DBObject pulltest = pullcursor.next();
								if(pulltest.get("merged").toString().equals("true")){
									DBObject object2 = new BasicDBObject();
									object2.put("pr_id", pulltest.get("id"));
									object2.put("pr_number", pulltest.get("number"));
									object2.put("patch_url", pulltest.get("patch_url"));
									object2.put("diff_url", pulltest.get("diff_url"));
									object2.put("fn", pulltest.get("fn"));
									mergedpr.save(object2);
									pull.save(pulltest);
								}else{
									pull.save(pulltest);
								}
							}
							pullcursor.close();
						}
					}			
				}
			}
		}
	}
}

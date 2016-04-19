package utility;

import githubCrawler.GitCrawler;
import githubCrawler.GitrefCrawler;
import githubCrawler.IssueCrawler;
import githubCrawler.PullCrawler;
import githubCrawler.RepoCrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageReceiver {
	private static final String TASK_QUEUE_NAME = "fixpullandissue";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setAutomaticRecoveryEnabled(true);
			factory.setHost("121.41.118.191");
			factory.setUsername("owen");
			factory.setPassword("owen");
			factory.setVirtualHost("/");
			factory.setPort(5672);
			Connection connection = factory.newConnection();
			final Channel channel = connection.createChannel();
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

			int prefetch = 1;
			channel.basicQos(prefetch);

			final Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					// TODO Auto-generated method stub
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
					try {
						handleTaskA(message);
					} finally {
						System.out.println(" [x] Done");
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}
			};
			channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			
			//need server connection judgement
			MessageReceiver.main(null);
		}
	}
	
	public static void handleTaskA(String message) {
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection pullcache = db.getCollection(GetHostName.getHostName() + "pullcache");
		DBCollection issuecache = db.getCollection(GetHostName.getHostName() + "issuecache");
		DBCollection pulls = db.getCollection("pullscp");
		DBCollection issues = db.getCollection("issuescp");
		
		pullcache.drop();
		issuecache.drop();
		
		PullCrawler pullCrawler = new PullCrawler();
		IssueCrawler issueCrawler = new IssueCrawler();
		issueCrawler.crawlIssues(message);
		pullCrawler.crawlPulls(message);
		
		DBCursor issuecursor = issuecache.find();
		issuecursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		while (issuecursor.hasNext()) {
			issues.save(issuecursor.next());
		}
		issuecursor.close();
		
		DBCursor pullcursor = pullcache.find();
		pullcursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		while (pullcursor.hasNext()) {
			pulls.save(pullcursor.next());
		}
		pullcursor.close();
	}
		
	
	public static void handleTask(String message) {
		System.out.println(message);
		GitCrawler gitCrawler = new GitCrawler();
		
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("Experiment");
		//DBCollection repolist = db.getCollection("repolist");
		DBCollection repository = db.getCollection("repository");
		DBCollection complete = db.getCollection("complete");
		DBObject judge = new BasicDBObject();
		judge.put("full_name", message);
		
		int number = 0;
		int rnumber = 0;
		
		if(repository.count() != 0){
			try {
				number = repository.find(judge).count();
			} catch (Exception e) {
				// TODO: handle exception
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
				
				number = repository.find(judge).count();
			}
		}
		
		if(number == 0){
			gitCrawler.crawl(message);
			/*DBObject object = new BasicDBObject();
			object.put("full_name", message);
			DBObject repo = repolist.find(object).next();
			
			DBObject before = new BasicDBObject();
			before.put("id", Integer.parseInt(repo.get("id").toString()));
			DBObject after = new BasicDBObject();
			after.put("id", Integer.parseInt(repo.get("id").toString()));
			after.put("full_name", repo.get("full_name").toString());
			after.put("state", "completed");
			repolist.update(before, after);*/
		}else{
			/*DBObject object = new BasicDBObject();
			object.put("full_name", message);
			DBObject repo = repolist.find(object).next();
			
			DBObject before = new BasicDBObject();
			before.put("id", Integer.parseInt(repo.get("id").toString()));
			DBObject after = new BasicDBObject();
			after.put("id", Integer.parseInt(repo.get("id").toString()));
			after.put("full_name", repo.get("full_name").toString());
			after.put("state", "completed");
			repolist.update(before, after);*/
			DBObject object = new BasicDBObject();
			object.put("full_name", message);
			if(complete.count() == 0){
				FileWriter fileWriter;
				try {
					fileWriter = new FileWriter("log1.txt",true);
					fileWriter.write(message + "\n");
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				try {
					rnumber = complete.find(object).count();
				} catch (Exception e) {
					// TODO: handle exception
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
					
					rnumber = complete.find(object).count();
				}
				
				if(rnumber == 0){
					FileWriter fileWriter;
					try {
						fileWriter = new FileWriter("log1.txt",true);
						fileWriter.write(message + "\n");
						fileWriter.flush();
						fileWriter.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}

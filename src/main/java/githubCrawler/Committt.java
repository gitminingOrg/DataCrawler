package githubCrawler;

import java.io.IOException;

import utility.MessageReceiver;
import utility.MessageSender;
import utility.MongoInfo;
import utility.ValidateInternetConnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Committt {
	private static final String TASK_QUEUE_NAME = "commit_queue1";

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
						handleTask(message);
					} finally {
						System.out.println(" [x] Done");
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}
			};
			channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void handleTaskA(String message){
		System.out.println(message);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void handleTask(String message) {
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection repos = db.getCollection("commentcondition");
		DBCollection commit = db.getCollection("commitsbyapi");
		DBCollection commitNum = db.getCollection("commitnumberbyapi");
		GitCommit gitCommit = new GitCommit();
		
		DBObject object = new BasicDBObject();
		object.put("fn", message);
		
		if(Integer.parseInt(repos.find(object).next().get("commitNumber").toString()) <= 10000){
			//if(commit.find(object).limit(1).size() == 0 && commitNum.find(object).limit(1).size() == 0){
				gitCommit.crawl(message);
			/*}else{
				commit.remove(object);
				commitNum.remove(object);
				gitCommit.crawl(message);
			}*/
		}
		//gitCommit.crawl(message);
		
		/*GitCommit gitCommit = new GitCommit();
		gitCommit.crawl("mojombo/grit");*/
	}
}

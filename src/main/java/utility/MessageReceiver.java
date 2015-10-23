package utility;

import githubCrawler.RepoCrawler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
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
	private static final String TASK_QUEUE_NAME = "crawlrepo_queue";

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void handleTask(String message) {
		System.out.println(message);
		RepoCrawler repoCrawler = new RepoCrawler();
		repoCrawler.crawl(message);
		
		Mongo mongo = new Mongo("121.41.118.191", 27017);
		DB db = mongo.getDB("ghcrawler");
		DBCollection repolist = db.getCollection("repolist");
		DBObject object = new BasicDBObject();
		object.put("full_name", message);
		DBObject repo = repolist.find(object).next();
		
		DBObject before = new BasicDBObject();
		before.put("id", Integer.parseInt(repo.get("id").toString()));
		DBObject after = new BasicDBObject();
		after.put("id", Integer.parseInt(repo.get("id").toString()));
		after.put("full_name", repo.get("full_name").toString());
		after.put("state", "completed");
		repolist.update(before, after);
	}
}

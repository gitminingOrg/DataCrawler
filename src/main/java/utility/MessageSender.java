package utility;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class MessageSender {
	//private final static String TASK_QUEUE_NAME = "filter_repo_queue#1";
	private final static String TASK_QUEUE_NAME = "fixpullandissue";

	public void sendMessage(String message) {
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
			Channel channel = connection.createChannel();
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
			channel.basicPublish("", TASK_QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
			channel.close();
			connection.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// GitHubPaShuJu
			e.printStackTrace();
		}
	}
}

package githubCrawler;

import utility.MessageSender;

public class testMQ {
	public static void main(String[] args){
		MessageSender messageSender = new MessageSender();
		for(int i = 0 ; i < 1000 ; i ++){
			String string = "fuck" + i ;
			messageSender.sendMessage(string);
		}
		System.exit(0);
	}

}

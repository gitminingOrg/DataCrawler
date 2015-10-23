package utility;


public class AccountUtil {
	public static String getLoginPassword(){
		String[] accounts = {
				"owenchen93:19930301owenchen",
				"owenchenx:19930301owenchen",
				"owencheny:19930301owenchen",
				"owenchenz:19930301owenchen",
				"ghcrawler3:zhao57252582",
				"ghcrawler1:zhao57252582",
				"ghcrawler2:zhao57252582",
				"Primer123:zhao57252582",
				
				
		};
		
		int random = (int) (Math.random() * accounts.length);
		return accounts[random];
	}
}

package utility;


public class AccountUtil {
	public static String getLoginPassword(){
		String[] accounts = {
				"miningtry1:1111aaaa",
				"miningtry2:1111aaaa",
				"miningtry3:1111aaaa",
				"miningtry4:1111aaaa",
				"miningtry5:1111aaaa",
				"miningtry6:1111aaaa",
				"miningtry7:1111aaaa",
				"miningtry8:1111aaaa",
				"miningtry9:1111aaaa",
				"miningtry10:1111aaaa",
				"miningtry11:1111aaaa",
				"miningtry12:1111aaaa",
				"miningtry13:1111aaaa",
				"miningtry14:1111aaaa",
				"miningtry15:1111aaaa",
				"miningtry16:1111aaaa",
				"miningtry17:1111aaaa",
				"miningtry18:1111aaaa",
				"owenchen93:19930301owenchen",
				"ghcrawler3:zhao57252582",
				"ghcrawler1:zhao57252582"
		};
		
		int random = (int) (Math.random() * accounts.length);
		return accounts[random];
	}
}

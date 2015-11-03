package utility;

import java.net.HttpURLConnection;
import java.net.URL;

public class ValidateInternetConnection {
	
	public static int validateInternetConnection(){
		try {
			URL url = new URL("https://www.baidu.com/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			return connection.getResponseCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}
}

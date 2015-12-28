package utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetHostName {
	
	public static String getHostName(){
		String hostname = "";
		try {
			InetAddress address = InetAddress.getLocalHost();
			hostname = address.getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hostname;
	}
}

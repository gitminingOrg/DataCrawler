package utility;

import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpDeal {
	public static String getResponse(String url){
		try{
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(new URI(url));

			String authorization = AccountUtil.getLoginPassword();
			Charset charset = Charset.forName("UTF-8");
			String encodedAuthorization = "Basic "
					+ new String(Base64.encodeBase64(authorization
							.getBytes(charset)), charset);
			get.setHeader("Authorization", encodedAuthorization);
			HttpResponse response = httpClient.execute(get);
			String userJsons = EntityUtils.toString(response.getEntity());
			return userJsons;			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}
}

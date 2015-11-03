package utility;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class GetAuthorization {
	
	public static String getAuthorization(){
		Charset charset = Charset.forName("UTF-8");
		String account = AccountUtil.getLoginPassword();
		String authorization = "Basic " + new String(Base64.encodeBase64(account.getBytes(charset)),charset);
		
		return authorization;
	}

}

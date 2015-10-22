package userInfoFetch;

import org.bson.Document;

import utility.HttpDeal;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FetchUserInfo {
	/**
	 * fetch user info insert into mongo
	 * 
	 * @param message
	 *            user_url
	 * @return
	 */
	public Document fecthUserInfo(String message) {
		System.out.println("fetch info");
		try {

			String url = message;
			String userJson = HttpDeal.getResponse(url);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(userJson);

			if (element.getAsJsonObject().get("id").getAsInt() > 0) {
				Document document = Document.parse(userJson);
				return document;
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}
}

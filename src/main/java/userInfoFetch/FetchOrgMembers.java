package userInfoFetch;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import utility.HttpDeal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FetchOrgMembers {
	public List<Document> insertOrgMembers(int orgid,String org){
		System.out.println("fetch org member " +orgid);
		List<Document> result = new ArrayList<Document>();
		int page = 1;
		boolean effect = true; 
		try {
			//parse message
			String url = "https://api.github.com/orgs/"+org+"/members";
			
			while (effect) {
				//get auth account		
				String searchUrl = url+"?page="+page;
				page++;			
				//get response str
				String memberJson = HttpDeal.getResponse(searchUrl);
				//parse to json array
				JsonParser parser = new JsonParser();
				JsonArray reposArray = parser.parse(memberJson).getAsJsonArray();
				
				if (reposArray.size() == 0) {
					effect = false;
				}else{
					//parse array , generate follower info (add user,id)
					for (JsonElement jsonElement : reposArray) {
						String userRepo = jsonElement.toString();						
						Document document = Document.parse(userRepo);
						document.append("org_id", orgid);
						document.append("org", org);
						result.add(document);
					}		
				}
			}

			return result;
		} catch (Exception e) {
			return result;
		}
	}
}

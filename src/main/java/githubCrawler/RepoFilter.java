package githubCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import utility.GetURLConnection;
import utility.MongoInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class RepoFilter {
	Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
	DB db = mongo.getDB("NewProject");
	DBCollection repository = db.getCollection("repo");
	DBCollection repocondition = db.getCollection("repocondition");
	int id = 0;
	String repoURL = "https://api.github.com/repositories?since=";
	HttpURLConnection urlConnection = null;
	BufferedReader reader = null;
	String response = "";
	DBObject repo = null;
	double javaPercent = 0;
	int closedissue = 0;
	int closedpull = 0;
	int contributor = 0;
	int methodEnd = 0;

	public static void main(String[] args) {
		RepoFilter repoFilter = new RepoFilter();
		try {
			FileReader reader = new FileReader(new File("IDLog.txt"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			repoFilter.setId(Integer.parseInt(bufferedReader.readLine()));
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		repoFilter.filter();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void filter() {
		System.out.println("Start filter repositories-----------------------");
		while (id < 16000000) {
			try {
				urlConnection = GetURLConnection.getUrlConnection(repoURL + id);
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				response = reader.readLine();

				if (response != null && response != "[]") {
					JsonArray jsonArray = new JsonParser().parse(response)
							.getAsJsonArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						DBObject object = (BasicDBObject) JSON.parse(jsonArray
								.get(i).toString());
						String concrete = object.get("url").toString();
						System.out.println(object.get("full_name"));
						
						if(validateFork(concrete)){
							String javaURL = "https://github.com/" + repo.get("full_name").toString();
							if(validateJava(javaURL)){
								String pullURL = "https://github.com/" + repo.get("full_name").toString() + "/pulls";
								if(validatePull(pullURL)){
									String issueURL = "https://github.com/" + repo.get("full_name").toString() + "/issues";
									if(validateIssue(issueURL)){
										String contributorURL = "https://api.github.com/repos/" + repo.get("full_name").toString() + "/contributors?page=1";
										if(validateContributor(contributorURL)){
											DBObject repocon = new BasicDBObject();
											repocon.put("java", javaPercent);
											repocon.put("closepull", closedpull);
											repocon.put("closeissue", closedissue);
											repocon.put("fn", repo.get("full_name").toString());
											
											repository.save(repo);
											repocondition.save(repocon);
											System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~西湖的水我的泪~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
										}
									}
								}
							}
						}
						
						if(methodEnd == 0){
							if(!object.get("full_name").toString().equals("behnam/openheatmap")){
								FileWriter fileWriter = new FileWriter("IDLog.txt");
								fileWriter.write(repo.get("id").toString());
								fileWriter.flush();
								fileWriter.close();
							}
						}else{
							i = jsonArray.size();
						}
					}
					
					if(methodEnd == 0){
						DBObject object = (BasicDBObject) JSON.parse(jsonArray.get(
								jsonArray.size() - 1).toString());
						id = Integer.parseInt(object.get("id").toString());
						System.out
								.println("***********************************one page over,now id = "
										+ id);
					}else{
						id = 123456789;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				id = 123456789;
			}
		}
	}

	public boolean validateFork(String concreteURL) {
		try {
			urlConnection = GetURLConnection.getUrlConnection(concreteURL);
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				response = reader.readLine();
				repo = (BasicDBObject) JSON.parse(response);

				if (repo.get("fork").equals(false)) {
					return true;
				} else {
					System.out.println("fork");
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			methodEnd = 1;
			return false;
		}
	}

	public boolean validateContributor(String contributorURL) {
		try {
			urlConnection = GetURLConnection.getUrlConnection(contributorURL);
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "utf-8"));
			response = reader.readLine();

			if (response != null && response != "[]") {
				JsonArray contributorList = new JsonParser().parse(response)
						.getAsJsonArray();
				if (contributorList.size() >= 10) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			methodEnd = 1;
			return false;
		}
	}

	/*public boolean validateCommit(String commitURL) {
		try {
			urlConnection = GetURLConnection.getUrlConnection(commitURL);
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "utf-8"));
			response = reader.readLine();

			if (response != null && response != "[]") {
				JsonArray commitList = new JsonParser().parse(response)
						.getAsJsonArray();
				if (commitList.size() >= 10) {
					return true;
				} else {
					if (filtercondition.find().size() == 1) {
						DBObject object = filtercondition.find().next();
						DBObject updata1 = new BasicDBObject();
						DBObject update2 = new BasicDBObject();
						updata1.put("commitfail", Integer.parseInt(object.get(
								"commitfail").toString()) + 1);
						update2.put("$set", updata1);
						filtercondition.update(object, update2);
					} else {
						DBObject object = new BasicDBObject();
						object.put("forkfail", 0);
						object.put("contributorfail", 0);
						object.put("commitfail", 1);
						object.put("issuefail", 0);
						object.put("pullfail", 0);
						object.put("others", 0);
						object.put("yearfail", 0);
						filtercondition.save(object);
					}
					return false;
				}
			} else {
				if (filtercondition.find().size() == 1) {
					DBObject object = filtercondition.find().next();
					DBObject updata1 = new BasicDBObject();
					DBObject update2 = new BasicDBObject();
					updata1.put("commitfail", Integer.parseInt(object.get(
							"commitfail").toString()) + 1);
					update2.put("$set", updata1);
					filtercondition.update(object, update2);
				} else {
					DBObject object = new BasicDBObject();
					object.put("forkfail", 0);
					object.put("contributorfail", 0);
					object.put("commitfail", 1);
					object.put("issuefail", 0);
					object.put("pullfail", 0);
					object.put("others", 0);
					object.put("yearfail", 0);
					filtercondition.save(object);
				}
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.exit(0);
			return false;
		}
	}*/

	public boolean validateIssue(String issueURL) {
		try {
			urlConnection = GetURLConnection.getUrlConnection(issueURL);
			if (urlConnection.getResponseCode() == 200) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String result = "";
				while ((response = reader.readLine()) != null) {
					result = result + response;
				}

				if (result
						.contains("<svg aria-hidden=\"true\" class=\"octicon octicon-check\" height=\"16\" role=\"img\" version=\"1.1\" viewBox=\"0 0 12 16\" width=\"12\"><path d=\"M12 5L4 13 0 9l1.5-1.5 2.5 2.5 6.5-6.5 1.5 1.5z\"></path></svg>")) {
					String str = result
							.split("<svg aria-hidden=\"true\" class=\"octicon octicon-check\" height=\"16\" role=\"img\" version=\"1.1\" viewBox=\"0 0 12 16\" width=\"12\"><path d=\"M12 5L4 13 0 9l1.5-1.5 2.5 2.5 6.5-6.5 1.5 1.5z\"></path></svg>")[1]
							.split(" Closed")[0].replace(" ", "");
					if (str.contains(",")) {
						str = str.replace(",", "");
					}
					System.out.println(str);
					if (Integer.parseInt(str) >= 20) {
						closedissue = Integer.parseInt(str);
						System.out.println(closedissue);
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			methodEnd = 1;
			return false;
		}
	}

	public boolean validatePull(String pullURL) {
		try {
			urlConnection = GetURLConnection.getUrlConnection(pullURL);
			if (urlConnection.getResponseCode() == 200) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String result = "";
				while ((response = reader.readLine()) != null) {
					result = result + response;
				}
				System.out.println(result);
				if (result.contains("<svg aria-hidden=\"true\" class=\"octicon octicon-check\" height=\"16\" role=\"img\" version=\"1.1\" viewBox=\"0 0 12 16\" width=\"12\"><path d=\"M12 5L4 13 0 9l1.5-1.5 2.5 2.5 6.5-6.5 1.5 1.5z\"></path></svg>")) {
					String str = result
							.split("<svg aria-hidden=\"true\" class=\"octicon octicon-check\" height=\"16\" role=\"img\" version=\"1.1\" viewBox=\"0 0 12 16\" width=\"12\"><path d=\"M12 5L4 13 0 9l1.5-1.5 2.5 2.5 6.5-6.5 1.5 1.5z\"></path></svg>")[1]
							.split(" Closed")[0].replace(" ", "");
					if (str.contains(",")) {
						str = str.replace(",", "");
					}
					System.out.println(str);
					if (Integer.parseInt(str) >= 200) {
						closedpull = Integer.parseInt(str);
						System.out.println(closedpull);
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			methodEnd = 1;
			return false;
		}
	}
	
	public boolean validateJava(String URL) {
		try {
			urlConnection = GetURLConnection.getUrlConnection(URL);
			if (urlConnection.getResponseCode() == 200) {
				reader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String result = "";
				while ((response = reader.readLine()) != null) {
					result = result + response;
				}

				if (result
						.contains("<div class=\"repository-lang-stats-graph js-toggle-lang-stats\" title=\"Click for language details\" data-ga-click=\"Repository, language bar stats toggle, location:repo overview\">")) {
					if(result.contains("aria-label=\"Java ")){
						String str = result
								.split("<div class=\"repository-lang-stats-graph js-toggle-lang-stats\" title=\"Click for language details\" data-ga-click=\"Repository, language bar stats toggle, location:repo overview\">")[1]
								.split("</div>")[0].split("aria-label=\"Java ")[1].split("%\" ")[0];
						System.out.println(Double.parseDouble(str));
						if (Double.parseDouble(str) >= 50.0) {
							javaPercent = Double.parseDouble(str);
							return true;
						} else {
							return false;
						}
					}else{
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			methodEnd = 1;
			return false;
		}
	}
}
	/*public boolean validateYear(String created_at,String pushed_at){
		if(Integer.parseInt(pushed_at.split("-")[0]) > (Integer.parseInt(created_at.split("-")[0]) + 5)){
			return true;
		}else if(Integer.parseInt(pushed_at.split("-")[0]) == (Integer.parseInt(created_at.split("-")[0]) + 5) && Integer.parseInt(pushed_at.split("-")[1]) > Integer.parseInt(created_at.split("-")[1])){
			return true;
		}else if(Integer.parseInt(pushed_at.split("-")[0]) == (Integer.parseInt(created_at.split("-")[0]) + 5) && Integer.parseInt(pushed_at.split("-")[1]) == Integer.parseInt(created_at.split("-")[1]) && Integer.parseInt(pushed_at.split("-")[2].split("T")[0]) >= Integer.parseInt(created_at.split("-")[2].split("T")[0])){
			return true;
		}else {
			if (filtercondition.find().size() == 1) {
				DBObject object = filtercondition.find().next();
				DBObject updata1 = new BasicDBObject();
				DBObject update2 = new BasicDBObject();
				updata1.put("yearfail", Integer.parseInt(object.get(
						"yearfail").toString()) + 1);
				update2.put("$set", updata1);
				filtercondition.update(object, update2);
			} else {
				DBObject object = new BasicDBObject();
				object.put("forkfail", 0);
				object.put("contributorfail", 0);
				object.put("commitfail", 0);
				object.put("issuefail", 0);
				object.put("pullfail", 0);
				object.put("others", 0);
				object.put("yearfail", 1);
				filtercondition.save(object);
			}
			return false;
		}
	} 
}*/
// System.out.println("----------------------------------------" +
// Integer.parseInt(repo.get("id").toString()) + ":" + repo.get("full_name"));
// repository.save(repo);
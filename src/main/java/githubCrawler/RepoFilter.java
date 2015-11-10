package githubCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import utility.GetURLConnection;
import utility.MessageSender;
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
	DB db = mongo.getDB("ghcrawlerV3");
	DBCollection repository = db.getCollection("repo");
	DBCollection filtercondition = db.getCollection("filtercondition");
	DBCollection issueandpull = db.getCollection("issueandpull");
	int id = 154216;
	String repoURL = "https://api.github.com/repositories?since=";
	HttpURLConnection urlConnection = null;
	BufferedReader reader = null;
	String response = "";
	DBObject repo = null;
	int openissue = 0;
	int closedissue = 0;
	int openpull = 0;
	int closedpull = 0;

	public static void main(String[] args) {
		RepoFilter repoFilter = new RepoFilter();
		repoFilter.filter();
	}

	public void filter() {
		System.out.println("Start filter repositories-----------------------");
		while (id < 10000000) {
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

						if (validateFork(concrete)) {
							String contributorURL = "https://api.github.com/repos/"
									+ repo.get("full_name").toString()
									+ "/contributors?page=1";
							if (validateContributor(contributorURL)) {
								String commitURL = "https://api.github.com/repos/"
										+ repo.get("full_name").toString()
										+ "/commits?page=4";

								if (validateCommit(commitURL)) {
									if (repo.get("has_issues").toString()
											.equals("true")) {
										String issueURL = "https://github.com/"
												+ repo.get("full_name")
														.toString() + "/issues";
										if(validateIssue(issueURL)){
											String pullURL = "https://github.com/"
													+ repo.get("full_name")
															.toString() + "/pulls";
											if(validatePull(pullURL)){
												System.out.println("----------------------------------------" + Integer.parseInt(repo.get("id").toString()) + ":" + repo.get("full_name"));
												DBObject pullAndIssue = new BasicDBObject();
												pullAndIssue.put("openissue", openissue);
												pullAndIssue.put("closedissue", closedissue);
												pullAndIssue.put("openpull", openpull);
												pullAndIssue.put("closedpull", closedpull);
												pullAndIssue.put("fn", repo.get("full_name").toString());
												issueandpull.save(pullAndIssue);
												repository.save(repo);
												MessageSender messageSender = new MessageSender();
												messageSender.sendMessage(repo.get("full_name").toString());
											}
										}
									}
								}

							}

						}

					}
					DBObject object = (BasicDBObject) JSON.parse(jsonArray.get(
							jsonArray.size() - 1).toString());
					id = Integer.parseInt(object.get("id").toString());
					System.out
							.println("***********************************one page over,now id = "
									+ id);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.exit(0);
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
					if (filtercondition.find().size() == 1) {
						DBObject object = filtercondition.find().next();
						DBObject updata1 = new BasicDBObject();
						DBObject update2 = new BasicDBObject();
						updata1.put("forkfail", Integer.parseInt(object.get(
								"forkfail").toString()) + 1);
						update2.put("$set", updata1);
						filtercondition.update(object, update2);
					} else {
						DBObject object = new BasicDBObject();
						object.put("forkfail", 1);
						object.put("contributorfail", 0);
						object.put("commitfail", 0);
						object.put("issuefail", 0);
						object.put("pullfail", 0);
						object.put("others", 0);
						filtercondition.save(object);
					}
					return false;
				}
			} else {
				if (filtercondition.find().size() == 1) {
					DBObject object = filtercondition.find().next();
					DBObject updata1 = new BasicDBObject();
					DBObject update2 = new BasicDBObject();
					updata1.put(
							"others",
							Integer.parseInt(object.get("others").toString()) + 1);
					update2.put("$set", updata1);
					filtercondition.update(object, update2);
				} else {
					DBObject object = new BasicDBObject();
					object.put("forkfail", 0);
					object.put("contributorfail", 0);
					object.put("commitfail", 0);
					object.put("issuefail", 0);
					object.put("pullfail", 0);
					object.put("others", 1);
					filtercondition.save(object);
				}
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
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
				if (contributorList.size() >= 3) {
					return true;
				} else {
					if (filtercondition.find().size() == 1) {
						DBObject object = filtercondition.find().next();
						DBObject updata1 = new BasicDBObject();
						DBObject update2 = new BasicDBObject();
						updata1.put("contributorfail", Integer.parseInt(object
								.get("contributorfail").toString()) + 1);
						update2.put("$set", updata1);
						filtercondition.update(object, update2);
					} else {
						DBObject object = new BasicDBObject();
						object.put("forkfail", 0);
						object.put("contributorfail", 1);
						object.put("commitfail", 0);
						object.put("issuefail", 0);
						object.put("pullfail", 0);
						object.put("others", 0);
						filtercondition.save(object);
					}
					return false;
				}
			} else {
				if (filtercondition.find().size() == 1) {
					DBObject object = filtercondition.find().next();
					DBObject updata1 = new BasicDBObject();
					DBObject update2 = new BasicDBObject();
					updata1.put("contributorfail", Integer.parseInt(object.get(
							"contributorfail").toString()) + 1);
					update2.put("$set", updata1);
					filtercondition.update(object, update2);
				} else {
					DBObject object = new BasicDBObject();
					object.put("forkfail", 0);
					object.put("contributorfail", 1);
					object.put("commitfail", 0);
					object.put("issuefail", 0);
					object.put("pullfail", 0);
					object.put("others", 0);
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
	}

	public boolean validateCommit(String commitURL) {
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
	}

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
						.contains("<span class=\"octicon octicon-check\"></span>")) {
					String str = result
							.split("<span class=\"octicon octicon-check\"></span>")[1]
							.split(" Closed")[0].replace(" ", "");
					if (str.contains(",")) {
						str = str.replace(",", "");
					}
					if (Integer.parseInt(str) > 0) {
						closedissue = Integer.parseInt(str);
						String str1 = result.split("<span class=\"octicon octicon-issue-opened\"></span>")[2].split(" Open")[0].replace(" ", "");
						if(str1.contains(",")){
							str1 = str1.replace(",", "");
						}
						openissue = Integer.parseInt(str1);
						return true;
					} else {
						if (filtercondition.find().size() == 1) {
							DBObject object = filtercondition.find().next();
							DBObject updata1 = new BasicDBObject();
							DBObject update2 = new BasicDBObject();
							updata1.put("issuefail", Integer.parseInt(object
									.get("issuefail").toString()) + 1);
							update2.put("$set", updata1);
							filtercondition.update(object, update2);
						} else {
							DBObject object = new BasicDBObject();
							object.put("forkfail", 0);
							object.put("contributorfail", 0);
							object.put("commitfail", 0);
							object.put("issuefail", 1);
							object.put("pullfail", 0);
							object.put("others", 0);
							filtercondition.save(object);
						}
						return false;
					}
				} else {
					if (filtercondition.find().size() == 1) {
						DBObject object = filtercondition.find().next();
						DBObject updata1 = new BasicDBObject();
						DBObject update2 = new BasicDBObject();
						updata1.put("issuefail", Integer.parseInt(object.get(
								"issuefail").toString()) + 1);
						update2.put("$set", updata1);
						filtercondition.update(object, update2);
					} else {
						DBObject object = new BasicDBObject();
						object.put("forkfail", 0);
						object.put("contributorfail", 0);
						object.put("commitfail", 0);
						object.put("issuefail", 1);
						object.put("pullfail", 0);
						object.put("others", 0);
						filtercondition.save(object);
					}
					return false;
				}
			} else {
				if (filtercondition.find().size() == 1) {
					DBObject object = filtercondition.find().next();
					DBObject updata1 = new BasicDBObject();
					DBObject update2 = new BasicDBObject();
					updata1.put("issuefail", Integer.parseInt(object.get(
							"issuefail").toString()) + 1);
					update2.put("$set", updata1);
					filtercondition.update(object, update2);
				} else {
					DBObject object = new BasicDBObject();
					object.put("forkfail", 0);
					object.put("contributorfail", 0);
					object.put("commitfail", 0);
					object.put("issuefail", 1);
					object.put("pullfail", 0);
					object.put("others", 0);
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

				if (result
						.contains("<span class=\"octicon octicon-check\"></span>")) {
					String str = result
							.split("<span class=\"octicon octicon-check\"></span>")[1]
							.split(" Closed")[0].replace(" ", "");
					if (str.contains(",")) {
						str = str.replace(",", "");
					}
					if (Integer.parseInt(str) > 0) {
						closedpull = Integer.parseInt(str);
						String str1 = result.split("<span class=\"octicon octicon-git-pull-request\"></span>")[2].split(" Open")[0].replace(" ", "");
						if(str1.contains(",")){
							str1 = str1.replace(",", "");
						}
						openpull = Integer.parseInt(str1);
						return true;
					} else {
						if (filtercondition.find().size() == 1) {
							DBObject object = filtercondition.find().next();
							DBObject updata1 = new BasicDBObject();
							DBObject update2 = new BasicDBObject();
							updata1.put("pullfail", Integer.parseInt(object
									.get("pullfail").toString()) + 1);
							update2.put("$set", updata1);
							filtercondition.update(object, update2);
						} else {
							DBObject object = new BasicDBObject();
							object.put("forkfail", 0);
							object.put("contributorfail", 0);
							object.put("commitfail", 0);
							object.put("issuefail", 0);
							object.put("pullfail", 1);
							object.put("others", 0);
							filtercondition.save(object);
						}
						return false;
					}
				} else {
					if (filtercondition.find().size() == 1) {
						DBObject object = filtercondition.find().next();
						DBObject updata1 = new BasicDBObject();
						DBObject update2 = new BasicDBObject();
						updata1.put("pullfail", Integer.parseInt(object.get(
								"pullfail").toString()) + 1);
						update2.put("$set", updata1);
						filtercondition.update(object, update2);
					} else {
						DBObject object = new BasicDBObject();
						object.put("forkfail", 0);
						object.put("contributorfail", 0);
						object.put("commitfail", 0);
						object.put("issuefail", 0);
						object.put("pullfail", 1);
						object.put("others", 0);
						filtercondition.save(object);
					}
					return false;
				}
			} else {
				if (filtercondition.find().size() == 1) {
					DBObject object = filtercondition.find().next();
					DBObject updata1 = new BasicDBObject();
					DBObject update2 = new BasicDBObject();
					updata1.put(
							"pullfail",
							Integer.parseInt(object.get("pullfail").toString()) + 1);
					update2.put("$set", updata1);
					filtercondition.update(object, update2);
				} else {
					DBObject object = new BasicDBObject();
					object.put("forkfail", 0);
					object.put("contributorfail", 0);
					object.put("commitfail", 0);
					object.put("issuefail", 0);
					object.put("pullfail", 1);
					object.put("others", 0);
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
	}
}
// System.out.println("----------------------------------------" +
// Integer.parseInt(repo.get("id").toString()) + ":" + repo.get("full_name"));
// repository.save(repo);
package githubCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import utility.GetURLConnection;
import utility.MongoInfo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class DownloadReadme {
	public static void main(String[] args){
		String root = "H:\\GitReadme\\";
		Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
		DB db = mongo.getDB("ghcrawlerV3");
		DBCollection repo = db.getCollection("repo");
		DBCursor cursor = repo.find();
		cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		
		try {
			while(cursor.hasNext()){
				DBObject object = cursor.next();
				System.out.println(object.get("full_name").toString());
				String filestr = root + object.get("full_name").toString().split("/")[0] + "@" + object.get("full_name").toString().split("/")[1];
				File file = new File(filestr);
				file.mkdir();
				
				FileWriter fileWriter1 = new FileWriter(new File(filestr + "\\" + "README.txt"),true);
				FileWriter fileWriter2 = new FileWriter(new File(filestr + "\\" + "description.txt"));
				
				HttpURLConnection urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.md");
				if(urlConnection.getResponseCode() == 200){
					BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					String str = "";
					while((str = reader.readLine()) != null){
						fileWriter1.write(str + "\n");
					}
				}else{
					urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.markdown");
					if(urlConnection.getResponseCode() == 200){
						BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
						String str = "";
						while((str = reader.readLine()) != null){
							fileWriter1.write(str + "\n");
						}
					}else{
						urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README");
						if(urlConnection.getResponseCode() == 200){
							BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
							String str = "";
							while((str = reader.readLine()) != null){
								fileWriter1.write(str + "\n");
							}
						}else{
							urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.rdoc");
							if(urlConnection.getResponseCode() == 200){
								BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
								String str = "";
								while((str = reader.readLine()) != null){
									fileWriter1.write(str + "\n");
								}
							}else{
								urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.textile");
								if(urlConnection.getResponseCode() == 200){
									BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
									String str = "";
									while((str = reader.readLine()) != null){
										fileWriter1.write(str + "\n");
									}
								}else{
									urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.text");
									if(urlConnection.getResponseCode() == 200){
										BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
										String str = "";
										while((str = reader.readLine()) != null){
											fileWriter1.write(str + "\n");
										}
									}else{
										urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.rst");
										if(urlConnection.getResponseCode() == 200){
											BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
											String str = "";
											while((str = reader.readLine()) != null){
												fileWriter1.write(str + "\n");
											}
										}else{
											urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.mkd");
											if(urlConnection.getResponseCode() == 200){
												BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
												String str = "";
												while((str = reader.readLine()) != null){
													fileWriter1.write(str + "\n");
												}
											}else{
												urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.mkdn");
												if(urlConnection.getResponseCode() == 200){
													BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
													String str = "";
													while((str = reader.readLine()) != null){
														fileWriter1.write(str + "\n");
													}
												}else{
													urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.txt");
													if(urlConnection.getResponseCode() == 200){
														BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
														String str = "";
														while((str = reader.readLine()) != null){
															fileWriter1.write(str + "\n");
														}
													}else{
														urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/readme.md");
														if(urlConnection.getResponseCode() == 200){
															BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
															String str = "";
															while((str = reader.readLine()) != null){
																fileWriter1.write(str + "\n");
															}
														}else{
															urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/DESCRIPTION");
															if(urlConnection.getResponseCode() == 200){
																BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
																String str = "";
																while((str = reader.readLine()) != null){
																	fileWriter1.write(str + "\n");
																}
															}else{
																urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/readme.txt");
																if(urlConnection.getResponseCode() == 200){
																	BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
																	String str = "";
																	while((str = reader.readLine()) != null){
																		fileWriter1.write(str + "\n");
																	}
																}else{
																	urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.pod");
																	if(urlConnection.getResponseCode() == 200){
																		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
																		String str = "";
																		while((str = reader.readLine()) != null){
																			fileWriter1.write(str + "\n");
																		}
																	}else{
																		urlConnection = GetURLConnection.getUrlConnection("https://raw.githubusercontent.com/"+ object.get("full_name").toString() +"/master/README.org");
																		if(urlConnection.getResponseCode() == 200){
																			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
																			String str = "";
																			while((str = reader.readLine()) != null){
																				fileWriter1.write(str + "\n");
																			}
																		}else{
																			System.out
																					.println(object.get("full_name") + "--------------------error");
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				fileWriter2.write(object.get("description").toString());
				fileWriter1.flush();
				fileWriter1.close();
				fileWriter2.flush();
				fileWriter2.close();
				System.out.println("complete!");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		/*File file = new File("H:\\GitReadme\\" + "sdas@saasd");
		file.mkdir();
		
		try {
			FileWriter fileWriter = new FileWriter(new File("H:\\GitReadme\\" + "sdas@saasd\\" + "readme.txt"));
			fileWriter.write("sdas");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}

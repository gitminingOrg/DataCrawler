package githubCrawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Scanner;

import utility.MongoInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Testtttttttttttttttttttttttt {

	public static void downloadImgByNet(String imgSrc,String filePath,String fileName){  
        try{  
            URL url = new URL(imgSrc);  
            URLConnection conn = url.openConnection();  
            //���ó�ʱ��Ϊ3��  
            conn.setConnectTimeout(3*1000);  
            //��ֹ���γ���ץȡ������403����  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
            //�����  
            InputStream str = conn.getInputStream();  
  
            //�������Ĵ�СΪ1k  
            byte[] bs = new byte[1024];  
  
            //��ȡ���ĳ���  
            int len = 0;  
  
            //�Ƿ���Ҫ�����ļ���  
            File saveDir = new File(filePath);    
            if(!saveDir.exists()){    
                saveDir.mkdir();    
            }    
            File file = new File(saveDir+File.separator+fileName);     
  
            //ʵ�����һ������  
            FileOutputStream out = new FileOutputStream(file);  
            //ѭ���жϣ������ȡ�ĸ���bΪ���ˣ���is.read()��������-1��������ο�InputStream��read();  
            while ((len = str.read(bs)) != -1) {  
                //������д�뵽��Ӧ���ļ���  
                out.write(bs, 0, len);     
            }  
  
            //ˢ����  
            out.flush();  
            //�ر���  
            out.close();  
            str.close();  
              
            System.out.println("���سɹ�");  
  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static void main(String[] args) {  
        //����ͼƬ  
        //downloadImgByNet("https://avatars.githubusercontent.com/u/4?v=","H:/GitRepo","���.jpg");  
          
        //������ҳ  
        //downloadImgByNet("http://manyou.189.cn/country/country.do?idCode=md276","d:/resource/images/diaodiao/country/","���.html");  
    	/*try {
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address.getHostName());
			System.out.println(address.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
    	DB db = mongo.getDB("ghcrawlerV3");
    	DBCollection repository = db.getCollection("repository");
    	DBCollection forks = db.getCollection("forks");
    	DBCollection assignees = db.getCollection("assignees");
    	DBCollection languages = db.getCollection("languages");
    	DBCollection stargazers = db.getCollection("stargazers");
    	DBCollection contributors = db.getCollection("contributors");
    	DBCollection subscribers = db.getCollection("subscribers");
    	DBCollection tags = db.getCollection("tags");
    	DBCollection branches = db.getCollection("branches");
    	DBCollection gitrefs = db.getCollection("gitrefs");
    	//DBCollection comments = db.getCollection("comments");
    	DBCollection issue_comment = db.getCollection("issuecomment");
    	DBCollection issue_events = db.getCollection("issueevents");
        //DBCollection events = db.getCollection("events");
    	DBCollection contents = db.getCollection("contents");
    	DBCollection commits = db.getCollection("commits");
    	DBCollection issues = db.getCollection("issues");
    	DBCollection pulls = db.getCollection("pulls");
    	DBCollection tree = db.getCollection("tree");
    	//DBCollection repolist = db.getCollection("repolist");
    	DBCollection complete = db.getCollection("complete");
    	DBCollection commitnumber = db.getCollection("commitnumber");
    	
    	DBObject object = new BasicDBObject();
    	object.put("full_name", "r1k0/kigen");
    	DBObject object2 = new BasicDBObject();
    	object2.put("fn", "r1k0/kigen");//scottransom/presto��froydnj/binascii,nofxx/geonames_local,bradbeattie/python-vote-core��seattlerb/ruby_to_c
    	
    	repository.remove(object);
    	complete.remove(object);
    	
    	forks.remove(object2);
    	assignees.remove(object2);
    	languages.remove(object2);
    	stargazers.remove(object2);
    	contributors.remove(object2);
    	subscribers.remove(object2);
    	tags.remove(object2);
    	branches.remove(object2);
    	gitrefs.remove(object2);
    	issue_comment.remove(object2);
    	issue_events.remove(object2);
    	contents.remove(object2);
    	issues.remove(object2);
    	pulls.remove(object2);
    	tree.remove(object2);
    	//commits.remove(object2);
    	//commitnumber.remove(object2);*/
    	
    	GitCrawler gitCrawler = new GitCrawler();
    	gitCrawler.crawl("r1k0/kigen");
    	
    	/*Mongo mongo = new Mongo(MongoInfo.getMongoServerIp(), 27017);
    	DB db = mongo.getDB("ghcrawlerV3");
    	DBCollection repo = db.getCollection("repository");
    	DBCursor cursor = repo.find();
    	CommitCrawler commitCrawler = new CommitCrawler();
    	GitCrawler gitCrawler = new GitCrawler();
    	boolean judge = false;
    	while(cursor.hasNext()){
    		DBObject object = cursor.next();
    		if(object.get("full_name").toString().equals("norman/friendly_id")){
    			judge = true;
    		}
    		if(judge && !object.get("full_name").toString().equals("norman/friendly_id")){
    			gitCrawler.crawl(object.get("full_name").toString());	
    		}
    	}*/
    	
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
		return false;
	}
}*/
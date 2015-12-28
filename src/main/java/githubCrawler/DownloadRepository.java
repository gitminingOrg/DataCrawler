package githubCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DownloadRepository {
	
	public void downloadRepository(String fullName){
		System.out.println("Start download repository------------------------");
		try {
			String str = "";
			String downloadURL = "https://github.com/" + fullName + ".git ";
			String file = fullName.split("/")[0] + "@" + fullName.split("/")[1];
			Process process = Runtime.getRuntime().exec("git clone " + downloadURL + file, null, new File("H:\\GitRepo"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"utf-8"));
			while((str = reader.readLine()) != null){
				System.out.println(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

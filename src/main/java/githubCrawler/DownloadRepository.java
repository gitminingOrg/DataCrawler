package githubCrawler;

import java.io.File;
import java.io.IOException;

public class DownloadRepository {
	
	public void downloadRepository(String fullName){
		System.out.println("Start download repository------------------------");
		try {
			String downloadURL = "https://github.com/" + fullName + ".git";
			Runtime.getRuntime().exec("git clone " + downloadURL, null, new File("H:\\GitRepo"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

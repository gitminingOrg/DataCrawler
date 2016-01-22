package metrics22;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileNameReader {
	public static List<String> getFileNames(String filePath) throws Exception{
		List<String> names = new ArrayList<String>();
		
		File file = new File(filePath);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String line = "";
		while((line = br.readLine()) != null){
			names.add(line);
		}
		
		br.close();
		fr.close();
		
		return names;
	}
}

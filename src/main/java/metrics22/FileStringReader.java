package metrics22;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileStringReader {
	public String getFileContent(String fileName) throws IOException{
		//init content
		String content = null;
		//read file
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));
		byte[] input = new byte[bufferedInputStream.available()];  
        bufferedInputStream.read(input);  
        bufferedInputStream.close();
        content = new String(input);
		//push into content
		return content;
	}
}

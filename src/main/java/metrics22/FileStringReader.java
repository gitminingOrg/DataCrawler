package metrics22;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileStringReader {
	public String getFileContent(String fileName) throws IOException {
		// init content
		String content = null;
		// read file
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(fileName));
		byte[] input = new byte[bufferedInputStream.available()];
		bufferedInputStream.read(input);
		bufferedInputStream.close();
		content = new String(input);
		// push into content
		return content;
	}

	public String removeUselessStmt(String content) throws IOException {
		String result = content.replaceAll("\\}", "");
		result = result.replaceAll("\\{", "\\{\\}");
		result = "public class ASTRANGE121NAME{\n" + result + "\n}";
		return result;
	}

	public String removeUselessStmtMetric5(String content) throws IOException {
		// String result = content.replaceAll("\\}", "");
		// result = result.replaceAll("\\{", "\\{\\}");
		String result = "public class ASTRANGE121NAME{\npublic static void main(String[] args){\n"
				+ content + "}\n}";
		return result;
	}
}

package metrics22;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Metrcis3 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String string = "\"sda.sdasd,.as,d.,.;][[;/asdsadas//*/*84556\"";
		//System.out.println(string.contains("\"[\\d\\D]*;//[\\d\\D]*\""));
		//Pattern pattern = Pattern.compile("\"[\\d\\D]*;//[\\d\\D]*\"");
		//Matcher matcher = pattern.matcher(string);
		//System.out.println(matcher.matches());
		List<String> list = new ArrayList<String>();
		list.add("StructureParser.java");
		list.add("I:\\EEEEEEEEEEclipse\\DataCrawler\\src\\main\\java\\githubCrawler\\GitCrawler.java");
		Metrcis3 codeAnalysis = new Metrcis3();
		//codeAnalysis.commentRatio(list);
		//codeAnalysis.commentMethod(list);
		//codeAnalysis.classLen(list);
		//codeAnalysis.funcLen(list);
		//codeAnalysis.blankB4cmt(list);
		//codeAnalysis.blankAfterCmt(list);
	}

	public String getMetrics3Result(List<String> fileNames) throws Exception{
		String result = "";
		int[] commentRatio = commentRatio(fileNames);
		int[] commentMethod = commentMethod(fileNames);
		int[] classLen = classLen(fileNames);
		int[] funcLen = funcLen(fileNames);
		int[] blankB4cmt = blankB4cmt(fileNames);
		int[] blankAfterCmt = blankAfterCmt(fileNames);
		
		if(commentRatio[0] == 0){
			result = result + "#,";
		}else{
			result = result + (commentRatio[1] / 1.0 / commentRatio[0]) + ",";
		}
		
		if((commentMethod[0] + commentMethod[1]) == 0){
			result = result + "#,";
		}else{
			result = result + (commentMethod[0] / 1.0 / (commentMethod[0] + commentMethod[1])) + ":" + (commentMethod[1] / 1.0 / (commentMethod[0] + commentMethod[1])) + ",";
		}
		
		if(blankB4cmt[0] == 0){
			result = result + "#,";
		}else{
			result = result + (blankB4cmt[1] / 1.0 / blankB4cmt[0]) + ",";
		}
		
		if(blankAfterCmt[0] == 0){
			result = result + "#,";
		}else{
			result = result + (blankAfterCmt[1] / 1.0 / blankAfterCmt[0]) + ",";
		}
		
		if(classLen[0] == 0){
			result = result + "#,";
		}else{
			result = result + (classLen[1] / 1.0 / classLen[0]) + ",";
		}
		
		if(funcLen[0] == 0){
			result = result + "#,";
		}else{
			result = result + (funcLen[1] / 1.0 / funcLen[0]);
		}
		
		return result;
	} 
	
	public int[] commentRatio(List<String> fileNames){
		int N = 0;
		int M = 0;
		int[] result = new int[2];
		int quotationState = 0;
		boolean hasComment = false;
		String commetState = "close";
		
		for(int k = 0 ; k < fileNames.size() ; k ++){
			quotationState = 0;
			hasComment = false;
			commetState = "close";
			try {
				FileReader reader = new FileReader(new File(fileNames.get(k)));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String string = "";
				while((string = bufferedReader.readLine()) != null){
					N ++;
					hasComment = false;
					if(string.length() == 0){
						if (commetState == "open") {
							M ++;
						}
					}else{
						for(int i = 0 ; i < string.length() ; i ++){
							if(commetState.equals("close") && string.charAt(i) == '"' &&  i > 0 && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
								if(quotationState == 0){
									quotationState = 1;
								}else{
									quotationState = 0;
								}
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								i = string.length();
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '*'){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								commetState = "open";
								i ++ ;
							}else if(quotationState == 0 && string.charAt(i) == '*' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								commetState = "close";
								i ++;
							}else if(commetState.equals("open")){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		result[0] = N;
		result[1] = M;
		return result;
	}
	
	public int[] commentMethod(List<String> fileNames){
		int N1 = 0;
		int N2 = 0;
		int[] result = new int[2];
		int quotationState = 0;
		boolean hasComment = false;
		String commetState = "close";
		
		for(int k = 0 ; k < fileNames.size() ; k ++){
			quotationState = 0;
			hasComment = false;
			commetState = "close";
			try {
				FileReader reader = new FileReader(new File(fileNames.get(k)));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String string = "";
				while((string = bufferedReader.readLine()) != null){
					for(int i = 0 ; i < string.length() ; i ++){
						if(commetState.equals("close") && string.charAt(i) == '"' && i > 0 && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
							if(quotationState == 0){
								quotationState = 1;
							}else{
								quotationState = 0;
							}
						}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
							N1 ++;
							i = string.length();
						}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '*'){
							N2 ++;
							commetState = "open";
							i ++ ;
						}else if(quotationState == 0 && string.charAt(i) == '*'  && string.length() > i + 1 && string.charAt(i + 1) == '/'){
							//N2 ++;
							commetState = "close";
							i ++;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		result[0] = N1;
		result[1] = N2;
		return result;
	}
	
	public int[] classLen(List<String> fileNames){
		int quotationState = 0;
		String commetState = "close";
		boolean inClass = false;
		int N = 0;
		int M = 0;
		int[] result = new int[2];
		int number = 0;
		int leftNumber = 0;
		
		for(int k = 0 ; k < fileNames.size() ; k ++){
			number = 0;
			leftNumber = 0;
			quotationState = 0;
			commetState = "close";
			inClass = false;
			
			FileStringReader fileStringReader = new FileStringReader();
			String content = null;
			try {
				content = fileStringReader.getFileContent(fileNames.get(k));
				// content = fileStringReader.getFileContent("wc");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//
			ASTParser astParser = ASTParser.newParser(AST.JLS3);
			astParser.setSource(new String(content).toCharArray());
			astParser.setKind(ASTParser.K_COMPILATION_UNIT);
			CompilationUnit result1 = (CompilationUnit) (astParser.createAST(null));
			// astParser.setKind(ASTParser.K_STATEMENTS);
			// Block result = (Block)astParser.createAST(null);
			ClassVisitor testVisitor = new ClassVisitor(content, LineIdentifier.paserLineEnd(content));
			result1.accept(testVisitor);
			
			List<String> className = testVisitor.className;
			List<Integer> classStart = testVisitor.classStart;
			
			for(int j = 0 ; j < className.size() ; j ++){
				N ++;
				int index = 0;
				number = 0;
				leftNumber = 0;
				quotationState = 0;
				commetState = "close";
				inClass = false;
				try {
					FileReader reader = new FileReader(new File(fileNames.get(k)));
					BufferedReader bufferedReader = new BufferedReader(reader);
					String string = "";
					while((string = bufferedReader.readLine()) != null){
						for(int i = 0 ; i < string.length() ; i ++){
							if(index == classStart.get(j)){
								inClass = true;
							}
							if(inClass){
								if(commetState.equals("close") && string.charAt(i) == '"' && i > 0 && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
									if(quotationState == 0){
										quotationState = 1;
									}else{
										quotationState = 0;
									}
								}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
									i = string.length();
								}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '*'){
									commetState = "open";
									i ++ ;
								}else if(quotationState == 0 && string.charAt(i) == '*' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
									commetState = "close";
									i ++;
								}else if(commetState.equals("open")){
									
								}else if(quotationState == 0 && commetState.equals("close") && string.charAt(i) == '{'){
									leftNumber ++;
									number ++;
								}else if(quotationState == 0 && commetState.equals("close") && string.charAt(i) == '}'){
									number -- ;
								}
								if(leftNumber > 0 && number == 0){
									M ++;
									inClass = false;
								}
							}
							index ++;
						}
						index = index + 2;
						if(inClass){
							M ++;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		result[0] = N;
		result[1] = M;
		return result;
	}
	
	public int[] funcLen(List<String> fileNames){
		int quotationState = 0;
		String commetState = "close";
		boolean inClass = false;
		int N = 0;
		int M = 0;
		int[] result = new int[2];
		int number = 0;
		int leftNumber = 0;
		
		for(int k = 0 ; k < fileNames.size() ; k ++){
			number = 0;
			leftNumber = 0;
			quotationState = 0;
			commetState = "close";
			inClass = false;
			
			FileStringReader fileStringReader = new FileStringReader();
			String content = null;
			try {
				content = fileStringReader.getFileContent(fileNames.get(k));
				// content = fileStringReader.getFileContent("wc");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//
			ASTParser astParser = ASTParser.newParser(AST.JLS3);
			astParser.setSource(new String(content).toCharArray());
			astParser.setKind(ASTParser.K_COMPILATION_UNIT);
			CompilationUnit result1 = (CompilationUnit) (astParser.createAST(null));
			// astParser.setKind(ASTParser.K_STATEMENTS);
			// Block result = (Block)astParser.createAST(null);
			ClassVisitor testVisitor = new ClassVisitor(content, LineIdentifier.paserLineEnd(content));
			result1.accept(testVisitor);
			
			List<String> methodsNames = testVisitor.methodsNames;
			List<Integer> methodsStart = testVisitor.methodsStart;
			
			for(int j = 0 ; j < methodsNames.size() ; j ++){
				N ++;
				int index = 0;
				number = 0;
				leftNumber = 0;
				quotationState = 0;
				commetState = "close";
				inClass = false;
				try {
					FileReader reader = new FileReader(new File(fileNames.get(k)));
					BufferedReader bufferedReader = new BufferedReader(reader);
					String string = "";
					while((string = bufferedReader.readLine()) != null){
						for(int i = 0 ; i < string.length() ; i ++){
							if(index == methodsStart.get(j)){
								inClass = true;
							}
							if(inClass){
								//System.out.print(string.charAt(i));
								if(commetState.equals("close") && string.charAt(i) == '"' && i > 0 && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
									if(quotationState == 0){
										quotationState = 1;
									}else{
										quotationState = 0;
									}
								}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
									i = string.length();
								}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '*'){
									commetState = "open";
									i ++ ;
								}else if(quotationState == 0 && string.charAt(i) == '*' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
									commetState = "close";
									i ++;
								}else if(commetState.equals("open")){
									
								}else if(quotationState == 0 && commetState.equals("close") && string.charAt(i) == '{'){
									leftNumber ++;
									number ++;
								}else if(quotationState == 0 && commetState.equals("close") && string.charAt(i) == '}'){
									number -- ;
								}
								if(leftNumber > 0 && number == 0){
									M ++;
									inClass = false;
								}
							}
							index ++;
						}
						//System.out.print("\n");
						index = index + 2;
						if(inClass){
							M ++;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		result[0] = N;
		result[1] = M;
		return result;
	}
	
	public int[] blankB4cmt(List<String> fileNames){
		int N = 0;
		int M = 0;
		int n = 0;
		int m = 0;
		int[] result = new int[2];
		int quotationState = 0;
		boolean hasComment = false;
		String commetState = "close";
		HashMap<Integer, Boolean> commentLine = new HashMap<Integer, Boolean>();
		ArrayList<Integer> blank = new ArrayList<Integer>();
		
		for(int k = 0 ; k < fileNames.size() ; k ++){
			N = 0;
			M = 0;
			quotationState = 0;
			hasComment = false;
			commetState = "close";
			commentLine.clear();
			blank.clear();
			try {
				FileReader reader = new FileReader(new File(fileNames.get(k)));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String string = "";
				while((string = bufferedReader.readLine()) != null){
					N ++;
					hasComment = false;
					if(string.length() == 0){
						if (commetState == "open") {
							M ++;
						}
					}else{
						for(int i = 0 ; i < string.length() ; i ++){
							if(commetState.equals("close") && string.charAt(i) == '"' && i > 0 && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
								if(quotationState == 0){
									quotationState = 1;
								}else{
									quotationState = 0;
								}
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
								n ++;
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								i = string.length();
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '*'){
								n ++;
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								commetState = "open";
								i ++ ;
							}else if(quotationState == 0 && string.charAt(i) == '*' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								commetState = "close";
								i ++;
							}else if(commetState.equals("open")){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
							}
						}
					}
					
					String line = string.replaceAll("\\s+", "");
					if(line.length() == 0 && commetState.equals("close")){
						blank.add(N);
					}
					
					if(line.startsWith("//") || line.startsWith("/*")){
						commentLine.put(N, true);
					}
				}
				
				for(int i = 0 ; i < blank.size() ; i ++){
					if(commentLine.containsKey(blank.get(i) + 1)){
						m ++;
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		result[0] = n;
		result[1] = m;
		return result;
	}
	
	public int[] blankAfterCmt(List<String> fileNames){
		int N = 0;
		int M = 0;
		int n = 0;
		int m = 0;
		int[] result = new int[2];
		int quotationState = 0;
		boolean hasComment = false;
		String commetState = "close";
		HashMap<Integer, Boolean> commentLine = new HashMap<Integer, Boolean>();
		ArrayList<Integer> blank = new ArrayList<Integer>();
		
		for(int k = 0 ; k < fileNames.size() ; k ++){
			N = 0;
			M = 0;
			quotationState = 0;
			hasComment = false;
			commetState = "close";
			commentLine.clear();
			blank.clear();
			try {
				FileReader reader = new FileReader(new File(fileNames.get(k)));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String string = "";
				while((string = bufferedReader.readLine()) != null){
					N ++;
					hasComment = false;
					if(string.length() == 0){
						if (commetState == "open") {
							M ++;
						}
					}else{
						for(int i = 0 ; i < string.length() ; i ++){
							if(commetState.equals("close") && string.charAt(i) == '"' && i > 0 && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
								if(quotationState == 0){
									quotationState = 1;
								}else{
									quotationState = 0;
								}
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
								n ++;
								if(!hasComment){
									M ++;
								}
								commentLine.put(N, true);
								hasComment = true;
								i = string.length();
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.length() > i + 1 && string.charAt(i + 1) == '*'){
								n ++;
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								commetState = "open";
								i ++ ;
							}else if(quotationState == 0 && string.charAt(i) == '*' && string.length() > i + 1 && string.charAt(i + 1) == '/'){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
								commetState = "close";
								i ++;
							}else if(commetState.equals("open")){
								if(!hasComment){
									M ++;
								}
								hasComment = true;
							}
						}
					}
					
					String line = string.replaceAll("\\s+", "");
					if(line.length() == 0 && commetState.equals("close")){
						blank.add(N);
					}
					
					if(line.endsWith("*/")){
						commentLine.put(N, true);
					}
				}
				
				for(int i = 0 ; i < blank.size() ; i ++){
					if(commentLine.containsKey(blank.get(i) - 1)){
						m ++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		result[0] = n;
		result[1] = m;
		return result;
	}
}

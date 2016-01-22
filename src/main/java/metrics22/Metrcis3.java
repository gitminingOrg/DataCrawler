package metrics22;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
		Metrcis3 codeAnalysis = new Metrcis3();
		//codeAnalysis.commentRatio();
		//codeAnalysis.commentMethod();
		//codeAnalysis.classLen();
		codeAnalysis.blankB4cmt();
	}

	public double commentRatio(){
		int N = 0;
		int M = 0;
		double P = 0;
		int quotationState = 0;
		boolean hasComment = false;
		String commetState = "close";
		
		try {
			FileReader reader = new FileReader(new File("I:\\EEEEEEEEEEclipse\\DataCrawler\\src\\main\\java\\metrics22\\Metrcis2.java"));
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
						if(commetState.equals("close") && string.charAt(i) == '"' && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
							if(quotationState == 0){
								quotationState = 1;
							}else{
								quotationState = 0;
							}
						}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '/'){
							if(!hasComment){
								M ++;
							}
							hasComment = true;
							i = string.length();
						}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '*'){
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
				System.out.println(string + "-------------" + M + "-------" + commetState);
				/*N ++;
				String line = string.replaceAll("\\s+", " ").replaceAll("; /", ";/");
				if(line.startsWith(" //") || line.contains(";//") || line.startsWith(" /*") || line.contains(";/*")){
					System.out.println(string);
				}
				//System.out.println(string.replaceAll("\\s+", " "));*/
			}
			System.out.println(N);
			System.out.println(M);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0.0;
	}
	
	public ArrayList<Double> commentMethod(){
		int N1 = 0;
		int N2 = 0;
		double P1 = 0;
		double P2 = 0;
		ArrayList<Double> result = new ArrayList<Double>();
		int quotationState = 0;
		boolean hasComment = false;
		String commetState = "close";
		
		try {
			FileReader reader = new FileReader(new File("I:\\EEEEEEEEEEclipse\\DataCrawler\\src\\main\\java\\githubCrawler\\GitCrawler.java"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String string = "";
			while((string = bufferedReader.readLine()) != null){
				for(int i = 0 ; i < string.length() ; i ++){
					if(commetState.equals("close") && string.charAt(i) == '"' && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
						if(quotationState == 0){
							quotationState = 1;
						}else{
							quotationState = 0;
						}
					}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '/'){
						N1 ++;
						i = string.length();
					}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '*'){
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
			System.out.println("//:" + N1);
			System.out.println("/**/:" + N2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public double classLen(){
		int quotationState = 0;
		String commetState = "close";
		boolean inClass = false;
		int M = 0;
		int number = 0;
		int leftNumber = 0;
		
		FileStringReader fileStringReader = new FileStringReader();
		String content = null;
		try {
			content = fileStringReader.getFileContent("I:\\EEEEEEEEEEclipse\\DataCrawler\\src\\main\\java\\metrics22\\Metrcis3.java");
			// content = fileStringReader.getFileContent("wc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(new String(content).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
		// astParser.setKind(ASTParser.K_STATEMENTS);
		// Block result = (Block)astParser.createAST(null);
		ClassVisitor testVisitor = new ClassVisitor(content, LineIdentifier.paserLineEnd(content));
		result.accept(testVisitor);
		System.out.println(testVisitor.className + ":" + testVisitor.classStart);
		
		List<String> className = testVisitor.className;
		List<Integer> classStart = testVisitor.classStart;
		
		for(int j = 0 ; j < className.size() ; j ++){
			int index = 0;
			number = 0;
			leftNumber = 0;
			quotationState = 0;
			commetState = "close";
			inClass = false;
			try {
				FileReader reader = new FileReader(new File("I:\\EEEEEEEEEEclipse\\DataCrawler\\src\\main\\java\\metrics22\\Metrcis3.java"));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String string = "";
				while((string = bufferedReader.readLine()) != null){
					for(int i = 0 ; i < string.length() ; i ++){
						if(index == classStart.get(j)){
							inClass = true;
						}
						if(inClass){
							System.out.print(string.charAt(i));
							if(commetState.equals("close") && string.charAt(i) == '"' && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
								if(quotationState == 0){
									quotationState = 1;
								}else{
									quotationState = 0;
								}
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '/'){
								i = string.length();
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '*'){
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
					System.out.print("-----------------" + number + "--------"+ quotationState + "-------" + commetState + "\n");
					index = index + 2;
					if(inClass){
						M ++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(M);
		}
		
		return 0.0;
	}
	
	public double funcLen(){
		int quotationState = 0;
		String commetState = "close";
		boolean inClass = false;
		int M = 0;
		int number = 0;
		int leftNumber = 0;
		
		FileStringReader fileStringReader = new FileStringReader();
		String content = null;
		try {
			content = fileStringReader.getFileContent("StructureParser.java");
			// content = fileStringReader.getFileContent("wc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(new String(content).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
		// astParser.setKind(ASTParser.K_STATEMENTS);
		// Block result = (Block)astParser.createAST(null);
		ClassVisitor testVisitor = new ClassVisitor(content, LineIdentifier.paserLineEnd(content));
		result.accept(testVisitor);
		System.out.println(testVisitor.className + ":" + testVisitor.classStart);
		
		List<String> methodsNames = testVisitor.methodsNames;
		List<Integer> methodsStart = testVisitor.methodsStart;
		
		for(int j = 0 ; j < methodsNames.size() ; j ++){
			int index = 0;
			number = 0;
			leftNumber = 0;
			quotationState = 0;
			commetState = "close";
			inClass = false;
			try {
				FileReader reader = new FileReader(new File("StructureParser.java"));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String string = "";
				while((string = bufferedReader.readLine()) != null){
					for(int i = 0 ; i < string.length() ; i ++){
						if(index == methodsStart.get(j)){
							inClass = true;
						}
						if(inClass){
							//System.out.print(string.charAt(i));
							if(commetState.equals("close") && string.charAt(i) == '"' && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
								if(quotationState == 0){
									quotationState = 1;
								}else{
									quotationState = 0;
								}
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '/'){
								i = string.length();
							}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '*'){
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
			
			System.out.println(M);
		}
		
		return 0.0;
	}
	
	public double blankB4cmt(){
		int N = 0;
		int M = 0;
		double P = 0;
		int quotationState = 0;
		boolean hasComment = false;
		String commetState = "close";
		
		try {
			FileReader reader = new FileReader(new File("I:\\EEEEEEEEEEclipse\\DataCrawler\\src\\main\\java\\metrics22\\Metrcis2.java"));
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
						if(commetState.equals("close") && string.charAt(i) == '"' && string.charAt(i - 1) != '\\' && string.charAt(i - 1) != '\''){
							if(quotationState == 0){
								quotationState = 1;
							}else{
								quotationState = 0;
							}
						}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '/'){
							if(!hasComment){
								M ++;
							}
							hasComment = true;
							i = string.length();
						}else if(commetState.equals("close") && quotationState == 0 && string.charAt(i) == '/' && string.charAt(i + 1) == '*'){
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
				//System.out.println(string + "-------------" + M + "-------" + commetState);
				/*N ++;
				String line = string.replaceAll("\\s+", " ").replaceAll("; /", ";/");
				if(line.startsWith(" //") || line.contains(";//") || line.startsWith(" /*") || line.contains(";/*")){
					System.out.println(string);
				}
				//System.out.println(string.replaceAll("\\s+", " "));*/
			}
			System.out.println(N);
			System.out.println(M);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0.0;
	}
}

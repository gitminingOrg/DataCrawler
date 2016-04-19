package metrics22;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*FileStringReader fileStringReader = new FileStringReader();
		String content = null;
		try {
			content = fileStringReader.getFileContent("I:\\EEEEEEEEEEclipse\\DataCrawler\\src\\main\\java\\githubCrawler\\GitCrawler.java");
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
		System.out.println(testVisitor.className + ":" + testVisitor.classStart);*/
		Test test = new Test();
		test.compareVersion("1.0", "1.1");
	}

	public int compareVersion(String version1, String version2) {
        if(!version1.contains(".") && !version2.contains(".")){
            if(Integer.parseInt(version1) > Integer.parseInt(version2)){
                return 1;
            }else if(Integer.parseInt(version1) < Integer.parseInt(version2)){
                return -1;
            }else{
                return 0;
            }
        }else if(version1.contains(".") && !version2.contains(".")){
            if(Integer.parseInt(version1.split(".")[0]) >= Integer.parseInt(version2)){
                return 1;
            }else{
                return -1;
            }
        }else{
        	System.out.println(version1.split(".").length);
            if(Integer.parseInt(version1.split(".")[0]) > Integer.parseInt(version2.split(".")[0])){
                return 1;
            }else if(Integer.parseInt(version1.split(".")[0]) < Integer.parseInt(version2.split(".")[0])){
                return -1;
            }else{
                String str1 = version1.split(".")[1];
                String str2 = version2.split(".")[1];
                if(str1.length() > str2.length()){
                    for(int i = 0 ; i < str2.length() ; i ++){
                        if(str1.charAt(i) > str2.charAt(i)){
                            return 1;
                        }else if(str1.charAt(i) < str2.charAt(i)){
                            return -1;
                        }
                    }
                    return 1;
                }else if(str1.length() < str2.length()){
                    for(int i = 0 ; i < str2.length() ; i ++){
                        if(str1.charAt(i) > str2.charAt(i)){
                            return 1;
                        }else if(str1.charAt(i) < str2.charAt(i)){
                            return -1;
                        }
                    }
                    return -1;
                }else{
                    for(int i = 0 ; i < str2.length() ; i ++){
                        if(str1.charAt(i) > str2.charAt(i)){
                            return 1;
                        }else if(str1.charAt(i) < str2.charAt(i)){
                            return -1;
                        }
                    }
                    return 0;
                }
            }
        }
    }
}

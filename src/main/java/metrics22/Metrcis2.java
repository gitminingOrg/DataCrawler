package metrics22;

import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Metrcis2 {
	
	public static void main(String[] args){
		Metrcis2 metrcis2 = new Metrcis2();
		ClassVisitor visitor = ASTsearch();
		System.out.println(visitor.assigns.size());
	}
	
	/**
	 * ast search
	 * @return
	 */
	public static ClassVisitor ASTsearch(){
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
		return testVisitor;
	}
	
	/**No. 8
	 * calculate average length of each non-empty line
	 * @param content the whole text
	 * @return average length
	 */
	public double singleLen(String content){
		double result = 0.0;
		//non-empty line count
		int lineCount = 0;
		//length sum of all lines
		int lengthSum = 0;
		String[] lines = content.split("\n");
		
		for (String line : lines) {
			//remove blank and 3 kinds of lines
			line = line.trim();
			if(!(line.length() < 1 || line.equals("//") || line.equals("{") || line.equals("}"))){
				lengthSum+=line.length();
				lineCount++;
			}
		}
		
		result = 1.0 * lengthSum / lineCount;
		return result;
	}
	
	/**No. 9
	 * calculate how many spaces
	 * @param contentthe whole text
	 * @return average space count
	 */
	public double spaceNum(String content){
		double result = 0.0;
		//non-empty line count
		int lineCount = 0;
		//inner space sum of all lines
		int space = 0;
		String[] lines = content.split("\n");
		
		for (String line : lines) {
			//remove blank and 3 kinds of lines
			line = line.trim();
			if(!(line.length() < 1 || line.equals("//") || line.equals("{") || line.equals("}"))){
				int length = line.length();
				line = line.replaceAll(" ", "");
				int length2 = line.length();
				space+=(length-length2);
			}
		}
		result = 1.0 * space / lineCount;
		return result;
	}
	
	/**No. 10
	 * calculate average statements count of each code line
	 * @param content
	 * @return average statements count
	 */
	public double stmtsPerLine(String content){
		double result = 0.0;
		
		return result;
	}
}

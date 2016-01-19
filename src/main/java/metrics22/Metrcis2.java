package metrics22;

import java.io.IOException;
import java.util.List;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Metrcis2 {
	
	public static void main(String[] args){
		Metrcis2 metrcis2 = new Metrcis2();
		ClassVisitor visitor = ASTsearch();
		//System.err.println(metrcis2.varsPerLine(visitor));
		
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
		if(lineCount < 1){
			return -1;
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
		if(lineCount < 1){
			return -1;
		}
		result = 1.0 * space / lineCount;
		return result;
	}
	
	/**No. 10
	 * calculate average statements count of each code line
	 * @param content
	 * @return average statements count
	 */
	public double stmtsPerLine(String content, ClassVisitor visitor){
		double result = 0.0;
		int stmt = 0;
		
		int index = 0;
		boolean quote = false;
		
		//fetch normal stmt ended with ';'
		while(index < content.length()){
			if(content.charAt(index) == '"'){
				quote = quote?false:true;
			}
			if(!quote && content.charAt(index) == ';'){
				stmt++;
			}
		}
		return result;
	}
	
	/** No.11 
	 * whether there is space beside '='
	 * @param visitor ASTVisitor
	 * @return
	 */
	public double assignSpaceUse(ClassVisitor visitor){
		List<Assign> assigns = visitor.assigns;
		int totalAssign = 0;
		int spaceAssign = 0;
		for (Assign assign : assigns) {
			//contains '=' , then need to count
			if(assign.getExpression().contains("=")){
				totalAssign++;
				String expression = assign.getExpression();
				int equal = expression.indexOf('=');
				
				char former = expression.charAt(equal-1);
				//figure out if use space beside
				if(expression.charAt(equal-1) == ' ' && expression.charAt(equal+1) == ' '){
					spaceAssign++;
				}
				// if like +=,-=,/=
				else if(!((former >= 'a' && former <= 'z') || (former >= 'A' && former <= 'Z') || (former >= '0' && former <='9') || former == '$' || former == '_')){
					if(expression.charAt(equal-2) == ' ' && expression.charAt(equal+1) == ' '){
						spaceAssign++;
					}
				}
			}
		}
		double result = -1.0;
		if(totalAssign > 0){
			result = 1.0 * spaceAssign / totalAssign;
		}
		return result;
	}
	
	/**No.12
	 *  operator per stmt
	 * @param visitor
	 * @return
	 */
	public double operatorPerStmt(String content){
		int operatorCount = 0;
		content = ' '+content;
		int length = content.length();
		
		boolean quote = false;
		//this is annotation1
		boolean annotation1 = false;
		/*this is annotation2*/
		boolean annotation2 = false;
		
		for (int i = 0; i < length; i++) {
			if(annotation1){
				if(content.charAt(i) == '\n'){
					annotation1 = false;
				}
			}
			if(annotation2){
				if(content.charAt(i) == '*' && i<length-1 && content.charAt(i+1) == '/'){
					annotation2 = false;
				}
			}	
			if(!annotation1 && !annotation2){
				if(!quote && content.charAt(i)=='/'&& i<length-1 && content.charAt(i+1) == '/'){
					annotation1=true;
				}
				else if(!quote && content.charAt(i)=='/'&& i<length-1 && content.charAt(i+1) == '*'){
					annotation2=true;
				}
				else if(content.charAt(i) == '"'){
					quote = quote?false:true;
				}
				else if(!quote){

////////////////////////////////////starting finding operator////////////////////////////////////
					if(content.charAt(i) == '!'){
						operatorCount++;
						if(content.charAt(i+1) == '='){
							i++;
						}
					}else if(content.charAt(i) == '~'){
						operatorCount++;
					}else if(content.charAt(i) == '+'){
						operatorCount++;
						if(content.charAt(i+1) == '=' || content.charAt(i+1) == '+'){
							i++;
						}
					}else if(content.charAt(i) == '-'){
						operatorCount++;
						if(content.charAt(i+1) == '=' || content.charAt(i+1) == '-'){
							i++;
						}
					}else if(content.charAt(i) == '*' || content.charAt(i) == '/' ||content.charAt(i) == '%' ||content.charAt(i) == '=' ||content.charAt(i) == '^'){
						operatorCount++;
						if(content.charAt(i+1) == '='){
							i++;
						}
					}else if(content.charAt(i) == '?'){
						operatorCount++;
					}else if (content.charAt(i) == '&' || content.charAt(i) == '|') {
						operatorCount++;
						if(content.charAt(i+1) == content.charAt(i)){
							i++;
						}
					}else if (content.charAt(i) == '<' || content.charAt(i) == '>'){
						operatorCount++;
						while(content.charAt(i+1) == '<' || content.charAt(i+1) == '>' || content.charAt(i+1) == '='){
							i++;
						}
					}
////////////////////////////////////////////////////////////////////////////////////////////////	
				}
			}
		}
		int lineCount=0;
		String[] lines = content.split("\n");
		for (String line : lines) {
			//remove blank and 3 kinds of lines
			line = line.trim();
			if(!(line.length() < 1 || line.equals("//") || line.equals("{") || line.equals("}"))){
				lineCount++;
			}
		}
		if(lineCount == 0){
			return -1;
		}
		return 1.0 * operatorCount / lineCount;
	}
	
	/**No.13
	 * average var count each varDeclaring sentences has
	 * @param visitor Whole File Parse
	 * @return
	 */
	public double varsPerLine(ClassVisitor visitor){
		int varDeclareLine = 0;
		List<VarDeclare> varDeclares = visitor.varDeclares;
		if(varDeclares == null || varDeclares.size()== 0){
			return -1;
		}
		
		List<Integer> lineEnd = visitor.lineEnd;
		
		int lineStart = 0;
		for (int i = 0; i < lineEnd.size(); i++) {
			if(i != 0){
				lineStart = lineEnd.get(i-1)+1;
			}
			for (int j = 0; j < varDeclares.size(); j++) {
				int varStart = varDeclares.get(j).start;
				int varEnd = varDeclares.get(j).length + varStart - 1;
				// if varStart or varEnd in range [lineStart, lineEnd] then the line has varDeclaration
				if((varStart >= lineStart && varStart <= lineEnd.get(i)) || (varEnd >= lineStart && varEnd <= lineEnd.get(i))){
					varDeclareLine++;
					break;
				}
			}
		}
		return 1.0 * varDeclares.size() / varDeclareLine;
	}
	
	/**No.14
	 * anaylse whether use single char var
	 * @param visitor
	 * @return
	 */
	public int singleCharVarUs(ClassVisitor visitor){
		List<String> methodParas = visitor.methodsParameterNames;
		List<VarDeclare> varDeclares = visitor.varDeclares;
		if(varDeclares == null){
			return 0;
		}
		for (VarDeclare varDeclare : varDeclares) {
			if(varDeclare.var.length() == 1){
				return 1;
			}
		}	
		for (String para : methodParas) {
			if(para.length()==1){
				return 1;
			}
		}
		return 0;
	}
//	/**No.14
//	 * anaylse whether use single char var
//	 * @param content
//	 * @return
//	 */	
//	public int singleCharVarUse(String content){
//		content = ' '+content;
//		int length = content.length();
//		
//		boolean quote = false;
//		//this is annotation1
//		boolean annotation1 = false;
//		/*this is annotation2*/
//		boolean annotation2 = false;
//		
//		for (int i = 0; i < length; i++) {
//			if(annotation1){
//				if(content.charAt(i) == '\n'){
//					annotation1 = false;
//				}
//			}
//			if(annotation2){
//				if(content.charAt(i) == '*' && i<length-1 && content.charAt(i+1) == '/'){
//					annotation2 = false;
//				}
//			}	
//			if(!annotation1 && !annotation2){
//				if(!quote && content.charAt(i)=='/'&& i<length-1 && content.charAt(i+1) == '/'){
//					annotation1=true;
//				}
//				else if(!quote && content.charAt(i)=='/'&& i<length-1 && content.charAt(i+1) == '*'){
//					annotation2=true;
//				}
//				else if(content.charAt(i) == '"'){
//					quote = quote?false:true;
//				}
//				else if(!quote){
//					if(singleCharVar(content.charAt(i)) && !varChar(content.charAt(i-1)) &&  !varChar(content.charAt(i+1))){
//						return 1;
//					}
//				}
//			}
//		}
//		return 0;
//	}
	
	public boolean singleCharVar(char a){
		if((a>='a' && a<='z') || (a>='A' && a<='Z') || a=='_'){
			return true;
		}
		else return false;
	}
	
	public boolean varChar(char a){
		if((a>='a' && a<='z') || (a>='A' && a<='Z') || (a>='0' && a<='9') || a=='_' || a=='$'){
			return true;
		}else return false;
	}
	/**No.15
	 * average length of each declared var
	 * @param visitor
	 * @return
	 */
	public double averageVarLength(ClassVisitor visitor){
		int totalLength = 0;
		List<VarDeclare> varDeclares = visitor.varDeclares;
		List<String> methodParas = visitor.methodsParameterNames;
		
		if(varDeclares == null || varDeclares.size()+methodParas.size() == 0){
			return -1;
		}
		for (VarDeclare varDeclare : varDeclares) {
			int length = varDeclare.var.length();
			totalLength+=length;
		}
		for (String para : methodParas) {
			int length = para.length();
			totalLength+=length;
		}
		
		
		return 1.0 * totalLength / (varDeclares.size()+methodParas.size());
	}
}

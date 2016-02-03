package metrics22;

import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Metrics2 {

	public static void main(String[] args) throws Exception {
		args = new String[] { "java", "wc" };
		if (args.length != 2) {
			System.out.println("r u ****ing kidding me?");
			return;
		}
		List<String> files = FileNameReader.getFileNames(args[1]);
		String type = args[0];
		Metrics2 metrics2 = new Metrics2();
		System.out.println(metrics2.getMetrics2Result(type, files));
	}

	public String getMetrics2Result(String type, List<String> fileNames) {
		try {
			String content = null;
			ClassVisitor visitor = null;

			int no81 = 0, no82 = 0, no91 = 0, no92 = 0, no111 = 0, no112 = 0, no121 = 0, no122 = 0, no131 = 0, no132 = 0, no14 = 0, no151 = 0, no152 = 0;
			if (type.equals("java")) {

				for (String file : fileNames) {
					content = getContent(file);
					visitor = ASTsearch(content);
//					int[] no8 = no8singleLen(content);
//					no81 += no8[0];
//					no82 += no8[1];
//
//					int[] no9 = no9spaceNum(content);
//					no91 += no9[0];
//					no92 += no9[1];
//
//					int[] no11 = no11assignSpaceUse(visitor);
//					no111 += no11[0];
//					no112 += no11[1];
//
//					int[] no12 = no12operatorPerStmt(content);
//					no121 += no12[0];
//					no122 += no12[1];

					int[] no13 = no13varsPerLine(visitor);
					no131 += no13[0];
					no132 += no13[1];

					no14 = no14 == 1 ? 1 : no14singleCharVarUs(visitor);

					int[] no15 = no15averageVarLength(visitor);
					no151 += no15[0];
					no152 += no15[1];
				}
			} else {

				for (String file : fileNames) {
					content = getContent(file);
					String astContent = new FileStringReader()
							.removeUselessStmt(content);
					visitor = ASTsearch(astContent);
//					int[] no8 = no8singleLen(content);
//					no81 += no8[0];
//					no82 += no8[1];
//
//					int[] no9 = no9spaceNum(content);
//					no91 += no9[0];
//					no92 += no9[1];
//
//					int[] no11 = no11assignSpaceUse(visitor);
//					no111 += no11[0];
//					no112 += no11[1];
//
//					int[] no12 = no12operatorPerStmt(content);
//					no121 += no12[0];
//					no122 += no12[1];

					int[] no13 = no13varsPerLine(visitor);
					no131 += no13[0];
					no132 += (no13[1] - 1);

					no14 = no14 == 1 ? 1 : no14singleCharVarUs(visitor);

					int[] no15 = no15averageVarLength(visitor);
					no151 += no15[0];
					no152 += no15[1];
				}

			}

			String result = "";
//
//			if (no82 == 0) {
//				result += "#,";
//			} else {
//				result += (1.0 * no81 / no82) + ",";
//			}
//
//			if (no92 == 0) {
//				result += "#,";
//			} else {
//				result += (1.0 * no91 / no92) + ",";
//			}
//
//			result += "#,";
//
//			if (no112 == 0) {
//				result += "#,";
//			} else {
//				result += (1.0 * no111 / no112) + ",";
//			}
//
//			if (no122 == 0) {
//				result += "#,";
//			} else {
//				result += (1.0 * no121 / no122) + ",";
//			}
			result += "#,";result += "#,";result += "#,";result += "#,";result += "#,";
			if (no132 == 0) {
				result += "#,";
			} else {
				result += (1.0 * no131 / no132) + ",";
			}
			result += no14 + ",";
			if (no152 == 0) {
				result += "#,";
			} else {
				result += (1.0 * no151 / no152) + ",";
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return "文件有问题";
		}

	}

	public static String getContent(String file) {
		FileStringReader fileStringReader = new FileStringReader();
		String content = null;
		try {
			content = fileStringReader.getFileContent(file);
			// content = fileStringReader.getFileContent("wc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * ast search
	 * 
	 * @return
	 */
	public static ClassVisitor ASTsearch(String content) {
		//
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(new String(content).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
		// astParser.setKind(ASTParser.K_STATEMENTS);
		// Block result = (Block)astParser.createAST(null);
		ClassVisitor testVisitor = new ClassVisitor(content,
				LineIdentifier.paserLineEnd(content));
		result.accept(testVisitor);
		return testVisitor;
	}

	/**
	 * ast search
	 * 
	 * @return
	 */
	public static ClassVisitor ASTsearchBlock(String content) {
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(new String(content).toCharArray());
		astParser.setKind(ASTParser.K_STATEMENTS);
		Block result = (Block) astParser.createAST(null);
		ClassVisitor testVisitor = new ClassVisitor(content,
				LineIdentifier.paserLineEnd(content));
		result.accept(testVisitor);
		return testVisitor;
	}

	/**
	 * No. 8 calculate average length of each non-empty line
	 * 
	 * @param content
	 *            the whole text
	 * @return average length
	 */
	public int[] no8singleLen(String content) {
		double result = 0.0;
		// non-empty line count
		int lineCount = 0;
		// length sum of all lines
		int lengthSum = 0;
		String[] lines = content.split("\n");
		for (String line : lines) {
			// remove blank and 3 kinds of lines
			line = line.trim();
			if (!(line.length() < 1 || line.equals("//") || line.equals("{") || line
					.equals("}"))) {
				lengthSum += line.length();
				lineCount++;
			}
		}

		return new int[] { lengthSum, lineCount };
	}

	/**
	 * No. 9 calculate how many spaces
	 * 
	 * @param contentthe
	 *            whole text
	 * @return average space count
	 */
	public int[] no9spaceNum(String content) {
		double result = 0.0;
		// inner space sum of all lines
		int space = 0;
		String[] lines = content.split("\n");
		// non-empty line count
		int lineCount = lines.length;
		for (String line : lines) {
			// remove blank and 3 kinds of lines
			line = line.trim();
			if (!(line.length() < 1 || line.equals("//") || line.equals("{") || line
					.equals("}"))) {
				int length = line.length();
				line = line.replaceAll(" ", "");
				int length2 = line.length();
				space += (length - length2);
			}
		}
		return new int[] { space, lineCount };
	}

	/**
	 * No. 10 calculate average statements count of each code line
	 * 
	 * @param content
	 * @return average statements count
	 */
	public int no10stmtsPerLine(String content, ClassVisitor visitor) {
		return -1;
	}

	/**
	 * No.11 whether there is space beside '='
	 * 
	 * @param visitor
	 *            ASTVisitor
	 * @return
	 */
	public int[] no11assignSpaceUse(ClassVisitor visitor) {
		List<Assign> assigns = visitor.assigns;
		int totalAssign = 0;
		int spaceAssign = 0;
		for (Assign assign : assigns) {
			// contains '=' , then need to count
			if (assign.getExpression().contains("=")) {
				totalAssign++;
				String expression = visitor.content.substring(assign.start,
						assign.start + assign.length);
				int equal = expression.indexOf('=');

				char former = expression.charAt(equal - 1);
				// figure out if use space beside
				if (expression.charAt(equal - 1) == ' '
						&& expression.charAt(equal + 1) == ' ') {
					spaceAssign++;
				}
				// if like +=,-=,/=
				else if (!((former >= 'a' && former <= 'z')
						|| (former >= 'A' && former <= 'Z')
						|| (former >= '0' && former <= '9') || former == '$' || former == '_')) {
					if (expression.charAt(equal - 2) == ' '
							&& expression.charAt(equal + 1) == ' ') {
						spaceAssign++;
					}
				}
			}
		}
		double result = -1.0;
		if (totalAssign > 0) {
			result = 1.0 * spaceAssign / totalAssign;
		}
		return new int[] { spaceAssign, totalAssign };
	}

	/**
	 * No.12 operator per stmt
	 * 
	 * @param visitor
	 * @return
	 */
	public int[] no12operatorPerStmt(String content) {
		try {
			int operatorCount = 0;
			content = ' ' + content;
			int length = content.length();

			boolean quote = false;
			// this is annotation1
			boolean annotation1 = false;
			/* this is annotation2 */
			boolean annotation2 = false;

			for (int i = 0; i < length; i++) {
				if (annotation1) {
					if (content.charAt(i) == '\n') {
						annotation1 = false;
					}
				}
				if (annotation2) {
					if (content.charAt(i) == '*' && i < length - 1
							&& content.charAt(i + 1) == '/') {
						annotation2 = false;
					}
				}
				if (!annotation1 && !annotation2) {
					if (!quote && content.charAt(i) == '/' && i < length - 1
							&& content.charAt(i + 1) == '/') {
						annotation1 = true;
					} else if (!quote && content.charAt(i) == '/'
							&& i < length - 1 && content.charAt(i + 1) == '*') {
						annotation2 = true;
					} else if (content.charAt(i) == '"') {
						quote = quote ? false : true;
					} else if (!quote) {

						// //////////////////////////////////starting finding
						// operator////////////////////////////////////
						if (content.charAt(i) == '!') {
							operatorCount++;
							if (i < content.length() - 1
									&& content.charAt(i + 1) == '=') {
								i++;
							}
						} else if (content.charAt(i) == '~') {
							operatorCount++;
						} else if (content.charAt(i) == '+') {
							operatorCount++;
							if (i < content.length() - 1
									&& content.charAt(i + 1) == '='
									|| content.charAt(i + 1) == '+') {
								i++;
							}
						} else if (content.charAt(i) == '-') {
							operatorCount++;
							if (i < content.length() - 1
									&& content.charAt(i + 1) == '='
									|| content.charAt(i + 1) == '-') {
								i++;
							}
						} else if (content.charAt(i) == '*'
								|| content.charAt(i) == '/'
								|| content.charAt(i) == '%'
								|| content.charAt(i) == '='
								|| content.charAt(i) == '^') {
							operatorCount++;
							if (i < content.length() - 1
									&& content.charAt(i + 1) == '=') {
								i++;
							}
						} else if (content.charAt(i) == '?') {
							operatorCount++;
						} else if (content.charAt(i) == '&'
								|| content.charAt(i) == '|') {
							operatorCount++;
							if (i < content.length() - 1
									&& content.charAt(i + 1) == content
											.charAt(i)) {
								i++;
							}
						} else if (content.charAt(i) == '<'
								|| content.charAt(i) == '>') {
							operatorCount++;
							while (content.charAt(i + 1) == '<'
									|| content.charAt(i + 1) == '>'
									|| content.charAt(i + 1) == '=') {
								i++;
							}
						}
						// //////////////////////////////////////////////////////////////////////////////////////////////
					}
				}
			}
			int lineCount = 0;
			String[] lines = content.split("\n");
			for (String line : lines) {
				// remove blank and 3 kinds of lines
				line = line.trim();
				if (!(line.length() < 1 || line.equals("//")
						|| line.equals("{") || line.equals("}"))) {
					lineCount++;
				}
			}
			return new int[] { operatorCount, lineCount };
		} catch (Exception e) {
			return new int[] { 0, 0 };
		}
	}

	/**
	 * No.13 average var count each varDeclaring sentences has
	 * 
	 * @param visitor
	 *            Whole File Parse
	 * @return
	 */
	public int[] no13varsPerLine(ClassVisitor visitor) {
		int varDeclareLine = 0;
		List<VarDeclare> varDeclares = visitor.varDeclares;
		if (varDeclares == null || varDeclares.size() == 0) {
			return new int[] { 0, 0 };
		}

		List<Integer> lineEnd = visitor.lineEnd;

		int lineStart = 0;
		for (int i = 0; i < lineEnd.size(); i++) {
			if (i != 0) {
				lineStart = lineEnd.get(i - 1) + 1;
			}
			for (int j = 0; j < varDeclares.size(); j++) {
				int varStart = varDeclares.get(j).start;
				int varEnd = varDeclares.get(j).length + varStart - 1;
				// if varStart or varEnd in range [lineStart, lineEnd] then the
				// line has varDeclaration
				if ((varStart >= lineStart && varStart <= lineEnd.get(i))
						|| (varEnd >= lineStart && varEnd <= lineEnd.get(i))) {
					varDeclareLine++;
					break;
				}
			}
		}
		return new int[] { varDeclares.size(), varDeclareLine };
	}

	/**
	 * No.14 anaylse whether use single char var
	 * 
	 * @param visitor
	 * @return
	 */
	public int no14singleCharVarUs(ClassVisitor visitor) {
		List<String> methodParas = visitor.methodsParameterNames;
		List<VarDeclare> varDeclares = visitor.varDeclares;
		if (varDeclares == null) {
			return 0;
		}
		for (VarDeclare varDeclare : varDeclares) {
			if (varDeclare.var.length() == 1) {
				return 1;
			}
		}
		for (String para : methodParas) {
			if (para.length() == 1) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * No.14 anaylse whether use single char var
	 * 
	 * @param content
	 * @return
	 */
	public int no14singleCharVarUse(String content) {
		content = ' ' + content;
		int length = content.length();

		boolean quote = false;
		// this is annotation1
		boolean annotation1 = false;
		/* this is annotation2 */
		boolean annotation2 = false;

		for (int i = 0; i < length; i++) {
			if (annotation1) {
				if (content.charAt(i) == '\n') {
					annotation1 = false;
				}
			}
			if (annotation2) {
				if (content.charAt(i) == '*' && i < length - 1
						&& content.charAt(i + 1) == '/') {
					annotation2 = false;
				}
			}
			if (!annotation1 && !annotation2) {
				if (!quote && content.charAt(i) == '/' && i < length - 1
						&& content.charAt(i + 1) == '/') {
					annotation1 = true;
				} else if (!quote && content.charAt(i) == '/' && i < length - 1
						&& content.charAt(i + 1) == '*') {
					annotation2 = true;
				} else if (content.charAt(i) == '"') {
					quote = quote ? false : true;
				} else if (!quote) {
					if (singleCharVar(content.charAt(i))
							&& !varChar(content.charAt(i - 1))
							&& !varChar(content.charAt(i + 1))) {
						return 1;
					}
				}
			}
		}
		return 0;
	}

	public boolean singleCharVar(char a) {
		if ((a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z') || a == '_') {
			return true;
		} else
			return false;
	}

	public boolean varChar(char a) {
		if ((a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z')
				|| (a >= '0' && a <= '9') || a == '_' || a == '$') {
			return true;
		} else
			return false;
	}

	/**
	 * No.15 average length of each declared var
	 * 
	 * @param visitor
	 * @return
	 */
	public int[] no15averageVarLength(ClassVisitor visitor) {
		int totalLength = 0;
		List<VarDeclare> varDeclares = visitor.varDeclares;
		List<String> methodParas = visitor.methodsParameterNames;

		if (varDeclares == null || varDeclares.size() + methodParas.size() == 0) {
			return new int[] { 0, 0 };
		}
		for (VarDeclare varDeclare : varDeclares) {
			int length = varDeclare.var.length();
			totalLength += length;
		}
		for (String para : methodParas) {
			int length = para.length();
			totalLength += length;
		}

		return new int[] { totalLength,
				(varDeclares.size() + methodParas.size()) };
	}
}

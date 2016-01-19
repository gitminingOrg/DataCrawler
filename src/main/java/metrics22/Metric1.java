package metrics22;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Metric1 {
//	public static void main(String[] args) throws IOException {
//		Metric1 analysis = new Metric1();
//		ClassVisitor visitor = analysis.ASTsearch();
//		FileStringReader fileStringReader = new FileStringReader();
//		String content = null;
//		try {
//			content = fileStringReader.getFileContent("StructureParser.java");
//			// content = fileStringReader.getFileContent("wc");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		double result = analysis.bracketUse(visitor);
//		System.out.println(result);
//	}

	/**
	 * ast search
	 * 
	 * @return
	 */
	public ClassVisitor ASTsearch() {
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
		ClassVisitor testVisitor = new ClassVisitor(content,
				LineIdentifier.paserLineEnd(content));
		result.accept(testVisitor);
		return testVisitor;
	}

	/**
	 * No. 1 calculate percentage of empty line
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of empty line
	 */
	public double empltyLine(String content) {
		double result = 0.0;
		// empty line count
		int emptyLine = 0;
		// sum of all lines
		int sumline = 0;
		String[] lines = content.split("\n");
		for (String line : lines) {
			// remove blank
			line = line.trim();
			if (line.length() < 1) {
				emptyLine++;
			}
		}
		sumline = lines.length;
		if (sumline == 0) {
			return -1;
		}
		result = 1.0 * emptyLine / sumline;
		return result;
	}

	/**
	 * No. 2 calculate tab and empty use
	 * 
	 * @param content
	 *            the whole text
	 * @return tab percentage & empty percentage
	 */
	public double[] indentationRatio(String content) {
		double[] result = { 0.0, 0.0 };
		// empty line count
		int tabCount = 0;
		// sum of all lines
		int emptyCount = 0;
		String[] lines = content.split("\n");
		for (String line : lines) {
			// remove blank
			// line = line.trim();
			if (line.length() > 1) {
				String sub = line.substring(0, 1);
				if (sub.equals("\t")) {
					tabCount++;
				} else {
					emptyCount++;
				}
			}
		}
		if (tabCount + emptyCount == 0) {
			result[0] = -1;
			result[1] = -1;
			return result;
		}
		result[0] = 1.0 * tabCount / (tabCount + emptyCount);
		result[1] = 1.0 * emptyCount / (tabCount + emptyCount);
		return result;
	}

	/**
	 * No. 3 calculate （）
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of（）when complicate lines
	 */
	public double bracketUse(ClassVisitor vistor) {
		double result = 0.0;
		// bracket line count
		int bracketCount = 0;
		// sum of complicate lines
		int sumCount = 0;
		ArrayList<Character> opers = new ArrayList<Character>();
		opers.add('+');
		opers.add('-');
		opers.add('*');
		opers.add('/');
		opers.add('%');
		opers.add('>');
		opers.add('<');
		opers.add('^');
		List<Expression> expressions = vistor.express;
		for (Expression ass : expressions) {
			String express = ass.getExpression();
			if (express.contains("=")) {
				express = express.substring(express.indexOf("=") + 1);
			}
			int operator = 0;
			for (int i = 0; i < express.length(); i++) {
				if (opers.contains(express.charAt(i))
						&& (!opers.contains(express.charAt(i + 1)))
						&& (!opers.contains(express.charAt(i - 1)))) {
					operator++;
				}
			}
			if (operator > 1) {
				sumCount++;
				if (express.contains("(")) {
					bracketCount++;
				}
			}
		}
		List<SpecialStmt> specialStmts = vistor.specialStmts;
		for (SpecialStmt specialStmt : specialStmts) {
			if (!specialStmt.type.equals("for")) {
				int temp = 0;
				int operator = 0;
				String express = specialStmt.getStmt();
				if (express.contains("{")) {
					express = express.substring(0, express.indexOf("{"));
				} else {
					express = express.substring(0, express.lastIndexOf(")"));
				}
				for (int i = 0; i < express.length(); i++) {
					if (opers.contains(express.charAt(i))) {
						operator++;
					}
					if (express.charAt(i) == '(') {
						temp++;
					}
				}
				if (operator > 1) {
					sumCount++;
					if (temp > 1) {
						bracketCount++;
					}
				}
			}
		}
		if (sumCount == 0) {
			return -1;
		}
		System.out.println(sumCount + "," + bracketCount);
		result = 1.0 * bracketCount / sumCount;
		return result;
	}

	/**
	 * No. 4 calculate use of {
	 * 
	 * @param content
	 *            the whole text
	 * @return end percentage & head percentage
	 */
	public double[] braceUse(String content) {
		double[] result = { 0.0, 0.0 };
		// { at the end of line
		int lastCount = 0;
		// { at the head of line
		int firstCount = 0;
		String[] lines = content.split("\n");
		for (String line : lines) {
			line = line.trim();
			if (line.length() > 0) {
				if (line.substring(0, 1).equals("{")) {
					firstCount++;
				} else if (line.substring(line.length() - 1).equals("{")) {
					lastCount++;
				}
			}
		}
		if (lastCount + firstCount == 0) {
			result[0] = -1;
			result[1] = -1;
			return result;
		}
		result[0] = 1.0 * lastCount / (lastCount + firstCount);
		result[1] = 1.0 * firstCount / (lastCount + firstCount);
		return result;
	}

	/**
	 * No. 5 calculate if use{} when one single brace
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of {} when one brace
	 */
	public double singleUseBrace(ClassVisitor vistor) {
		double result = 0.0;
		// at the next of case
		int useCount = 0;
		// sum of all case
		int nouseCount = 0;

		List<SpecialStmt> specialStmts = vistor.specialStmts;
		for (SpecialStmt mystmt : specialStmts) {
			String expression = mystmt.stmt;
			int index = expression.indexOf("{");
			if (index >= 0) {
				expression = expression.substring(index);
				String[] temps = expression.trim().split(";");
				if (temps.length == 2) {
					useCount++;
				}
			} else {
				nouseCount++;
			}
		}
		if (useCount + nouseCount == 0) {
			return -1;
		}
		result = 1.0 * useCount / (useCount + nouseCount);
		return result;
	}

	/**
	 * No. 6 calculate if cut lines when complex
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of cut lines
	 */
	public double complexCut(ClassVisitor vistor) {
		double result = 0.0;
		// at the next of case
		int cutCount = 0;
		// sum of all case
		int complexCount = 0;
		List<Assign> assigns = vistor.assigns;
		List<SpecialStmt> specialStmts = vistor.specialStmts;
		for (Assign ass : assigns) {
			String expre = ass.getExpression();
			if (expre.trim().length() >= 80) {
				complexCount++;
				if ((expre.indexOf("\n") > 0)
						&& (expre.indexOf("\n") < (expre.length() - 1))) {
					cutCount++;
				}
			}
		}
		for (SpecialStmt special : specialStmts) {
			String expre = special.getStmt();
			if (expre.trim().length() >= 80) {
				complexCount++;
				if (expre.indexOf("\n") < (expre.length() - 1)) {
					cutCount++;
				}
			}
		}
		if (complexCount == 0) {
			return -1;
		}
		result = 1.0 * cutCount / complexCount;
		return result;
	}

	/**
	 * No. 7 calculate if param is at the next of case
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of next use
	 */
	public double caseUse(String content) {
		double result = 0.0;
		// at the next of case
		int nextline = 0;
		// sum of all case
		int caseline = 0;
		String[] lines = content.split("\n");
		for (String line : lines) {
			line = line.trim();
			int index = line.indexOf("case");
			if ((line.length() > 1) && (!line.substring(0, 2).equals("//"))
					&& (index >= 0)) {
				caseline++;
				if (!line.substring(line.length() - 1).equals(":")) {
					nextline++;
				}
			}
		}
		if (caseline == 0) {
			return -1;
		}
		result = 1.0 * nextline / caseline;
		return result;
	}
}

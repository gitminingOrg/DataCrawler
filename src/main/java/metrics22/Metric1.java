package metrics22;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Metric1 {
	// public static void main(String[] args) throws IOException {
	// Metric1 analysis = new Metric1();
	// ClassVisitor visitor = analysis.ASTsearch();
	// FileStringReader fileStringReader = new FileStringReader();
	// String content = null;
	// try {
	// content = fileStringReader.getFileContent("StructureParser.java");
	// // content = fileStringReader.getFileContent("wc");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// String result = analysis.getMetrics1Result("java",visitor);
	// System.out.println(result);
	// }

	public String getMetrics1Result(String type, List<String> fileNames)
			throws Exception {
		String content = null;
		ClassVisitor visitor = null;

		int no11 = 0, no12 = 0, no21 = 0, no22 = 0, no31 = 0, no32 = 0, no41 = 0, no42 = 0, no51 = 0, no52 = 0, no61 = 0, no62 = 0, no71 = 0, no72 = 0;

		for (String file : fileNames) {
			content = getContent(file);
			if (!type.equals("java")) {
				content = new FileStringReader().removeUselessStmt(content);
			}
			visitor = ASTsearch(content);
			int[] no1 = empltyLine(content);
			no11 += no1[0];
			no12 += no1[1];

			int[] no2 = indentationRatio(content);
			no21 += no2[0];
			no22 += no2[1];

			int[] no3 = bracketUse(visitor);
			no31 += no3[0];
			no32 += no3[1];

			int[] no4 = braceUse(content);
			no41 += no4[0];
			no42 += no4[1];

			int[] no5 = singleUseBrace(visitor);
			no51 += no5[0];
			no52 += no5[1];

			int[] no6 = complexCut(visitor);
			no61 += no6[0];
			no62 += no6[1];

			int[] no7 = caseUse(content);
			no71 += no7[0];
			no72 += no7[1];
		}

		StringBuffer result = new StringBuffer();

		if (no12 == 0) {
			result.append("#,");
		} else {
			double re = 1.0 * no11 / no12;
			result.append(String.format("%.2f", re) + ",");
		}

		int notemp2 = no21 + no22;

		if (notemp2 == 0) {
			result.append("#:#,");
		} else {
			double re1 = 1.0 * no21 / notemp2;
			double re2 = 1.0 * no22 / notemp2;
			result.append(String.format("%.2f", re1) + ":"
					+ String.format("%.2f", re2) + ",");
		}

		if (no32 == 0) {
			result.append("#,");
		} else {
			double re3 = 1.0 * no31 / no32;
			result.append(String.format("%.2f", re3) + ",");
		}

		int notemp4 = no41 + no42;

		if (notemp4 == 0) {
			result.append("#:#,");
		} else {
			double re4 = 1.0 * no41 / notemp4;
			double re44 = 1.0 * no42 / notemp4;
			result.append(String.format("%.2f", re4) + ":"
					+ String.format("%.2f", re44) + ",");
		}

		int notemp5 = no51 + no52;

		if (notemp5 == 0) {
			result.append("#:#,");
		} else {
			double re5 = 1.0 * no51 / notemp5;
			result.append(String.format("%.2f", re5) + ",");
		}

		if (no62 == 0) {
			result.append("#,");
		} else {
			double re6 = 1.0 * no61 / no62;
			result.append(String.format("%.2f", re6) + ",");
		}

		if (no72 == 0) {
			result.append("#,");
		} else {
			double re7 = 1.0 * no71 / no72;
			result.append(String.format("%.2f", re7) + ",");
		}

		return result.toString();

	}

	public static String getContent(String file) {
		FileStringReader fileStringReader = new FileStringReader();
		String content = null;
		try {
			content = fileStringReader.getFileContent(file);
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
	public int[] empltyLine(String content) {
		double result = 0.0;
		// empty line count
		int emptyLine = 0;
		// sum of all lines
		int sumline = 0;
		if (content.trim().equals("")) {
			return new int[] { emptyLine, sumline };
		}
		String[] lines = content.split("\n");
		for (String line : lines) {
			// remove blank
			line = line.trim();
			if (line.length() < 1) {
				emptyLine++;
			}
		}
		sumline = lines.length;
		System.out.println(sumline);
		return new int[] { emptyLine, sumline };
	}

	/**
	 * No. 2 calculate tab and empty use
	 * 
	 * @param content
	 *            the whole text
	 * @return tab percentage & empty percentage
	 */
	public int[] indentationRatio(String content) {
		double[] result = { 0.0, 0.0 };
		// empty line count
		int tabCount = 0;
		// sum of all lines
		int emptyCount = 0;
		if (content.trim().equals("")) {
			return new int[] { tabCount, emptyCount };
		}
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
		// if (tabCount + emptyCount == 0) {
		// result[0] = -1;
		// result[1] = -1;
		// return result;
		// }
		// result[0] = 1.0 * tabCount / (tabCount + emptyCount);
		// result[1] = 1.0 * emptyCount / (tabCount + emptyCount);
		return new int[] { tabCount, emptyCount };
	}

	/**
	 * No. 3 calculate （）
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of（）when complicate lines
	 */
	public int[] bracketUse(ClassVisitor vistor) {
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
			for (int i = 1; i < (express.length() - 1); i++) {
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
				} else if (express.contains(")")) {
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
		// if (sumCount == 0) {
		// return -1;
		// }
		// result = 1.0 * bracketCount / sumCount;
		return new int[] { bracketCount, sumCount };
	}

	/**
	 * No. 4 calculate use of {
	 * 
	 * @param content
	 *            the whole text
	 * @return end percentage & head percentage
	 */
	public int[] braceUse(String content) {
		// { at the end of line
		int lastCount = 0;
		// { at the head of line
		int firstCount = 0;
		if (content.trim().equals("")) {
			return new int[] { lastCount, firstCount };
		}
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
		// if (lastCount + firstCount == 0) {
		// result[0] = -1;
		// result[1] = -1;
		// return result;
		// }
		// result[0] = 1.0 * lastCount / (lastCount + firstCount);
		// result[1] = 1.0 * firstCount / (lastCount + firstCount);
		return new int[] { lastCount, firstCount };
	}

	/**
	 * No. 5 calculate if use{} when one single brace
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of {} when one brace
	 */
	public int[] singleUseBrace(ClassVisitor vistor) {
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
		// if (useCount + nouseCount == 0) {
		// return -1;
		// }
		// result = 1.0 * useCount / (useCount + nouseCount);
		return new int[] { useCount, nouseCount };
	}

	/**
	 * No. 6 calculate if cut lines when complex
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of cut lines
	 */
	public int[] complexCut(ClassVisitor vistor) {
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
		// if (complexCount == 0) {
		// return -1;
		// }
		// result = 1.0 * cutCount / complexCount;
		return new int[] { cutCount, complexCount };
	}

	/**
	 * No. 7 calculate if param is at the next of case
	 * 
	 * @param content
	 *            the whole text
	 * @return percentage of next use
	 */
	public int[] caseUse(String content) {
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
		// if (caseline == 0) {
		// return -1;
		// }
		// result = 1.0 * nextline / caseline;
		return new int[] { nextline, caseline };
	}
}

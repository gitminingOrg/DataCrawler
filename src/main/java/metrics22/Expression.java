package metrics22;

public class Expression {
	private String expression;
	private int start;
	private int length;

	public Expression(int start, int length, String expression) {
		this.start = start;
		this.length = length;
		this.expression = expression;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}

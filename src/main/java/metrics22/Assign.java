package metrics22;

public class Assign {
	int start;
	int length;
	String expression;
	public Assign(int start, int length, String expression) {
		super();
		this.start = start;
		this.length = length;
		this.expression = expression;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}

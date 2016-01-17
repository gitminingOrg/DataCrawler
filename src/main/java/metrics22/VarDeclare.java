package metrics22;

public class VarDeclare {
	String var;
	int start;
	int length;
	String statement;
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
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
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public VarDeclare(String var, int start, int length, String statement) {
		super();
		this.var = var;
		this.start = start;
		this.length = length;
		this.statement = statement;
	}
	
}

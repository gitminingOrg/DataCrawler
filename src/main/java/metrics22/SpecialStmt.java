package metrics22;

public class SpecialStmt {
	String stmt;
	int start;
	int length;
	String type;
	public String getStmt() {
		return stmt;
	}
	public void setStmt(String stmt) {
		this.stmt = stmt;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SpecialStmt(String stmt, int start, int length, String type) {
		super();
		this.stmt = stmt;
		this.start = start;
		this.length = length;
		this.type = type;
	}
	
}

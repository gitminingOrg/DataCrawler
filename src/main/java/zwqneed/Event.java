package zwqneed;

import java.util.Date;

public class Event {
	private String full_name;
	private String type;
	private Date time;
	private String person;
	private String action;
	public Event(String full_name, String type, Date time, String person,
			String action) {
		super();
		this.full_name = full_name;
		this.type = type;
		this.time = time;
		this.person = person;
		this.action = action;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}

package zwqneed;

public class PullRequest {
	int id=-1;
	String fn;
	String committer;
	String created_time;
	boolean is_merged;
	boolean is_insider;
	double time_close = -1;
	
	int commit_num=-1;
	int churn_total_size=-1;
	int churn_final_size=-1;
	int churn_max_size=-1;
	int churn_file_num=-1;
	int final_churn_file_num=-1;
	
	int proj_loc=-1;
	double proj_age=-1;
	int proj_fork=-1;
	int proj_star=-1;
	int proj_insider=-1;
	int proj_open_pr = -1;
	
	int scommit_num=-1;
	int schurn_num=-1;
	int sissue_num=-1;
	int sclosedPR_num=-1;
	int smergedPR_num=-1;
	double sclosedPR_avgtime=-1;
	public int getCommit_num() {
		return commit_num;
	}
	public void setCommit_num(int commit_num) {
		this.commit_num = commit_num;
	}
	public int getChurn_total_size() {
		return churn_total_size;
	}
	public void setChurn_total_size(int churn_total_size) {
		this.churn_total_size = churn_total_size;
	}
	public int getChurn_final_size() {
		return churn_final_size;
	}
	public void setChurn_final_size(int churn_final_size) {
		this.churn_final_size = churn_final_size;
	}
	public int getChurn_max_size() {
		return churn_max_size;
	}
	public void setChurn_max_size(int churn_max_size) {
		this.churn_max_size = churn_max_size;
	}
	public int getChurn_file_num() {
		return churn_file_num;
	}
	public void setChurn_file_num(int churn_file_num) {
		this.churn_file_num = churn_file_num;
	}
	public int getFinal_churn_file_num() {
		return final_churn_file_num;
	}
	public void setFinal_churn_file_num(int final_churn_file_num) {
		this.final_churn_file_num = final_churn_file_num;
	}
	public int getProj_loc() {
		return proj_loc;
	}
	public void setProj_loc(int proj_loc) {
		this.proj_loc = proj_loc;
	}
	public int getProj_fork() {
		return proj_fork;
	}
	public void setProj_fork(int proj_fork) {
		this.proj_fork = proj_fork;
	}
	public int getProj_star() {
		return proj_star;
	}
	public void setProj_star(int proj_star) {
		this.proj_star = proj_star;
	}
	public int getProj_insider() {
		return proj_insider;
	}
	public void setProj_insider(int proj_insider) {
		this.proj_insider = proj_insider;
	}
	public int getProj_open_pr() {
		return proj_open_pr;
	}
	public void setProj_open_pr(int proj_open_pr) {
		this.proj_open_pr = proj_open_pr;
	}
	public int getScommit_num() {
		return scommit_num;
	}
	public void setScommit_num(int scommit_num) {
		this.scommit_num = scommit_num;
	}
	public int getSchurn_num() {
		return schurn_num;
	}
	public void setSchurn_num(int schurn_num) {
		this.schurn_num = schurn_num;
	}
	public int getSissue_num() {
		return sissue_num;
	}
	public void setSissue_num(int sissue_num) {
		this.sissue_num = sissue_num;
	}
	public int getSclosedPR_num() {
		return sclosedPR_num;
	}
	public void setSclosedPR_num(int sclosedPR_num) {
		this.sclosedPR_num = sclosedPR_num;
	}
	public int getSmergedPR_num() {
		return smergedPR_num;
	}
	public void setSmergedPR_num(int smergedPR_num) {
		this.smergedPR_num = smergedPR_num;
	}
	public String getFn() {
		return fn;
	}
	public void setFn(String fn) {
		this.fn = fn;
	}
	public String getCommitter() {
		return committer;
	}
	public void setCommitter(String committer) {
		this.committer = committer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public boolean isIs_merged() {
		return is_merged;
	}
	public void setIs_merged(boolean is_merged) {
		this.is_merged = is_merged;
	}
	public double getTime_close() {
		return time_close;
	}
	public void setTime_close(double time_close) {
		this.time_close = time_close;
	}
	public boolean isIs_insider() {
		return is_insider;
	}
	public void setIs_insider(boolean is_insider) {
		this.is_insider = is_insider;
	}
	public double getProj_age() {
		return proj_age;
	}
	public void setProj_age(double proj_age) {
		this.proj_age = proj_age;
	}
	public double getSclosedPR_avgtime() {
		return sclosedPR_avgtime;
	}
	public void setSclosedPR_avgtime(double sclosedPR_avgtime) {
		this.sclosedPR_avgtime = sclosedPR_avgtime;
	}
	
	
}

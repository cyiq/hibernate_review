package cyiq.relation.onetomany.single.bean;

import java.io.Serializable;

public class Student implements Serializable {
	private Long sid;
	private String sname;
	private String description;
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

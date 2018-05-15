package laojy.entity;

import java.util.Date;

public class SeqNo {
	private long id;
	private Date dateTime;
	private String type;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SeqNo [id=" + id + ", dateTime=" + dateTime + ", type=" + type + "]";
	}
}

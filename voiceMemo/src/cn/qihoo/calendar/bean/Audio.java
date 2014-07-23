package cn.qihoo.calendar.bean;

import java.util.Date;

public class Audio {

	int id;

	String date;

	String remind;

	String url;

	public Audio() {
		super();
	}

	public Audio(String date, String remind, String url) {
		super();
		this.date = date;
		this.remind = remind;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	
	
	
	
	
	
}

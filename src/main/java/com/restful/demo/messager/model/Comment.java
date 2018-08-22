package com.restful.demo.messager.model;

import java.util.Date;

public class Comment {
	private long id;
	private String messgae;
	private Date created;
	private String author;

	public Comment(){	
	}
	public Comment(long id, String messgae, String author) {
		super();
		this.id = id;
		this.messgae = messgae;
		this.created = new Date();
		this.author = author;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessgae() {
		return messgae;
	}
	public void setMessgae(String messgae) {
		this.messgae = messgae;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}

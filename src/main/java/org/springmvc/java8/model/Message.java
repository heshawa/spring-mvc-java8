package org.springmvc.java8.model;

public class Message {
	private String title;
	private String body;

	// Required: default constructor
	public Message() {
	}

	public Message(String title, String body) {
		this.title = title;
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}

package org.springmvc.java8.controller;

public class Response {
	public Response() {
		this.status = 200;
		this.success = true;
		this.message = "";
	}

	private int status;
	private boolean success;

	private String message;

	private String body;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
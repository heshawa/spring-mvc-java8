package org.springmvc.java8.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {
	
	@GetMapping("/hello")
	public Message hello() {
		return new Message("Hello", "World");
	}
	class Message {
		private String title;
		private String body;

		// Required: default constructor
		public Message() {}

		public Message(String title, String body) {
			this.title = title;
			this.body = body;
		}

		public String getTitle() { return title; }
		public void setTitle(String title) { this.title = title; }

		public String getBody() { return body; }
		public void setBody(String body) { this.body = body; }
	}
}

package org.springmvc.java8.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloWorldController {
	
	@GetMapping("")
	public Message hello() {
		return new Message("Hello", "World");
	}
	
	@GetMapping("/{title}/{body}")
	public Message helloWithParams(@PathVariable String title,@PathVariable String body) {
		return new Message(title, body);
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

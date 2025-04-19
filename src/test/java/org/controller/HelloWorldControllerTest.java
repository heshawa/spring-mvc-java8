package org.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springmvc.java8.controller.HelloWorldController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HelloWorldControllerTest {
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp(){
		HelloWorldController hwController = new HelloWorldController();
		mockMvc = MockMvcBuilders.standaloneSetup(hwController).build();
	}
	
	@Test
	public void testHelloDefault(){
		try {
			mockMvc.perform(get("/api/hello"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.title").value("Hello"))
				.andExpect(jsonPath("$.body").value("World"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testHelloWithParams(){
		String title = "Greetings";
		String body = "Earth";
		try {
			mockMvc.perform(get(String.format("/api/hello/%s/%s", title, body)))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.title").value(title))
				.andExpect(jsonPath("$.body").value(body));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

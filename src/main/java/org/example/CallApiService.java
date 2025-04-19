package org.example;

import org.spring.app.model.Product;
import org.springframework.web.client.RestTemplate;

public class CallApiService {

	private static final RestTemplate restTemplate = new RestTemplate();
	private static final String API_URL = "https://api.restful-api.dev/objects/";
	public static String getProductNameForId(int id) {
		// Simulate an API call
		return restTemplate.getForObject(API_URL + id, String.class);
	}
	
	public static Product getProductDetailForId(int id) {
		// Simulate an API call
		return restTemplate.getForObject(API_URL + id, Product.class);
	}
}

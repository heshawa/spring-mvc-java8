package org.springmvc.tests;

import org.example.CallApiService;
import org.junit.jupiter.api.Test;
import org.spring.app.model.Product;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CallApiServiceTest {
	
	private final CallApiService service = new CallApiService();
	
	@Test
	public void testGetProductNameForId() {
		String productName = service.getProductNameForId(7);
		assertNotNull(productName);
		assertTrue(productName.contains("Mac"));
	}
	
	@Test
	public void testGetProductDetailForId() {
		Product product = service.getProductDetailForId(7);
		assertNotNull(product);
		assertTrue(product.getName().contains("Mac"));
		assertNotNull(product.getData());
		assertTrue(product.getData().getCpuModel().contains("Intel"));
		assertTrue(product.getData().getHardDiskSize().contains("1 TB"));
	}
}

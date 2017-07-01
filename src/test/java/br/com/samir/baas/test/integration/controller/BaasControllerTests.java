package br.com.samir.baas.test.integration.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.samir.baas.test.integration.IntegrationTest;

public class BaasControllerTests extends IntegrationTest {

	@Test
	public void getTest() {
		String body = get("/teste/1", String.class);
		assertEquals(body, "{}");
	}
}

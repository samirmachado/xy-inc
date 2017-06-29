package br.com.samir.baas.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaasTests {

	@LocalServerPort
	int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void getTest() {
		String body = testRestTemplate.getForObject("http://localhost:"+port+"/teste/1", String.class);
		assertEquals(body, "{}");
	}
}

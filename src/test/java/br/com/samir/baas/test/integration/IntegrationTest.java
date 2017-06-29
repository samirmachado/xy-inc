package br.com.samir.baas.test.integration;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

	@LocalServerPort
	protected int PORT;
	
	protected String BASE_URI_TO_TESTS = "http://localhost";
	
	@Autowired
	protected TestRestTemplate testRestTemplate;
	
	public <T> T get(String endPoint, Class<T> responseClass) {
		return testRestTemplate.getForObject(createUri(endPoint), responseClass);
	}
	
	private String createUri(String endPoint){
		return BASE_URI_TO_TESTS+":"+PORT+endPoint;
	}
}

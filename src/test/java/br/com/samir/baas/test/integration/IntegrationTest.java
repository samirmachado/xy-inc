package br.com.samir.baas.test.integration;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.samir.baas.database.Database;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

	@LocalServerPort
	protected int PORT;
	
	@Autowired
	protected Database database;
	
	protected String BASE_URI_TO_TESTS = "http://localhost";
	
	@Autowired
	protected TestRestTemplate testRestTemplate;
	
	public ResponseEntity<String> get(String endPoint) {
		return testRestTemplate.getForEntity(createUri(endPoint), String.class);
	}
	
	public ResponseEntity<String> post(String endPoint, String jsonObject) {
		return testRestTemplate.postForEntity(createUri(endPoint), jsonObject, String.class);
	}
	
	public void clearDatabase() {
		database.clear();
	}
	
	private String createUri(String endPoint){
		return BASE_URI_TO_TESTS+":"+PORT+endPoint;
	}
}

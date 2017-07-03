package br.com.samir.baas.test.integration;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.samir.baas.database.Database;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class IntegrationTest {

	@LocalServerPort
	protected int PORT;
	
	@Value("${server.url}")
	protected String SERVER_URL;
	
	@Autowired
	protected Database database;
	
	@Autowired
	protected TestRestTemplate testRestTemplate;
	
	public ResponseEntity<String> get(String endPoint) {
		return testRestTemplate.getForEntity(createUri(endPoint), String.class);
	}
	
	public ResponseEntity<String> post(String endPoint, String jsonObject) {
		return testRestTemplate.postForEntity(createUri(endPoint), jsonObject, String.class);
	}
	
	public ResponseEntity<String> update(String endPoint, String jsonObject) {
		HttpEntity<String> entity = new HttpEntity<String>(jsonObject, null); 
		return testRestTemplate.exchange(createUri(endPoint), HttpMethod.PUT, entity, String.class);
	}
	
	public ResponseEntity<String> delete(String endPoint) {
		return testRestTemplate.exchange(createUri(endPoint), HttpMethod.DELETE, null, String.class);
	}
	
	public void clearDatabase() {
		database.clear();
	}
	
	private String createUri(String endPoint){
		return SERVER_URL+":"+PORT+endPoint;
	}
}

package br.com.samir.baas.test.unit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.samir.baas.exception.NotFoundException;
import br.com.samir.baas.repository.BaasRepository;
import br.com.samir.baas.service.BaasService;

@RunWith(MockitoJUnitRunner.class)
public class BaasServiceTests{
	
	@InjectMocks
	BaasService baasService;
	
	@Mock
	private BaasRepository baasRepository;

	@Test(expected = NotFoundException.class)
	public void findByTableAndIdTestObjectNotFound() throws NotFoundException {
		String tableName = "table";
		String id = "1";
		
		when(baasRepository.findById(tableName, id)).thenReturn(null);
		baasService.findByTableAndId(tableName, id);
	}
	
	@Test
	public void findByTableAndIdTestObjectFound() throws NotFoundException {
		String tableName = "table";
		String id = "1";
		String jsonResponse = "{}";
		
		when(baasRepository.findById(tableName, id)).thenReturn(jsonResponse);
		String response = baasService.findByTableAndId(tableName, id);
		
		assertEquals(jsonResponse, response);
	}
	
	@Test(expected = NotFoundException.class)
	public void removeByTableAndIdTestObjectNotFound() throws NotFoundException {
		String tableName = "table";
		String id = "1";
		
		when(baasRepository.remove(tableName, id)).thenReturn(false);
		baasService.removeByTableAndId(tableName, id);
	}
}

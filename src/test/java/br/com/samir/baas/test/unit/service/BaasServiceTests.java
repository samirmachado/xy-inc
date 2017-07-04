package br.com.samir.baas.test.unit.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
	
	@Mock
	private List<String> listOfString;

	@Test(expected = NotFoundException.class)
	public void findByTableAndIdTestWithObjectNotFound() throws NotFoundException {
		String tableName = "table";
		String id = "1";
		
		when(baasRepository.findById(tableName, id)).thenReturn(null);
		baasService.findByTableAndId(tableName, id);
	}
	
	@Test(expected = NotFoundException.class)
	public void listTestWithObjectNotFound() throws NotFoundException {
		String tableName = "table";
		
		when(baasRepository.list(tableName)).thenReturn(new ArrayList<String>());
		baasService.list(tableName);
	}
}

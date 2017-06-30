package br.com.samir.baas.test.unit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.samir.baas.repository.BaasRepository;
import br.com.samir.baas.service.BaasService;

@RunWith(MockitoJUnitRunner.class)
public class BaasServiceTests{
	
	@InjectMocks
	BaasService baasService;
	
	@Mock
	private BaasRepository baasRepository;

	@Test
	public void findByTableAndIdTestIfCallTheRepositoryMethodCorrectly() {
		String tableName = "table";
		Long id = 1L;
		String expectedResult = "{}";
		when(baasRepository.findById(tableName, id)).thenReturn(expectedResult);
		
		String result = baasService.findByTableAndId(tableName, id);
		
		verify(baasRepository, times(1)).findById(tableName, id);
		assertEquals(expectedResult, result);
	}
}

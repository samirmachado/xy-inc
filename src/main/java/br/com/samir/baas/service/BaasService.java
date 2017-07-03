package br.com.samir.baas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.samir.baas.exception.InvalidJsonObjectException;
import br.com.samir.baas.exception.NotFoundException;
import br.com.samir.baas.repository.BaasRepository;

@Service
public class BaasService {

	@Autowired
	private BaasRepository baasRepository;

	public String findByTableAndId(String tableName, String id) throws NotFoundException {
		String jsonObject = baasRepository.findById(tableName, id);
		if(jsonObject==null) {
			throw new NotFoundException();
		}
		return jsonObject;
	}
	
	public void removeByTableAndId(String tableName, String id) throws NotFoundException {
		baasRepository.remove(tableName, id);
	}

	public String insert(String tableName, String jsonObject) throws InvalidJsonObjectException {
		return baasRepository.insert(tableName, jsonObject);
	}
	
	public void update(String tableName, String jsonObject, String id) throws InvalidJsonObjectException, NotFoundException {
		baasRepository.update(tableName, jsonObject, id);
	}
}

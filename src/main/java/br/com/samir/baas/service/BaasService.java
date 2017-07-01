package br.com.samir.baas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.samir.baas.repository.BaasRepository;

@Service
public class BaasService {

	@Autowired
	private BaasRepository baasRepository;

	public String findByTableAndId(String tableName, String id) {
		return baasRepository.findById(tableName, id);
	}
}

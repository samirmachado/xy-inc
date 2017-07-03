package br.com.samir.baas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.samir.baas.exception.NotFoundException;
import br.com.samir.baas.service.BaasService;

@RestController
public class BaasController {

	@Autowired
	private BaasService baasService;

	@RequestMapping(value = { "/{tableName}/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<String> findById(@PathVariable("tableName") String tableName, @PathVariable("id") String id) {
		try {
			String response = baasService.findByTableAndId(tableName, id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = { "/{tableName}" }, method = RequestMethod.POST)
	public ResponseEntity<String> post(@PathVariable("tableName") String tableName, @RequestBody String jsonObject) {
		try {
			String response = baasService.insert(tableName, jsonObject);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = { "/{tableName}/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("tableName") String tableName, @PathVariable("id") String id) {
		try {
			baasService.removeByTableAndId(tableName, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

package br.com.samir.baas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaasController {
	
	@RequestMapping(value={ "/{table}/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<String> getTest(@PathVariable("table") String table, @PathVariable("id") Long id) {
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
}

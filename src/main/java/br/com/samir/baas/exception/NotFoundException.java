package br.com.samir.baas.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Object was not found";
	}
}

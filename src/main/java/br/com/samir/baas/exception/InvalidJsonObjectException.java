package br.com.samir.baas.exception;

public class InvalidJsonObjectException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Json with invalid format";
	}

}

package br.com.samir.baas.exception;

public class InvalidJsonObjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Json with invalid format";
	}

}

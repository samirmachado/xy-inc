package br.com.samir.baas.exception;

public class NonUniqueResultException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "There is more than one query result";
	}
}

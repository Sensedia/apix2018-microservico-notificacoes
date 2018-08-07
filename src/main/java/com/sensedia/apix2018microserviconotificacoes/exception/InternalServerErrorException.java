package com.sensedia.apix2018microserviconotificacoes.exception;

public class InternalServerErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(String message) {
		super(message);
	}

	public InternalServerErrorException(String message, Throwable throwable) {
		super(message, throwable);
	}

}

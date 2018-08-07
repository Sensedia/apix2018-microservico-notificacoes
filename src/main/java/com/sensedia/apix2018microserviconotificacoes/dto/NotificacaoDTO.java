package com.sensedia.apix2018microserviconotificacoes.dto;

import java.io.Serializable;

public class NotificacaoDTO implements Serializable {

	private String type;

	private EmailDTO email;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EmailDTO getEmail() {
		return email;
	}

	public void setEmail(EmailDTO email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "NotificacaoDTO{" + "type='" + type + '\'' + ", email=" + email + '}';
	}
}

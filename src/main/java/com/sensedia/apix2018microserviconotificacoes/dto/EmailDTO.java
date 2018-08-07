package com.sensedia.apix2018microserviconotificacoes.dto;

import java.io.Serializable;

public class EmailDTO implements Serializable {

	private String to;

	private String subject;

	private String emailText;

	public EmailDTO(String to, String subject, String emailText) {
		this.to = to;
		this.subject = subject;
		this.emailText = emailText;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	@Override
	public String toString() {
		return "EmailDTO{" + "to='" + to + '\'' + ", subject='" + subject + '\'' + ", emailText='" + emailText + '\''
				+ '}';
	}
}

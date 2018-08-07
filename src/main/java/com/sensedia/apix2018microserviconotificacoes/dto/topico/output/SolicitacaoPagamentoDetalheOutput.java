package com.sensedia.apix2018microserviconotificacoes.dto.topico.output;

import com.sensedia.apix2018microserviconotificacoes.dto.enums.StatusRetornoEnum;

import java.io.Serializable;

public class SolicitacaoPagamentoDetalheOutput implements Serializable {

	private static final long serialVersionUID = 1L;

	private final StatusRetornoEnum status;
	private final String mensagem;

	public SolicitacaoPagamentoDetalheOutput(StatusRetornoEnum status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}

	public StatusRetornoEnum getStatus() {
		return status;
	}

	public String getMensagem() {
		return mensagem;
	}

}

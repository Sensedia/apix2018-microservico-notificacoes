package com.sensedia.apix2018microserviconotificacoes.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sensedia.apix2018microserviconotificacoes.dto.topico.input.SolicitacaoPagamentoInput;

public interface INotificacaoService {

	void enviarNotificacao(SolicitacaoPagamentoInput solicitacaoPagamentoInput) throws JsonProcessingException;
}

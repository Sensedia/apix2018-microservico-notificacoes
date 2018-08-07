package com.sensedia.apix2018microserviconotificacoes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensedia.apix2018microserviconotificacoes.dto.EmailDTO;
import com.sensedia.apix2018microserviconotificacoes.dto.NotificacaoDTO;
import com.sensedia.apix2018microserviconotificacoes.dto.enums.StatusRetornoEnum;
import com.sensedia.apix2018microserviconotificacoes.dto.topico.input.SolicitacaoPagamentoInput;
import com.sensedia.apix2018microserviconotificacoes.dto.topico.output.SolicitacaoPagamentoDetalheOutput;
import com.sensedia.apix2018microserviconotificacoes.dto.topico.output.SolicitacaoPagamentoOutput;
import com.sensedia.apix2018microserviconotificacoes.exception.InternalServerErrorException;
import com.sensedia.apix2018microserviconotificacoes.pubsub.NotificacaoProducer;
import com.sensedia.apix2018microserviconotificacoes.services.INotificacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class NotificacaoService implements INotificacaoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacaoService.class);

	private static final String CLIENT_ID = "client_id";

	private static final String MENSAGEM_SUCESSO = "Pagamento confirmado com sucesso";

	private static final String MENSAGEM_ERRO = "Ocorreu um erro ao realizar o pagamento";

	private static final String MENSAGEM_ASSUNTO = "Solicitação de Pagamento #";

	@Autowired
	private RestTemplate restTemplate;

	@Value("${notificacao.host}")
	private String notificacaoHost;

	@Value("${notificacao.client.id}")
	private String notificacaoClientId;

	@Autowired
	private NotificacaoProducer.NotificacaoOutputGateway notificacaoOutputGateway;

	@Autowired
	private ObjectMapper objectMapper;

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(CLIENT_ID, notificacaoClientId.trim());
		return headers;
	}

	private HttpEntity<String> buildEntity(NotificacaoDTO notificacao) throws JsonProcessingException {
		return new HttpEntity<>(objectMapper.writeValueAsString(notificacao), buildHeaders());
	}

	private ResponseEntity<String> makeRequest(NotificacaoDTO notificacao) {
		try {
			return restTemplate
					.exchange(notificacaoHost.trim(), HttpMethod.POST, buildEntity(notificacao), String.class);
		} catch (Exception e) {
			LOGGER.error("Falha ao comunicar com host: {}", notificacaoHost);
			throw new InternalServerErrorException("Falha ao comunicar com host", e);
		}
	}

	private void publicarResposta(SolicitacaoPagamentoInput solicitacaoPagamentoInput) throws JsonProcessingException {

		SolicitacaoPagamentoOutput solicitacaoPagamentoOutput = new SolicitacaoPagamentoOutput(
				solicitacaoPagamentoInput.getId(), Arrays.asList(
				new SolicitacaoPagamentoDetalheOutput(StatusRetornoEnum.SUCESSO, "Notificação enviada com sucesso")));

		notificacaoOutputGateway.sendToPubsub(objectMapper.writeValueAsString(solicitacaoPagamentoOutput));

	}

	@Override
	public void enviarNotificacao(SolicitacaoPagamentoInput solicitacaoPagamentoInput) throws JsonProcessingException {

		NotificacaoDTO notificacao = new NotificacaoDTO();
		notificacao.setType("EMAIL");
		notificacao.setEmail(new EmailDTO(solicitacaoPagamentoInput.getUsuarioEmail(),
				MENSAGEM_ASSUNTO + solicitacaoPagamentoInput.getId(),
				solicitacaoPagamentoInput.isValid() ?
						MENSAGEM_SUCESSO :
						MENSAGEM_ERRO));

		LOGGER.debug("Enviando notificacao: {}", notificacao);

		ResponseEntity<String> response = makeRequest(notificacao);

		if (response.getStatusCode().value() != 201) {
			LOGGER.error("Erro ao enviar notificacao: " + response);
			throw new InternalServerErrorException(
					"Erro ao enviar notificacao, status code: " + response.getStatusCode().value());
		}

		publicarResposta(solicitacaoPagamentoInput);

	}
}

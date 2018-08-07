package com.sensedia.apix2018microserviconotificacoes.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.sensedia.apix2018microserviconotificacoes.configuration.PubSubConfiguration;
import com.sensedia.apix2018microserviconotificacoes.dto.topico.input.SolicitacaoPagamentoInput;
import com.sensedia.apix2018microserviconotificacoes.services.INotificacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PubSubConfiguration.class);

	@Value("${subscription.name}")
	private String subscriptionName;

	@Autowired
	private INotificacaoService notificacaoService;

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("notificacoesInputChannel")
					MessageChannel inputChannel, PubSubOperations pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "notificacoesInputChannel")
	public MessageHandler messageReceiver() {
		return message -> {
			LOGGER.debug("Mensagem recebida: " + message.getPayload());

			AckReplyConsumer consumer = (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);

			try {
				SolicitacaoPagamentoInput solicitacaoPagamentoInput = objectMapper
						.readValue(message.getPayload().toString(), SolicitacaoPagamentoInput.class);

				notificacaoService.enviarNotificacao(solicitacaoPagamentoInput);
				consumer.ack();

			} catch (Exception e) {
				consumer.nack();
				LOGGER.error("Erro processando mensagem", e);
			}

		};
	}

}

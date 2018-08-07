package com.sensedia.apix2018microserviconotificacoes.pubsub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoProducer {

	@Value("${topic.out.name}")
	private String topicOutName;

	@Bean
	@ServiceActivator(inputChannel = "notificacoesOutputChannel")
	public MessageHandler messageSender(PubSubOperations pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, topicOutName);
	}

	@MessagingGateway(defaultRequestChannel = "notificacoesOutputChannel")
	public interface NotificacaoOutputGateway {

		void sendToPubsub(String text);
	}

}

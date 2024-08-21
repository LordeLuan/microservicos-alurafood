package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoAMQPConfiguration {

	// configurando para utilizar converter especifico para envio de objetos json
	// por mensagem
	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// Setando o conversor especifico para Json na instancia da classe de template
	// da mensagem que é enviada
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}
	
	// Cria a fila que recebe mensagem da exchange FANOUT
	@Bean 
	public Queue criaDetalhesPedidosPagamento() {
		return QueueBuilder.nonDurable("pagamentos.detalhes-pedido").build();
	}
	
	@Bean
	public FanoutExchange criaFanoutExchange() {
		return ExchangeBuilder.fanoutExchange("pagamentos.ex").build();
	}
	
	@Bean
	
	public Binding bindMensagemExchangePagamentosToQueueDetalhesPedidos(FanoutExchange fanoutExchante) {
		return BindingBuilder
				.bind(criaDetalhesPedidosPagamento()) // Fila
				.to(fanoutExchante);				  // Exchange
	}
	
	// UTILIZADOS PARA CRIAR AS COISAS NO ADMININSTRADOR DO RABBITMQ
	@Bean
	public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
		return new RabbitAdmin(conn);
	}
	
	// Para inicializar o rabbitAdmin
	@Bean
	public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin){
		return event -> rabbitAdmin.initialize();
	}
}


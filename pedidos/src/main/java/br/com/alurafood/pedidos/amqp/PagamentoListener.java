package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.alurafood.pedidos.dto.amqp.PagamentoDto;

// Classe que fica "OUVINDO" as filas que forem configuradas nos metodos
@Component
public class PagamentoListener {

    @RabbitListener(queues = "pagamentos.detalhes-pedido")
    public void recebeMensagem(@Payload PagamentoDto pagamento) { 
    	String mensagem = """
    			Dados do pagamento: %s
    			Número do pedido: %s
    			Valor R$: %s
    			Status: %s 
    			""".formatted(pagamento.getId(), pagamento.getNumero(), pagamento.getValor(), pagamento.getStatus());
        System.out.println("Recebi a mensagem " + mensagem);
    }
}

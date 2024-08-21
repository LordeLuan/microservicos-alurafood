package br.com.alurafood.pedidos.dto.amqp;

import java.math.BigDecimal;

import br.com.alurafood.pedidos.model.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDto {

	  private Long id;
	    private BigDecimal valor;
	    private String nome;
	    private String numero;
	    private String expiracao;
	    private String codigo;
	    private StatusPagamento status;
	    private Long pedidoId;
	    private Long formaDePagamentoId;
	    
}

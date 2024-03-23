package br.com.alurafood.pagamentos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// marca a classe como de configuração pro spring
@Configuration
public class Configuracao {

//	Criando e passando a intancia da classe para que ele possa gerenciar e fazer a injeção de dependencia
	@Bean
	ModelMapper obterModelMapper() {
		return new ModelMapper();
	}
}

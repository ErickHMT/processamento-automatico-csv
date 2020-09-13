package com.sincronizacaoreceita.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.sincronizacaoreceita.SincronizacaoreceitaApplication;
import com.sincronizacaoreceita.model.CsvConta;

@Service
public class SincronizacaoReceitaService {
	
	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoreceitaApplication.class);

	@Bean
	public ReceitaService getReceitaService(){
		return new ReceitaService();
	}
	
	/**
	 * Realiza processamento das informaçoes através do serviço da receita {@link ReceitaService}.
	 * 
	 * @param infoContas
	 * @return
	 */
	public List<CsvConta> realizarProcessamento(List<CsvConta> infoContas) {
		
		try {

			int progresso = 1;
			int totalItems = infoContas.size();
			for(CsvConta conta: infoContas) {
				// Faz o log do progresso do processamento em relaçao ao total de itens
				logger.info("Enviando a atualização para a Receita... {}/{}", progresso, totalItems);

				double saldoFormatado = Double.parseDouble(conta.getSaldo().replace(',', '.'));
				String contaFormatada = conta.getConta().replace("-", "");

				boolean resultado = getReceitaService().atualizarConta(conta.getAgencia(), contaFormatada, saldoFormatado, conta.getStatus());
				conta.setResultado(resultado);			

				progresso++;
			}
		} catch (InterruptedException e) {
			logger.error("Falha ao realizar Sincronização!");
			e.printStackTrace();
		}
		
		return infoContas;
	}
}

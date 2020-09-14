package com.sincronizacaoreceita.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sincronizacaoreceita.SincronizacaoreceitaApplication;
import com.sincronizacaoreceita.model.ContaBean;

/**
 * 
 * Realiza a comunicaçao com o serviço da receita.
 * 
 * @author Erick Teixeira
 *
 */
@Service
public class SincronizacaoReceitaService {

	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoreceitaApplication.class);

	ReceitaService receitaService;

	public SincronizacaoReceitaService() {
		receitaService = new ReceitaService();
	}

	/**
	 * Realiza sincronizacao das informaçoes através do serviço da receita
	 * {@link ReceitaService}.
	 * 
	 * @param contas
	 * @return lista {@link ContaBean} atualizada com o resultado do processamento
	 */
	public List<ContaBean> realizarSincronizacao(List<ContaBean> contas) {

		try {
			int progresso = 1;
			int totalItems = contas.size();
			for (ContaBean conta : contas) {
				logger.info("Enviando a atualizacao para a Receita... {}/{}", progresso, totalItems);

				double saldoFormatado = Double.parseDouble(conta.getSaldo().replace(',', '.'));
				String contaFormatada = conta.getConta().replace("-", "");

				boolean resultado = receitaService.atualizarConta(conta.getAgencia(), contaFormatada, saldoFormatado,
						conta.getStatus());
				conta.setResultado(resultado);

				progresso++;
			}
		} catch (InterruptedException e) {
			logger.error("Falha ao enviar atualizacao para a Receita!");
			e.printStackTrace();
		}

		return contas;
	}
}

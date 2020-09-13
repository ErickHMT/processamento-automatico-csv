package com.sincronizacaoreceita;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sincronizacaoreceita.model.ContaBean;
import com.sincronizacaoreceita.service.CsvContaService;
import com.sincronizacaoreceita.service.SincronizacaoReceitaService;

@SpringBootApplication
public class SincronizacaoreceitaApplication implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoreceitaApplication.class);
	
	@Autowired
	CsvContaService csvContaService;
	
	@Autowired
	SincronizacaoReceitaService sincronizacaoReceitaService;

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoreceitaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        if (args.length > 0) {
        	String fileName = args[0];
        	
    		List<ContaBean> contaBeanList = csvContaService.leArquivoCsv(fileName);
    		
    		if(contaBeanList != null) {
	        	List<ContaBean> resultadoList = sincronizacaoReceitaService.realizarSincronizacao(contaBeanList);
	        	csvContaService.escreveArquivoCsv(resultadoList);
    		}
        } else {
        	logger.error("Nome do arquivo deve ser informado como parametro. Ex: \"java -jar <exemplo>.jar <nome-arquivo>.csv\"");
        }
	}

}

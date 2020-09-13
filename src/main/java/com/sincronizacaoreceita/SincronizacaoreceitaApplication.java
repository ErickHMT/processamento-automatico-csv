package com.sincronizacaoreceita;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sincronizacaoreceita.model.CsvConta;
import com.sincronizacaoreceita.model.ReceitaBean;
import com.sincronizacaoreceita.service.CsvContaService;

@SpringBootApplication
public class SincronizacaoreceitaApplication implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoreceitaApplication.class);
	
	@Autowired
	CsvContaService csvContaService;

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoreceitaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args));
        
        if (args.length > 0) {
        	String fileName = args[0];

        	List<CsvConta> infoContas = csvContaService.leArquivoCsv(fileName);     
        	csvContaService.escreveArquivoCsv(infoContas);

        } else {
        	logger.error("Nome do arquivo deve ser informado através do comando \"java -jar <example>.jar ./<file-name>.csv\"");
        }
	}

}

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
import com.sincronizacaoreceita.service.CsvContaService;
import com.sincronizacaoreceita.service.SincronizacaoReceitaService;

import static java.lang.System.exit;

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
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args));
        
        if (args.length > 0) {
        	String fileName = args[0];

        	List<CsvConta> infoContas = csvContaService.leArquivoCsv(fileName);
        	List<CsvConta> resultadoList = sincronizacaoReceitaService.realizarProcessamento(infoContas);
        	csvContaService.escreveArquivoCsv(resultadoList);

        } else {
        	logger.error("Nome do arquivo deve ser informado \"java -jar <example>.jar ./<file-name>.csv\"");
        }
        
        exit(0);
	}

}

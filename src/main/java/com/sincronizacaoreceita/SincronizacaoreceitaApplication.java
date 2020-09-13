package com.sincronizacaoreceita;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sincronizacaoreceita.model.CsvConta;

@SpringBootApplication
public class SincronizacaoreceitaApplication implements ApplicationRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoreceitaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoreceitaApplication.class, args);
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());
		
		System.out.println("Olha aí: " + args.getSourceArgs()[0]);
		leArquivoCsv();
	}
	
	@Bean
	public ReceitaService getReceitaService(){
		return new ReceitaService();
	}
	
	private void leArquivoCsv() {
		var fileName = "C:/Users/erick/Downloads/info-contas.csv";
        Path filePath = Paths.get(fileName);

		try {			
			Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
			CsvToBean<CsvConta> csvToBean = new CsvToBeanBuilder<CsvConta>(reader).withType(CsvConta.class).withSeparator(';').build();
			List<CsvConta> infoContas = csvToBean.parse();

			escreveArquivoCsv(infoContas);

		} catch (IOException | RuntimeException | InterruptedException e) {
			System.out.println("Falha ao realizar leitura do arquivo");
			e.printStackTrace();
		}
	}
	
	private void escreveArquivoCsv(List<CsvConta> infoContas) throws RuntimeException, InterruptedException {
		List<ReceitaBean> resultadoList = getReceitaBeanListFromCsvOrigem(infoContas);
		
		try {
			char SEPARATOR = ';';
			Writer writer = Files.newBufferedWriter(Paths.get("C:/Users/erick/Downloads/receita-processada.csv"));
			StatefulBeanToCsv<ReceitaBean> beanToCsv = new StatefulBeanToCsvBuilder<ReceitaBean>(writer).withSeparator(SEPARATOR).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			
			for(var resultado: resultadoList) {
				System.out.println("item processado: " + resultado);
			}
			
			beanToCsv.write(resultadoList);
			writer.close();
		} catch(IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
	}
	
	private List<ReceitaBean> getReceitaBeanListFromCsvOrigem(List<CsvConta> infoContas) throws RuntimeException, InterruptedException {
		List<ReceitaBean> resultadoList = new ArrayList<>();

		for(CsvConta receita: infoContas) {
			double saldo = Double.parseDouble(receita.getSaldo().replace(',', '.'));			
			var resultado = getReceitaService().atualizarConta(receita.getAgencia(), receita.getConta(), saldo, receita.getStatus());
			var itemProcessado = new ReceitaBean(receita.getAgencia(), receita.getConta(), saldo, receita.getStatus());

			itemProcessado.setResultado(resultado);
			
			resultadoList.add(itemProcessado);
		}
		
		return resultadoList;
	}

}

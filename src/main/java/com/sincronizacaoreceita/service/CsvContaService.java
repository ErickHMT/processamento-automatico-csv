package com.sincronizacaoreceita.service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sincronizacaoreceita.ReceitaService;
import com.sincronizacaoreceita.SincronizacaoreceitaApplication;
import com.sincronizacaoreceita.model.CsvConta;
import com.sincronizacaoreceita.model.ReceitaBean;

@Service
public class CsvContaService {
	
	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoreceitaApplication.class);
	
	public static final String NOME_ARQUIVO_FINAL = "resultado.csv";
	public static final char CSV_SEPARATOR = ';';
	private Path filePath;
	
	@Bean
	public ReceitaService getReceitaService(){
		return new ReceitaService();
	}

	/**
	 * Realiza leitura do arquivo csv
	 * 
	 * @param fileName
	 * @return
	 */
	public List<CsvConta> leArquivoCsv(String fileName) {
		List<CsvConta> infoContas = new ArrayList<>();
		filePath = Paths.get(fileName);
        
		try {
			logger.info("Realizando leitura do arquivo...");

			Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
			CsvToBean<CsvConta> csvToBean = new CsvToBeanBuilder<CsvConta>(reader).withType(CsvConta.class).withSeparator(';').build();
			infoContas = csvToBean.parse();

			reader.close();
		} catch (IOException e) {
			logger.error("Falha ao realizar leitura do arquivo");
			e.printStackTrace();
		}
		
		return infoContas;
	}

	public void escreveArquivoCsv(List<CsvConta> infoContas) {
		List<ReceitaBean> resultadoList = realizarProcessamento(infoContas);
		var filePathNovoArquivo = Paths.get(filePath.getParent().toString() + "/" + NOME_ARQUIVO_FINAL);

		try {
			Writer writer = Files.newBufferedWriter(filePathNovoArquivo);
			StatefulBeanToCsv<ReceitaBean> beanToCsv = new StatefulBeanToCsvBuilder<ReceitaBean>(writer).withSeparator(CSV_SEPARATOR).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(resultadoList);

			writer.close();
		} catch(IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Realiza processamento das informaçoes através do serviço da receita
	 * 
	 * @param infoContas
	 * @return
	 */
	private List<ReceitaBean> realizarProcessamento(List<CsvConta> infoContas) {
		List<ReceitaBean> resultadoList = new ArrayList<>();
		
		try {
			logger.info("Realizando geração do arquivo final...");

			for(CsvConta receita: infoContas) {
					double saldoFormatado = Double.parseDouble(receita.getSaldo().replace(',', '.'));
					String contaFormatada = receita.getConta().replace("-", "");

					var itemProcessado = new ReceitaBean(receita.getAgencia(), receita.getConta(), saldoFormatado, receita.getStatus());
	
					boolean resultado = getReceitaService().atualizarConta(receita.getAgencia(), contaFormatada, saldoFormatado, receita.getStatus());
					itemProcessado.setResultado(resultado);			
					resultadoList.add(itemProcessado);
	
			}
			
			logger.info("Arquivo gerado com sucesso!");
		} catch (RuntimeException | InterruptedException e) {
			logger.info("Falha ao gerar arquivo!");
			e.printStackTrace();
		}
		
		return resultadoList;
	}
}

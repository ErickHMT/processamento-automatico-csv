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
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sincronizacaoreceita.SincronizacaoreceitaApplication;
import com.sincronizacaoreceita.model.CsvConta;
import com.sincronizacaoreceita.model.CustomMappingCsvContaBean;

@Service
public class CsvContaService {
	
	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoreceitaApplication.class);
	
	public static final String NOME_ARQUIVO_FINAL = "resultado.csv";
	public static final char CSV_SEPARATOR = ';';
	private Path filePath;

	/**
	 * Realiza leitura do arquivo csv.
	 * 
	 * @param fileName
	 * @return
	 */
	public List<CsvConta> leArquivoCsv(String fileName) {
		List<CsvConta> infoContas = new ArrayList<>();
		filePath = Paths.get(fileName);
        
		try (Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)){
			logger.info("Realizando leitura do arquivo...");

			CsvToBean<CsvConta> csvToBean = new CsvToBeanBuilder<CsvConta>(reader).withType(CsvConta.class).withSkipLines(1).withSeparator(CSV_SEPARATOR).build();
			infoContas = csvToBean.parse();

		} catch (IOException e) {
			logger.error("Falha ao realizar leitura do arquivo");
			e.printStackTrace();
		}
		
		return infoContas;
	}

	/**
	 * Gera arquivo csv através da lista de {@link ContaBean} informada.
	 * 
	 * @param infoContas
	 */
	public void escreveArquivoCsv(List<CsvConta> infoContas) {
		var filePathNovoArquivo = Paths.get(filePath.getParent().toString() + "/" + NOME_ARQUIVO_FINAL);
		
		try (Writer writer = Files.newBufferedWriter(filePathNovoArquivo)) {
			logger.info("Gerando arquivo final...");
			
			final CustomMappingCsvContaBean<CsvConta> mappingStrategy = new CustomMappingCsvContaBean<>();
			mappingStrategy.setType(CsvConta.class);

			final StatefulBeanToCsv<CsvConta> beanToCsv = new StatefulBeanToCsvBuilder<CsvConta>(writer).withSeparator(CSV_SEPARATOR).withMappingStrategy(mappingStrategy).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(infoContas);

			logger.info("Arquivo gerado com sucesso!");
		} catch(IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			logger.error("Falha ao gerar arquivo!");
			e.printStackTrace();
		}

	}
}

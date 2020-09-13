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
import com.sincronizacaoreceita.model.ContaBean;

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

			writer.close();
	/**
	 * Gera arquivo csv através da lista de {@link ContaBean} informada.
	 * 
	 * @param infoContas
	 */
		try {
	public void escreveArquivoCsv(List<ContaBean> infoContas) {
		var filePathNovoArquivo = Paths.get(filePath.getParent().toString() + "/" + NOME_ARQUIVO_FINAL);

			logger.info("Gerando arquivo final...");

			StatefulBeanToCsv<ContaBean> beanToCsv = new StatefulBeanToCsvBuilder<ContaBean>(writer).withSeparator(CSV_SEPARATOR).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(infoContas);

			logger.info("Arquivo gerado com sucesso!");
		} catch(IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			logger.error("Falha ao gerar arquivo!");
			e.printStackTrace();
		}

	}
}

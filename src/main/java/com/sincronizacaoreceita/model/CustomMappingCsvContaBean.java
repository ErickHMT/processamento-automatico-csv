package com.sincronizacaoreceita.model;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;


public class CustomMappingCsvContaBean<T> extends ColumnPositionMappingStrategy<T> {

	private static final String[] HEADER = new String[]{"agencia", "conta", "saldo", "status", "resultado"};
	
    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
    	super.setColumnMapping(new String[ FieldUtils.getAllFields(bean.getClass()).length]);
    	return HEADER;
    }
}

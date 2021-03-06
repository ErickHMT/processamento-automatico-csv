package com.sincronizacaoreceita.model;

import com.opencsv.bean.CsvBindByPosition;

public class ContaBean {
	
	@CsvBindByPosition(position = 0, required = true)
    private String agencia;

	@CsvBindByPosition(position = 1, required = true)
    private String conta;

	@CsvBindByPosition(position = 2, required = true)
    private String saldo;

	@CsvBindByPosition(position = 3, required = true)
    private String status;

	@CsvBindByPosition(position = 4) 
    private boolean resultado;
	
	public ContaBean() {
	}

    public ContaBean(String agencia, String conta, String saldo, String status, boolean resultado) {
		super();
		this.agencia = agencia;
		this.conta = conta;
		this.saldo = saldo;
		this.status = status;
		this.resultado = resultado;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public String toString() {
        return "CsvPessoa{agencia='" + agencia + "\', conta=" + conta + ", saldo='" + saldo + ", status='" + status +"\'}";
    }
}

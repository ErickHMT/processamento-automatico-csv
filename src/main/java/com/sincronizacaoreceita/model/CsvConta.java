package com.sincronizacaoreceita.model;

import com.opencsv.bean.CsvBindByPosition;

public class CsvConta {
	
	@CsvBindByPosition(position = 0)
    private String agencia;

	@CsvBindByPosition(position = 1)
    private String conta;

	@CsvBindByPosition(position = 2)
    private String saldo;

	@CsvBindByPosition(position = 3)
    private String status;

	@CsvBindByPosition(position = 4) 
    private boolean resultado;
	
	public CsvConta() {
	}

    public CsvConta(String agencia, String conta, String saldo, String status, boolean resultado) {
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

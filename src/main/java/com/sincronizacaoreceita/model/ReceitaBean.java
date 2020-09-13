package com.sincronizacaoreceita.model;

public class ReceitaBean {
    private String agencia;
    private String conta;
    private double saldo;
    private String status;
    private boolean resultado;

    public ReceitaBean() {
	}

	public ReceitaBean(String agencia, String conta, double saldo, String status) {
		this.agencia = agencia;
		this.conta = conta;
		this.saldo = saldo;
		this.status = status;
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
	
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
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
        return "CsvPessoa{agencia=" + agencia + ", conta=" + conta + ", saldo=" + saldo + ", status=" + status + ", resultado=" + resultado +"}";
    }
}

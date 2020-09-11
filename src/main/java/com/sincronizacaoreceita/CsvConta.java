package com.sincronizacaoreceita;

public class CsvConta {
    private String agencia;
    private String conta;
    private String saldo; //double
    private String status;
    private String itemSobrando;

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
	
	public String getItemSobrando() {
		return itemSobrando;
	}

	public void setItemSobrando(String itemSobrando) {
		this.itemSobrando = itemSobrando;
	}

	public String toString() {
        return "CsvPessoa{agencia='" + agencia + "\', conta=" + conta + ", saldo='" + saldo + ", status='" + status +"\'}";
    }
}

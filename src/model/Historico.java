package model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Historico {

	private String dataCompra;
	private BigDecimal codPedido;	
	private BigDecimal total;
	private String capa;
	private Map<String, Integer> nomeEquantidade = new TreeMap<>();	

	public Map<String, Integer> getNomeEquantidade() {
		return nomeEquantidade;
	}

	public void setNomeEquantidade(Map<String, Integer> nomeEquantidade) {
		this.nomeEquantidade = nomeEquantidade;
	}

	public String getCapa() {
		return capa;
	}

	public void setCapa(String capa) {
		this.capa = capa;
	}

	public String getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}

	public BigDecimal getCodPedido() {
		return codPedido;
	}

	public void setCodPedido(BigDecimal codPedido) {
		this.codPedido = codPedido;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}	

}

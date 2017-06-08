package model;

import java.math.BigDecimal;

public class Pedido {
	
	private String id;
	private BigDecimal codPedido;
	private String dataCompra;
	private BigDecimal total;
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getCodPedido() {
		return codPedido;
	}
	public void setCodPedido(BigDecimal codPedido) {
		this.codPedido = codPedido;
	}
	public String getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}
	
	
}

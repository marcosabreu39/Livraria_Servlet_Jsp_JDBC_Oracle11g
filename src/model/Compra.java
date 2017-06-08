package model;

import java.math.BigDecimal;

public class Compra {
	
	private BigDecimal codPedido;
	private BigDecimal isbn;
	private int quantidade;
	private BigDecimal total;
			
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getCodPedido() {
		return codPedido;
	}
	public void setCodPedido(BigDecimal codPedido) {
		this.codPedido = codPedido;
	}
	public BigDecimal getIsbn() {
		return isbn;
	}
	public void setIsbn(BigDecimal isbn) {
		this.isbn = isbn;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	@Override
	public String toString() {
		return "Compra [codPedido=" + codPedido + ", isbn=" + isbn + ", quantidade=" + quantidade + "]";
	}
	
}

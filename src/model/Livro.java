package model;

import java.math.BigDecimal;

public class Livro implements Comparable<Livro> {
	private BigDecimal isbn;
	private String genero;
	private String nome;
	private String autor;
	private BigDecimal preco;
	private int quantidade;
	private String imagem;
	private String editora;

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public BigDecimal getIsbn() {
		return isbn;
	}

	public void setIsbn(BigDecimal isbn) {
		this.isbn = isbn;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "Livro [isbn=" + isbn + ", genero=" + genero + ", nome=" + nome + ", autor=" + autor + ", preco=" + preco
				+ ", quantidade=" + quantidade + ", imagem=" + imagem + ", editora=" + editora + "]";
	}

	/*
	@Override
	public int compareTo(Livro o) {
		
		o.getIsbn().compareTo(isbn);
		
		return 1;
	}
	*/
	
	@Override	
	public int compareTo(Livro l2) {		
		
		return  l2.getIsbn().compareTo(getIsbn());
	}
	
	
}

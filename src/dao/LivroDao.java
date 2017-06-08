package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Livro;

public class LivroDao {

	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;

	private void iniciarConexao() {

		if (connection == null) {
			connection = new FabricaDeConexoes().getConnection();
		} else
			try {
				if (connection.isClosed()) {
					connection = new FabricaDeConexoes().getConnection();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

	private void fecharConexao() {

		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (connection != null) {
				connection.close();
				System.out.printf("\n%s\n", "Conexão fechada.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Livro> obterLivros() {
		List<Livro> livros = new ArrayList<>();
		try {
			iniciarConexao();
			sql = "select * from livro order by nomelivro";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Livro livro = new Livro();
				livro.setNome(rs.getString("nomeLivro"));
				livro.setAutor(rs.getString("autor"));
				livro.setGenero(rs.getString("genero"));
				livro.setIsbn(rs.getBigDecimal("isbn"));
				livro.setEditora(rs.getString("editora"));
				livro.setQuantidade(rs.getInt("quantidade"));
				livro.setPreco(rs.getBigDecimal("preco"));
				livro.setImagem("/fotos/" + livro.getNome() + ".png");

				livros.add(livro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return livros;

	}

	public Livro obterLivro(Livro livro, String nome) {
		try {
			iniciarConexao();
			sql = "select * from livro where nomelivro = '" + nome + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				livro.setNome(rs.getString("nomeLivro"));
				livro.setAutor(rs.getString("autor"));
				livro.setGenero(rs.getString("genero"));
				livro.setIsbn(rs.getBigDecimal("isbn"));
				livro.setEditora(rs.getString("editora"));
				livro.setQuantidade(rs.getInt("quantidade"));
				livro.setPreco(rs.getBigDecimal("preco"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		return livro;
	}

	public int obterQuantidadeDeLivros(BigDecimal isbn) {
		int estoque = 0;
		try {
			iniciarConexao();
			sql = "select quantidade from livro where isbn = '" + isbn + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				estoque = rs.getInt("quantidade");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return estoque;
	}

	public void atualizarQuantidadeDeLivros(BigDecimal isbn, int quantidade) {

		try {
			iniciarConexao();

			sql = "update livro set quantidade = ? where isbn = ?";

			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, quantidade);
			pstmt.setBigDecimal(2, isbn);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

	}

	public String obterLivroPeloNome(BigDecimal isbn) {

		String nomeLivro = null;

		try {
			iniciarConexao();

			sql = "select nomelivro from livro where isbn = '" + isbn + "'";

			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				nomeLivro = rs.getString("nomelivro");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return nomeLivro;
	}

}

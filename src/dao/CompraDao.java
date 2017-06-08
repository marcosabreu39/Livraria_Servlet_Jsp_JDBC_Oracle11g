package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import model.Compra;
import model.Livro;
import model.Pedido;

public class CompraDao {

	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	LivroDao livroDao = new LivroDao();

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

	public Map<Livro, Integer> concluirCompra(List<Compra> compras, List<Livro> carrinho, Pedido pedido,
			Map<Livro, Integer> map) {

		Map<BigDecimal, Integer> atualizadorDeEstoque = new HashMap<>();

		boolean apto = true;

		int estoque = 0;
		int estoqueAtual = 0;

		try {

			iniciarConexao();

			sql = "insert into compra (fk_codpedido, fk_isbn, quantidade) values (?, ?, ?)";

			pstmt = connection.prepareStatement(sql);

			for (Compra compra : compras) {

				estoque = livroDao.obterQuantidadeDeLivros(compra.getIsbn());

				estoqueAtual = estoque - compra.getQuantidade();

				if (estoque < compra.getQuantidade()) {

					apto = false;

					int contador = compra.getQuantidade();

					for (int index = 0; index < carrinho.size(); index++) {

						if (estoque < contador && compra.getIsbn().equals(carrinho.get(index).getIsbn())) {

							carrinho.remove(index);
							contador--;
							index--;

						} else {

							if (compra.getIsbn().equals(carrinho.get(index).getIsbn())) {

								carrinho.get(index).setQuantidade(estoque);
							}
						}
					}

					map.clear();

					for (Livro cart : carrinho) {

						Integer cont = map.get(cart);

						map.put(cart, cont == null ? 1 : cont + 1);
					}

					for (Livro l : map.keySet()) {
						if (l.getIsbn().equals(compra.getIsbn())) {
							l.setNome(l.getNome().concat("*"));
						}

					}

				} else {

					pstmt.setBigDecimal(1, compra.getCodPedido());
					pstmt.setBigDecimal(2, compra.getIsbn());
					pstmt.setInt(3, compra.getQuantidade());

					pstmt.addBatch();

					// livroDao.atualizarQuantidadeDeLivros(compra.getIsbn(),
					// estoqueAtual);

					atualizadorDeEstoque.put(compra.getIsbn(), estoqueAtual);

				}

			}

			if (apto) {
				PedidoDao pedidoDao = new PedidoDao();
				
				pedidoDao.inserirPedido(pedido);
				
				pstmt.executeBatch();

				for (Entry<BigDecimal, Integer> updater : atualizadorDeEstoque.entrySet()) {

					livroDao.atualizarQuantidadeDeLivros(updater.getKey(), updater.getValue());
				}

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		return map;

	}

	public Map<String, Integer> obterDadosCompra(BigDecimal fk_codpedido) {
		Map<String, Integer> nomeEquantidade = new TreeMap<>();

		try {
			iniciarConexao();

			sql = "select fk_isbn, quantidade from compra where fk_codpedido = '" + fk_codpedido + "'";

			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				nomeEquantidade.put(livroDao.obterLivroPeloNome(rs.getBigDecimal("fk_isbn")), rs.getInt("quantidade"));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return nomeEquantidade;
	}

}

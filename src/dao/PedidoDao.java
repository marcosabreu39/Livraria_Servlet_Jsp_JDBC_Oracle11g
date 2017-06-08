package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Pedido;

public class PedidoDao {
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	Pedido pedido = new Pedido();

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

	public void inserirPedido(Pedido pedido) {

		try {

			iniciarConexao();

			sql = "insert into pedido (codpedido, fk_codcli, datacompra, total) values (?, ?, "
					+ "to_timestamp(?, 'dd/mm/yyyy hh24:mi:ss'), ?)";

			PreparedStatement pstmt = connection.prepareStatement(sql);

			pstmt.setBigDecimal(1, pedido.getCodPedido());
			pstmt.setString(2, pedido.getId());
			pstmt.setString(3, pedido.getDataCompra());
			pstmt.setBigDecimal(4, pedido.getTotal());

			pstmt.execute();

		} catch (

		SQLException e)

		{
			e.printStackTrace();

		} finally

		{
			fecharConexao();
		}
	}

	public List<Pedido> obterDadosPedido(String fk_codcli) {
		List<Pedido> pedidos = new ArrayList<>();
		try {

			DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Timestamp dataHoraDoBd = null;
			String dataEhoraFormatados = null;

			iniciarConexao();

			sql = "select codpedido, datacompra, total from pedido where fk_codcli = '" + fk_codcli
					+ "' order by datacompra";

			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Pedido pedido = new Pedido();

				pedido.setCodPedido(rs.getBigDecimal("codpedido"));

				dataHoraDoBd = rs.getTimestamp("datacompra");

				pedido.setTotal(rs.getBigDecimal("total"));

				dataEhoraFormatados = fmt.format(dataHoraDoBd);

				pedido.setDataCompra(dataEhoraFormatados);

				pedidos.add(pedido);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return pedidos;

	}

	public Pedido obterDadosPedidoPorCodigo(String codcli, BigDecimal codPedido) {

		try {

			DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Timestamp dataHoraDoBd = null;
			String dataEhoraFormatados = null;
			String codcliBD = null;

			iniciarConexao();

			sql = "select fk_codcli, datacompra, total from pedido where codpedido = '" + codPedido + "'";

			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				codcliBD = rs.getString("fk_codcli");

				if (codcliBD.equals(codcli)) {

					dataHoraDoBd = rs.getTimestamp("datacompra");

					dataEhoraFormatados = fmt.format(dataHoraDoBd);

					pedido.setDataCompra(dataEhoraFormatados);

					pedido.setTotal(rs.getBigDecimal("total"));

				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return pedido;

	}

}

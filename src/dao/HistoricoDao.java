package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Historico;
import model.Pedido;
import model.Suporte;

public class HistoricoDao {

	Cliente cliente = new Cliente();
	Suporte suporte = new Suporte();
	Pedido pedido = new Pedido();
	PedidoDao pedidoDao = new PedidoDao();
	Historico historico;
	CompraDao compraDao;

	public List<Historico> buscarHistorico(String id) {

		List<Historico> historicoCompras = new ArrayList<>();
		List<Pedido> pedidos = new ArrayList<>();

		try {

			pedidos = pedidoDao.obterDadosPedido(id);
			
			for (Pedido pedido : pedidos) {

				historico = new Historico();
				historico.setCodPedido(pedido.getCodPedido());
				historico.setDataCompra(pedido.getDataCompra());
				historico.setTotal(pedido.getTotal());

				compraDao = new CompraDao();

				historico.setNomeEquantidade(compraDao.obterDadosCompra(pedido.getCodPedido()));

				historicoCompras.add(historico);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return historicoCompras;

	}

	public Historico obterHistoricoViaCodigo(String id, BigDecimal codigo) {
		
		historico = new Historico();
		compraDao = new CompraDao();
		
		pedido = pedidoDao.obterDadosPedidoPorCodigo(id, codigo);
				
		historico.setDataCompra(pedido.getDataCompra());
		historico.setTotal(pedido.getTotal());

		historico.setNomeEquantidade(compraDao.obterDadosCompra(codigo));

		return historico;
	}

}

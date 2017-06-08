package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClienteDao;
import dao.CompraDao;
import dao.HistoricoDao;
import dao.LivroDao;
import dao.PedidoDao;
import model.Cliente;
import model.Compra;
import model.Historico;
import model.Livro;
import model.Pedido;
import model.Suporte;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerServlet() {
		super();
	}

	Cliente cliente = new Cliente();
	Suporte suporte = new Suporte();
	ClienteDao clienteDao = new ClienteDao();
	LivroDao livroDao = new LivroDao();
	String[] msg = new String[13];
	Livro livro = new Livro();
	Pedido pedido = new Pedido();
	public List<Livro> livros;
	public List<Livro> carrinho;
	public Map<Livro, Integer> map;
	BigDecimal total = new BigDecimal(0);
	PedidoDao pedidoDao = new PedidoDao();
	Compra compra = new Compra();
	CompraDao compraDao = new CompraDao();
	Historico historico;
	HistoricoDao historicoDao = new HistoricoDao();
	public String msgCarrinho;
	HttpSession session;

	// Verifica se o cpf do cliente já existe no banco de dados.
	private void processarCpf(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");

			String cpf = request.getParameter("cpf") + "";
			String msgCpf = null;

			if (cpf.equals("")) {
				msgCpf = "O preenchimento do cpf é obrigatório.";
			} else if (!suporte.validarFormatoCpf(cpf)) {
				msgCpf = "O cpf está com formato incorreto.";
			} else if (!suporte.validarCpf(cpf)) {
				msgCpf = "O cpf não foi validado.";
			} else if (clienteDao.checarCpf(suporte.formatarCpf(cpf))) {
				msgCpf = "O cpf já está cadastrado";
			} else {
				msgCpf = "O cpf foi checado com sucesso.";
			}

			request.setAttribute("msgCpf", msgCpf);

			request.getRequestDispatcher("cadastro.jsp").forward(request, response);

		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}

	// Verifica se o login já existe no banco de dados.
	private void processarLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String login = request.getParameter("login") + "";
			String msgLogin = "";

			if (login.equals("")) {
				msgLogin = "O preenchimento do login é obrigatório.";
			} else if (!suporte.validarLogin(login)) {
				msgLogin = "O login está com o formato incorreto.";
			} else if (!clienteDao.checarLogin(login)) {
				msgLogin = "Esse login já está sendo utilizado. Por favor insira outro.";
			} else {
				msgLogin = "O login está apto para ser utilizado.";
			}

			request.setAttribute("msgLogin", msgLogin);
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro.jsp");
			dispatcher.forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	// Verifica se o email já existe no banco de dados
	private void processarEmail(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String retorno = request.getParameter("retorno");

			String email = request.getParameter("email") + "";
			String msgEmail = "";

			if (email.equals("")) {
				msgEmail = "O preenchimento do email é obrigatório.";
			} else if (!suporte.validarEmail(email)) {
				msgEmail = "O e-mail está com o formato incorreto.";
			} else if (!clienteDao.checarEmail(email)) {
				msgEmail = "O email inserido já está sendo utilizado. Por favor insira outro.";
			} else {
				msgEmail = "O email foi verificado com sucesso.";
			}

			if (retorno.equals("cadastro")) {

				request.setAttribute("msgEmail", msgEmail);
				request.getRequestDispatcher("cadastro.jsp").forward(request, response);

			} else {

				String msgDados = "Ocorreu um erro na verificação do email.";

				request.setAttribute("msgDados", msgDados);
				request.setAttribute("msgEmail", msgEmail);
				request.getRequestDispatcher("atualizarDadosCadastro.jsp").forward(request, response);

			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	// Busca o logradouro, o bairro e a cidade através do cep.
	private void processarCep(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String retorno = request.getParameter("retorno");

			// String id = (String) request.getSession().getAttribute("id");
			String cpf = request.getParameter("cpf");
			String login = request.getParameter("login");
			cliente.setCpf(cpf);
			cliente.setLogin(login);

			String cep = request.getParameter("cep") + "";
			String logradouro = "";
			String bairro = "";
			String cidade = "";
			String msgCep = "";

			if (cep.equals("")) {
				msgCep = "O preenchimento do cep é obrigatório.";
			} else if (!suporte.validarCep(cep)) {
				msgCep = "O cep está com formato incorreto.";
			} else {
				String[] dados = new String[3];
				suporte.buscarCep(suporte.formatarCep(cep), dados);
				logradouro = dados[0];
				bairro = dados[1];
				cidade = dados[2];

				msgCep = (dados[0].isEmpty() && dados[1].isEmpty() && dados[2].isEmpty())
						? "Cep não encontrado. Por favor preencha os campos abaixo." : "";
			}

			cliente.setLogradouro(logradouro);
			cliente.setBairro(bairro);
			cliente.setCidade(cidade);

			String msgDados = "O cep foi verificado com sucesso.";
			request.setAttribute("cliente", cliente);
			request.setAttribute("msgDados", msgDados);
			request.setAttribute("msgCep", msgCep);

			if (retorno.equals("cadastro")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("atualizarDadosCadastro.jsp");
				dispatcher.forward(request, response);
			}

		} catch (IOException | ServletException e) {
			e.printStackTrace();
			try {
				request.setAttribute("erro", e.getMessage());
				request.getRequestDispatcher("erro.jsp").forward(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// Realizar o cadastro do cliente.
	private void processarCadastro(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String cpf = request.getParameter("cpf") + "";
			String nome = request.getParameter("nome") + "";
			String email = request.getParameter("email") + "";
			String fone = request.getParameter("fone") + "";
			String cel = request.getParameter("cel") + "";
			String cep = request.getParameter("cep") + "";
			String logradouro = request.getParameter("logradouro") + "";
			String cidade = request.getParameter("cidade") + "";
			String bairro = request.getParameter("bairro") + "";
			String complemento = request.getParameter("complemento") + "";
			String login = request.getParameter("login") + "";
			String senha = request.getParameter("senha") + "";
			String confirmaSenha = request.getParameter("confirmaSenha");

			if (cpf.equals("")) {
				msg[0] = "O preenchimento do cpf é obrigatório.";
			} else if (!suporte.validarFormatoCpf(cpf)) {
				msg[0] = "O cpf está com formato incorreto.";
			} else if (!suporte.validarCpf(cpf)) {
				msg[0] = "O cpf não foi validado.";
			} else if (clienteDao.checarCpf(suporte.formatarCpf(cpf))) {
				msg[0] = "O cpf já está cadastrado";
			} else {
				msg[0] = "";
			}

			if (nome.equals("")) {
				msg[1] = "O preenchimento do nome é obrigatório.";
			} else if (!suporte.validarNome(nome)) {
				msg[1] = "O nome está com o formato incorreto.";
			} else {
				msg[1] = "";
			}

			if (email.equals("")) {
				msg[2] = "O preenchimento do e-mail é obrigatório.";
			} else if (!suporte.validarEmail(email)) {
				msg[2] = "O e-mail está com o formato incorreto.";
			} else {
				msg[2] = "";
			}

			if (fone.equals("")) {
				msg[3] = "";
			} else if (!suporte.validarTelefone(fone)) {
				msg[3] = "O telefone está com o formato incorreto.";
			} else {
				msg[3] = "";
			}

			if (cel.equals("")) {
				msg[4] = "O preenchimento do celular é obrigatório.";
			} else if (!suporte.validarCelular(cel)) {
				msg[4] = "O celular está com o formato incorreto";
			} else {
				msg[4] = "";
			}

			if (cep.equals("")) {
				msg[5] = "O preenchimento do cep é obrigatório.";
			} else if (!suporte.validarCep(cep)) {
				msg[5] = "O cep está com o formato incorreto.";
			} else {
				msg[5] = "";
			}

			msg[6] = (logradouro.equals("")) ? "O preenchimento do logradouro é obrigatório." : "";

			msg[7] = cidade.equals("") ? "O preenchimento da cidade é obrigatório." : "";

			msg[8] = bairro.equals("") ? "O preenchimento do bairro é obrigatório." : "";

			msg[9] = complemento.equals("") ? "O preenchimento do complemento é obrigatório." : "";

			if (login.equals("")) {
				msg[10] = "O preenchimento do login é obrigatório.";
			} else if (!suporte.validarLogin(login)) {
				msg[10] = "O login está com o formato incorreto.";
			} else if (!clienteDao.checarLogin(login)) {
				msg[10] = "O login inserido já está sendo utilizado. Por favor insira outro.";
			} else {
				msg[10] = "";
			}

			if (senha.equals("")) {
				msg[11] = "O preenchimento da senha é obrigatório.";
			} else if (!suporte.validarSenha(senha)) {
				msg[11] = "A senha está com o formato incorreto.";
			} else {
				msg[11] = "";
			}

			if (confirmaSenha.equals("")) {
				msg[12] = "O preenchimento da confirmação da senha é obrigatório.";
			} else {
				msg[12] = "";
			}

			if (!senha.equals(confirmaSenha)) {
				msg[11] = "A senha está diferente da confirmação de senha.";
				msg[12] = "A confirmação da senha está diferente da senha informada.";
			}

			boolean apto = true;

			for (int i = 0; i < msg.length; i++) {
				if (msg[i] != "")
					apto = false;
				System.out.printf("%s\n", msg[i]);
			}

			if (apto == true) {

				cliente.setCpf(suporte.formatarCpf(cpf));
				cliente.setNome(nome);
				cliente.setEmail(email.toLowerCase());
				cliente.setFone(suporte.formatarTelefone(fone));
				cliente.setCel(suporte.formatarCelular(cel));
				cliente.setCep(suporte.formatarCep(cep));
				cliente.setLogradouro(logradouro);
				cliente.setCidade(cidade);
				cliente.setBairro(bairro);
				cliente.setComplemento(complemento);
				cliente.setLogin(login.toLowerCase());
				byte[] byteSalt = suporte.gerarSalt();
				cliente.setSenha(suporte.criptografar(byteSalt, senha));
				String saltEmString = suporte.converterSaltParaString(byteSalt);
				cliente.setSalt(saltEmString);
				cliente.setDataCadastro(suporte.gerarDataCadastro());
				String msgSucesso = "O cadastro foi realizado com sucesso.";

				request.setAttribute("cliente", cliente);
				request.setAttribute("msgSucesso", msgSucesso);

				clienteDao.insere(cliente);

				request.getRequestDispatcher("sucesso.jsp").forward(request, response);

				System.out.printf("\nA senha possui %s caracteres.", cliente.getSenha().length());

			} else {
				cliente.setLogradouro(logradouro);
				cliente.setComplemento(complemento);
				cliente.setBairro(bairro);
				cliente.setCidade(cidade);

				request.setAttribute("cliente", cliente);
				request.setAttribute("msg", msg);

				request.getRequestDispatcher("cadastro.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Realiza o processamento de acesso (login) ao sistema.
	private void processarAcesso(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String login = request.getParameter("login") + "";
			String senha = request.getParameter("senha") + "";

			// String url = request.getRequestURI();

			// System.out.printf("\n A uri é:%s \n", url);

			String msgLogin = (login.equals("") ? "O preenchimento do login é obrigatório." : "");
			String msgSenha = (senha.equals("") ? "O preenchimento da senha é obrigatório." : "");

			if (msgLogin.equals("") && msgSenha.equals("")) {

				if (clienteDao.checarDadosLogon(login.toLowerCase(), senha)) {
					session = request.getSession();

					session.setAttribute("nome", clienteDao.obterNome(login));
					session.setAttribute("id", clienteDao.obterId(login));

					if (carrinho == null || carrinho.isEmpty()) {
						response.sendRedirect("index.jsp");
					} else {
						response.sendRedirect("carrinho.jsp");
					}

				} else {
					String msgErro = "Usuário ou senha incorretos.";
					cliente.setLogin(login);
					request.setAttribute("cliente", cliente);
					request.setAttribute("msgErro", msgErro);
					RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
					dispatcher.forward(request, response);
				}

			} else {
				cliente.setLogin(login);
				request.setAttribute("cliente", cliente);
				request.setAttribute("msgLogin", msgLogin);
				request.setAttribute("msgSenha", msgSenha);
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Realiza a saída (logout) do sistema.
	private void processarSaida(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();
			// request.getSession().invalidate();

			// response.sendRedirect("index.jsp");

			session.removeAttribute("nome");
			session.removeAttribute("id");

			livros = livroDao.obterLivros();

			request.setAttribute("livros", livros);

			request.getRequestDispatcher("index.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void processarBuscaDadosCadastrais(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String codCli = (String) session.getAttribute("id");
			String nomeCli = (String) session.getAttribute("nome");

			String[] dadosCadastrais = clienteDao.obterDadosCadastrais(codCli);

			cliente.setEmail(dadosCadastrais[0]);
			cliente.setCep(dadosCadastrais[1]);
			cliente.setLogradouro(dadosCadastrais[2]);
			cliente.setComplemento(dadosCadastrais[3]);
			cliente.setBairro(dadosCadastrais[4]);
			cliente.setCidade(dadosCadastrais[5]);
			cliente.setNome(dadosCadastrais[6]);
			cliente.setFone(dadosCadastrais[7]);
			cliente.setCel(dadosCadastrais[8]);

			boolean todos = true;

			for (int i = 0; i < dadosCadastrais.length; i++) {

				todos = dadosCadastrais[i] == null ? false : todos;
			}

			String msgDados = todos ? "Dados Cadastrais de " + nomeCli + " obtidos com sucesso."
					: "Ocorreu um erro ao buscar os dados cadastrais!";

			request.setAttribute("cliente", cliente);
			request.setAttribute("msgDados", msgDados);

			RequestDispatcher dispatcher = request.getRequestDispatcher("atualizarDadosCadastro.jsp");
			dispatcher.forward(request, response);
			// }
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	// Realiza a atualização dos dados cadastrais.
	private void processarAtualizacaoDeDadosCadastrais(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String[] paraAtualizar = new String[9];
			String[] msg = new String[9];
			String msgDados = "";

			session = request.getSession();

			String id = (String) session.getAttribute("id") + "";
			String nome = (String) session.getAttribute("nome") + "";
			paraAtualizar[0] = request.getParameter("email") + "";
			paraAtualizar[1] = request.getParameter("cep") + "";
			paraAtualizar[2] = request.getParameter("logradouro") + "";
			paraAtualizar[3] = request.getParameter("complemento") + "";
			paraAtualizar[4] = request.getParameter("bairro") + "";
			paraAtualizar[5] = request.getParameter("cidade") + "";
			paraAtualizar[6] = request.getParameter("nome") + "";
			paraAtualizar[7] = request.getParameter("fone") + "";
			paraAtualizar[8] = request.getParameter("cel") + "";

			msgDados = (id.equals("") || nome.equals("")) ? "Ocorreu um erro na Sessão!" : msgDados;

			if (paraAtualizar[0].equals("")) {
				msg[0] = "O preenchimento do e-mail é obrigatório.";
			} else if (!suporte.validarEmail(paraAtualizar[0])) {
				msg[0] = "O e-mail está com o formato incorreto.";
			} else if (!clienteDao.checarEmail(paraAtualizar[0]) && !clienteDao.confirmarEmail(paraAtualizar[0], id)) {
				msg[0] = "O email inserido já está sendo utilizado. Por favor insira outro.";
			} else {
				msg[0] = "";
			}

			if (paraAtualizar[1].equals("")) {
				msg[1] = "O preenchimento do cep é obrigatório.";
			} else if (!suporte.validarCep(paraAtualizar[1])) {
				msg[1] = "O cep está com o formato incorreto.";
			} else {
				msg[1] = "";
			}

			msg[2] = paraAtualizar[2].equals("") ? "O preenchimento do logradouro é obrigatório." : "";

			msg[3] = paraAtualizar[3].equals("") ? "O preenchimento do complemento é obrigatório." : "";

			msg[4] = paraAtualizar[4].equals("") ? "O preenchimento do bairro é obrigatório." : "";

			msg[5] = paraAtualizar[5].equals("") ? "O preenchimento da cidade é obrigatório." : "";

			if (paraAtualizar[6].equals("")) {
				msg[6] = "O preenchimento do nome é obrigatório.";
			} else if (!suporte.validarNome(paraAtualizar[6])) {
				msg[6] = "O nome está com o formato incorreto.";
			} else {
				msg[6] = "";
			}

			if (paraAtualizar[7].equals("")) {
				msg[7] = "O preenchimento do Telefone é obrigatório.";
			} else if (!suporte.validarTelefone(paraAtualizar[7])) {
				msg[7] = "O telefone está com o formato incorreto.";
			} else {
				msg[7] = "";
			}

			if (paraAtualizar[8].equals("")) {
				msg[8] = "O preenchimento do celular é obrigatório.";
			} else if (!suporte.validarCelular(paraAtualizar[8])) {
				msg[8] = "O celular está com o formato incorreto";
			} else {
				msg[8] = "";
			}

			boolean semErros = true;

			for (int i = 0; i < msg.length; i++) {
				// semErros = msg[i].equals("") ? semErros : false;
				if (msg[i].equals("")) {
					semErros = true;
				} else {
					semErros = false;
					break;
				}
			}

			if (Arrays.equals(paraAtualizar, clienteDao.obterDadosCadastrais(id))) {

				msgDados = "Nenhum dado cadastral foi alterado para ser atualizado.";
				request.setAttribute("msgDados", msgDados);

				RequestDispatcher dispatcher = request.getRequestDispatcher("atualizarDadosCadastro.jsp");
				dispatcher.forward(request, response);

			} else if (semErros) {

				cliente.setEmail(paraAtualizar[0]);
				cliente.setCep(suporte.formatarCep(paraAtualizar[1]));
				cliente.setLogradouro(paraAtualizar[2]);
				cliente.setComplemento(paraAtualizar[3]);
				cliente.setBairro(paraAtualizar[4]);
				cliente.setCidade(paraAtualizar[5]);
				cliente.setNome(paraAtualizar[6]);
				cliente.setFone(suporte.formatarTelefone(paraAtualizar[7]));
				cliente.setCel(suporte.formatarCelular(paraAtualizar[8]));
				String msgSucesso = "A atualização cadastral de " + nome + " foi realizada com sucesso.";

				request.setAttribute("cliente", cliente);
				request.setAttribute("msgSucesso", msgSucesso);

				clienteDao.atualizarDadosCadastrais(cliente, id);

				RequestDispatcher dispatcher = request.getRequestDispatcher("sucesso.jsp");
				dispatcher.forward(request, response);

			} else {

				msgDados = "Algum erro ocorreu na atualização dos dados.";

				request.setAttribute("msgDados", msgDados);
				request.setAttribute("msg", msg);

				RequestDispatcher dispatcher = request.getRequestDispatcher("atualizarDadosCadastro.jsp");
				dispatcher.forward(request, response);
			}

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	// Realiza a busca pelo dado de acesso selecionado pelo usuário para ser
	// alterado..
	private void processarBuscaDeDadosDeAcesso(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String id = (String) session.getAttribute("id");
			String dadoAcesso = request.getParameter("dadoAcesso") + "";
			String msgSistema = "";

			if (dadoAcesso.equals("login")) {
				cliente.setLogin(clienteDao.obterLogin(id));
				msgSistema = "Você escolheu alterar o login.";
				request.setAttribute("msgSistema", msgSistema);
				request.setAttribute("cliente", cliente);

			} else if (dadoAcesso.equals("senha")) {
				msgSistema = "Você escolheu alterar a senha.";
				request.setAttribute("msgSistema", msgSistema);
				request.setAttribute("senha", dadoAcesso);

			} else {
				String msgSelect = "Você deve selecionar uma opção antes de clicar em Escolher.";
				request.setAttribute("msgSelect", msgSelect);
			}
			request.getRequestDispatcher("alterarDadosAcesso.jsp").forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void processarAlteracaoDeLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String id = (String) session.getAttribute("id");
			String login = request.getParameter("login") + "";
			String msgLogin = "O login foi alterado com sucesso.";
			String msgSistema = "Você escolheu alterar o login.";
			String loginAtual = clienteDao.obterLogin(id);

			if (id.equals("")) {
				msgSistema = "Erro no sistema. Por favor, contate o administrador.";
			}

			else if (login.equals("")) {
				msgLogin = "O preenchimento do login é obrigatório.";
			}

			else if (!suporte.validarLogin(login)) {
				msgLogin = "O login está com a formatação incorreta.";
			}

			else if (login.equals(loginAtual)) {
				msgLogin = "O login não foi modificado para que possa ser alterado.";
			}

			else if (!clienteDao.checarLogin(login)) {
				msgLogin = "O login inserido já está sendo usado. Por favor, insira outro.";
			} else {
				cliente.setLogin(login);
				clienteDao.alterarLogin(cliente, id);
				msgSistema = "A alteração do login foi realizada com sucesso.";
			}
			request.setAttribute("cliente", cliente);
			request.setAttribute("msgLogin", msgLogin);
			request.setAttribute("msgSistema", msgSistema);

			request.getRequestDispatcher("alterarDadosAcesso.jsp").forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void processarAlteracaoDeSenha(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession();

			request.setCharacterEncoding("utf-8");

			String id = (String) session.getAttribute("id") + "";

			// senha do cliente para confirmar se é ele mesmo.
			String senhaAtual = request.getParameter("senhaAtual") + "";

			// A nova senha que foi inserida pelo cliente para a alteração.
			String senha = request.getParameter("senha") + "";

			// Confirmação da nova senha inserida pelo cliente.
			String confirmaSenha = request.getParameter("confirmaSenha") + "";

			// senha que está armazenada no banco de dados para a comparação.
			String senhaArmazenada = clienteDao.obterSenha(id);

			String salt = clienteDao.obterSalt(id);

			String msgSenha = "";

			String msgSenhaAtual = "";

			String msgConfirmaSenha = "";

			String msgSistema = "Você escolheu alterar a senha.";

			byte[] byteSalt = suporte.obterByteSalt(salt);

			// A nova senha inserida pelo cliente já criptografada.
			String senhaCriptografada = suporte.criptografar(byteSalt, senha);

			// A senha inserida pelo cliente para confirmar se é ele mesmo já
			// criptografada.
			String senhaParaConfirmar = suporte.criptografar(byteSalt, senhaAtual);

			if (id.equals("")) {
				msgSistema = "Erro no sistema. Por favor contate o administrador.";

			} else if (senha.equals("")) {
				msgSenha = "O preenchimento da senha nova é obrigatório.";

			} else if (senhaAtual.equals("")) {
				msgSenhaAtual = "O preenchimento da senha atual é obrigatório.";

			} else if (confirmaSenha.equals("")) {
				msgConfirmaSenha = "O preenchimento da confirmação da senha é obrigatório.";

			} else if (!senha.equals(confirmaSenha)) {
				msgConfirmaSenha = "A senha nova e a sua confirmação são diferentes.";

			} else if (!senhaParaConfirmar.equals(senhaArmazenada)) {
				msgSenhaAtual = "A senha inserida está incorreta.";

			} else if (!suporte.validarSenha(senha)) {
				msgSenha = "A senha inserida está com a formatação incorreta.";

			} else if (senhaCriptografada.equals(senhaArmazenada)) {
				msgSenha = "A senha inserida é a mesma que já está sendo utilizada.";
			} else {
				cliente.setSenha(senhaCriptografada);
				clienteDao.alterarSenha(cliente, id);
				msgSenha = "A senha foi alterada com sucesso.";
				msgSistema = "Alteração de senha concluída.";
			}

			request.setAttribute("msgSenha", msgSenha);
			request.setAttribute("msgSistema", msgSistema);
			request.setAttribute("msgSenhaAtual", msgSenhaAtual);
			request.setAttribute("msgConfirmaSenha", msgConfirmaSenha);
			request.setAttribute("senha", senha);

			request.getRequestDispatcher("alterarDadosAcesso.jsp").forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void processarObterLivros(HttpServletRequest request, HttpServletResponse response) {

		try {

			session = request.getSession();

			if (session.isNew()) {

				carrinho = new ArrayList<>();
				map = new TreeMap<>();

				msgCarrinho = new String();
				msgCarrinho = "";

				session.setAttribute("carrinho", carrinho);
				session.setAttribute("map", map);
				session.setAttribute("msgCarrinho", msgCarrinho);

			}

			/*
			 * session.setAttribute("carrinho", carrinho);
			 * session.setAttribute("map", map);
			 * session.setAttribute("msgCarrinho", msgCarrinho);
			 */

			livros = livroDao.obterLivros();

			if (!msgCarrinho.equals("")) {

				msgCarrinho = (String) session.getAttribute("msgCarrinho");

				msgCarrinho = "";

				session.setAttribute("msgCarrinho", msgCarrinho);
			}

			request.setAttribute("livros", livros);

			request.getRequestDispatcher("index.jsp").forward(request, response);

		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processarDetalhes(HttpServletRequest request, HttpServletResponse response) {
		try {

			session = request.getSession();

			int index = Integer.parseInt(request.getParameter("index"));

			List<Livro> detalhes = livroDao.obterLivros();

			livro = (Livro) detalhes.get(index);

			request.setAttribute("livro", livro);
			request.setAttribute("i", index);

			request.getRequestDispatcher("detalhes.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void processarCarrinho(HttpServletRequest request, HttpServletResponse response) {
		try {

			session = request.getSession();

			int index = Integer.parseInt(request.getParameter("index"));

			livro = (Livro) livros.get(index);

			total = BigDecimal.ZERO;

			if (carrinho != null) {
				carrinho = (List<Livro>) session.getAttribute("carrinho");
			}

			carrinho.add(livro);

			map = (Map<Livro, Integer>) session.getAttribute("map");
			map.clear();

			for (Livro cart : carrinho) {

				Integer cont = map.get(cart);

				map.put(cart, cont == null ? 1 : cont + 1);

				total = total.add(cart.getPreco());
			}

			if (carrinho.size() > 0) {

				msgCarrinho = "1 livro foi adicionado ao carrinho";

				session.setAttribute("msgCarrinho", msgCarrinho);

				session.setAttribute("carrinho", carrinho);

				session.setAttribute("map", map);

				session.setAttribute("total", total);

			} else {
				msgCarrinho = "";
				session.setAttribute("msgCarrinho", msgCarrinho);

				session.setAttribute("carrinho", carrinho);

				session.setAttribute("map", map);

				session.setAttribute("total", total);
			}

			response.sendRedirect("carrinho.jsp");

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void processarAtualizacaoDoCarrinho(HttpServletRequest request, HttpServletResponse response) {

		try {
			session = request.getSession();

			carrinho = (List<Livro>) session.getAttribute("carrinho");
			map = (Map<Livro, Integer>) session.getAttribute("map");
			msgCarrinho = (String) session.getAttribute("msgCarrinho");
			double isbnDouble = Double.parseDouble(request.getParameter("isbn"));
			BigDecimal isbn = BigDecimal.valueOf(isbnDouble);
			total = (BigDecimal) session.getAttribute("total");
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			int diferenca = 0;
			int quantidadeReal = 0;
			int add = 0;
			int remove = 0;

			Livro livroUpdate = new Livro();
			livroUpdate.setIsbn(isbn);

			for (Livro l : map.keySet()) {
				if (l.getIsbn().equals(livroUpdate.getIsbn())) {
					quantidadeReal = map.get(l);
					livroUpdate = l;
				}
			}

			if (quantidade == quantidadeReal && !carrinho.isEmpty()) {

				session.setAttribute("Não ocorreu nenhuma atualização no carrinho.", msgCarrinho);

			} else if (quantidade > quantidadeReal) {
				diferenca = quantidade - quantidadeReal;
				add = diferenca;

				while (diferenca > 0) {
					diferenca--;

					livro = new Livro();
					livro = livroUpdate;

					carrinho.add(livro);
				}

				map.clear();

				total = BigDecimal.ZERO;

				for (Livro cart : carrinho) {

					Integer cont = map.get(cart);

					// livro.compareTo(cart);

					map.put(cart, cont == null ? 1 : cont + 1);

					total = total.add(cart.getPreco());
				}

				msgCarrinho = (add > 1) ? add + " livros foram adicionados ao carrinho."
						: "1 livro foi adicionado ao carrinho.";

				session.setAttribute("msgCarrinho", msgCarrinho);

			} else {
				diferenca = quantidadeReal - quantidade;
				remove = diferenca;

				while (diferenca > 0) {
					diferenca--;

					carrinho.remove(livroUpdate);
				}

				map.clear();
				total = BigDecimal.ZERO;

				for (Livro cart : carrinho) {

					Integer cont = map.get(cart);

					livro.compareTo(cart);

					map.put(cart, cont == null ? 1 : cont + 1);

					total = total.add(cart.getPreco());
				}

				List<?> cdc = (List<?>) request.getSession().getAttribute("carrinho");

				if (!cdc.isEmpty()) {

					msgCarrinho = (remove > 1) ? remove + " livros foram removidos do carrinho."
							: remove + " livro foi removido do carrinho.";

					session.setAttribute("msgCarrinho", msgCarrinho);
				} else {
					session.setAttribute("msgCarrinho", "");
				}

			}
			session.setAttribute("total", total);

			session.setAttribute("carrinho", carrinho);

			session.setAttribute("map", map);

			response.sendRedirect("carrinho.jsp");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void processarFinalCompra(HttpServletRequest request, HttpServletResponse response) {

		try {
			session = request.getSession(false);

			if (session == null) {

				response.sendRedirect("index.jsp");

			} else {

				String id = (String) session.getAttribute("id");
				carrinho = (List<Livro>) session.getAttribute("carrinho");
				map = (Map<Livro, Integer>) session.getAttribute("map");
				total = (BigDecimal) session.getAttribute("total");

				if (id == null || id.equals("")) {

					response.sendRedirect("login.jsp");

				} else {

					List<Compra> compras = new ArrayList<>();

					String dataCompra = suporte.gerarDataHora();
					BigDecimal codCompra = suporte.gerarCodPedido(dataCompra, id);

					pedido = new Pedido();

					pedido.setId(id);
					pedido.setDataCompra(dataCompra);
					pedido.setCodPedido(codCompra);
					pedido.setTotal(total);

					for (Livro l : map.keySet()) {

						if (l.getNome().endsWith("*")) {
							l.setNome(l.getNome().replaceAll("\\*", "").trim());
						}

						compra = new Compra();

						compra.setIsbn(l.getIsbn());
						compra.setQuantidade(map.get(l));
						compra.setCodPedido(codCompra);

						compras.add(compra);
					}

					int sizeBefore = carrinho.size();

					map = compraDao.concluirCompra(compras, carrinho, pedido, map);

					int sizeAfter = carrinho.size();

					if (map.isEmpty()) {

						session.setAttribute("msgCarrinho", "O(s) livro(s) não está(ão) mais disponível(is).");

					} else {

						boolean apto = true;

						for (Livro livro : map.keySet()) {
							if (livro.getNome().endsWith("*") || sizeBefore > sizeAfter) {
								apto = false;
								break;
							}
						}

						if (apto) {
							total = BigDecimal.ZERO;
							carrinho.clear();
							map.clear();

							session.setAttribute("msgCarrinho",
									"A compra foi efetuada com sucesso! o código da sua compra é " + codCompra);

						} else {

							map.clear();
							total = BigDecimal.ZERO;

							for (Livro cart : carrinho) {

								Integer cont = map.get(cart);

								// livro.compareTo(cart);

								map.put(cart, cont == null ? 1 : cont + 1);

								total = total.add(cart.getPreco());
							}

							session.setAttribute("msgCarrinho", "Durante sua compra, ocorreram mudanças no "
									+ "estoque do(s) livro(s) do carrinho.");

						}
					}

					session.setAttribute("total", total);

					session.setAttribute("carrinho", carrinho);

					session.setAttribute("map", map);

					response.sendRedirect("carrinho.jsp");

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processarHistoricoDeCompras(HttpServletRequest request, HttpServletResponse response) {
		try {
			session = request.getSession(false);

			if (session == null) {
				response.sendRedirect("index.jsp");

			} else {

				String id = (String) session.getAttribute("id");
				String opcaoDeHistorico = request.getParameter("opcaoDeHistorico");
				List<Historico> historicos = new ArrayList<>();
				String msgHistorico = "";

				if (id == null || id.equals("")) {
					response.sendRedirect("login.jsp");

				} else if (opcaoDeHistorico == null || opcaoDeHistorico.equals("")) {
					msgHistorico = "Ocorreu um erro no sistema.";

					request.setAttribute("msgHistorico", msgHistorico);

					request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);

				} else if (opcaoDeHistorico.equals("historicoViaId")) {

					msgHistorico = "Você selecionou Buscar o Histórico via código da compra.";

					request.setAttribute("msgHistorico", msgHistorico);

					request.setAttribute("historicoViaId", "historicoViaId");

					request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);

				} else if (opcaoDeHistorico.equals("historicoTotal")) {

					historicos = historicoDao.buscarHistorico(id);

					if (historicos.isEmpty()) {

						msgHistorico = "Nenhum histórico de compras foi encontrado.";

						request.setAttribute("msgHistorico", msgHistorico);

						request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);

					} else {

						msgHistorico = "Você selecionou a opção de busca do histórico completo de compras.";

						request.setAttribute("msgHistorico", msgHistorico);

						request.setAttribute("historicos", historicos);

						request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);
					}
				} else {

					request.setAttribute("msgSelect", "Nenhuma opção de busca de histórico foi selecionada.");

					request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);
				}
			}

		} catch (IOException | ServletException e) {

		}

	}

	private void processarHistoricoDeComprasViaCodigo(HttpServletRequest request, HttpServletResponse response) {

		try {
			session = request.getSession(false);

			if (session == null) {

				response.sendRedirect("index.jsp");
			}

			String id = new String();
			id = (String) session.getAttribute("id");
			Double codigoDb = Double.parseDouble(request.getParameter("codigo").trim());
			BigDecimal codigo = BigDecimal.valueOf(codigoDb);
			String msgHistorico = "";

			if (id == null || id.equals("")) {

				response.sendRedirect("login.jsp");

			} else if (codigo == null || codigo.equals("")) {

				msgHistorico = "O código parece não ter sido preenchido. Caso tenha sido, por favor contate o administrador do sistema.";

				request.setAttribute("msgHistorico", msgHistorico);

				request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);

			} else {

				historico = new Historico();
				historico = historicoDao.obterHistoricoViaCodigo(id, codigo);

				historico.setCodPedido(codigo);

				boolean apto = (historico.getDataCompra() != null && historico.getTotal() != null
						&& !historico.getNomeEquantidade().isEmpty()) ? true : false;

				if (apto) {

					request.setAttribute("historico", historico);

					request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);

				} else {

					msgHistorico = "Não foi encontrado nenhum histórico de compras para o código informado.";

					request.setAttribute("msgHistorico", msgHistorico);

					request.getRequestDispatcher("historicoCompras.jsp").forward(request, response);
				}

			}

		} catch (IOException | ServletException e) {

			e.printStackTrace();
		}
	}

	// Gerencia todo o fluxo da aplicação.
	private void gerenciarFluxo(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("utf-8");

			String acao = request.getParameter("acao") + "";

			switch (acao) {

			case ("checarcpf"): {
				processarCpf(request, response);
				break;
			}

			case ("checarlogin"): {
				processarLogin(request, response);
				break;
			}

			case ("checaremail"): {
				processarEmail(request, response);
				break;
			}

			case ("checarcep"): {
				processarCep(request, response);
				break;
			}

			case ("cadastrar"): {
				processarCadastro(request, response);
				break;
			}

			case ("entrar"): {
				processarAcesso(request, response);
				break;
			}

			case ("sair"): {
				processarSaida(request, response);
				break;
			}
			case ("obterdadoscadastrais"): {
				processarBuscaDadosCadastrais(request, response);
				break;
			}

			case ("atualizar"): {
				processarAtualizacaoDeDadosCadastrais(request, response);
				break;
			}

			case ("alterar"): {
				processarBuscaDeDadosDeAcesso(request, response);
				break;
			}

			case ("alterarLogin"): {
				processarAlteracaoDeLogin(request, response);
				break;
			}

			case ("alterarSenha"): {
				processarAlteracaoDeSenha(request, response);
				break;
			}

			case ("detalhes"): {
				processarDetalhes(request, response);
				break;
			}

			case ("carrinho"): {
				processarCarrinho(request, response);
				break;
			}

			case ("atualizarCarrinho"): {
				processarAtualizacaoDoCarrinho(request, response);
				break;
			}

			case ("finalizarCompra"): {
				processarFinalCompra(request, response);
				break;
			}

			case ("opcaoHistorico"): {
				processarHistoricoDeCompras(request, response);
				break;
			}

			case ("historicoViaCodigo"): {
				processarHistoricoDeComprasViaCodigo(request, response);
				break;
			}

			default: {
				processarObterLivros(request, response);
			}

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		gerenciarFluxo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Livro;
import model.Suporte;

public class ClienteDao {

	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;

	Cliente cliente = new Cliente();
	Suporte suporte = new Suporte();

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

	public void insere(Cliente cliente) {

		try {
			iniciarConexao();

			sql = "insert into cliente (cpf, nome, email, foneCel, foneRes, "
					+ "login, senha, logradouro, complemento, cidade, bairro, cep, salt, datacadastro)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, cliente.getCpf());
			pstmt.setString(2, cliente.getNome());
			pstmt.setString(3, cliente.getEmail());
			pstmt.setString(4, cliente.getCel());
			pstmt.setString(5, cliente.getFone());
			pstmt.setString(6, cliente.getLogin());
			pstmt.setString(7, cliente.getSenha());
			pstmt.setString(8, cliente.getLogradouro());
			pstmt.setString(9, cliente.getComplemento());
			pstmt.setString(10, cliente.getCidade());
			pstmt.setString(11, cliente.getBairro());
			pstmt.setString(12, cliente.getCep());
			pstmt.setString(13, cliente.getSalt());
			pstmt.setString(14, cliente.getDataCadastro());

			pstmt.execute();

		} catch (SQLException | RuntimeException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

	}

	public boolean checarCpf(String cpf) {
		boolean cpfApto = false;
		try {
			iniciarConexao();

			sql = "select * from cliente where cpf = '" + cpf + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			cpfApto = rs.next();

		} catch (SQLException | RuntimeException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return cpfApto;

	}

	public boolean checarLogin(String login) {
		boolean loginApto = true;
		try {
			iniciarConexao();

			sql = "select * from cliente where login  = '" + login + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			loginApto = rs.next() ? false : loginApto;

		} catch (SQLException | RuntimeException e) {
			e.printStackTrace();

		} finally {
			fecharConexao();
		}

		return loginApto;
	}

	public boolean checarEmail(String email) {
		boolean emailApto = true;
		try {
			iniciarConexao();

			sql = "select * from cliente where email  = '" + email + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			emailApto = rs.next() ? false : emailApto;

		} catch (SQLException | RuntimeException e) {
			e.printStackTrace();

		} finally {
			fecharConexao();
		}

		return emailApto;
	}

	public boolean checarDadosLogon(String login, String senha) {
		boolean confirmado = false;
		try {
			iniciarConexao();

			sql = "select senha, salt from cliente where login = '" + login + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String senhaArmazenada = rs.getString("senha");

				String salt = rs.getString("salt");

				int length = salt.length();
				byte[] byteSalt = new byte[length / 2];

				for (int i = 0; i < length; i += 2) {
					byteSalt[i / 2] = (byte) ((Character.digit(salt.charAt(i), 16) << 4)
							+ Character.digit(salt.charAt(i + 1), 16));
				}

				String senhaInserida = suporte.criptografar(byteSalt, senha);

				confirmado = (senhaArmazenada).equals(senhaInserida) ? true : false;
				System.out.printf("\nSenha do banco:%s\n\nSenha inserida:%s\n", senhaArmazenada, senhaInserida);

			} else {
				confirmado = false;
			}

		} catch (SQLException | RuntimeException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return confirmado;
	}

	public String obterNome(String login) {
		String nome = null;
		try {
			iniciarConexao();

			sql = "select nome from cliente where login =" + "'" + login + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				String nomeTodo = rs.getString("nome");

				String[] partesNome = nomeTodo.split(" ");
				nome = partesNome[0];
			}
		} catch (SQLException | RuntimeException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return nome;
	}

	public String obterId(String login) {
		String codcli = null;
		try {
			iniciarConexao();

			sql = "select codcli from cliente where login =" + "'" + login + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				codcli = rs.getString("codcli");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return codcli;
	}

	public String[] obterDadosCadastrais(String codcli) {
		String[] registro = new String[9];
		try {
			iniciarConexao();

			sql = "select email, cep, logradouro, complemento, bairro, "
					+ "cidade, nome, foneres, fonecel from cliente where codcli = '" + codcli + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				registro[0] = rs.getString("email");
				registro[1] = rs.getString("cep");
				registro[2] = rs.getString("logradouro");
				registro[3] = rs.getString("complemento");
				registro[4] = rs.getString("bairro");
				registro[5] = rs.getString("cidade");
				registro[6] = rs.getString("nome");
				registro[7] = rs.getString("foneres");
				registro[8] = rs.getString("fonecel");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		return registro;
	}

	public void atualizarDadosCadastrais(Cliente cliente, String codcli) {
		try {
			iniciarConexao();
			sql = "update cliente set email = ?, cep = ?, logradouro = ?, complemento = ?, "
					+ "bairro = ?, cidade = ?, nome = ?, foneres = ?, fonecel = ? where codcli = '" + codcli + "'";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, cliente.getEmail());
			pstmt.setString(2, cliente.getCep());
			pstmt.setString(3, cliente.getLogradouro());
			pstmt.setString(4, cliente.getComplemento());
			pstmt.setString(5, cliente.getBairro());
			pstmt.setString(6, cliente.getCidade());
			pstmt.setString(7, cliente.getNome());
			pstmt.setString(8, cliente.getFone());
			pstmt.setString(9, cliente.getCel());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
	}

	public String obterLogin(String id) {
		String login = null;
		try {
			iniciarConexao();

			sql = "select login from cliente where codcli ='" + id + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				login = rs.getString("login");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		return login;
	}

	public void alterarLogin(Cliente cliente, String id) {
		try {
			iniciarConexao();

			sql = "update cliente set login = ? where codcli = '" + id + "'";

			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, cliente.getLogin());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
	}

	public String obterSenha(String id) {
		String senha = null;
		try {
			iniciarConexao();

			sql = "select senha from cliente where codcli ='" + id + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				senha = rs.getString("senha");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return senha;
	}

	public String obterSalt(String id) {
		String salt = null;
		try {
			iniciarConexao();

			sql = "select salt from cliente where codcli ='" + id + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				salt = rs.getString("salt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return salt;
	}

	public void alterarSenha(Cliente cliente, String id) {
		try {
			iniciarConexao();

			sql = "update cliente set senha = ? where codcli = '" + id + "'";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, cliente.getSenha());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
	}

	public boolean confirmarEmail(String email, String id) {
		boolean emailConfirmado = true;
		try {
			String emailBd = "";
			iniciarConexao();

			sql = "select email from cliente where codcli ='" + id + "'";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				emailBd = rs.getString("email");
			}

			emailConfirmado = (emailBd.equals(email)) ? emailConfirmado : false;

		} catch (SQLException | RuntimeException e) {
			e.printStackTrace();

		} finally {
			fecharConexao();
		}

		return emailConfirmado;
	}

}

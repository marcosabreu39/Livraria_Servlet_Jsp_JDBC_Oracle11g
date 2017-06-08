package model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Suporte {
	Cliente cliente = new Cliente();

	public boolean validarEmail(String email) {
		boolean emailValido = false;
		if (email != null && email.length() > 0) {
			final String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				emailValido = true;
			}
		}
		return emailValido;
	}

	public boolean validarCpf(String cpf) {

		boolean validado = true;

		for (int i = 0; i < 10; i++) {
			int[] repetidos = { i, i, i, i, i, i, i, i, i, i, i };

			String cpfInvalido = (String.valueOf(repetidos[0])) + (String.valueOf(repetidos[1]))
					+ (String.valueOf(repetidos[2])) + (String.valueOf(repetidos[3])) + (String.valueOf(repetidos[4]))
					+ (String.valueOf(repetidos[5])) + (String.valueOf(repetidos[6])) + (String.valueOf(repetidos[7]))
					+ (String.valueOf(repetidos[8])) + (String.valueOf(repetidos[9])) + (String.valueOf(repetidos[10]));

			// System.out.println(cpfInvalido);

			String outroCpfInvalido = (String.valueOf(repetidos[0])) + (String.valueOf(repetidos[1]))
					+ (String.valueOf(repetidos[2])) + (".") + (String.valueOf(repetidos[3]))
					+ (String.valueOf(repetidos[4])) + (String.valueOf(repetidos[5])) + (".")
					+ (String.valueOf(repetidos[6])) + (String.valueOf(repetidos[7])) + (String.valueOf(repetidos[8]))
					+ ("-") + (String.valueOf(repetidos[9])) + (String.valueOf(repetidos[10]));

			// System.out.println(outroCpfInvalido);

			if (cpf.equals(cpfInvalido) || cpf.equals(outroCpfInvalido)) {

				validado = false;
			}
		}

		return validado;
	}

	public boolean validarFormatoCpf(String cpf) {
		boolean cpfValido = false;
		final Pattern pattern = Pattern.compile("\\d{11}|\\d{3}[.]\\d{3}[.]\\d{3}-\\d{2}");
		Matcher matcher = pattern.matcher(cpf);

		if (matcher.matches()) {
			cpfValido = true;
		}

		return cpfValido;
	}

	public String formatarCpf(String cpf) {

		String cpfFormatado = cpf;

		if (cpf.length() == 11) {
			cpfFormatado = cpf.substring(0, 3) + (".") + cpf.substring(3, 6) + (".") + cpf.substring(6, 9) + ("-")
					+ cpf.substring(9);

			// System.out.println(cpfFormatado);

		}

		return cpfFormatado;
	}

	public boolean validarNome(String nome) {
		final String regexNome = "^[a-zA-ZÀ-ú-' ]+$";
		boolean nomeValidado = (nome.matches(regexNome) ? true : false);

		return nomeValidado;
	}

	public boolean validarTelefone(String fone) {
		final String regexFone = "^\\d{10}|\\d{6}-\\d{4}|[(]\\d{2}[)]\\d{8}|[(]\\d{2}[)]\\d{4}-\\d{4}$";
		boolean foneValidado = (fone.matches(regexFone) ? true : false);

		return foneValidado;
	}

	public String formatarTelefone(String fone) {
		String telefoneFormatado = null;
		switch (fone.length()) {

		case 0:
			telefoneFormatado = fone;
			break;

		case 10:
			telefoneFormatado = "(" + fone.substring(0, 2) + ")" + fone.substring(2, 6) + "-" + fone.substring(6, 10);
			break;

		case 11:
			telefoneFormatado = "(" + fone.substring(0, 2) + ")" + fone.substring(2);
			break;

		case 12:
			telefoneFormatado = fone.substring(0, 8) + "-" + fone.substring(8);
			break;

		default:
			telefoneFormatado = fone;
			break;
		}

		return telefoneFormatado;
	}

	public boolean validarCelular(String cel) {
		final String regexCel = "^\\d{11}|\\d{7}-\\d{4}|[(]\\d{2}[)]\\d{9}|[(]\\d{2}[)]\\d{5}-\\d{4}$";
		boolean celularValidado = (cel.matches(regexCel) ? true : false);

		return celularValidado;
	}

	public String formatarCelular(String cel) {
		String celularFormatado = null;
		switch (cel.length()) {
		case 11:
			celularFormatado = "(" + cel.substring(0, 2) + ")" + cel.substring(2, 7) + "-" + cel.substring(7, 11);
			break;

		case 12:
			celularFormatado = "(" + cel.substring(0, 2) + ")" + cel.substring(2);
			break;

		case 13:
			celularFormatado = cel.substring(0, 9) + "-" + cel.substring(9);
			break;

		default:
			celularFormatado = cel;
			break;
		}

		return celularFormatado;
	}

	public String[] buscarCep(String cep, String[] endereco) {
		WebServiceCep wsCep = WebServiceCep.searchCep(cep);
		if (wsCep.wasSuccessful()) {
			endereco[0] = (wsCep.getLogradouroFull());
			endereco[1] = (wsCep.getBairro());
			endereco[2] = (wsCep.getCidade() + " - " + wsCep.getUf());
		} else {
			endereco[0] = "";
			endereco[1] = "";
			endereco[2] = "";
		}
		return endereco;

	}

	public boolean validarCep(String cep) {
		final String regexCep = "^\\d{8}|\\d{5}-\\d{3}$";
		boolean cepValidado = (cep.matches(regexCep) ? true : false);

		return cepValidado;
	}

	public String formatarCep(String cep) {
		String cepFormatado = (cep.length() == 8 ? cep.substring(0, 5) + "-" + cep.substring(5) : cep);

		return cepFormatado;
	}

	public boolean validarLogin(String login) {
		final String regexLogin = "^[a-zA-Z]{4,15}$";
		boolean loginValidado = (login.matches(regexLogin) ? true : false);

		return loginValidado;
	}

	public boolean validarSenha(String senha) {
		final String regexSenha = "^.{8,16}$";
		boolean senhaValidada = (senha.matches(regexSenha) ? true : false);

		return senhaValidada;
	}

	public byte[] gerarSalt() {
		byte[] byteSalt = new byte[8];
		try {
			SecureRandom random;
			random = SecureRandom.getInstance("SHA1PRNG");

			random.nextBytes(byteSalt);

			/*
			 * StringBuilder hexSalt = new StringBuilder(); for (byte b :
			 * byteSalt) { hexSalt.append(String.format("%02X", 0xFF & b)); }
			 * 
			 * String saltString = hexSalt.toString();
			 * 
			 * cliente.setSalt(saltString);
			 */

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return byteSalt;
	}

	public String converterSaltParaString(byte[] byteSalt) {
		String stringSalt = null;

		StringBuilder hexSalt = new StringBuilder();
		for (byte b : byteSalt) {
			hexSalt.append(String.format("%02X", 0xFF & b));
		}

		stringSalt = hexSalt.toString();

		return stringSalt;
	}

	public String criptografar(byte[] byteSalt, String seraCriptografado) {
		String criptografado = null;
		try {
			final int ITERATION_NUMBER = 1000;
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.reset();
			messageDigest.update(byteSalt);

			byte byteHash[] = messageDigest.digest(seraCriptografado.getBytes("utf-8"));

			for (int i = 0; i < ITERATION_NUMBER; i++) {
				messageDigest.reset();
				byteHash = messageDigest.digest(byteHash);
			}

			StringBuilder hexHash = new StringBuilder();
			for (byte b : byteHash) {
				hexHash.append(String.format("%02x", 0xFF & b));
			}

			criptografado = hexHash.toString();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return criptografado;
	}

	public String gerarDataCadastro() {
		LocalDateTime data = LocalDateTime.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String dataCadastro = fmt.format(data);

		return dataCadastro;
	}

	public byte[] obterByteSalt(String salt) {
		byte[] byteSalt = null;

		int length = salt.length();
		byteSalt = new byte[length / 2];

		for (int i = 0; i < length; i += 2) {
			byteSalt[i / 2] = (byte) ((Character.digit(salt.charAt(i), 16) << 4)
					+ Character.digit(salt.charAt(i + 1), 16));
		}

		return byteSalt;
	}

	public String gerarDataHora() {
		LocalDateTime data = LocalDateTime.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String dataHora = fmt.format(data);

		return dataHora;
	}

	public BigDecimal gerarCodPedido(String dataCompra, String id) {
		String allNumber = dataCompra.replaceAll("[^0-9$]", "").concat(id);
		BigDecimal codCompra = new BigDecimal(allNumber);

		return codCompra;
	}

}

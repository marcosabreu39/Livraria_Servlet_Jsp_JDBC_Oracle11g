<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style.css">
<title>Página de confirmação de cadastro</title>
</head>
<body>
	<div id="container">
		<header id="header" role="banner">
		<!-- <img src="livraweb.png" alt="Banner image" /> -->
		<h1 id="cabecalho">Livraweb</h1>
		</header>
		<nav id="menu">
			<ul>
				<li><a href="${pageContext.request.contextPath}/">Home</a></li>
				<li><a href="/contact">Contato</a></li>
				<li><a href="${pageContext.request.contextPath}/carrinho.jsp"><input type="image" src="fotos/carrinho_branco.png" class="carrinho" id="cart"/>
				<label for="cart">${fn:length(carrinho)}</label></a></li>	
				<li>
				<div id="busca">
				<form action="ControllerServlet?acao=busca" method="post">
				<input type="search" name="busca" placeholder="Busca de livros..." id="inputsearch"/>
				<input type="submit" value="Buscar" id="search"/>
				</form>
				</div>
				</li>				
				<c:choose>
					<c:when test="${empty sessionScope.id}">
						<li><a href="#">Cadastro</a>
							<ul class="submenu-1">
								<li><a
									href="${pageContext.request.contextPath}/cadastro.jsp">Novo
										cadastro</a></li>
							</ul></li>
					</c:when>
					<c:when test="${sessionScope.id != ''}">
						<li id="ativo"><a href="#">Cadastro</a>
							<ul class="submenu-1">
								<li><a
									href="${pageContext.request.contextPath}/ControllerServlet?acao=obterdadoscadastrais">Atualizar
										dados cadastrais</a></li>
								<li><a href="${pageContext.request.contextPath}/alterarDadosAcesso.jsp">Alterar dados de acesso</a></li>
								<li><a href="#">Remover cadastro</a></li>
							</ul></li>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${empty sessionScope.nome}">
						<li><a href="#">Visitante</a></li>
					</c:when>
					<c:when test="${sessionScope.nome != ''}">
						<li><a href="#">${sessionScope.nome}</a>
							<ul class="submenu-1">
								<li><a href="#">Status do pedido</a></li>
								<li><a href="${pageContext.request.contextPath}/historicoCompras.jsp">Histórico de compras</a></li>
							</ul></li>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${empty sessionScope.id}">
						<li><a href="${pageContext.request.contextPath}/login.jsp">Entrar</a></li>
					</c:when>
					<c:when test="${sessionScope.id != ''}">
						<li><a
							href="${pageContext.request.contextPath}/ControllerServlet?acao=sair">Sair</a></li>
					</c:when>
				</c:choose>
			</ul>

		</nav>
		<div id="conteudo">
			<div id="sucesso">
				<h3>${msgSucesso}</h3>
				<br>
				<table class="tabsucesso">
				<c:if test="${!empty cliente.cpf}">
					<tr>
						<td>Cpf:</td>
						<td>${cliente.cpf}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.nome}">
					<tr>
						<td>Nome:</td>
						<td>${cliente.nome}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.email}">
					<tr>
						<td>Email:</td>
						<td>${cliente.email}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.fone}">
						<tr>
							<td>Telefone:</td>
							<td>${cliente.fone}</td>
						</tr>
					</c:if>
					<c:if test="${!empty cliente.cel}">
					<tr>
						<td>Celular:</td>
						<td>${cliente.cel}</td>
					</tr>			
					</c:if>
					<c:if test="${!empty cliente.cep}">		
					<tr>
						<td>Cep:</td>
						<td>${cliente.cep}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.logradouro}">
					<tr>
						<td>Logradouro:</td>
						<td>${cliente.logradouro}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.complemento}">
					<tr>
						<td>Complemento:</td>
						<td>${cliente.complemento}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.bairro}">
					<tr>
						<td>Bairro:</td>
						<td>${cliente.bairro}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.cidade}">
					<tr>
						<td>Cidade:</td>
						<td>${cliente.cidade}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.login}">
					<tr>
						<td>Login:</td>
						<td>${cliente.login}</td>
					</tr>
					</c:if>
					<c:if test="${!empty cliente.dataCadastro}">
					<tr>
						<td>Data do Cadastro:</td>
						<td>${cliente.dataCadastro}</td>
					</tr>
					</c:if>
				</table>
			</div>
		</div>

		<footer id="footer">
			<p id="pf">&copy; Copyright 2016 by Marcos</p>
		</footer>
	</div>
</body>
</html>
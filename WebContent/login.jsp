<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style.css">
<!--[if lte IE 8]>
 <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
 <![endif]-->
<title>Página de login</title>
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
						<li><a href="#">Cadastro</a>
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
						<li id="ativo"><a href="${pageContext.request.contextPath}/login.jsp">Entrar</a></li>
					</c:when>
					<c:when test="${sessionScope.id != ''}">
						<li><a
							href="${pageContext.request.contextPath}/ControllerServlet?acao=sair">Sair</a></li>
					</c:when>
				</c:choose>
			</ul>
		</nav>
		<div id="conteudo">

			<div id="divlogin">
				<form action="ControllerServlet?acao=entrar" method="post">
					<fieldset>
						<legend>Tela de login</legend>
						<p>${msgErro}</p>
						<br> <label for="login" class="formlogin">Login:</label> <input
							type="text" name="login" id="login" class="formlogin"
							value="${cliente.login}" />
						<p>${msgLogin}</p>
						<br> <label for="login" class="formlogin">Senha:</label> <input
							type="password" name="senha" id="senha" class="formlogin" />
						<p>${msgSenha}</p>
						<br> 
						<label for="entrar" class="formlogin"></label>
						<input type="submit" value="Entrar" class="botao" id="entrar"><br>
						<a href="">Esqueci a minha senha de acesso</a>
					</fieldset>
				</form>
			</div>

		</div>

		<footer id="footer">
			<p id="pf">&copy; Copyright 2016 by Marcos</p>
		</footer>
	</div>
</body>
</html>
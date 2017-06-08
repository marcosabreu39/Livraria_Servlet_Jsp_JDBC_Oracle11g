<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link rel="stylesheet" type="text/css" href="style.css">
<!--[if lte IE 8]>
 <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
 <![endif]-->
<title>Página de Detalhes</title>
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
							
			<div class="detalhes">	
												
		    <table>
		    <thead><tr><th><h3>Descrição do livro:</h3></th></tr></thead>
			<tr> <td rowspan="10"><img class="imgdetalhes" src='<c:url value="${livro.imagem}"/>' 
			alt="${livro.nome}"></td>
			</tr>
			<tr>
			<td id="linhatitulo">Nome:</td><td><c:out value="${livro.nome}"/></td>
			<tr/>			
			<tr>
			<td id="linhatitulo">Isbn:</td><td><c:out value="${livro.isbn}"/></td>
			</tr>
			<tr>
			<td id="linhatitulo">Gênero:</td><td><c:out value="${livro.genero}"/></td>
			</tr>
			<tr>
			<td id="linhatitulo">Autor(es):</td><td><c:out value="${livro.autor}"/></td>
			</tr>
			<tr>
			<td id="linhatitulo">Editora:</td><td><c:out value="${livro.editora}"/></td>
			</tr>
			<tr>
			<td id="linhatitulo">Estoque:</td><td><c:out value="${livro.quantidade}"/></td>
			</tr>
			<tr>
			<td id="linhatitulo">Preço:</td><td><fmt:setLocale value="pt-BR"/>
			<fmt:formatNumber value="${livro.preco}" minFractionDigits="2" type="currency"/></td>
			</tr>
			<tr><td id="linhatitulo" colspan="2">
			<form action="ControllerServlet" method="post">
			<input type="hidden" name="index" value="${i}"/>
			<input type="hidden" name="acao" value="carrinho"/>
			<input type="submit" value="Colocar no Carrinho" class="botaoDefault"/>
			</form>
			
			</td>
			</tr>
			</table>			
								
			</div>			
			
		</div>

		<footer id="footer">
			<p id="pf">&copy; Copyright 2016 by Marcos</p>
		</footer>
	</div>
</body>
</html>
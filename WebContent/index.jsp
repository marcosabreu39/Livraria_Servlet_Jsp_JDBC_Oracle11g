<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="5">
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<link rel="stylesheet" type="text/css" href="style.css">
<!--[if lte IE 8]>
 <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
 <![endif]-->
<title>Página inicial</title>
</head>
<body>
	<div id="container">
		<header id="header" role="banner">
			<!-- <img src="livraweb.png" alt="Banner image" /> -->
			<h1 id="cabecalho">Livraweb</h1>
		</header>
		<nav id="menu">
			<ul>
				<li id="ativo"><a href="${pageContext.request.contextPath}/">Home</a></li>
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
			
			<div id="livros">			
						
			<ul>		
			<c:forEach var="livro" items="${requestScope.livros}" varStatus="i"> 										
			<li>		
				
			<div class="livro">					
					
			<c:choose>
			
			<c:when test="${livro.quantidade == 0}">
			<table>
			<tr>
			<td colspan="2">
			<figure>
			<img class="imga" src='<c:url value="${livro.imagem}"/>' title="${livro.nome}">
			<figcaption>${livro.nome}</figcaption>
			</figure>
			</td>
			</tr>			
			<tr>			
			<td colspan="2"><fmt:setLocale value="pt-BR"/>
			<fmt:formatNumber value="${livro.preco}" minFractionDigits="2" type="currency"/>
			</td>
			</tr>
			<tr>
			<td colspan="2">
			<h4 style="color: red;">Livro indisponível</h4>
			</td>
			</tr>
			</table>
			</c:when>	
				
			<c:when test="${livro.quantidade > 0}">		
			<table>
			<tr>
			<td colspan="2">
			<figure>
			<img class="imga" src='<c:url value="${livro.imagem}"/>' title="${livro.nome}">
			<figcaption>${livro.nome}</figcaption>
			</figure>
			</td>
			</tr>			
			<tr>			
			<td colspan="2"><fmt:setLocale value="pt-BR"/>
			<fmt:formatNumber value="${livro.preco}" minFractionDigits="2" type="currency"/>
			</td>
			</tr>
			<tr>
			<td>
			<form action="ControllerServlet" method="get">
			<input type="hidden" name="acao" value="detalhes"/>
			<input type="hidden" name="index" value="${i.index}"/>
			<input type="submit" value="Detalhes" class="botaoDefault"/>
			</form>			
			</td>			
			<td>
			<form action="ControllerServlet" method="post">
			<input type="hidden" name="acao" value="carrinho"/>
			<input type="hidden" name="index" value="${i.index}"/>
			<input type="submit" value="Carrinho" class="botaoDefault"/>
			</form>			
			</td>
			</tr>
			</table>	
			</c:when>					
			</c:choose>	
													
			</div>
							
			</li>			
			
			</c:forEach>
			</ul>
			
			</div>
			
		</div>

		<footer id="footer">
			<p id="pf">&copy; Copyright 2016 by Marcos</p>
		</footer>
	</div>
</body>
</html>
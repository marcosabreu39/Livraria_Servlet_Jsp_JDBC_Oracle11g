<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style.css">
<!--[if lte IE 8]>
 <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
 <![endif]-->
<title>Página do Carrinho de compras</title>
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
				<li id="ativo"><a href="${pageContext.request.contextPath}/carrinho.jsp">
				<input type="image" src="fotos/carrinho_verde.png" class="carrinho" id="cart"/>				  				
				<label for="cart" class="carrinho">${fn:length(carrinho)}</label></a></li>	
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
		
							
			<div id="cart_table">	
			
			
			<c:choose>
		
			<c:when test="${empty carrinho && empty msgCarrinho}">			
			<c:set var="color" value="background: #FFF; color: black;"/>
			<h2 style="${color}">O carrinho de compras está vazio.</h2><br>
			</c:when>
		
			<c:when test="${fn:contains(msgCarrinho, 'removido')}">			
			<c:set var="color" value="background: #FFF; color: orange;"/>
			</c:when>
		
			<c:when test="${fn:contains(msgCarrinho, 'Nenhuma')}">			
			<c:set var="color" value="background: #FFF; color: yellow;"/>
			</c:when>		
		
			<c:when test="${fn:contains(msgCarrinho, 'adicionado')}">			
			<c:set var="color" value="background: #FFF; color: blue;"/>
			</c:when>
		
			<c:when test="${fn:contains(msgCarrinho, 'disponível(is)')}">			
			<c:set var="color" value="background: #FFF; color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgCarrinho, 'mudanças')}">			
			<c:set var="color" value="background: #FFF; color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgCarrinho, 'sucesso')}">			
			<c:set var="color" value="background: #FFF; color: #32CD32;"/>
			</c:when>
		
		</c:choose>		
				
			<c:set var="mensagem" value="${msgCarrinho}"/>
								
			<h2 style="${color}"><c:out value="${mensagem}"/></h2><br>			
			
															
			<c:choose>
				 																		
			<c:when test="${not empty carrinho}">			
											
			<table class="produtos">					
			<caption>Carrinho de Compras</caption>						
			<tr><th style="text-align: left;padding-left: 17px;">Capa</th><th>Nome do(s) livro(s)</th>
			<th>Quantidade</th><th>Estoque</th><th>Preço</th><th>Subtotal</th></tr>	
					
			<c:forEach var="livros" items="${carrinho}" varStatus="x">			
		<%--<c:set var="cont" value="${x.index}"/>			
			<c:set var="total"  value="${total = total + carrinho[cont].preco}"/>--%>			
			</c:forEach>
			<c:forEach var="livro" items="${map}" varStatus="indice">
			<c:set var="i" value="indice.index"/>	
			<%-- 
			<c:set var="book" value="map[i].key.nome"/>							
			--%>
			<c:set var="nome" value="${livro.key.nome}"/>
			<tr>
			<td id="colimg">
			<img class="imgcart" src='<c:url value="${livro.key.imagem}"/>' 
			alt="${livro.key.nome}">
			</td>
			<td>
				
			<c:choose>
							
			<c:when test="${fn:contains(nome, '*')}">
			<c:set var="nomeLivro" value="${fn:substringBefore(nome,'*')}"/>
			<c:set var="cor" value="color: red;"/>
			<p style="${cor}"><c:out value="${nomeLivro}"></c:out></p>
			</c:when>	
						
			<c:otherwise>			
			<c:set var="cor" value="color: black;"/>			
			<p style="${cor}"><c:out value="${nome}"></c:out></p>			
			</c:otherwise>					
			 
			</c:choose>		
					
			</td>		
			<td>	
			<c:set var="estoque" value="${livro.key.quantidade - livro.value}"/>
			<c:set var="quantidade" value="${livro.key.quantidade}"/>
			<c:set var="quantReal" value="${quantidade > livro.key.quantidade ? livro.key.quantidade : quantidade}" scope="session"/>	
			<form action="ControllerServlet" method="post">	
			<input type="hidden" name="acao" value="atualizarCarrinho"/>
			<input type="hidden" name="isbn" value="${livro.key.isbn}"/>
			<input type="hidden" name="total" value="${total}"/>										
			<input type="number" name="quantidade" min="0" max="${quantReal}" value="${livro.value}"class="inpnumber" /> 
			<input type="submit" value="Atualizar" id="atualizar"/>
			</form>			
			</td>	
			<td>			
			<input type="number" readonly="readonly" min="0" value="${estoque}"class="inpnumber" />
			</td>
			<td>
			<fmt:setLocale value="pt-BR"/>
			<fmt:formatNumber value="${livro.key.preco}" minFractionDigits="2" type="currency"/>				
			</td>
			<td id="subtotal">
			<c:set var="subtotal" value="${livro.key.preco * livro.value}"/>	
			 <fmt:formatNumber value="${subtotal}" minFractionDigits="2" type="currency"/>
			</td>								
			</tr>								 		 						 
			</c:forEach>						
			<tr>	
			<td id="total" colspan="6">																	 
			<input type="hidden" value="<fmt:formatNumber value="${total}"
			 minFractionDigits="2" type="currency"/>"/><h3>Total:
			 <fmt:formatNumber value="${total}" minFractionDigits="2" type="currency"/></h3>
			</td>		
			</tr>					
			<tr>			
			<td class="finalizar" colspan="6">
		<%--<a href="${pageContext.request.contextPath}/ControllerServlet?acao=finalizarCompra">
			Finalizar Compra</a>--%>
			<form action="ControllerServlet" method="post">
			<input type="hidden" name="acao" value="finalizarCompra"> 
	    <%--<input type="hidden" name="total" value="${total}"/>--%>
			<input type="submit" value="Finalizar compra" id="submitFinal">
			</form>
						
			</td>			
			</tr>								
		    </table>	
		    
		     		
			</c:when>
				
			
			
			</c:choose>			
			
								
			</div>			
			
		</div>

		<footer id="footer">
			<p id="pf">&copy; Copyright 2016 by Marcos</p>
		</footer>
	</div>
</body>
</html>
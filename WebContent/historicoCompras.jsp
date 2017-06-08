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
<title>Página de Histórico de compras</title>
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
				<li><a href="${pageContext.request.contextPath}/carrinho.jsp">
				<input type="image" src="fotos/carrinho_branco.png" class="carrinho" id="cart"/>
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
								<li id="ativo"><a href="${pageContext.request.contextPath}/historicoCompras.jsp">Histórico de compras</a></li>
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
							
			<div id="div_historico">	
			
			<h3>${msgHistorico}</h3>
			
			<fieldset>
			
			<legend>Histórico de compras</legend>
						
			<form action="ControllerServlet" method="post">
			
			<label for="select" class="formulario">Selecionar:</label>
			
			<select class="selectOpt" id="select" name="opcaoDeHistorico" style="width: 404px;"> 
						
			<option selected="selected" class="optionHistorico" style="width: 400px;">Selecione a opção de Busca</option>
			<option class="optionOpt" value="historicoViaId" style="width: 400px;">Buscar histórico usando o código da compra</option>
			<option class="optionOpt" value="historicoTotal" style="width: 400px;">Buscar o meu histórico de compras completo</option>
			
			</select>
			
			<input type="hidden" name="acao" value="opcaoHistorico">
			
			<input type="submit" value="Enviar" class="botao"> <c:if test="${not empty msgSelect}">
			<strong style="color: red;">${msgSelect}</strong></c:if>
						
			</form>			
					
			<c:if test="${not empty historicoViaId || not empty param.codigo}">
			
			<form action="ControllerServlet" method="post">
				<input type="hidden" name="acao" value="historicoViaCodigo">
				<label class="formulario"></label>
				<input type="text" name="codigo" value="${param.codigo}" class="historico" placeholder="Insira o código da compra" 
				required="required" style="width: 400px;" id="input_historico"/>
				<input type="submit" value="Buscar"class="botao"/>			
				</form>
				
			</c:if>
			
			<br>
			
			</fieldset>
			
				<c:choose>			
				
				<c:when test="${not empty historicos}">
				
				<table class="table_historico">
				
				<caption style="color: green; text-align: center;">Histórico de compras completo</caption>
				
				<c:forEach var="historico" items="${historicos}">				
				
				<thead>
				
				<tr><th>Data da compra</th><th>Código do pedido</th><th>Total da compra</th></tr>
				
				<fmt:setLocale value="pt-BR"/>				
				<tr><td>${historico.dataCompra}</td><td>${historico.codPedido}</td>				
			    <td><fmt:formatNumber value="${historico.total}" minFractionDigits="2" type="currency"/></td></tr>	
				
				</thead>
				
				<tbody>	
				
				<tr><th>Capa</th><th>Nome do(s) livro(s)</th><th>Quantidade</th></tr>							
				
				<c:forEach var="hist" items="${historico.nomeEquantidade}">
												
				<tr><td><img class="imgHistorico" src='<c:url value="/fotos/${hist.key}.png"/>' alt="${hist.key}"></td>
				<td>${hist.key}</td><td>${hist.value}</td></tr>				
				
				</c:forEach>	
				
				</tbody>			
				
				<tfoot>
				
				</tfoot>
				
				</c:forEach>	
																
				</table>				
				
				</c:when>	
				
				<c:when test="${not empty historico}">
				
				<table class="table_historico">
				
				<caption style="color: blue; text-align: center;">Histórico de compras pelo código</caption>
							
				<thead>
				
				<tr><th>Código do pedido</th><th>Data da compra</th><th>Total da compra</th></tr>
				
				<fmt:setLocale value="pt-BR"/>				
				<tr><td>${historico.codPedido}</td><td>${historico.dataCompra}</td>				
			    <td><fmt:formatNumber value="${historico.total}" minFractionDigits="2" type="currency"/></td></tr>	
				
				</thead>
				
				<tbody>	
				
				<tr><th>Capa</th><th>Nome do livro</th><th>Quantidade</th></tr>							
				
				<c:forEach var="hist" items="${historico.nomeEquantidade}">
												
				<tr><td><img class="imgcart" src='<c:url value="/fotos/${hist.key}.png"/>' alt="${hist.key}"></td>
				<td>${hist.key}</td><td>${hist.value}</td></tr>				
				
				</c:forEach>	
				
				</tbody>			
				
				<tfoot>
				
				</tfoot>
																						
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
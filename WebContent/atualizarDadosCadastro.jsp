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
<title>Página de atualização de dados cadastrais</title>
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
						<li id="ativo"><a href="#">Cadastro</a>
							<ul class="submenu-1">
								<li><a
									href="${pageContext.request.contextPath}/cadastro.jsp">Novo
										cadastro</a></li>
							</ul></li>
					</c:when>
					<c:when test="${sessionScope.id != ''}">
						<li><a href="#">Cadastro</a>
							<ul class="submenu-1">
								<li  id="ativo"><a
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
			<div id="cadastro">			
				<h3>${msgDados}</h3>
				<fieldset>
					<legend>Atualizar dados cadastrais</legend>
					<%--Primeiro form --%>										
					<form action="ControllerServlet?acao=checaremail&retorno=atualizar" method="post">					
					<input type="hidden" name="cep" 
					value="${valor = cliente.cep == null ? param.cep : cliente.cep}"/>
					<input type="hidden" name="logradouro" 
					value="${valor = cliente.logradouro == null ? param.logradouro : cliente.logradouro}"/>
					<input type="hidden" name="complemento" 
					value="${valor = cliente.complemento == null ? param.complemento : cliente.complemento}"/>
					<input type="hidden" name="bairro" 
					value="${valor = cliente.bairro == null ? param.bairro : cliente.bairro}"/>
					<input type="hidden" name="cidade" 
					value="${valor = cliente.cidade == null ? param.cidade : cliente.cidade}"/>
					<input type="hidden" name="nome" 
					value="${valor = cliente.nome == null ? param.nome : cliente.nome}"/>
					<input type="hidden" name="fone" 
					value="${valor = cliente.fone == null ? param.fone : cliente.fone}"/>
					<input type="hidden" name="cel" 
					value="${valor = cliente.cel == null ? param.cel : cliente.cel}"/>
						<label for="email" class="formulario">E-mail:</label> 
						<input type="text" name="email" id="email" class="apenas" 
						value="${valor = cliente.email == null ? param.email : cliente.email}" 
						placeholder="Ex: meuemail@mail.com" required="required" maxlength="80" />
						 <input type="submit" value="Verificar Email" name="email" class="botao" />${msgEmail}
						<c:if test="${!empty msg[0]}">
					 ${msg[0]}
					 </c:if>					 
					</form>
					<%--Segundo form --%>					
					<form action="ControllerServlet?acao=checarcep&retorno=atualizar" method="post">
			<%--	<input type = "hidden" name="jsp" value="/atualizarCadCli.jsp">		--%>
					<input type="hidden" name="logradouro" 
					value="${valor = cliente.logradouro == null ? param.logradouro : cliente.logradouro}"/>
					<input type="hidden" name="complemento" 
					value="${valor = cliente.complemento == null ? param.complemento : cliente.complemento}"/>
					<input type="hidden" name="bairro" 
					value="${valor = cliente.bairro == null ? param.bairro : cliente.bairro}"/>
					<input type="hidden" name="cidade" 
					value="${valor = cliente.cidade == null ? param.cidade : cliente.cidade}"/>
					<input type="hidden" name="nome" 
					value="${valor = cliente.nome == null ? param.nome : cliente.nome}"/>
					<input type="hidden" name="fone" 
					value="${valor = cliente.fone == null ? param.fone : cliente.fone}"/>
					<input type="hidden" name="cel" 
					value="${valor = cliente.cel == null ? param.cel : cliente.cel}"/>
					<input type="hidden" name="email" 
					value="${valor = cliente.email == null ? param.email : cliente.email}"/>
					<label for="cep" class="formulario">CEP:</label> 
					<input type="text" name="cep" id="cep" class="apenas" 
					value="${valor = cliente.cep == null ? param.cep : cliente.cep}"					
					placeholder="Ex:xxxxxxxx ou xxxxx-xxx" required="required" maxlength="50" /> 
					<input type="submit" value="Verificar Cep" name="cep" class="botao" /> ${msgCep}
						<c:if test="${!empty msg[1]}">
					 ${msg[1]}
					 </c:if>
					</form>
					<%--Form com os dados cadastrais para atualizar --%>
					<form action="ControllerServlet?acao=atualizar" method="post">
						<c:forEach var="msg" items="${msg}" />
						<input type="hidden" name="email" 
						value="${valor = cliente.email == null ? param.email : cliente.email}"/>
						<input type="hidden" name="cep"
						 value="${valor = cliente.cep == null ? param.cep : cliente.cep}" /> 
						<label for="logradoouro" class="formulario">Logradouro:</label> 
						<input type="text" name="logradouro" id="logradouro" class="formulario"
							value="${valor = cliente.logradouro == null ? param.logradouro : cliente.logradouro}"
							 placeholder="Ex:Minha rua" required="required"
							 maxlength="150" />
						<c:if test="${!empty msg[2]}">
					 ${msg[2]}
					 </c:if>
						<br> <label for="complemento" class="formulario">Complemento:</label>
						<input type="text" name="complemento" id="complemento" class="formulario"
						 value="${valor = cliente.complemento == null ? param.complemento : cliente.complemento}" 
						 placeholder="Ex:Número 01, bloco 03, apartamento 208"
							required="required" maxlength="150" />
						<c:if test="${!empty msg[3]}">
					 ${msg[3]}
					 </c:if>
						<br> <label for="bairro" class="formulario">Bairro:</label>
						<input type="text" name="bairro" id="bairro" class="formulario" 
						value="${valor = cliente.bairro == null ? param.bairro : cliente.bairro}"
						 placeholder="Ex:Meu bairro" required="required" maxlength="150" />
						<c:if test="${!empty msg[4]}">
					 ${msg[4]}
					 </c:if>
						<br> <label for="cidade" class="formulario">Cidade:</label>
						<input type="text" name="cidade" id="cidade" class="formulario" 
						value="${valor = cliente.cidade == null ? param.cidade : cliente.cidade}"
						 placeholder="Ex:Rio de Janeiro-RJ" required="required" maxlength="150" />
						<c:if test="${!empty msg[5]}">
					 ${msg[5]}
					 </c:if>
						<br> <label for="nome" class="formulario">Nome:</label>
						 <input type="text" name="nome" id="nome" class="formulario" 
						 value="${valor = cliente.nome == null ? param.nome : cliente.nome}" 
						 placeholder="Ex: João da Silva" required="required" maxlength="100" />
						<c:if test="${!empty msg[6]}">
					 ${msg[6]}
					 </c:if>
						<br> <label for="fone" class="formulario">Telefone:</label>
						 <input type="text" name="fone" id="fone" class="formulario" 
						 value="${valor = cliente.fone == null ? param.fone : cliente.fone}"						 
							placeholder="Ex: xxxxxxxxxx ou (xx)xxxx-xxxx" maxlength="60" />
						<c:if test="${!empty msg[7]}">
					 ${msg[7]}
					 </c:if>
						<br> <label for="cel" class="formulario">Celular:</label> 
						<input type="text" name="cel" id="cel" class="formulario" 
						value="${valor = cliente.cel == null ? param.cel : cliente.cel}"
							placeholder="Ex: xxxxxxxxxxx ou (xx)xxxxx-xxxx" required="required" maxlength="50" />
						<c:if test="${!empty msg[8]}">
					${msg[8]}
					 </c:if>					 
						<br> 
						<label for="atualizar" class="formulario"></label>
						<input type="submit" value="Atualizar" class="botao" id="atualizar">
					</form>
				</fieldset>
			</div>
		</div>
		<footer id="footer">
			<p id="pf">&copy; Copyright 2016 by Marcos</p>
		</footer>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style.css">
<!--[if lte IE 8]>
 <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
 <![endif]-->
<title>Página de cadastro</title>
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

			<div id="cadastro">
			
			<c:choose>
			
			<c:when test="${fn:contains(msgCpf, 'sucesso')}">			
			<c:set var="color" value="color: green;"/>
			</c:when>
		
			<c:when test="${fn:contains(msgCpf, 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>	
			
			<c:when test="${fn:contains(msg[0], 'sucesso')}">			
			<c:set var="color" value="color: green;"/>
			</c:when>
		
			<c:when test="${fn:contains(msg[0], 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgLogin, 'apto')}">			
			<c:set var="color" value="color: green;"/>
			</c:when>
			
			<c:when test="${fn:contains(msg[10], 'apto')}">			
			<c:set var="color" value="color: green;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgLogin, 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgLogin, 'utilizado')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msg[10], 'utilizado')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
				
			<c:when test="${fn:contains(msg[10], 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgEmail, 'utilizado')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
				
			<c:when test="${fn:contains(msgEmail, 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgEmail, 'sucesso')}">			
			<c:set var="color" value="color: green;"/>
			</c:when>
				
			<c:when test="${fn:contains(msg[2], 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msg[2], 'utilizado')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msg[2], 'sucesso')}">			
			<c:set var="color" value="color: green;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgCep, 'não')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			<c:when test="${fn:contains(msg[5], 'não')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msgCep, 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
			
			<c:when test="${fn:contains(msg[5], 'incorreto')}">			
			<c:set var="color" value="color: red;"/>
			</c:when>
						
			</c:choose>
						
				<h3>Os campos com asterisco são obrigatórios.</h3>
				<fieldset>
					<legend>Novo cadastro</legend>
					<%--Primeiro form --%>
					<form action="ControllerServlet?acao=checarcpf" method="post">
						<label for="cpf" class="formulario">*CPF:</label> <input
							type="text" name="cpf" id="cpf" class="apenas"
							value="${param.cpf}"
							placeholder="Ex: xxxxxxxxxxx ou xxx.xxx.xxx-xx"
							required="required" maxlength="25" /> <input type="submit"
							value="Verificar Cpf" name="cpf" class="botao" /> 
							<strong style="${color}"><c:out value="${msgCpf}"/></strong>
						<c:if test="${!empty msg[0]}"> 					 
					 <strong style="${color}"><c:out value="${msg[0]}"/></strong>
					 </c:if>

					</form>
					<%--Segundo form --%>
					<form action="ControllerServlet?acao=checarlogin" method="post">
						<input type="hidden" name="cpf" value="${param.cpf}"> <label
							for="login" class="formulario">*Login:</label> <input type="text"
							name="login" id="login" class="apenas" value="${param.login}"
							placeholder="Entre 8 e 16 letras. Ex:meulogin"
							required="required" maxlength="100" /> <input type="submit"
							value="Verificar login" name="login" class="botao" />						
						<strong style="${color}"><c:out value="${msgLogin}"/></strong>
						<c:if test="${!empty msg[10]}">
						<strong style="${color}"><c:out value="${msg[10]}"/></strong>					 
					 </c:if>
						<br>
					</form>
					<%--Terceiro form --%>
					<form action="ControllerServlet?acao=checaremail&retorno=cadastro" method="post">
						<input type="hidden" name="cpf" value="${param.cpf}"> <input
							type="hidden" name="login" value="${param.login}"> <label
							for="email" class="formulario">*E-mail:</label> <input
							type="text" name="email" id="email" class="apenas"
							value="${param.email}" placeholder="Ex: meuemail@mail.com"
							required="required" maxlength="80" /> <input type="submit"
							value="Verificar Email" name="email" class="botao" />
							<strong style="${color}"><c:out value="${msgEmail}"/></strong>
						<c:if test="${!empty msg[2]}">					 
					 <strong style="${color}"><c:out value="${msg[2]}"/></strong>
					 </c:if>
					</form>
					<%--Quarto form --%>
					<form action="ControllerServlet?acao=checarcep&retorno=cadastro" method="post">
						<input type="hidden" name="cpf" value="${param.cpf}"> <input
							type="hidden" name="login" value="${param.login}"> <input
							type="hidden" name="email" value="${param.email}"><label
							for="cep" class="formulario">*CEP:</label> <input type="text"
							name="cep" id="cep" class="apenas" value="${param.cep}"
							placeholder="Ex:xxxxxxxx ou xxxxx-xxx" required="required"
							maxlength="50" /> <input type="submit" value="Verificar Cep"
							name="cep" class="botao" /> 
							<strong style="${color}"><c:out value="${msgCep}"/></strong>
						<c:if test="${!empty msg[5]}">
						<strong style="${color}"><c:out value="${msg[5]}"/></strong>
					 
					 </c:if>
					</form>
					<%--Form com todos os atributos --%>
					<form action="ControllerServlet?acao=cadastrar" method="post">
						<c:forEach var="msg" items="${msg}" />
						<input type="hidden" name="cpf" value="${param.cpf}"> <input
							type="hidden" name="login" value="${param.login}"> <input
							type="hidden" name="email" value="${param.email}"> <input
							type="hidden" name="cep" value="${param.cep}" /> <label
							for="logradoouro" class="formulario">*Logradouro:</label> <input
							type="text" name="logradouro" id="logradouro" class="formulario"
							value="${valor = cliente.logradouro == null ? param.logradouro : cliente.logradouro}" 
							placeholder="Ex:Minha rua" required="required" maxlength="150" />
						<c:if test="${!empty msg[6]}">
						<strong style="color: red;"><c:out value="${msg[6]}"/></strong>					 
					 </c:if>
						<br> <label for="complemento" class="formulario">*Complemento:</label>
						<input type="text" name="complemento" id="complemento" class="formulario" 
						value="${valor = cliente.complemento == null ? param.complemento : cliente.complemento}"
							placeholder="Ex:Número 01, bloco 03, apartamento 208"
							required="required" maxlength="150" />
						<c:if test="${!empty msg[9]}">
					 <strong style="color: red;"><c:out value="${msg[9]}"/></strong>
					 </c:if>
						<br> <label for="bairro" class="formulario">*Bairro:</label>
						<input type="text" name="bairro" id="bairro" class="formulario"
							value="${valor = cliente.bairro == null ? param.bairro : cliente.bairro}"
							 placeholder="Ex:Meu bairro" required="required" maxlength="150" />
						<c:if test="${!empty msg[7]}">
					 <strong style="color: red;"><c:out value="${msg[7]}"/></strong>
					 </c:if>
						<br> <label for="cidade" class="formulario">*Cidade:</label>
						<input type="text" name="cidade" id="cidade" class="formulario"
							value="${valor = cliente.cidade == null ? param.cidade : cliente.cidade}" 
							placeholder="Ex:Rio de Janeiro-RJ" required="required" maxlength="150" />
						<c:if test="${!empty msg[8]}">
					 <strong style="color: red;"><c:out value="${msg[8]}"/></strong>
					 </c:if>
						<br> <label for="nome" class="formulario">*Nome:</label> <input
							type="text" name="nome" id="nome" class="formulario"
							value="${param.nome}" placeholder="Ex: João da Silva"
							required="required" maxlength="100" />
						<c:if test="${!empty msg[1]}">
					 <strong style="color: red;"><c:out value="${msg[1]}"/></strong>
					 </c:if>
						<br> <label for="fone" class="formulario">Telefone:</label> <input
							type="text" name="fone" id="fone" class="formulario"
							value="${param.fone}"
							placeholder="Ex: xxxxxxxxxx ou (xx)xxxx-xxxx" maxlength="60" />
						<c:if test="${!empty msg[3]}">
					<strong style="color: red;"><c:out value="${msg[3]}"/></strong>
					 </c:if>
						<br> <label for="cel" class="formulario">*Celular:</label> <input
							type="text" name="cel" id="cel" class="formulario"
							value="${param.cel}"
							placeholder="Ex: xxxxxxxxxxx ou (xx)xxxxx-xxxx"
							required="required" maxlength="50" />
						<c:if test="${!empty msg[4]}">
					<strong style="color: red;"><c:out value="${msg[4]}"/></strong>
					 </c:if>
						<br> <label for="senha" class="formulario">*Senha:</label> <input
							type="password" name="senha" id="senha" class="formulario"
							placeholder="Entre 8 e 16 caracteres. Ex: (\s3nh4/)"
							required="required" maxlength="20" />
						<c:if test="${!empty msg[11]}">
					<strong style="color: red;"><c:out value="${msg[11]}"/></strong>
					 </c:if>
						<br> <label for="confirmaSenha" class="formulario">*Confirmar
							Senha:</label> <input type="password" name="confirmaSenha"
							id="confirmaSenha" class="formulario"
							placeholder="Confirme a senha inserida acima."
							required="required" maxlength="20" /> 
							<strong style="color: red;"><c:out value="${msg[12]}"/></strong><br> 
							<label for="cadastrar" class="formulario"></label>
							<input type="submit" value="Cadastrar" class="botao" id="cadastrar"> <br>
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
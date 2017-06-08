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
<title>Página de atualização de dados de acesso</title>
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
								<li><a href="${pageContext.request.contextPath}/ControllerServlet?acao=obterdadoscadastrais">Atualizar
										dados cadastrais</a></li>
								<li  id="ativo"><a href="${pageContext.request.contextPath}/alterarDadosAcesso.jsp">Alterar dados de acesso</a></li>
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
				<h3>${msgSistema=(empty msgSistema)?"Escolha a opção na caixa de seleção abaixo:":msgSistema}</h3>
				<fieldset>
					<legend>Alterar dados de acesso</legend>
					<%--Form para Selecionar qual alterar --%>					
					<form action="ControllerServlet?acao=alterar" method="post">					
					<label for="select" class="formulario">Selecionar:</label>
					<select name="dadoAcesso" class="selectOpt" id="select">
					<option selected hidden>Esconha o dado que deseja alterar</option>					
					<option value="login" class="optionOpt">Alterar o Login</option>
					<option value="senha" class="optionOpt">Alterar a Senha</option>				
					</select>			 	 				
					<input type="submit" value="Escolher" class="botao"> ${msgSelect}
					</form>
					
					<%--Form para Login --%>
					<c:if test="${!empty cliente.login}">					
					<form action="ControllerServlet?acao=alterarLogin" method="post">			
						<input type="hidden" name="updateLogin"/>		
						 <label for="login" class="formulario">Login:</label> 
						 <input type="text" name="login" id="login" class="apenas" 
						 value="${valor = cliente.login == null ? param.login : cliente.login}" 
						 placeholder="Entre 8 e 16 letras. Ex:meulogin" required="required" maxlength="100"/> 
							 <input type="submit" value="Alterar" name="login" class="botao" />
						${msgLogin}
					</form>
					</c:if>					
					<%--Form para Senha --%>														
					<c:if test="${!empty senha}">
					<form action="ControllerServlet?acao=alterarSenha" method="post">
					<input type="hidden" name="updateSenha"/>
					
					<label for="senhaAtual" class="formulario">Senha Atual:</label>
						<input type="password" name="senhaAtual" id="senhaAtual" class="formulario" 
						placeholder="Digite a senha atual."
						required="required" maxlength="20" />${msgSenhaAtual}		
					<br>
					<label for="senha" class="formulario">Nova Senha:</label>
						<input type="password" name="senha" id="senha" class="formulario" 
						placeholder="Nova senha entre 8 e 16 caracteres. Ex: (\s3nh4/)"
						required="required" maxlength="20" />${msgSenha}						 
						<br> 
						<label for="confirmaSenha" class="formulario">Confirmar
						Senha:</label> <input type="password" name="confirmaSenha" id="confirmaSenha" 
						class="formulario" placeholder="Confirme a senha inserida acima."
						required="required" maxlength="20" /> ${msgConfirmaSenha}
						<br> 
						<label for="alterar" class="formulario"></label>
						<input type="submit" value="Alterar" class="botao" id="alterar">
						</form>
					</c:if>					
				</fieldset>
			</div>
		</div>
		<footer id="footer">
			<p id="pf">&copy; Copyright 2016 by Marcos</p>
		</footer>
	</div>
</body>
</html>
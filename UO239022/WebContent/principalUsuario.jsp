<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>GTD Task Manager - Página principal del usuario</title>
</head>
<body>
	<i>Iniciaste sesión el <fmt:formatDate
			pattern="dd-MM-yyyy' a las 'HH:mm"
			value="${sessionScope.fechaInicioSesion}" /> (usuario número
		${contador})
	</i>
	<br />
	<br />
	<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
	<table>
		<tr>
			<td>Id:</td>
			<td id="id"><jsp:getProperty property="id" name="user" /></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td id="emailTD">
				<form action="modificarEmail">
					<input type="text" id="email" name="email" size="15"
						value="<jsp:getProperty property="email" name="user"/>">
					<button id="modificarEmail" type="submit" value="Modificar">Modificar</button>
				</form>
			</td>
		</tr>
		<tr>
			<td>Es administrador:</td>
			<td id="isAdmin"><jsp:getProperty property="isAdmin" name="user" /></td>
		</tr>
		<tr>
			<td>Login:</td>
			<td id="login"><jsp:getProperty property="login" name="user" /></td>
		</tr>
		<tr>
			<td>Estado:</td>
			<td id="status"><jsp:getProperty property="status" name="user" /></td>
		</tr>
		<tr>
			<td>Contraseña antigua:</td>
			<td id="contrasenaAntigua"><jsp:getProperty property="password"
					name="user" /></td>

		</tr>
	</table>
	<form action="modificarDatos" method="POST">
		<table>
			<tr>
				<td>Contraseña nueva:</td>
				<td id="contrasenaNuevaTD"><input type="password"
					id="contrasenaNueva" name="contrasenaNueva" size="15" name="user" /></td>
			</tr>
			<tr>
				<td>Repita contraseña:</td>
				<td id="contrasenaNuevaAgainTD"><input
					id="contrasenaNuevaAgain" type="password"
					name="contrasenaNuevaAgain" size="15">
					<button id="modificarContrasena" type="submit">Modificar</button></td>

			</tr>
		</table>
	</form>
	<br />
	<c:if test="${user.isAdmin==true}">
		<a id="listarUsuarios_link_id" href="listarUsuarios?orderBy=Login">Listar
			Usuarios</a>
	</c:if>
	<a href="cerrarSesion">
		<button id="cerrarSesion" class="btn btn-danger">Cerrar
			Sesion</button>
	</a>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>

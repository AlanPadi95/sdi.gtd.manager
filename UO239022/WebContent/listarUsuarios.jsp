<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="uo.sdi.dto.User"%>
<%@ page import="uo.sdi.dto.types.UserStatus"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>AdminManager - Listado de Usuario</title>
</head>
<body>
	<%
		String orderBy ="Login";
		if(request.getParameter("orderBy")!=null)
			orderBy = request.getParameter("orderBy");
	%>
	<form action="modificarStatus" method="post" name="listarUsarios_again">
		<table id="tablaListarUsuarios" border="1" align="center">
			<tr>
				<th><a href="listarUsuarios?orderBy=Login">Login</a></th>
				<th><a href="listarUsuarios?orderBy=Email">Email</a></th>
				<th><a href="listarUsuarios?orderBy=Status">Status</a></th>
				<th></th>
				<%
					List<User> listaUsuariosOrdenada = new LinkedList<User>();
					List<User> listaUsuariosAux = (List<User>) request.getAttribute("listaUsuarios");
					User pivote = null;
					while (listaUsuariosAux.size() != 0) {
						pivote = listaUsuariosAux.get(0);
						for (int i = 0; i < listaUsuariosAux.size() - 1; i++) {
							if (pivote.compareBy(listaUsuariosAux.get(i + 1), orderBy) > 0)
								pivote = listaUsuariosAux.get(i + 1);
						}
						listaUsuariosOrdenada.add(pivote);
						listaUsuariosAux.remove(pivote);
					}
					request.setAttribute("listaUsuarios", listaUsuariosOrdenada);
				%>
				<%-- System.out.println(orderBy); --%>

			</tr>
			<c:forEach var="entry" items="${listaUsuarios}" varStatus="i">
				<tr>
					<td>${entry.getLogin()}</td>
					<td>${entry.getEmail()}</td>
					<td >${entry.getStatus()}</td>

					<c:choose>
						<c:when test="${entry.getStatus()=='ENABLED'}">
							<td><input type="checkbox" id="checkbox${entry.getLogin()}" name="checkbox${entry.getLogin()}" checked> <br></td>
						</c:when>
						<c:otherwise>
							<td><input type="checkbox" name="checkbox${entry.getLogin()}" > <br></td>
						</c:otherwise>
					</c:choose>
					<!--<c:out value="checkbox${entry.getLogin()}"></c:out>-->

				</tr>

			</c:forEach>
			<tr>
				<td><input type="submit" value="Guardar cambios de status" /></td>
			</tr>


		</table>
	</form>
	
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
</html>
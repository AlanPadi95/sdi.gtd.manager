<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>GTD Task Manager - Listado de categorías</title>
</head>
<body>
	<div>
		<h3>Categorías</h3>
		<form action="gestionarCategoria">
			<button id="anadirCategoria" class="btn btn-danger">Añadir
				categoría</button>
		</form>
	</div>
	<table border="1" align=left>
		<tr>
			<th>Editar</th>
			<th>Nombre</th>
		</tr>
		<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
			<tr id="item_${i.index}">
				<c:choose>
					<c:when test="${entry.id>=0}">
						<td><a id="gestionarCategoria?${entry.name}"
							href="gestionarCategoria?${entry.id}">*</a></td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
				<td><a href="listarTareas?${entry.id}">${entry.name}</a></td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
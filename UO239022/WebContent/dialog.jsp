<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GTD Task Manager - Eliminar Categoría</title>
</head>
<body>
	<div align="center">
		<br> <br> <br>
		<p>¿Está seguro de que desea eliminar esta categoría?</p>
		<a href="eliminarCategoria?<%=request.getQueryString()%>">
			<button id="eliminarCategoria" class="btn btn-danger">Eliminar</button>
		</a> <a href="gestionarCategoria?<%=request.getQueryString()%>">
			<button id="cancelar" class="btn btn-danger">Cancelar</button>
		</a>
	</div>
</body>
</html>
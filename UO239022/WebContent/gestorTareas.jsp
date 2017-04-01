<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="comprobarNavegacion.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/gtdStyle.css" />
<title>GTD Task Manager - Gestionar tareas</title>
</head>
<body style="height: 5px;">
	<div>
		<form action="modificarDatos">
			<button id="principalUsuario" class="btn btn-danger">Ajustes</button>
		</form>
	</div>
	<div>
		<div class="box">
			<jsp:include page="listarCategorias.jsp" />
		</div>
		<div class="box">
			<div>
				<h3>Tareas</h3>
				<form action="anadirTarea">
					<label class="col-md-4 " >Nombre tarea nueva: </label>				
					<input type="text" name="nombreTareaNueva" size="15" />
					<button class="btn btn-danger">Añadir tarea</button>
				</form>
			</div>

			<table border="1" align="right">
				<tr>
					<th>Título</th>
					<th>Comentarios</th>
					<th>Fecha de creación</th>
					<th>Estimación de la finalización</th>
					<th>Fecha de finalización</th>
					<th>Marcar como finalizada</th>
				</tr>
				<<c:forEach var="entry" items="${listaTareas}" varStatus="i">
					
						<tr id="item_${i.index}">
							<td><a href="updateTarea?id=${entry.id}">${entry.title}</a></td>
							<td>${entry.comments}</td>
							<td>${entry.created}</td>
							<td>${entry.planned}</td>
							<td>${entry.finished}</td>
							<td><c:if test="${entry.finished==null}">
									<a href="markAsFinished?${entry.id}">Finalizar</a>
								</c:if></td>
					</tr>
					
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>
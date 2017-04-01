<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>GTD Task Manager - Iniciar Sesión</title>
<body>
	<form action="guardarCambiosTarea" method="post" >

		<center>
			<h1>Update tarea</h1>
		</center>
		<hr>
		<br>
		<input type="hidden" name="id" value="${tareaParaActualizar.id}" />
		<table border="1" align="right">
			<tr>
				<th>Título</th>
				<th>Comentarios</th>
				<th>Fecha de creación</th>
				<th>Estimación de la finalización</th>
				<th>Fecha de finalización</th>
				<th>Marcar como finalizada</th>
			</tr>
			<tr>
				<td><input type="text" name="newTitle" value="${tareaParaActualizar.title}"/></td>
				<td><input type="text" name="newComments" value="${tareaParaActualizar.comments}"/></td>
				<td>${tareaParaActualizar.created}</td>
				<td>
					<table>
						<tr>
							<td><label>Hora: </label>	<input type="text" name="newHour" value="${tareaParaActualizar.getPlanned().getHours()}"/></td>
							<td><label>Dia: </label>	<input type="text" name="newDay" value="${tareaParaActualizar.getPlanned().getDate()}"/></td>
							<td><label>Mes: </label>	<input type="text" name="newMonth" value="${tareaParaActualizar.getPlanned().getMonth()}"/></td>
							<td><label>Año: </label>	<input type="text" name="newYear" value="${tareaParaActualizar.getPlanned().getYear()+1900}"/></td>
						</tr>
					</table>
				</td>
				<td>${tareaParaActualizar.finished}</td>
				<td><button>Actualizar</button>
			</tr>
		</table>
	</form>


	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
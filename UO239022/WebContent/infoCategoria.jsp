<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GTD Task Manager - Categoria</title>
</head>
<body>
	<form
		action="${categoria==null?'anadirCategoria':'editarCategoria'}?${categoria.id}"
		method="post" class="form-horizontal">
		<fieldset>
			<div class="container">
				<br>
				<!-- Form Name -->
				<div class="panel-group">
					<div class="panel panel-primary">
						<div class="panel-heading">Categoria</div>
						<div class="panel-body">
							<!-- Appended Input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="email">Título</label>
								<div class="col-md-4">
									<div class="input-group">
										<c:choose>
											<c:when test="${id==null}">
												<input id="name" name="name" class="form-control"
													placeholder="Ej:Categoria 12" type="text" required>
											</c:when>
											<c:otherwise>
												<input id="name" name="name" class="form-control"
													value="${categoria.name}" placeholder="Ej:Categoria 12"
													type="text" required>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
		<div>
			<button id="guardar" class="btn btn-danger">Guardar</button>
		</div>
		<c:out value="${mensajeParaElUsuario}" />
	</form>
	<c:if test="${id!=null}">
		<a href="duplicarCategoria?${id}">
			<button class="btn btn-danger">Duplicar Categoría</button>
		</a>
		<a href="dialog.jsp?${id}">
			<button id="eliminarCategoria" class="btn btn-danger">Eliminar Categoría</button>
		</a>
	</c:if>

</body>
</html>
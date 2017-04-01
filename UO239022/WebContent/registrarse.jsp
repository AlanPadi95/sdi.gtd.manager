<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GTD Task Manager - Registrarse</title>
</head>
<body>
	<form name="registrarse_form" action="registrarse" method="post"
		class="form-horizontal">
		<fieldset>
			<div class="container" align="center">
				<br>
				<!-- Form Name -->
				<div class="panel-group">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3>Formulario de registro</h3>
						</div>
						<div class="panel-body">
							<!-- Appended Input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="email">Email</label>
								<div class="col-md-4">
									<div class="input-group">
										<input id="email" name="email" class="form-control"
											placeholder="Introduzca su email" type="text" required>
									</div>

								</div>
							</div>
							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="usuario">Usuario</label>
								<div class="col-md-4">
									<input id="login" name="login" type="text"
										placeholder="Introduzca su nombre de usuario"
										class="form-control input-md" required>

								</div>
							</div>

							<!-- Password input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="password">Password</label>
								<div class="col-md-4">
									<input id="pass1" name="pass1" type="password"
										placeholder="Introduzca su password"
										class="form-control input-md" required data-toggle="tooltip"
										data-placement="left" title="Tooltip on left">

								</div>
							</div>

							<!-- Password input-->
							<div class="form-group">
								<label class="col-md-4 control-label"
									for="passwordUsuarioRepetido">Repita su password </label>
								<div class="col-md-4">
									<input id="pass2" name="pass2" type="password"
										placeholder="Repita su password" class="form-control input-md"
										required>
								</div>
							</div>
							<br />
							<!-- Button (Double) -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="registrarse"></label>
								<div class="col-md-8">
									<input type="submit" id="registrarse" name="registrarse"
										class="btn btn-success" value="Registrarse">
									<button id="cancelar" name="cancelar" class="btn btn-danger"
										onclick="window.location.replace('login.jsp')">Cancelar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
		<c:out value="${mensajeParaElUsuario}" />
	</form>
</body>
</html>
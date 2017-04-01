<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>GTD Task Manager - Iniciar Sesión</title>
<body>
	<form id="validarse_form_name" action="validarse" method="post"
		name="validarse_form_name">

		<center>
			<h1>GTD Manager</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<td align="center">
					<div class="form-group">
						<label id="labelUser" class="col-md-4 control-label" for="usuario">Login</label>
						<div class="col-md-4">
							<input id="nombreUsuario" name="nombreUsuario" type="text"
								placeholder="Ej:menganito" class="form-control input-md"
								required>

						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<div class="form-group">
						<label id="labelPass" class="col-md-4 control-label"
							for="password">Contraseña</label>
						<div class="col-md-4">
							<input id="passwordUsuario" name="passwordUsuario"
								type="password" placeholder="Ej:1234"
								class="form-control input-md" required>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right"><button id="login" type="submit">Login</button></td>
				<td align="left"><a id="registro" href="registro">
						<button id="registro">Registrarse</button>
				</a>
			</tr>
		</table>
	</form>


	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
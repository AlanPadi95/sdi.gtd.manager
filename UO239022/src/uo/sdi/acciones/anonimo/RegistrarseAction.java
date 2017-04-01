package uo.sdi.acciones.anonimo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.persistence.Persistence;
import alb.util.log.Log;

public class RegistrarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String password1 = request.getParameter("pass1");
		String password2 = request.getParameter("pass2");

		User user = new User();
		user.setEmail(email);
		user.setIsAdmin(false);
		user.setLogin(login);
		user.setStatus(UserStatus.ENABLED);
		user.setPassword(password1);

		HttpSession session = request.getSession();

		try {
			if (session.getAttribute("user") == null) {
				User userLogin = Persistence.getUserDao().findByLogin(
						user.getLogin());
				if (userLogin == null) {
					if (compuebaEmail(email)) {
						if (compruebaPasswords(password1, password2, request)) {
							Persistence.getUserDao().save(user);
							synchronized (session) {
								session.setAttribute("user", user);
							}
							Log.debug("Creado usuario [%s]", user.getLogin());

						} else {
							resultado = "FRACASO";

						}
					} else {
						Log.error("El campo email no tiene una estructura de correo electrónico");
						synchronized (request.getServletContext()) {
							request.setAttribute("mensajeParaElUsuario",
									"El campo email no tiene una estructura de correo electrónico");
						}
						resultado = "FRACASO";
					}
				} else {
					Log.error("Ya existe un usuario con ese login");
					synchronized (request.getServletContext()) {
						request.setAttribute("mensajeParaElUsuario",
								"Ya existe un usuario con ese login");
					}
					resultado = "FRACASO";
				}
			} else {
				Log.error("Ya existe una sesión para el usuario [%s]",
						user.getLogin());
				resultado = "FRACASO";
			}
		} catch (Exception e) {
			Log.error("Ha ocurrido un error creando al usuario [%s]",
					user.getLogin());
		}
		return resultado;
	}

	private boolean compruebaPasswords(String password1, String password2,
			HttpServletRequest request) {
		if (password1.equals(password2)) {
			if (password1.length() >= 8
					&& password1.matches("[0-9]+[a-zA-Z]+||[a-zA-Z]+[0-9]+")) {

				return true;
			} else
				Log.error("Error en el formato de las contraseñas");
			synchronized (request.getServletContext()) {
				request.setAttribute("mensajeParaElUsuario",
						"Error en el formato de las contraseñas");
			}
		} else {
			Log.error("Las contraseñas no coinciden");
			synchronized (request.getServletContext()) {
				request.setAttribute("mensajeParaElUsuario",
						"Las contraseñas no coinciden");
			}
		}
		return false;
	}

	private boolean compuebaEmail(String email) {
		if (email.contains("@") && email.endsWith(".com"))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return getClass().getName();

	}

}

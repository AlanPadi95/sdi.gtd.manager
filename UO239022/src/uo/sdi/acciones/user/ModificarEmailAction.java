package uo.sdi.acciones.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class ModificarEmailAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		String nuevoEmail = request.getParameter("email");
		//
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));

		try {
			User userClone = Cloner.clone(user);
			UserService userService = Services.getUserService();
			synchronized (session) {
				session.setAttribute("user", userClone);
			}

			if (nuevoEmail != null && nuevoEmail.contains("@")
					&& nuevoEmail.endsWith(".com")) {
				userClone.setEmail(nuevoEmail);
				userService = Services.getUserService();
				userService.updateUserDetails(userClone);
				Log.debug("Modificado email de [%s] con el valor [%s]",
						userClone.getLogin(), nuevoEmail);
				synchronized (session) {
					session.setAttribute("user", userClone);
				}
			} else {
				Log.debug("No se han cambiado las contraseñas");
			}
		} catch (Exception b) {// formato invalido de email, son
								// excepciones que causa el usuario. El
								// codigo esta bien
			Log.debug(
					"Algo ha ocurrido actualizando el email de [%s] a [%s]: %s, contraseña nueva no es igual en los dos campos",
					user.getLogin(), nuevoEmail, b.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}

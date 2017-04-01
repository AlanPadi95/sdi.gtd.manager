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

public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		String contrasenaNuevaAgain = request
				.getParameter("contrasenaNuevaAgain");
		String contrasenaNueva = request.getParameter("contrasenaNueva");
		//
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		User userClone = Cloner.clone(user);
		// tarea 3

		try {
			UserService userService = Services.getUserService();
			userService.updateUserDetails(userClone);
			synchronized (session) {
				session.setAttribute("user", userClone);
			}

			if (contrasenaNuevaAgain.equals(contrasenaNueva)
					&& contrasenaNueva != null && contrasenaNuevaAgain != null) {
				userClone.setPassword(contrasenaNueva);
				userService = Services.getUserService();
				userService.updateUserDetails(userClone);
				Log.debug("Modificado contraseÃ±a de [%s] con el valor [%s]",
						userClone.getPassword(), contrasenaNueva);
				synchronized (session) {
					session.setAttribute("user", userClone);
				}
			} else {
				Log.debug("No se han cambiado las contraseÃ±as");
			}
		} catch (Exception b) {// formato invalido de email, son
								// excepciones que causa el usuario. El
								// codigo esta bien
			Log.debug(
					"Algo ha ocurrido actualizando la contraseÃ±a de [%s] a [%s]: %s, contraseÃ±a nueva no es igual en los dos campos",
					user.getLogin(), contrasenaNueva, b.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}

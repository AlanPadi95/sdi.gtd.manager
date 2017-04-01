package uo.sdi.acciones.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import alb.util.log.Log;

public class CerrarSesionAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			Log.info("El usuario no ha iniciado sesión todavía");
			resultado = "FRACASO";
		} else {
			synchronized (session) {
				session.invalidate();
			}
		}

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}

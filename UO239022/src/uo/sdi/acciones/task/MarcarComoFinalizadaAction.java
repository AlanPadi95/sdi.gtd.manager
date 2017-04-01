package uo.sdi.acciones.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.anonimo.ValidarseAction;
import uo.sdi.business.Services;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class MarcarComoFinalizadaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		String stringId = request.getQueryString();
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
		List<Task> listaTareas = null;

		if (usuario == null) {
			Log.error("No existeun usuario en sesión");
			resultado = "FRACASO";
		}
		try {
			Long id = Long.parseLong(stringId);
			Services.getTaskService().markTaskAsFinished(id);
			Log.debug("Marcando tarea como finalizada");
			listaTareas = Services.getTaskService().findInboxTasksByUserId(
					usuario.getId());
		} catch (Exception e) {
			Log.debug(
					"Algo ha ocurrido actualizando la tarea a finalizada: %s",
					e.getMessage());
			resultado = "FRACASO";
		}

		synchronized (request) {
			request.setAttribute("listaCategorias",
					ValidarseAction.getCategory(usuario.getId()));

			request.setAttribute("listaTareas", listaTareas);
		}

		return resultado;
	}

	public String toString() {
		return getClass().getName();
	}
}

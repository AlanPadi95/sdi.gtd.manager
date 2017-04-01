package uo.sdi.acciones.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.date.DateUtil;
import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.acciones.anonimo.ValidarseAction;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;

public class AnadirTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Task tarea = new Task();
		
		try {
			String nombreTareaNueva = request.getParameter("nombreTareaNueva");
			tarea.setTitle(nombreTareaNueva);
			tarea.setUserId(user.getId());
			Services.getTaskService().createTask(tarea);
		} catch (BusinessException e) {
			Log.error("No se ha podido crear la tarea [%s]: [%s]",
					tarea.getTitle(), e.getMessage());
		}

		synchronized (request) {
			request.setAttribute("listaCategorias",
					ValidarseAction.getCategory(user.getId()));

			try {
				request.setAttribute("listaTareas", Services.getTaskService()
						.findInboxTasksByUserId(user.getId()));
			} catch (BusinessException e) {
				Log.error(e.getMessage());
				resultado = "FRACASO";
			}
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}

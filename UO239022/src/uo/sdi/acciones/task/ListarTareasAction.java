package uo.sdi.acciones.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.anonimo.ValidarseAction;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarTareasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		HttpSession session = request.getSession();
		if (session != null) {
			String stringId = request.getQueryString();
			Long id = Long.parseLong(stringId);
			Category categoria = null;
			try {
				categoria = Services.getTaskService().findCategoryById(id);
			} catch (BusinessException e1) {
				Log.error(e1.getMessage());
			}
			User user = (User) session.getAttribute("user");
			synchronized (request) {
				request.setAttribute("idCategoria", id);
				request.setAttribute("listaCategorias",
						ValidarseAction.getCategory(user.getId()));
			}
			if (id == -1) {
				resultado = new ListarTareasInboxAction().execute(request,
						response);
			} else if (id == -2) {
				resultado = new ListarTareasHoyAction().execute(request,
						response);
			} else if (id == -3) {
				resultado = new ListarTareasSemanaAction().execute(request,
						response);

			} else {
				if (categoria != null) {
					List<Task> listaTareas = null;
					try {
						listaTareas = Services.getTaskService()
								.findTasksByCategoryId(categoria.getId());
					} catch (BusinessException e) {
						Log.error(e.getMessage());
					}
					synchronized (request.getServletContext()) {
						request.setAttribute("listaTareas", listaTareas);
					}
				} else {
					Log.error("No existe categoría con id [%d]", id);
					resultado = "FRACASO";
				}
			}
		} else {
			Log.error("No hay una sesión de usuario");
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}

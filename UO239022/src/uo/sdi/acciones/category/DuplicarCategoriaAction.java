package uo.sdi.acciones.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.acciones.anonimo.ValidarseAction;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;

public class DuplicarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long id = Long.parseLong(request.getQueryString());
		try {
			Services.getTaskService().duplicateCategory(id);
		} catch (BusinessException e) {
			Log.error(e.getMessage());
			resultado = "FRACASO";
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

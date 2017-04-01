package uo.sdi.acciones.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.anonimo.ValidarseAction;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class EditarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String name = request.getParameter("name");
		String idString = request.getQueryString();

		try {
			if (idString != null) {
				Long id = Long.parseLong(idString);
				Category categoria = Services.getTaskService()
						.findCategoryById(id);
				categoria.setName(name);
				Services.getTaskService().updateCategory(categoria);
			} else {
				Log.error("No hay una categoría seleccionada");
				resultado = "FRACASO";
			}
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

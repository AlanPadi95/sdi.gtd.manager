package uo.sdi.acciones.category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarCategoriasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		List<Category> listaCategorias;

		try {
			HttpSession session = request.getSession();
			if (session != null) {
				TaskService taskService = Services.getTaskService();
				User usuario = (User) session.getAttribute("user");
				listaCategorias = taskService.findCategoriesByUserId(usuario
						.getId());
				synchronized (request.getServletContext()) {
					request.setAttribute("listaCategorias", listaCategorias);
				}
				Log.debug(
						"Obtenida lista de categorías conteniendo [%d] categorías",
						listaCategorias.size());
			} else {
				Log.info("No hay un usuario en sesión");
			}
		} catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de categorías: %s",
					b.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}

package uo.sdi.acciones.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import alb.util.log.Log;

public class GestionarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		String idString = request.getQueryString();
		request.setAttribute("id", idString);
		Category category = null;

		if (idString == null) {
			Log.debug("Se debe ejecutar la acción añadir categoría");
		} else {
			try {
				Long id = Long.parseLong(idString);
				Log.debug("Se debe ejecutar la acción editar categoría");
				category = Services.getTaskService().findCategoryById(id);
			} catch (BusinessException e) {
				Log.error(e.getMessage());
			}

			request.setAttribute("categoria", category);

		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
}

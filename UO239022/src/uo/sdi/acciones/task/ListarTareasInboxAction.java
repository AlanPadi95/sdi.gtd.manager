package uo.sdi.acciones.task;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;

public class ListarTareasInboxAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		List<Task> listaTareas = new ArrayList<Task>();
		try {
			HttpSession session = request.getSession();
			if (session != null) {
				TaskService taskService = Services.getTaskService();
				User usuario = (User) session.getAttribute("user");
				listaTareas = taskService.findInboxTasksByUserId(usuario.getId());
				synchronized (request.getServletContext()) {
					request.setAttribute("listaTareas", listaTareas);
				}
				Log.debug("Obtenida lista de tareas conteniendo [%d] tareas",
						listaTareas.size());
			} else {
				Log.info("No hay un usuario en sesión");
				resultado = "FRACASO";

			}
		} catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas: %s",
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

package uo.sdi.acciones.task;



import java.util.Date;

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

public class GuardarCambiosTareaAction implements Accion {

	@SuppressWarnings("deprecation")
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Task tarea = new Task();
		
		try {
			
			String id = request.getParameter("id");
			String title = request.getParameter("newTitle");
			String comments = request.getParameter("newComments");
			String shoraNueva = request.getParameter("newHour");
			String sdiaNuevo = request.getParameter("newDay");
			String smesNuevo = request.getParameter("newMonth");
			String sanoNuevo = request.getParameter("newYear");
			
			tarea = Services.getTaskService().findTaskById(Long.parseLong(id));
						
			tarea.setTitle(title);
			tarea.setComments(comments);
			try{
				int horaNueva = Integer.parseInt(shoraNueva);
				int diaNuevo = Integer.parseInt(sdiaNuevo);
				int mesNuevo = Integer.parseInt(smesNuevo);
				int anoNuevo = Integer.parseInt(sanoNuevo);
				if(shoraNueva.equals("") || sdiaNuevo.equals("") || smesNuevo.equals("") ||sanoNuevo.equals(""))
						tarea.setPlanned(null);
				else if(anoNuevo<=0 || mesNuevo<=0 || mesNuevo>12 ||diaNuevo<=0 || diaNuevo>31 || horaNueva<0 || horaNueva>23)
					;
				else{
					Date nuevoDate = DateUtil.today();
					nuevoDate.setYear(anoNuevo-1900);
					nuevoDate.setMonth(mesNuevo);
					nuevoDate.setDate(diaNuevo);
					nuevoDate.setHours(horaNueva);
					
					tarea.setPlanned(nuevoDate);
					Services.getTaskService().updateTask(tarea);
				}
			}catch(NumberFormatException e){
				Log.error(e.getMessage() +" error formato numeros. Hora, dia, mes y año son numeros enteros positivos en los limites EJ para mes <=12");
				resultado = "FRACASO";
			}catch(Exception e){
				Log.error(e.getMessage() +" error formato nueva fecha planeada");
				resultado = "FRACASO";
			}
			
		} catch (BusinessException e) {
			Log.error("No se ha podido actualizar la tarea [%s]: [%s]",
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

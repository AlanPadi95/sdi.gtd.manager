package uo.sdi.acciones.user.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class ModificarStatusAction implements Accion {

	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<User> listaUsuarios;
		
		
		try {
			AdminService adminService = Services.getAdminService();
			listaUsuarios=adminService.findAllUsers();
			int contador =0;
			for(int i=0;i<listaUsuarios.size();i++){
				String nombreParameter= "checkbox"+listaUsuarios.get(i).getId();
				User usuarioActual = listaUsuarios.get(i);
				Object parametroActual = request.getParameter(nombreParameter);
				if(request.getParameter(nombreParameter)!=null){
					adminService.enableUser(usuarioActual.getId());
					contador++;
				}else{
					adminService.disableUser(usuarioActual.getId());
				}
			}
			
			listaUsuarios=adminService.findAllUsers();
			request.setAttribute("listaUsuarios", listaUsuarios);
			Log.debug("Actualizada lista de usuarios conteniendo [%d] usuarios con status enabled", 
					contador);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de usuarios: %s",
					b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}

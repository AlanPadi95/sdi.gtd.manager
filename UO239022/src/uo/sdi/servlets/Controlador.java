package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.AseguraNavegacionAction;
import uo.sdi.acciones.anonimo.RegistrarseAction;
import uo.sdi.acciones.anonimo.ValidarseAction;
import uo.sdi.acciones.category.AnadirCategoriaAction;
import uo.sdi.acciones.category.DuplicarCategoriaAction;
import uo.sdi.acciones.category.EditarCategoriaAction;
import uo.sdi.acciones.category.EliminarCategoriaAction;
import uo.sdi.acciones.category.GestionarCategoriaAction;
import uo.sdi.acciones.category.ListarCategoriasAction;
import uo.sdi.acciones.task.AnadirTareaAction;
import uo.sdi.acciones.task.GuardarCambiosTareaAction;
import uo.sdi.acciones.task.ListarTareasAction;
import uo.sdi.acciones.task.MarcarComoFinalizadaAction;
import uo.sdi.acciones.task.UpdateTareaAction;
import uo.sdi.acciones.user.CerrarSesionAction;
import uo.sdi.acciones.user.ModificarDatosAction;
import uo.sdi.acciones.user.ModificarEmailAction;
import uo.sdi.acciones.user.admin.ListarUsuariosAction;
import uo.sdi.acciones.user.admin.ModificarStatusAction;
import uo.sdi.dto.User;
import uo.sdi.persistence.PersistenceException;
import alb.util.log.Log;

public class Controlador extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion,
																// objeto
																// Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; // <rol,
																			// <opcion,
																			// <resultado,
																			// JSP>>>

	public void init() throws ServletException {
		crearMapaAcciones();
		crearMapaDeNavegacion();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String accionNavegadorUsuario, resultado, jspSiguiente;
		Accion objetoAccion;
		String rolAntes, rolDespues;

		try {
			accionNavegadorUsuario = request.getServletPath().replace("/", ""); // Obtener
																				// el
																				// string
																				// que
																				// hay
																				// a
																				// la
																				// derecha
																				// de
																				// la
																				// última
																				// /

			rolAntes = obtenerRolDeSesion(request);

			objetoAccion = buscarObjetoAccionParaAccionNavegador(rolAntes,
					accionNavegadorUsuario);

			request.removeAttribute("mensajeParaElUsuario");

			resultado = objetoAccion.execute(request, response);

			rolDespues = obtenerRolDeSesion(request);

			jspSiguiente = buscarJSPEnMapaNavegacionSegun(rolDespues,
					accionNavegadorUsuario, resultado);

			request.setAttribute("jspSiguiente", jspSiguiente);

		} catch (PersistenceException e) {

			request.getSession().invalidate();

			Log.error(
					"Se ha producido alguna excepción relacionada con la persistencia [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente = "/login.jsp";

		} catch (Exception e) {

			request.getSession().invalidate();

			Log.error("Se ha producido alguna excepción no manejada [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente = "/login.jsp";
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspSiguiente);

		dispatcher.forward(request, response);
	}

	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion = req.getSession();
		if (sesion.getAttribute("user") == null)
			return "ANONIMO";
		else if (((User) sesion.getAttribute("user")).getIsAdmin())
			return "ADMIN";
		else
			return "USUARIO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol,
			String opcion) {

		Accion accion = mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]", accion,
				opcion, rol);
		return accion;
	}

	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion,
			String resultado) {

		String jspSiguiente = mapaDeNavegacion.get(rol).get(opcion)
				.get(resultado);
		Log.debug(
				"Elegida página siguiente [%s] para el resultado [%s] tras realizar [%s] con rol [%s]",
				jspSiguiente, resultado, opcion, rol);
		return jspSiguiente;
	}

	private void crearMapaAcciones() {

		mapaDeAcciones = new HashMap<String, Map<String, Accion>>();

		Map<String, Accion> mapaPublico = new HashMap<String, Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("registrarse", new RegistrarseAction());
		mapaPublico.put("registro", new AseguraNavegacionAction());
		mapaPublico.put("cerrarSesion", new CerrarSesionAction());
		mapaDeAcciones.put("ANONIMO", mapaPublico);

		Map<String, Accion> mapaRegistradoUsuario = new HashMap<String, Accion>();
		mapaRegistradoUsuario.put("registrarse", new RegistrarseAction());
		mapaRegistradoUsuario.put("listarTareas", new ListarTareasAction());
		mapaRegistradoUsuario.put("listarCategorias",
				new ListarCategoriasAction());
		mapaRegistradoUsuario.put("modificarDatos", new ModificarDatosAction());
		mapaRegistradoUsuario.put("modificarEmail", new ModificarEmailAction());
		mapaRegistradoUsuario.put("cerrarSesion", new CerrarSesionAction());
		mapaRegistradoUsuario.put("updateTarea", new UpdateTareaAction());
		mapaRegistradoUsuario.put("gestionarCategoria",
				new GestionarCategoriaAction());
		mapaRegistradoUsuario.put("markAsFinished",
				new MarcarComoFinalizadaAction());
		mapaRegistradoUsuario.put("anadirCategoria",
				new AnadirCategoriaAction());
		mapaRegistradoUsuario.put("eliminarCategoria",
				new EliminarCategoriaAction());
		mapaRegistradoUsuario.put("editarCategoria",
				new EditarCategoriaAction());
		mapaRegistradoUsuario.put("duplicarCategoria",
				new DuplicarCategoriaAction());
		mapaRegistradoUsuario.put("anadirTarea", new AnadirTareaAction());
		mapaRegistradoUsuario.put("guardarCambiosTarea",
				new GuardarCambiosTareaAction());
		mapaDeAcciones.put("USUARIO", mapaRegistradoUsuario);

		Map<String, Accion> mapaRegistradoAdmin = new HashMap<String, Accion>();
		mapaRegistradoAdmin.put("validarse", new ValidarseAction());
		mapaRegistradoAdmin.put("modificarDatos", new ModificarDatosAction());
		mapaRegistradoAdmin.put("modificarStatus", new ModificarStatusAction());
		mapaRegistradoAdmin.put("modificarDatos", new ModificarDatosAction());
		mapaRegistradoAdmin.put("cerrarSesion", new CerrarSesionAction());
		mapaRegistradoAdmin.put("listarUsuarios", new ListarUsuariosAction());
		mapaDeAcciones.put("ADMIN", mapaRegistradoAdmin);
	}

	private void crearMapaDeNavegacion() {

		mapaDeNavegacion = new HashMap<String, Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de anónimo
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/registrarse.jsp");
		// Esta acción nunca fracasará
		opcionResultadoYJSP.put("registro", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("FRACASO", "/registrarse.jsp");
		opcionResultadoYJSP.put("registrarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);

		mapaDeNavegacion.put("ANONIMO", opcionResultadoYJSP);

		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de usuarios normales
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("registrarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarEmail", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		resultadoYJSP.put("FRACASO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("listarTareas", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/infoCategoria.jsp");
		resultadoYJSP.put("FRACASO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("gestionarCategoria", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/infoTarea.jsp");
		resultadoYJSP.put("FRACASO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("gestionarTarea", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		resultadoYJSP.put("FRACASO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("markAsFinished", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("anadirCategoria", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("eliminarCategoria", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("duplicarCategoria", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("anadirTarea", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/updateTarea.jsp");
		opcionResultadoYJSP.put("updateTarea", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		resultadoYJSP.put("FRACASO", "/gestorTareas.jsp");
		opcionResultadoYJSP.put("guardarCambiosTarea", resultadoYJSP);

		mapaDeNavegacion.put("USUARIO", opcionResultadoYJSP);

		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación del administrador
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/gestorTareas.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarEmail", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarUsuarios", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);

		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		// resultadoYJSP.put("FRACASO","/listarUsuarios.jsp");
		opcionResultadoYJSP.put("modificarStatus", resultadoYJSP);

		mapaDeNavegacion.put("ADMIN", opcionResultadoYJSP);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}
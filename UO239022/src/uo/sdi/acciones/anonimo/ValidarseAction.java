package uo.sdi.acciones.anonimo;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ValidarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		String nombreUsuario = request.getParameter("nombreUsuario");
		String passwordUsuario = request.getParameter("passwordUsuario");
		HttpSession session = request.getSession();
		User userByLogin = null;
		// int contador;

		if (session.getAttribute("user") == null) {
			UserService userService = Services.getUserService();
			try {
				userByLogin = userService.findLoggableUser(nombreUsuario,
						passwordUsuario);
			} catch (BusinessException b) {
				session.invalidate();
				Log.debug(
						"Algo ha ocurrido intentando iniciar sesión [%s]: %s",
						nombreUsuario, b.getMessage());
				request.setAttribute("mensajeParaElUsuario", b.getMessage());
				resultado = "FRACASO";
			}
			if (userByLogin != null) {
				synchronized (session) {
					session.setAttribute("user", userByLogin);
					session.setAttribute("fechaInicioSesion",
							new java.util.Date());

				}

				// synchronized (request.getServletContext()) {

				// contador = Integer.parseInt((String) request
				// .getServletContext().getAttribute("contador"));
				// request.getServletContext().setAttribute("contador",
				// String.valueOf(contador + 1));
				// }
				Log.info("El usuario [%s] ha iniciado sesión", nombreUsuario);
			} else {
				synchronized (session) {
					session.invalidate();
				}

				Log.info("El usuario [%s] no está registrado", nombreUsuario);
				synchronized (request.getServletContext()) {
					request.setAttribute("mensajeParaElUsuario", "El usuario ["
							+ nombreUsuario + "] no está registrado");
				}

				resultado = "FRACASO";
			}
		} else if (!nombreUsuario.equals(session.getAttribute("user"))) {
			Log.info(
					"Se ha intentado iniciar sesión como [%s] teniendo la sesión iniciada como [%s]",
					nombreUsuario,
					((User) session.getAttribute("user")).getLogin());
			synchronized (request.getServletContext()) {
				request.setAttribute(
						"mensajeParaElUsuario",
						"Se ha intentado iniciar sesión como ["
								+ nombreUsuario
								+ "] teniendo la sesión iniciada como ["
								+ ((User) session.getAttribute("user"))
										.getLogin() + "]");
			}
			synchronized (session) {
				session.invalidate();

			}
			resultado = "FRACASO";
		}
		synchronized (request) {
			request.setAttribute("listaCategorias",
					getCategory(userByLogin.getId()));

			try {
				request.setAttribute("listaTareas", Services.getTaskService()
						.findInboxTasksByUserId(userByLogin.getId()));
			} catch (BusinessException e) {
				Log.error(e.getMessage());
			}
		}

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

	/**
	 * Obtiene las categorías y pseudo categorías del usuario
	 * 
	 * @param userId
	 *            El ID del usuario
	 * @return La lista de categorías y pseudo categorías del usuario
	 */
	public static List<Category> getCategory(Long userId) {
		Category inbox = new Category();
		inbox.setId(Long.parseLong("-1"));
		inbox.setName("Inbox");
		inbox.setUserId(userId);
		Category hoy = new Category();
		hoy.setId(Long.parseLong("-2"));
		hoy.setName("Hoy");
		hoy.setUserId(userId);
		Category semana = new Category();
		semana.setId(Long.parseLong("-3"));
		semana.setName("Semana");
		semana.setUserId(userId);
		List<Category> listCategory = null;
		try {
			listCategory = Services.getTaskService().findCategoriesByUserId(
					userId);
		} catch (BusinessException e) {
			Log.error(e.getMessage());
		}
		listCategory.add(semana);
		listCategory.add(hoy);
		listCategory.add(inbox);
		Collections.reverse(listCategory);
		return listCategory;
	}

}

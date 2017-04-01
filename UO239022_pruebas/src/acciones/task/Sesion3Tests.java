package acciones.task;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class Sesion3Tests {

	private WebTester admin;

	@Before
	public void prepare() {
		admin = new WebTester();
		admin.setBaseUrl("http://localhost:8280/UO239022/");
	}

	@Test
	public void testCambiarStatusUsuario() {
		admin.beginAt("/"); // Navegar a la URL
		// admin.assertFormPresent("validarse_form_name"); // Comprobar
		// formulario está presente
		admin.setTextField("nombreUsuario", "administrador1"); // Rellenar
																// primer campo
																// de formulario
		admin.setTextField("passwordUsuario", "administrador1");
		admin.submit(); // Enviar formulario
		admin.clickLink("listarUsuarios_link_id"); // Seguir el hipervínculo
		admin.assertTitleEquals("AdminManager - Listado de Usuario"); // Comprobar
																		// título
																		// de la
																		// página
		Table tabla = admin.getTable("tablaListarUsuarios");

		List<Row> filas = tabla.getRows();
		String statusActual = "";
		for (int i = 1; i < filas.size(); i++) {
			List<Cell> casillas = filas.get(i).getCells();
			if (casillas.get(0).getValue().equals("mary"))
				statusActual = casillas.get(2).getValue();

		}
		if (statusActual.equals("DISABLED"))
			admin.checkCheckbox("checkboxmary");// selecciona un checkbox, id
												// mary es 1
		else
			admin.uncheckCheckbox("checkboxmary");

		for (int i = 1; i < filas.size(); i++) {// comprobacion de que ha
												// cambiado la celda de status
			List<Cell> casillas = filas.get(i).getCells();
			if (casillas.get(0).getValue().equals("mary")) {
				if (statusActual.equals("ENABLED"))
					assertEquals(casillas.get(2).getValue(), "DISABLED");
				else
					assertEquals(casillas.get(2).getValue(), "ENABLED");
			}
		}

		admin.clickLink("volver"); // Seguir el hipervínculo
		admin.assertTitleEquals("GTD Task Manager - Página principal del usuario"); // vuelve
																					// a
																					// principalUsuario.jsp
		admin.submit(); // Enviar formulario
		admin.assertTitleEquals("GTD Task Manager - Iniciar Sesión"); // vuelve
																		// a
																		// login.jsp

	}
	//
	//
	//
	// @Test
	// public void testListarCategorias() {
	// admin.beginAt("/"); // Navegar a la URL
	// admin.assertLinkPresent("listarCategorias_link_id"); // Comprobar que
	// existe el hipervínculo
	// admin.clickLink("listarCategorias_link_id"); // Seguir el hipervínculo
	//
	// admin.assertTitleEquals("TaskManager - Listado de categorías"); //
	// Comprobar título de la página
	//
	// // La base de datos contiene 7 categorías tal y como se entrega
	// int i=0;
	// for (i=0;i<7;i++)
	// admin.assertElementPresent("item_"+i); // Comprobar elementos presentes
	// en la página
	// admin.assertElementNotPresent("item_"+i);
	// }
	//
	// @Test
	// public void testIniciarSesionConExito() {
	// admin.beginAt("/"); // Navegar a la URL
	// admin.assertFormPresent("validarse_form_name"); // Comprobar formulario
	// está presente
	// admin.setTextField("nombreUsuario", "admin"); // Rellenar primer campo de
	// formulario
	// admin.submit(); // Enviar formulario
	// admin.assertTitleEquals("TaskManager - Página principal del usuario"); //
	// Comprobar título de la página
	// admin.assertTextInElement("login", "admin"); // Comprobar cierto elemento
	// contiene cierto texto
	// admin.assertTextInElement("id", "2"); // Comprobar cierto elemento
	// contiene cierto texto
	// admin.assertTextPresent("Iniciaste sesión el"); // Comprobar cierto texto
	// está presente
	// }
	//
	//
	// @Test
	// public void testIniciarSesionSinExito() {
	// WebTester browser=new WebTester();
	// browser.setBaseUrl("http://localhost:8280/sesion3.MVCCasero");
	// browser.beginAt("/"); // Navegar a la URL
	// browser.setTextField("nombreUsuario", "yoNoExisto"); // Rellenar primer
	// campo de formulario
	// browser.submit(); // Enviar formulario
	// browser.assertTitleEquals("TaskManager - Inicie sesión"); // Comprobar
	// título de la página
	// }

}
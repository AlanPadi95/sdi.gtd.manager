package acciones.anonimo;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import org.junit.Before;
import org.junit.Test;

public class ValidarseTest {
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8280/UO239022");
		// Voy a la página de inicio
		beginAt("/");

	}

	@Test
	public void testInicio() {
		// Compruebo que el título es el correcto
		assertTitleEquals("GTD Task Manager - Iniciar Sesión");
		// Compruebo que se muestra el formulario de validación
		assertFormPresent("validarse_form_name");
		// Compruebo que los campos del login y la password estén presentes
		assertLabelPresent("labelUser");
		assertLabelPresent("labelPass");
		assertElementPresent("nombreUsuario");
		assertElementPresent("passwordUsuario");
		// Compruebo que se muestra el botón de login
		assertButtonPresent("login");
		// Compruebo que se muestra el enlace la opción de registrarse
		assertLinkPresent("registro");

	}

	@Test
	public void testIsEmpty() {
		// Compruebo que los campos están vacíos
		assertTextInElement("nombreUsuario", "");
		assertTextInElement("passwordUsuario", "");
	}

	@Test
	public void testValidarseUsuarioExito() {
		// Relleno los campos de login y password
		setTextField("nombreUsuario", "usuario1");
		setTextField("passwordUsuario", "usuario1");
		// El usuario1 se loguea
		clickButton("login");
		// Comprobar título de la página
		assertTitleEquals("GTD Task Manager - Gestionar tareas");
		// Compruebo que el título es el correcto
		assertTitleNotEquals("GTD Task Manager - Iniciar Sesión");
		// Compruebo que no se muestra el formulario de validación del login
		assertFormNotPresent("validarse_form_name");
		// Compruebo que los campos del login y la password no estén presentes
		assertElementNotPresent("nombreUsuario");
		assertElementNotPresent("passwordUsuario");
		// Compruebo que no se muestra el botón de login
		assertButtonNotPresent("login");
		// Compruebo que se muestra el enlace la opción de registrarse
		assertLinkNotPresent("registro");

	}

	@Test
	public void testValidarseAdminExito() {
		// Relleno los campos de login y password
		setTextField("nombreUsuario", "administrador1");
		setTextField("passwordUsuario", "administrador1");
		// El usuario1 se loguea
		clickButton("login");
		// Comprobar título de la página
		assertTitleEquals("GTD Task Manager - Página principal del usuario");
		// Compruebo que el título es el correcto
		assertTitleNotEquals("GTD Task Manager - Iniciar Sesión");
		// Compruebo que no se muestra el formulario de validación del login
		assertFormNotPresent("validarse_form_name");
		// Compruebo que los campos del login y la password no estén presentes
		assertElementNotPresent("nombreUsuario");
		assertElementNotPresent("passwordUsuario");
		// Compruebo que no se muestra el botón de login
		assertButtonNotPresent("login");
		// Compruebo que se muestra el enlace la opción de registrarse
		assertLinkNotPresent("registro");
	}

	@Test
	public void testValidarseFracaso() {
		// Relleno los campos de login y password
		setTextField("nombreUsuario", "noVálido");
		setTextField("passwordUsuario", "noVálido");
		// El usuario1 se loguea
		clickButton("login");
		// Compruebo que el título es el correcto
		assertTitleEquals("GTD Task Manager - Iniciar Sesión");
		// Compruebo que se muestra el formulario de validación
		assertFormPresent("validarse_form_name");
		// Compruebo que los campos del login y la password estén presentes
		assertLabelPresent("labelUser");
		assertLabelPresent("labelPass");
		assertElementPresent("nombreUsuario");
		assertElementPresent("passwordUsuario");
		// Compruebo que se muestra el botón de login
		assertButtonPresent("login");
		// Compruebo que se muestra el enlace la opción de registrarse
		assertLinkPresent("registro");
		// Compruebo que los campos están vacíos
		assertTextInElement("nombreUsuario", "");
		assertTextInElement("passwordUsuario", "");
	}

	@Test
	public void testRegistro() {
		clickLink("registro");
		// Compruebo que se ha ido a la pantalla de registro
		assertTitleEquals("GTD Task Manager - Registrarse");

	}

}

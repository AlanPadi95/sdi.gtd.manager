package acciones.user;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.eclipse.jetty.util.log.Log;
import org.junit.*;

import acciones.anonimo.ValidarseTest;

public class ModificarContraseñaTest {

	private ValidarseTest validarse = new ValidarseTest();

	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8280/UO239022");
		beginAt("/");
		validarse.testValidarseUsuarioExito();
		clickButton("principalUsuario");
	}

	@Test
	public void testInicio() {
		assertTitleEquals("GTD Task Manager - Página principal del usuario");
		assertTextFieldEquals("contrasenaNueva", "");
		assertTextFieldEquals("contrasenaNuevaAgain", "");
		assertTextInElement("contrasenaAntigua", "usuario1");
	}

	@Test
	public void testCambiarContraseñaExito() {
		setTextField("contrasenaNueva", "usuario11");
		setTextField("contrasenaNuevaAgain", "usuario11");
		clickButton("modificarContrasena");
		assertTitleEquals("GTD Task Manager - Gestionar tareas");
		clickButton("principalUsuario");
		assertTextFieldEquals("contrasenaNueva", "");
		assertTextFieldEquals("contrasenaNuevaAgain", "");
		assertTextInElement("contrasenaAntigua", "usuario11");
		setTextField("contrasenaNueva", "usuario1");
		setTextField("contrasenaNuevaAgain", "usuario1");
		clickButton("modificarContrasena");
		assertTitleEquals("GTD Task Manager - Gestionar tareas");
		clickButton("principalUsuario");
		assertTextInElement("contrasenaAntigua", "usuario1");
	}
}

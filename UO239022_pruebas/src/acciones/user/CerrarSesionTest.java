package acciones.user;

import org.junit.*;

import acciones.anonimo.ValidarseTest;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class CerrarSesionTest {

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
	}

	@Test
	public void testCerrarSesionExito() {
		clickButton("cerrarSesion");
		validarse.testInicio();

	}

}

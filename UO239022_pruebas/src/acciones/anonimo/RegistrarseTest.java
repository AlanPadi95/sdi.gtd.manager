package acciones.anonimo;

import org.junit.*;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class RegistrarseTest {
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8280/UO239022");
		// Voy a la página de inicio
		beginAt("/registro");
	}

	@Test
	public void testAllRegistrarse(){
		testInicio();
		testIsEmpty();
		testRegistrarUsuarioExistente();
	}
	
	@Test
	public void testInicio() {
		// Compruebo que el título es el correcto
		assertTitleEquals("GTD Task Manager - Registrarse");
		// Compruebo que se muestra el formulario de validación
		assertFormElementPresent("registrarse_form");
		// Compruebo que los botones se encuentran en el jsp
		assertElementPresent("registrarse");
		assertButtonPresent("cancelar");
	}

	@Test
	public void testIsEmpty() {
		// Comprueba que los campos están vacíos
		assertTextFieldEquals("email", "");
		assertTextFieldEquals("login", "");
		assertTextFieldEquals("pass1", "");
		assertTextFieldEquals("pass2", "");

	}

	@Test
	public void testRegistrarUsuarioExistente() {
		setTextField("email", "usuario3@gmail.com");
		setTextField("login", "usuario3");
		setTextField("pass1", "usuario3");
		setTextField("pass2", "usuario3");
		submit("registrarse");
		assertTitleEquals("GTD Task Manager - Registrarse");

	}
}

package acciones.category;

import org.junit.*;

import acciones.anonimo.ValidarseTest;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class GestionarCategoriaTest {
	private ValidarseTest validarse = new ValidarseTest();

	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8280/UO239022");
		beginAt("/");
		validarse.testValidarseUsuarioExito();
		clickButton("anadirCategoria");
	}

	@Test
	public void testInicio() {
		assertTitleEquals("GTD Task Manager - Categoria");
		assertTextFieldEquals("name", "");
	}

	/*
	 * @Test public void testAnadirEditarEliminarCategoriaTest() {
	 * setTextField("name", "Category_Prueba"); clickButton("guardar");
	 * assertTitleEquals("GTD Task Manager - Gestionar tareas");
	 * clickLink("gestionarCategoria?Category_Prueba");
	 * assertTitleEquals("GTD Task Manager - Categoria"); setTextField("name",
	 * "Category_Prueba2"); clickButton("guardar");
	 * assertTitleEquals("GTD Task Manager - Gestionar tareas");
	 * clickLink("gestionarCategoria?Category_Prueba2");
	 * clickButton("eliminarCategoria");
	 * assertTitleEquals("GTD Task Manager - Eliminar Categoría");
	 * clickButton("eliminarCategoria");
	 * assertTitleEquals("GTD Task Manager - Gestionar tareas"); }
	 */

}

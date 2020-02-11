package com.seguridad;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.seguridad.dto.AutenticacionRequestDTO;

/**
 * Test para los procesos de negocio del recuros http://localhost:port/auth/
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AutenticacionResourceTest {

	/** Es un random puerto para hacer el llamado a los rest */
	@LocalServerPort
	private int port;

	/** Se utiliza para ser las invocaciones REST */
	@Autowired
	private TestRestTemplate template;

	/**
	 * Metodo test para validar un inicio sesion EXITOSO
	 */
	@Test
	public void iniciarSesionExitoso() throws Exception {
		// url del recurso de inicio de sesion
		String url = "http://localhost:" + this.port + "/auth/login/";

		// se construye el request con las credenciales existente en BD
		AutenticacionRequestDTO credenciales = new AutenticacionRequestDTO();
		credenciales.setClaveIngreso("admin");
		credenciales.setUsuarioIngreso("admin");
		ResponseEntity<Object> result = this.template.postForEntity(url, credenciales, Object.class);

		// el response debe retornar OK con el identificador del usuario
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
	}

	/**
	 * Metodo test para validar un inicio sesion FALLIDO
	 */
	@Test
	public void iniciarSesionFallido() throws Exception {
		// url del recurso de inicio de sesion
		String url = "http://localhost:" + this.port + "/auth/login/";

		// se construye el request con las credenciales NO existente en BD
		AutenticacionRequestDTO credenciales = new AutenticacionRequestDTO();
		credenciales.setClaveIngreso("fallido");
		credenciales.setUsuarioIngreso("fallido");
		ResponseEntity<Object> result = this.template.postForEntity(url, credenciales, Object.class);

		// el response debe retornar BAD_REQUEST
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCodeValue());
	}
}

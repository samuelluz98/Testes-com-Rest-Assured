package br.ce.jsamuel.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJsonTest {
	
	@Test
	public void deveVerificarPrimeiroNivel() {
		
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18));
		
	}
	
	@Test
	public void deveVerificarPrimeiroNivelOutrasFormas() {
		
		// Obter o response
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/users/1");
		
		// path
		System.out.println(response.path("id"));
		
		// Outra forma de fazer
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s", "id"));
		
		//JSonPath
		JsonPath Jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, Jpath.getInt("id"));
		
		//From
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
		
	}
	
	@Test
	public void deveVerificarSegundoNivel() {
		
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/2")
		.then()
			.statusCode(200)
			.body("name", containsString("Joaquina"))
			.body("endereco.rua",is("Rua dos bobos"));
		
	}
	
	@Test
	public void deveVerificarLista() {
		
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/3")
		.then()
			.statusCode(200)
			.body("name", containsString("Ana"))
			.body("filhos", hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name", hasItem("Zezinho"))
			.body("filhos.name", hasItems("Zezinho", "Luizinho"))
			;
		
	}

}

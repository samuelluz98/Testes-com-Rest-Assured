package br.ce.jsamuel.rest;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
		
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		
		Assert.assertTrue("O status code deveria ser 200",response.statusCode() == 200);
		Assert.assertEquals(200, response.statusCode());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		
		RestAssured.given()    //Pré condições
		.when()    			  //Ação 
			.get("http://restapi.wcaquino.me/ola")
		.then()     //Assertivas;  O que vai ser testado depois da ação 
		.statusCode(200);
		
	} 
	
	@Test
	public void devoConhecerMatchersHamcrest() {
		
		//IGUALDADES
		assertThat("Maria", Matchers.is("Maria"));
		assertThat(128, Matchers.is(128));
		assertThat(128, Matchers.isA(Integer.class));
		assertThat(128d, Matchers.isA(Double.class));
		assertThat(128d, Matchers.greaterThan(110d));
		assertThat(128d, Matchers.lessThan(140d));
		
		//LISTAS
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1,5));
		
		//CONECTANDO ASSERTIVAS NA MESMA LÓGICA
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));       //Forma alternativa de escrever
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));

	}
	
	@Test
	public void devoValidarBody() {
		
		RestAssured.given()   
		.when()    			 
			.get("http://restapi.wcaquino.me/ola")
		.then()   
		.statusCode(200)
		.body(is("Ola Mundo!"))
		.body(containsString("Mundo"))
		.body(is(not(nullValue())));
		
	}

}

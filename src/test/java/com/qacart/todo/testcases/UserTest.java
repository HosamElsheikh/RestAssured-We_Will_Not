package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {
    @Test
    public void shouldBeAbleToRegister() {
//        String body = "{\n" +
//                "    \"firstName\": \"Hosam\",\n" +
//                "    \"lastName\": \"Elsheikh\",\n" +
//                "    \"email\": \"hosam.els@test.com\",\n" +
//                "    \"password\": \"12123456\"\n" +
//                "}";
        User user = new User("Hosam", "Elsheikh", "hos.elsdh@test.com", "12345678");
//        user.setFirstName("Hosam");
//        user.setEmail("hos.elsh@test.com");
//        user.setLastName("Elsheikh");
//        user.setPassword("12345678");

        given().
                baseUri("https://qacart-todo.herokuapp.com/")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName", equalTo("Hosam"));
    }
    @Test
    public void shouldFailToRegisterWithTheSameEmail(){
//        String body = "{\n" +
//                "    \"firstName\": \"Hosam\",\n" +
//                "    \"lastName\": \"Elsheikh\",\n" +
//                "    \"email\": \"test@test.com\",\n" +
//                "    \"password\": \"12123456\"\n" +
//                "}";
        User user = new User("Hosam", "Elsheikh", "hos.elsdh@test.com", "12345678");

        given().
                baseUri("https://qacart-todo.herokuapp.com/")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }
    @Test
    public void shouldBeAbleToLogin(){
//        String body = "{\n" +
//                "    \"email\": \"hosam.els@test.com\",\n" +
//                "    \"password\": \"12123456\"\n" +
//                "}";
        User user = new User("hos.elsdh@test.com", "12345678");


        given().
                baseUri("https://qacart-todo.herokuapp.com/")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("firstName", equalTo("Hosam"))
                .assertThat().body("access_token", not(equalTo(null)));
    }
    @Test
    public void shouldFailToLoginIncorrectCrededntials(){
//        String body = "{\n" +
//                "    \"email\": \"hosam.els@test.com\",\n" +
//                "    \"password\": \"22123456\"\n" +
//                "}";
        User user = new User("hos.elsdh@test.com", "123456f78");


        given().
                baseUri("https://qacart-todo.herokuapp.com/")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("The email and password combination is not correct, please fill a correct email and password"));
    }
}

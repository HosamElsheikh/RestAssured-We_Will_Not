package com.qacart.todo.testcases;

import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TodoTest {
    @Test
    public void shouldBeAbleToAddTask(){
//        String body = "{\n" +
//                "    \"isCompleted\": false,\n" +
//                "    \"item\": \"Learn Appium\"\n" +
//                "}";
        Todo todo = new Todo(false, "Automation Too");


        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2NGE0MDRlZTI4Nzc0MDAxNDk5Nzc5MyIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcxNjE0NTIzN30.7PRojf4zRaW7AeOFVXTQJVaUv-AOUUA9dy03k53bPwg";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("api/v1/tasks")
                .then()
                .log().all()
                .extract().response();

            Todo newTodo = response.body().as(Todo.class);
                assertThat(response.statusCode(), equalTo(201));
                assertThat(newTodo.getItem(), equalTo(todo.getItem()));
                assertThat(newTodo.getIsCompleted(),equalTo(todo.getIsCompleted()));
    }

    @Test
    public void shouldFailToAddTaskIfIsCompletedIsMissing(){
//        String body = "{\n" +
//                "    \"item\": \"Learn Appium\"\n" +
//                "}";
        Todo todo = new Todo("Automation Too");

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2NGE0MDRlZTI4Nzc0MDAxNDk5Nzc5MyIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcxNjE0NTIzN30.7PRojf4zRaW7AeOFVXTQJVaUv-AOUUA9dy03k53bPwg";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("api/v1/tasks")
                .then()
                .log().all()
                .extract().response();

//                .assertThat().statusCode(400)
//                .assertThat().body("message", equalTo("\"isCompleted\" is required"));

        Error returnedError = response.body().as(Error.class);

                assertThat(response.statusCode(),equalTo(400));
                assertThat(returnedError.getMessage(), equalTo("\"isCompleted\" is required"));
    }

//    @Test
//    public void shouldGetAllTodo(){
//
//
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2NGE0MDRlZTI4Nzc0MDAxNDk5Nzc5MyIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcxNjE0NTIzN30.7PRojf4zRaW7AeOFVXTQJVaUv-AOUUA9dy03k53bPwg";
//        given()
//                .baseUri("https://qacart-todo.herokuapp.com")
//                .contentType(ContentType.JSON)
//                .auth().oauth2(token)
//                .when()
//                .get("api/v1/tasks")
//                .then()
//                .log().all()
//                .assertThat().statusCode(200)
//                .assertThat().body("[0]", hasKey("tasks"));
//    }

    @Test
    public void shouldGetASingleTodo(){

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2NGE0MDRlZTI4Nzc0MDAxNDk5Nzc5MyIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcxNjE0NTIzN30.7PRojf4zRaW7AeOFVXTQJVaUv-AOUUA9dy03k53bPwg";
        String taskId = "66844197aeace900145dbb06";

                Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .get("api/v1/tasks/" + taskId)
                .then()
                .log().all().extract().response();
//
//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("I am just a freak"))
//                .assertThat().body("isCompleted", equalTo(false));

        Todo returnedTodo = response.body().as(Todo.class);
                assertThat(response.statusCode(),equalTo(200));
                assertThat(returnedTodo.getItem(), equalTo("I am just a freak"));
                assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }

    @Test
    public void shouldDeleteTodo(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2NGE0MDRlZTI4Nzc0MDAxNDk5Nzc5MyIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcxNjE0NTIzN30.7PRojf4zRaW7AeOFVXTQJVaUv-AOUUA9dy03k53bPwg";
        String taskId = "664a4dd0e2877400149977de";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete("api/v1/tasks/" + taskId)
                .then()
                .log().all()
                .extract().response();

//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));
        Todo returnedTodo = response.getBody().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo("Learn Appium"));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }
}

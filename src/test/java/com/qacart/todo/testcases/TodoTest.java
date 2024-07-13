package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Feature("Todo Feature")
public class TodoTest {
    @Story("Should be able to add Todo")
    @Test(description = "Should be able to add Todo")
    public void shouldBeAbleToAddTask(){
//        String body = "{\n" +
//                "    \"isCompleted\": false,\n" +
//                "    \"item\": \"Learn Appium\"\n" +
//                "}";
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        Response response = TodoApi.addTodo(todo,token);

            Todo newTodo = response.body().as(Todo.class);
                assertThat(response.statusCode(), equalTo(201));
                assertThat(newTodo.getItem(), equalTo(todo.getItem()));
                assertThat(newTodo.getIsCompleted(),equalTo(todo.getIsCompleted()));
    }
    @Story("Should Fail to Add Task if IsCompleted is Missing")
    @Test(description = "Should Fail to Add Task if IsCompleted is Missing")
    public void shouldFailToAddTaskIfIsCompletedIsMissing(){
//        String body = "{\n" +
//                "    \"item\": \"Learn Appium\"\n" +
//                "}";
        Todo todo = new Todo("Automation Too");

        String token = UserSteps.getUserToken();

        Response response = TodoApi.addTodo(todo,token);

//        Response response = given()
//                .baseUri("https://qacart-todo.herokuapp.com")
//                .body(todo)
//                .contentType(ContentType.JSON)
//                .auth().oauth2(token)
//                .when()
//                .post("api/v1/tasks")
//                .then()
//                .log().all()
//                .extract().response();

//                .assertThat().statusCode(400)
//                .assertThat().body("message", equalTo("\"isCompleted\" is required"));

        Error returnedError = response.body().as(Error.class);

                assertThat(response.statusCode(),equalTo(400));
                assertThat(returnedError.getMessage(), equalTo(ErrorMessages.ISCOMPLETED_IS_REQUIRED));
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
@Story("Should get a single Todo")
    @Test(description = "Should get a single Todo")
    public void shouldGetASingleTodo(){
        Todo todo = TodoSteps.generateTodo();
        String token = UserSteps.getUserToken();

        String taskId = TodoSteps.createTaskId(todo, token);

        Response response = TodoApi.getTodo(taskId, token);
//
//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("I am just a freak"))
//                .assertThat().body("isCompleted", equalTo(false));

        Todo returnedTodo = response.body().as(Todo.class);
                assertThat(response.statusCode(),equalTo(200));
                assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
                assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }
    @Story("Should delete Todo")

    @Test(description = "Should delete Todo")
    public void shouldDeleteTodo(){
        Todo todo = TodoSteps.generateTodo();
        String token = UserSteps.getUserToken();
        String taskId = TodoSteps.createTaskId(todo, token);
        Response response = TodoApi.deleteTodo(taskId, token);
        System.out.println("cool");

//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));
        Todo returnedTodo = response.getBody().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }
}

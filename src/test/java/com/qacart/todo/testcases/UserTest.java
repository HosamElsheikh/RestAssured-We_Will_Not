package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.not;

public class UserTest {
    @Test
    public void shouldBeAbleToRegister()
    {
        User user = new User("Hosam", "Elsheikh", "ffa.elsdh@test.com", "12345678");

        Response response = UserApi.register(user);

        User newUser = response.body().as(User.class); //The response body will be like the user class instance.

        assertThat(response.statusCode(), equalTo(201));
        assertThat(newUser.getFirstName(), equalTo(user.getFirstName()));

    }
    @Test
    public void shouldFailToRegisterWithTheSameEmail(){
        User user = new User("Hosam", "Elsheikh", "hos.elsdh@test.com", "12345678");

        Response response = UserApi.register(user);


        Error returnedError = response.body().as(Error.class);


        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(),equalTo("Email is already exists in the Database"));
    }
    @Test
    public void shouldBeAbleToLogin(){
        User user = new User("hos.elsdh@test.com", "12345678");


        Response response = UserApi.login(user);

        User newUser = response.body().as(User.class); //The response body will be like the user class instance.


        assertThat(response.statusCode(), equalTo(200));
        assertThat(newUser.getFirstName(), equalTo("Hosam"));
        assertThat(newUser.getAccessToken(), not(equalTo(null)));
    }
    @Test
    public void shouldFailToLoginIncorrectCrededntials(){

        User user = new User("hos.elsdh@test.com", "123456f78");


        Response response = UserApi.login(user);


        Error returnedError = response.body().as(Error.class);


        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(),equalTo("The email and password combination is not correct, please fill a correct email and password"));
    }
}

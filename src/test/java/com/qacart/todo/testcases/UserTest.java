package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.not;

@Feature("User Feature")
public class UserTest {
    @Story("User Feature")
    @Test(description = "Should be able to Register")
    public void shouldBeAbleToRegister()
    {
        User user = UserSteps.generateUser();

        Response response = UserApi.register(user);

        User newUser = response.body().as(User.class); //The response body will be like the user class instance.

        assertThat(response.statusCode(), equalTo(201));
        assertThat(newUser.getFirstName(), equalTo(user.getFirstName()));

    }
    @Story("User Feature")
    @Test(description = "Should fail to register with the same Email")
    public void shouldFailToRegisterWithTheSameEmail(){
        User user = UserSteps.getRegisteredUser();

        Response response = UserApi.register(user);


        Error returnedError = response.body().as(Error.class);


        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.EMAIL_IS_REGISTERED));
    }
    @Story("User Feature")
    @Test(description = "Should be able to Login")
    public void shouldBeAbleToLogin(){
        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), user.getPassword());

        Response response = UserApi.login(loginData);

        User newUser = response.body().as(User.class); //The response body will be like the user class instance.


        assertThat(response.statusCode(), equalTo(200));
        assertThat(newUser.getFirstName(), equalTo(user.getFirstName()));
        assertThat(newUser.getAccessToken(), not(equalTo(null)));
    }
    @Story("User Feature")
    @Test(description = "Should fail to Login with wrong credentials")
    public void shouldFailToLoginIncorrectCrededntials(){

        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), "WrongPassword");

        Response response = UserApi.login(loginData);


        Error returnedError = response.body().as(Error.class);


        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.EMAIL_OR_PASSWORD_INCORRECT));
    }
}

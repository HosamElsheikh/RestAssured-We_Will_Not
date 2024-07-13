package com.qacart.todo.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {
    public static String getEnv(){
        String env = System.getProperty("env", "PRODUCTION");
        String baseUrl;
        switch (env)
        {
            case "PRODUCTION":
                baseUrl = "https://qacart-todo.herokuapp.com";
                break;
            case "LOCAL":
                baseUrl = "https://qacart-todo.herokuapp.com";
                break;
            default:
                throw new RuntimeException("Env not supported");
        }
        return baseUrl;
    }
    public static RequestSpecification getRequestSpecs(){
        RequestSpecification requestSpecification = given()
                .baseUri(getEnv())
                .contentType(ContentType.JSON)
                .log().all();
        return requestSpecification;
    }
}

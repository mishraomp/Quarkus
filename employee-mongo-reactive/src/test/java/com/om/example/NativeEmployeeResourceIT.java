package com.om.example;

import io.quarkus.test.junit.NativeImageTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@NativeImageTest
public class NativeEmployeeResourceIT extends EmployeeResourceTest {

    @Test
    public void test_getEmployeeEndpoint_givenRandomEmployeeId_shouldReturn404StatusCode() {
        given()
                .when().get("/api/v1/employee/123")
                .then()
                .statusCode(404);

    }

    @Test
    public void test_getEmployeeEndpoint_givenValidEmployeeId_shouldReturn200StatusCode() {
        given()
                .when().get("/api/v1/employee")
                .then()
                .statusCode(200);

    }
}
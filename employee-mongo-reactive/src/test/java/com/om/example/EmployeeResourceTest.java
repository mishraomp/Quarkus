package com.om.example;

import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeResourceTest {


    @Test
    @Order(1)
    public void test_postEmployeeEndpoint_givenValidEmployee_shouldReturn201StatusCode() throws IOException {
        final Path path = Paths.get("/home/om/dev/Quarkus/employee-mongo-reactive/src/test/resources/Employee.json");
        given().body(Files.readAllBytes(path)).with().contentType("application/json").then().statusCode(201)
                .when().post("/api/v1/employee");


    }

    @Test
    @Order(2)
    public void test_getEmployeeEndpoint_givenRandomEmployeeId_shouldReturn404StatusCode() {
        given().basePath("/api/v1/employee")
                .when().get("/{employeeId}", 112121213)
                .then()
                .statusCode(404);

    }

    @Test
    @Order(3)
    public void test_getEmployeeEndpoint_givenValidEmployeeId_shouldReturn200StatusCode() {
        given()
                .when().get("/api/v1/employee/1")
                .then()
                .statusCode(200);

    }

    @Test
    @Order(4)
    public void test_getEmployeeEndpoint_givenNoId_shouldReturn200StatusCode() {
        given()
                .when().get("/api/v1/employee")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    public void test_deleteEmployeeEndpoint_givenNoId_shouldReturn200StatusCode() {
        given()
                .when().delete("/api/v1/employee/1")
                .then()
                .statusCode(200);
    }

}

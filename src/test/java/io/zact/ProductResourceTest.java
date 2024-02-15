package io.zact;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProductResourceTest {

    @Test
    public void testGetProducts() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetProductById() {
        given()
                .pathParam("id", 2)
                .when().get("/products/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateProduct() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Test Product\", \"description\": \"Test Description\", \"price\": 10.0, \"stock\": 100}")
                .when().post("/products")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateProduct() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 33)
                .body("{\"name\": \"Updated Product\", \"description\": \"Updated Description\", \"price\": 20.0, \"stock\": 50}")
                .when().put("/products/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteProduct() {
        given()
                .pathParam("id", 33)
                .when().delete("/products/{id}")
                .then()
                .statusCode(200);
    }
}
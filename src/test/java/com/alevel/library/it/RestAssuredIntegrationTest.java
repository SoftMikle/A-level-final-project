package com.alevel.library.it;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestAssuredIntegrationTest {

    @LocalServerPort
    int port;

    @Test
    void getDemoTest() {
        RestAssured
                .given()
                .when()
                .get("http://localhost:" + port + "/library/assured")
                .then()
                .statusCode(201);
    }
}

package tests;

import org.junit.jupiter.api.Test;
import tests.models.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.specs.ReqresSpecs.badRequestResponse;
import static tests.specs.ReqresSpecs.createUserRequest;
import static tests.specs.ReqresSpecs.userCreatedResponse;

public class UserCreationTests {

    @Test
    void verifyUserCreation() {
        User user = given()
                .spec(createUserRequest)
                .body(User.builder().name("testUser").job("testJob").build())
                .when()
                .post("/users")
                .then()
                .spec(userCreatedResponse)
                .extract().as(User.class);

        assertEquals("testUser", user.getName());
        assertEquals("testJob", user.getJob());
    }

    @Test
    void verifyUserCreationWithoutJobField() {
        given()
                .spec(createUserRequest)
                .body("{\"name\": \"testUser\"}")
                .when()
                .post("/users")
                .then()
                .spec(userCreatedResponse)
                .body("name", is("testUser"));
    }

    @Test
    void verifyUserCreationWithoutNameField() {
        given()
                .spec(createUserRequest)
                .body("{\"job\": \"testJob\"}")
                .when()
                .post("/users")
                .then()
                .spec(userCreatedResponse)
                .body("job", is("testJob"));
    }

    @Test
    void verifyUserCreationWithoutNameAndJobFields() {
        given()
                .spec(createUserRequest)
                .when()
                .post("/users")
                .then()
                .spec(userCreatedResponse);
    }

    @Test
    void verifyUnsuccessfulUserCreationWithoutNameAndJobValues() {
        given()
                .spec(createUserRequest)
                .body("{\"name\": ," +
                              "\"job\": }")
                .when()
                .post("/users")
                .then()
                .spec(badRequestResponse);
    }

    @Test
    void verifyUnsuccessfulUserCreationWithoutJobValue() {
        given()
                .spec(createUserRequest)
                .body("{\"name\": \"testUser\"," +
                              "\"job\": }")
                .when()
                .post("/users")
                .then()
                .spec(badRequestResponse);
    }

    @Test
    void verifyUnsuccessfulUserCreationWithoutNameValue() {
        given()
                .spec(createUserRequest)
                .body("{\"name\": ," +
                              "\"job\": \"testJob\"}")
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .spec(badRequestResponse);
    }
}
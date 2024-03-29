package tests.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.containsString;

public class ReqresSpecs {

    public static RequestSpecification createUserRequest = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().method()
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);

    public static ResponseSpecification userCreatedResponse = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.STATUS)
            .build();

    public static ResponseSpecification badRequestResponse = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .expectBody(containsString("Bad Request"))
            .log(LogDetail.STATUS)
            .build();
}
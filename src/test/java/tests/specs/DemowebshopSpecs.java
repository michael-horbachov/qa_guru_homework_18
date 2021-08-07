package tests.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.is;

public class DemowebshopSpecs {

    public static RequestSpecification getCartItemsRequest = with()
            .baseUri("http://demowebshop.tricentis.com")
            .log().method()
            .log().uri();

    public static ResponseSpecification getCartItemsResponse = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .build();

    public static RequestSpecification addItemToCartRequest = with()
            .baseUri("http://demowebshop.tricentis.com")
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .log().method()
            .log().uri();

    public static ResponseSpecification addItemToCartResponse = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("success", is(true))
            .log(LogDetail.STATUS)
            .build();
}

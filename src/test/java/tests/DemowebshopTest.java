package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import tests.models.CartItem;
import tests.specs.DemowebshopSpecs;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemowebshopTest {

    private static final String BASE_URL = "http://demowebshop.tricentis.com";
    private static final String COOKIE_NAME = "NOP.CUSTOMER";
    private static final String ADD_TO_CART_BODY = "addtocart_13.EnteredQuantity=";
    private static final int ITEMS_QUANTITY_TO_ADD = 11;

    @Test
    void verifyCartItemsIncreased() {

        Response response = given()
                .spec(DemowebshopSpecs.getCartItemsRequest)
                .when()
                .get("/cart")
                .then()
                .spec(DemowebshopSpecs.getCartItemsResponse)
                .extract().response();

        String cookie = response.getCookie(COOKIE_NAME);

        String itemsQuantityBeforeAdding = response.htmlPath().getString("**.find {it.@class == 'cart-qty'}")
                                                   .replaceAll("\\p{P}", "");
        int itemsBeforeAdding = Integer.parseInt(itemsQuantityBeforeAdding);

        CartItem cartItem = given()
                .spec(DemowebshopSpecs.addItemToCartRequest)
                .body(ADD_TO_CART_BODY + ITEMS_QUANTITY_TO_ADD)
                .cookie(String.valueOf(new Cookie(COOKIE_NAME, cookie)))
                .when()
                .post("/addproducttocart/details/13/1")
                .then()
                .spec(DemowebshopSpecs.addItemToCartResponse)
                .extract().as(CartItem.class);

        assertEquals("(" + (itemsBeforeAdding + ITEMS_QUANTITY_TO_ADD) + ")", cartItem.getUpdatetopcartsectionhtml());

        open(BASE_URL + "/Themes/DefaultClean/Content/images/logo.png");
        getWebDriver().manage().addCookie(new Cookie(COOKIE_NAME, cookie));
        open(BASE_URL);
        $(".cart-qty").shouldHave(exactText("(" + (itemsBeforeAdding + ITEMS_QUANTITY_TO_ADD) + ")"));
    }
}
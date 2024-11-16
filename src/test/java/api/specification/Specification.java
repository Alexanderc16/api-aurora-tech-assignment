package api.specification;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {

    public static RequestSpecification requestSpec(String baseURL) {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setBaseUri(baseURL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpec(int response) {
        return new ResponseSpecBuilder()
                .expectStatusCode(response)
                .build();
    }

    public static void installRequestSpec(RequestSpecification request) {
        RestAssured.requestSpecification = request;
    }

    public static void installResponseSpec(ResponseSpecification response) {
        RestAssured.responseSpecification = response;
    }
}

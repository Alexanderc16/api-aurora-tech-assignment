package actions.steps;

import datastorage.DataStorage;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import propertiesreader.ConfigReader;

import java.util.*;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class GetMoviesSteps {

    @Step
    @Then("user gets movies")
    public void getMovies() {
        // setting url for request
        RestAssured.baseURI = ConfigReader.getProperty("base_url").toString() + ConfigReader.getProperty("get_movies").toString();

        //logging url
        log.info("URI: " + RestAssured.baseURI);

        //getting response from url with GET method
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "?limit=1000");

        //logging the response and saving it to singleton DataStorage object
        log.info("Response: " + response.asString());
        DataStorage.putOnStorage("Movies", response);
    }

    @Step
    @Then("user verifies movies total value is {int}")
    public void verifyMoviesQuantity(int quantity) {
        Response response = DataStorage.getFromStorage("Movies");
        JsonPath jsonPathEvaluator = response.jsonPath();

        log.info("Actual total value: " + jsonPathEvaluator.get("total") + " Expected total value: " + quantity);
        assertThat(jsonPathEvaluator.get("total").equals(String.valueOf(quantity)));
    }

    @Step
    @Then("user verifies movies total quantity is equal to total value")
    public void verifyMoviesQuantityAndTotal() {
        Response response = DataStorage.getFromStorage("Movies");

        JsonPath jsonPathEvaluator = new JsonPath(response.body().asString());
        List<String> items = jsonPathEvaluator.getList("items");

        log.info("Actual length: " + jsonPathEvaluator.get("total") + " Expected length: " + items.size());
        assertThat(jsonPathEvaluator.get("total").equals(items.size()));
    }

    @Step
    @Then("user verifies all IDs are unique")
    public void verifyIdsAreUnique() {
        Response response = DataStorage.getFromStorage("Movies");

        JsonPath jsonPathEvaluator = new JsonPath(response.body().asString());
        List<String> ids = jsonPathEvaluator.getList("items.id");
        Set<String> uniqueSet = new HashSet<>(ids);

        log.info("Unique IDs quantity: " + uniqueSet.size() + ", Returned IDs quantity: " + ids.size());
        assertThat(uniqueSet.size() == ids.size());
    }

    @Step
    @Then("user verifies titles may have duplicates")
    public void verifyTitlesMayHaveDuplicates() {
        Response response = DataStorage.getFromStorage("Movies");

        JsonPath jsonPathEvaluator = new JsonPath(response.body().asString());
        List<String> duplicates = jsonPathEvaluator.getList("items.title");
        Set<String> uniqueSet = new HashSet<>(duplicates);

        log.info("Unique titles quantity: " + uniqueSet.size() + ", Returned titles quantity: " + duplicates.size());
        assertThat(uniqueSet.size() < duplicates.size());
    }

    @Step
    @Then("user verifies getting {int} items when searching for {string}")
    public void verifySearchFunctionality(int number, String search) {
        RestAssured.baseURI = ConfigReader.getProperty("base_url").toString() + ConfigReader.getProperty("get_movies").toString();

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "?query=" + search);
        log.info(RestAssured.baseURI + "?query=" + search);

        JsonPath jsonPathEvaluator = new JsonPath(response.body().asString());
        List<String> list = jsonPathEvaluator.getList("items");

        log.info(search + " titles quantity: " + list.size() + ", Expected titles quantity: " + number);
        assertTrue(list.size() == number);
    }

    @Step
    @Then("user verifies getting {int} items when skipping {int} items")
    public void verifySkipFunctionality(int result, int skipping) {
        RestAssured.baseURI = ConfigReader.getProperty("base_url").toString() + ConfigReader.getProperty("get_movies").toString();

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "?limit=1000&skip=" + skipping);
        log.info(RestAssured.baseURI + "?skip=" + skipping);

        JsonPath jsonPathEvaluator = new JsonPath(response.body().asString());
        List<String> list = jsonPathEvaluator.getList("items");

        log.info(skipping + " titles skipped, Expected titles quantity: " + result + ", Actual titles quantity: " + list.size());
        assertTrue(list.size() == result);
    }

    @Step
    @Then("user verifies movies status code is {}")
    public void verifyMoviesStatusCode(int statusCode) {
        Response response = DataStorage.getFromStorage("Movies");

        log.info("Expected status: " + statusCode + ", Response status code: " + response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }

    @Step
    @Then("user verifies movies response structure and data types")
    public void verifyMoviesFormat() {
        Response response = DataStorage.getFromStorage("Movies");

        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("movies-schema.json"));
        log.info("Structure and data types are correct");
    }

    @Step
    @Then("user verifies IDs follow a valid UUID format for all items")
    public void verifyIdFollowsUUIDFormat() {
        Response response = DataStorage.getFromStorage("Movies");

        JsonPath jsonPathEvaluator = new JsonPath(response.body().asString());
        List<String> ids = jsonPathEvaluator.getList("items.id");
        Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

        for (String id : ids) {
            if (!UUID_REGEX.matcher(id).matches()) {
                log.info("Wrong format ID: " + id);
            }
            assertTrue(UUID_REGEX.matcher(id).matches());
        }
        log.info("IDs follow UUID format");
    }

    @Step
    @Then("user verifies inability to retrieve movies with {string} method")
    public void userTriesRetrieveMoviesWithWrongMethod(String method) {
        RestAssured.baseURI = ConfigReader.getProperty("base_url").toString() + ConfigReader.getProperty("get_movies").toString();
        log.info("URI: " + RestAssured.baseURI);
        RequestSpecification httpRequest = RestAssured.given();

        switch (method) {
            case "POST":
                Response postRespone = httpRequest.request(Method.POST, "?limit=1000");
                log.info(method + " status code: " + postRespone.getStatusCode() + ", expected status code: " + 405);
                log.info("Response message: " + postRespone.jsonPath().get("detail"));
                assertTrue(postRespone.jsonPath().get("detail").equals("Method Not Allowed"));
                assertTrue(postRespone.getStatusCode() == 405);
                break;
            case "PUT":
                Response putResponse = httpRequest.request(Method.PUT, "?limit=1000");
                log.info(method + " status code: " + putResponse.getStatusCode() + ", expected status code: " + 405);
                log.info("Response message: " + putResponse.jsonPath().get("detail"));
                assertTrue(putResponse.jsonPath().get("detail").equals("Method Not Allowed"));
                assertTrue(putResponse.getStatusCode() == 405);
                break;
            case "DELETE":
                Response deleteResponse = httpRequest.request(Method.DELETE, "?limit=1000");
                log.info(method + " status code: " + deleteResponse.getStatusCode() + ", expected status code: " + 405);
                log.info("Response message: " + deleteResponse.jsonPath().get("detail"));
                assertTrue(deleteResponse.jsonPath().get("detail").equals("Method Not Allowed"));
                assertTrue(deleteResponse.getStatusCode() == 405);
                break;
            case "PATCH":
                Response patchResponse = httpRequest.request(Method.PATCH, "?limit=1000");
                log.info(method + " status code: " + patchResponse.getStatusCode() + ", expected status code: " + 405);
                assertTrue(patchResponse.jsonPath().get("detail").equals("Method Not Allowed"));
                assertTrue(patchResponse.getStatusCode() == 405);
                break;
        }
    }
}

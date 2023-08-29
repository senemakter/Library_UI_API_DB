package com.library.steps;

import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.internal.ref.CleanerImpl;
import org.junit.*;

import static io.restassured.RestAssured.given;

public class US01stepDefs {


    private Response response;
    private String baseUrl = "https://library2.cydeo.com/rest/v1";

    @Given("I logged Library api as a {string}")
    public void iLoggedLibraryApiAsAUser(String user) {
        // Implement the necessary steps to log in the user
        // You might need to store authentication tokens or session data
    }

    @And("Accept header is {string}")
    public void acceptHeaderIs(String headerValue) {
        RestAssured.given()
                .header("Accept", headerValue);
    }

    @When("I send GET request to {string} endpoint")
    public void iSendGETRequestToEndpoint(String endpoint) throws Exception {
        response = given()
                .header("Accept", "application/json")
                .when()
                .get(baseUrl + endpoint);
    }

    @Then("status code should be {int}")
    public void statusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("Response Content type is {string}")
    public void responseContentTypeIs(String expectedContentType) {
        String actualContentType = response.contentType();
        Assert.assertEquals(expectedContentType, actualContentType);
    }

    @Then("\"id\" field should not be null")
    public void idFieldShouldNotBeNull() {
        String jsonResponse = response.getBody().asString();
        Assert.assertNotNull(jsonResponse);
    }

    @Then("\"name\" field should not be null")
    public void nameFieldShouldNotBeNull() {
        String jsonResponse = response.getBody().asString();
        Assert.assertNotNull(jsonResponse);

    }
}
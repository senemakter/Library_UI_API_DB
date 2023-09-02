package com.library.steps;

import com.library.pages.LoginPage;
import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class US01stepDefs {
    public String librarianToken;
    public RequestSpecification requestedAcceptType;
    public Response response;
    JsonPath jsonPath;
    ValidatableResponse validatableResponse;

    @Given("I logged Library api as a {string}")
    public void logIntoLibraryAsUser(String type) {

        librarianToken = LibraryAPI_Util.getToken(type);

         //LoginPage loginPage = new LoginPage();
         //loginPage.login("librarian");
    }

    @And("Accept header is {string}")
    public void acceptHeaderIs(String acceptHeaderValue) {
        requestedAcceptType = RestAssured.given().accept(acceptHeaderValue).header("x-library-token", librarianToken);
        //request = RestAssured.given().header("Accept", "application/json");
    }

    @When("I send GET request to {string} endpoint")
    public void iSendGETRequestToEndpoint(String endpoint) {
        response = requestedAcceptType.when().get(ConfigurationReader.getProperty("library.baseUri")+endpoint);
    }

    @Then("status code should be {int}")
    public void statusCodeShouldBe(int expectedStatusCode) {

       validatableResponse = response.then().statusCode(expectedStatusCode);
    }


    @And("Response Content type is {string}")
    public void responseContentTypeIs(String expectedContentType) {
        jsonPath =  validatableResponse.contentType(expectedContentType).extract().jsonPath();
    }

    @And("{string} field should not be null")
    public void idFieldShouldNotBeNull(String id) {

        response.then().body(id, everyItem(is(notNullValue())));
    }

    @And("{string} field should not be null path")
    public void nameFieldShouldNotBeNull(String pathParam) {
        String name = jsonPath.getString(pathParam);
        assertThat(name, notNullValue());
    }
}

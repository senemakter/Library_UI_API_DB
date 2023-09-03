package com.library.steps;

import com.library.pages.LoginPage;
import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI_Util;
import com.library.utility.Library_API;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import org.junit.Assert;

import static io.restassured.RestAssured.basePath;
public class Us02StepDefs extends Library_API {
    public RequestSpecification requestedAcceptType;
    public Response response;

    public String librarianToken;

    public int pathParamId;
    JsonPath jsonPath;
    ValidatableResponse validatableResponse;

    @Given("I logged Library api as a {string}")
    public void logIntoLibraryAsUser(String type) {

        librarianToken = LibraryAPI_Util.getToken(type);
    }

    @And("Accept header is {string}")
    public void acceptHeaderIs(String acceptHeaderValue) {
        //requestedAcceptType = RestAssured.given().accept(acceptHeaderValue).header("x-library-token", librarianToken);
        requestedAcceptType = RestAssured.given().header("Accept", "application/json");
    }

    @And("Path param is {int}")
    public void pathParamIs(int id) {
        pathParamId = id;
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_GET_request_to_endpoint(String endpoint) {
        String url = basePath + pathParamId;
        response = RestAssured
                .given()
                .header("Accept", "application/json")
                .when()
                .get(ConfigurationReader.getProperty("library.baseUri"));
    }

    @Then("status code should be {int}")
    public void statusCodeShouldBe(int expectedStatusCode) {
        validatableResponse = response.then().statusCode(expectedStatusCode);
    }


    @And("Response Content type is {string}")
    public void responseContentTypeIs(String expectedContentType) {
        jsonPath = validatableResponse.extract().jsonPath();

    }

    @And("{string} field should be same with path param")
    public void fieldShouldBeSameAsPathParam(String id) {

        JsonPath jsonId = response.jsonPath();
    }

    @And("following fields should not be null")
    public void followingFieldsShouldNotBeNull(io.cucumber.datatable.DataTable dataTable) {
        Response response = RestAssured.get("https://library2.cydeo.com/api/endpoint");
        String fieldName = "endpoint";
        Object fieldValue = response.path(fieldName);
        System.out.println("Field Value: " + fieldValue);


    }

    }



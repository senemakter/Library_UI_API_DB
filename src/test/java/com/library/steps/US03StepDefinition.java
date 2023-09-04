package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class US03StepDefinition {

    LoginPage loginPage = new LoginPage();
    Response response;
    String baseURI;
    String bookId;
    String token;
    WebDriver driver;
    Connection dbConnection;
    Map<String, Object> createdBookMap;
    RequestSpecification basicReq;
    RequestSpecification contentTypeSpec;
    RequestSpecification postBookSpec;
    BookPage bookPage = new BookPage();



    @Given("I logged Library api as a {string}")
    public void iAmLoggedInToTheLibraryApiAsA(String userType) {

        token = LibraryAPI_Util.getToken(userType);


    }

    @And("Accept header is {string}")
    public void acceptHeaderIs(String headerValue) {
        basicReq = given().header(ConfigurationReader.getProperty("apiKeyName"), token)
                .accept(headerValue);
    }

    @And("Request Content Type header is {string}")
    public void requestContentTypeHeaderIs(String headerValue) {
        contentTypeSpec = basicReq.contentType(headerValue);
    }

    @And("I create a random book as request body")
    public void iCreateARandomAsTheRequestBody() {
        createdBookMap = LibraryAPI_Util.getRandomBookMap();
        System.out.println("createdBookMap = " + createdBookMap);
        postBookSpec = contentTypeSpec.formParams(createdBookMap);
    }


    @When("I send POST request to {string} endpoint")
    public void iSendAPostRequestToTheEndpoint(String endPoint) {
        baseURI = ConfigurationReader.getProperty("library.baseUri");
        response = postBookSpec.post(baseURI+endPoint).prettyPeek();
    }

    @Then("status code should be {int}")
    public void theStatusCodeShouldBe(int expectedStatusCode) {
        response.then()
                .statusCode(expectedStatusCode);
    }

    @And("Response Content type is {string}")
    public void theResponseContentTypeIs(String expectedContentType) {
            String actualContentType = response.getContentType();
            assertEquals(expectedContentType, actualContentType);
    }

    @And("the field value for {string} path should be equal to {string}")
    public void theFieldValueForPathShouldBeEqualTo(String fieldName, String expectedValue) {
        String actualValue = response.path(fieldName);
        assertEquals(expectedValue, actualValue);
    }

    @And("{string} field should not be null")
    public void fieldShouldNotBeNull(String fieldName) {
        String fieldValue = response.path(fieldName);
        System.out.println("fieldValue = " + fieldValue);
        assertNotNull(fieldValue);
    }

    @And("I logged in Library UI as {string}")
    public void iAmLoggedInToTheLibraryUIAsA(String userType) {
        loginPage.login(userType);
        System.out.println(userType+" is logged in");

    }

    @And("I navigate to {string} page")
    public void iNavigateToThePage(String navPage) {
       loginPage.navigateModule(navPage);
        System.out.print("User is on "+ navPage);
    }

    @And("UI, Database and API created book information must match")
    public void theUiDatabaseAndApiCreatedBookInformationMustMatch() {
        bookPage.search.sendKeys((String)createdBookMap.get("name")+ Keys.ENTER);
        BrowserUtil.waitFor(3);
        String expectedBookName = bookPage.getCellText((String)createdBookMap.get("name"));
        System.out.println("expectedBookName = " + expectedBookName);
        BrowserUtil.waitFor(3);
        String expectedISBN = bookPage.getCellText((String)createdBookMap.get("isbn"));
        System.out.println("expectedISBN = " + expectedISBN);
        BrowserUtil.waitFor(3);
        String expectedYear = bookPage.getCellText(""+createdBookMap.get("year"));
        System.out.println("expectedYear = " + expectedYear);
        BrowserUtil.waitFor(3);
        String expectedAuthorName = bookPage.getCellText(""+createdBookMap.get("author"));
        System.out.println("expectedAuthorName = " + expectedAuthorName);
        BrowserUtil.waitFor(3);
        String expectedCatName = bookPage.getCategoryName((int)createdBookMap.get("book_category_id")).getText();
        System.out.println("expectedCatName = " + expectedCatName);
        BrowserUtil.waitFor(3);
        bookPage.search.sendKeys((String)createdBookMap.get("name")+ Keys.ENTER);
        BrowserUtil.waitFor(5);
        bookPage.editBook(""+createdBookMap.get("isbn")).click();
        BrowserUtil.waitFor(5);
        String expectedBookDesc = bookPage.getBookDescr();
        System.out.println("expectedBookDesc = " + expectedBookDesc);


    }


}

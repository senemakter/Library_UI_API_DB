package com.library.utility;

import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import org.junit.Before;

public class LibraryAPI {

    @BeforeAll
    public void init(){
        RestAssured.baseURI="https://library2.cydeo.com/rest/v1";
    }
}

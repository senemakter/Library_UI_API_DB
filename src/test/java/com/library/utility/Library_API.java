package com.library.utility;

import org.junit.BeforeClass;
import io.restassured.RestAssured;

public class Library_API {

    @BeforeClass
    public static void init(){
        RestAssured.baseURI="https://library2.cydeo.com/rest/v1";
    }
}

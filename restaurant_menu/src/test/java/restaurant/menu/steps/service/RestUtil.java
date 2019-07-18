package restaurant.menu.steps.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;


public class RestUtil {
    //Global Setup Variables
    public static String path; //Rest request path

    /*
    ***Sets Base URI***
    Before starting the test, set the RestAssured.baseURI
    */
    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    /*
    ***Sets base path***
    Before starting the test, set the RestAssured.basePath
    */
    public static void setBasePath(String basePathTerm) {
        RestAssured.basePath = basePathTerm;
    }

    /*
    ***Reset Base URI (after test)***
    After the test, reset the RestAssured.baseURI
    */
    public static void resetBaseURI() {
        RestAssured.baseURI = null;
    }

    /*
    ***Reset base path (after test)***
    After the test, reset the RestAssured.basePath
    */
    public static void resetBasePath() {
        RestAssured.basePath = null;
    }

    /*
    ***Sets ContentType***
    Set content type as JSON or XML before starting the test
    */
    public static void setContentType(ContentType Type) {
        given().contentType(Type);
    }

    /*
     ***search query path of first example***
     */
    public static void createSearchQueryPath(String searchTerm, String jsonPathTerm, String param, String paramValue) {
        path = searchTerm + "/" + jsonPathTerm + "?" + param + "=" + paramValue;
    }

    /*
    ***Returns response***
    Send "path" as a parameter to the Rest Assured'a "get" method
    and "get" method returns response of API
    */
    public static Response getResponse() {
        return get();
    }

    /*
     ***Returns JsonPath object***
     * First convert the API's response to String type with "asString()" method.
     * Then, send this String formatted json response to the JsonPath class and return the JsonPath
     */
    public static JsonPath getJsonPath(Response res) {
        String json = res.asString();
        //System.out.print("returned json: " + json +"\n");
        return new JsonPath(json);
    }
}

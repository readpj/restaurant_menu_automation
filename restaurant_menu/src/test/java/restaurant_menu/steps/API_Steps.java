package restaurant_menu.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class API_Steps {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    private String ENDPOINT_DISHES = "http://localhost:3000/api/dishes";


    @Given("^I want to get all the dishes in the restaurant$")
    public void iWantToGetAllTheDishesInTheRestaurant() throws Throwable {
        request = given();
    }

    @When("^I request the dishes$")
    public void iRequestTheDishes() throws Throwable {
        response = request.when().get(ENDPOINT_DISHES);
        System.out.println("response: " + response.prettyPrint());
    }

    @Then("^the dishes are returned$")
    public void theDishesAreReturned() throws Throwable {
        response.then().statusCode(200);
    }
}

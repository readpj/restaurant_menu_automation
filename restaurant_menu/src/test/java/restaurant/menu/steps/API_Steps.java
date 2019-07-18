package restaurant.menu.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import restaurant.menu.steps.service.Dish;
import restaurant.menu.steps.service.HelperMethods;
import restaurant.menu.steps.service.RestUtil;


import static io.restassured.RestAssured.given;

//@Component
public class API_Steps {

    private Response response = null;
    private ValidatableResponse json;
    private RequestSpecification request;
    private RequestSpecBuilder requestSpecBuilder;
    private JsonPath jsonPath = null;
    private String id = null;

    private String ENDPOINT_DISHES = "http://localhost:3000/api/dishes";
    // need to fix Spring dependency injection to parametise the baseURL
    //    @Value("${baseURL}")
    private String endpoint;

    public void setup() {
        RestUtil.setBaseURI("http://localhost:3000/api/");
        RestUtil.setBasePath("dishes");
        RestUtil.setContentType(ContentType.JSON);
        response = RestUtil.getResponse();
        jsonPath = RestUtil.getJsonPath(response);
    }


    @Given("^I want to get all the dishes in the restaurant$")
    public void iWantToGetAllTheDishesInTheRestaurant() throws Throwable {
        RestUtil.setBaseURI("http://localhost:3000/api/");
        RestUtil.setBasePath("dishes");
        RestUtil.setContentType(ContentType.JSON);
    }

    @When("^I request the dishes$")
    public void iRequestTheDishes() throws Throwable {
        response = RestUtil.getResponse();
        jsonPath = RestUtil.getJsonPath(response);
    }

    @Then("^the dishes are returned$")
    public void theDishesAreReturned() throws Throwable {
        HelperMethods.checkStatusIs200(response);
        System.out.println("response: " + response.prettyPrint());
    }

    @Given("^I want to add a dish$")
    public void iWantToAddADish() throws Throwable {
        request = given();
    }

    @When("^I add a dish$")
    public void iAddADish() throws Throwable {
        Dish dish = new Dish();
        dish.setName("Peas");
        dish.setPrice(3);
        response = request.contentType("application/json").body(dish).when().post(ENDPOINT_DISHES);
    }

    @Then("^the dish is added$")
    public void theDishIsAdded() throws Throwable {
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        String result = JSONResponseBody.getString("name");
        Assert.assertEquals(result, "Peas");
        id = JSONResponseBody.getString("id");
        System.out.println("ID is" + id);
    }


    @When("^I add the dish ([^\"]*) at a price of (\\d+) pounds$")
    public void iAddTheDishAtAPriceOfPounds(String dishName, int price) throws Throwable {
        Dish dish = new Dish();
        dish.setName(dishName);
        dish.setPrice(price);
        response = request.contentType("application/json").body(dish).when().post(ENDPOINT_DISHES);
    }


    @Then("^the dish ([^\"]*) at a price of (\\d+) pounds is added$")
    public void theDishAtAPriceOfPoundsIsAdded(String dishName, int dishPrice) throws Throwable {
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        String name = JSONResponseBody.getString("name");
        Assert.assertEquals(name, dishName);
        int cost = JSONResponseBody.getInt("price");
        Assert.assertEquals(cost, dishPrice);
        String id = JSONResponseBody.getString("id");
        System.out.println("ID is" + id);
    }

    @Given("^I want to find a dish$")
    public void iWantToFindADish() throws Throwable {
        iWantToAddADish();
        iAddADish();
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        id = JSONResponseBody.getString("id");
    }

    @When("^I attempt to find a dish$")
    public void iAttemptToFindADish() throws Throwable {
        request = given().pathParam("id", id);
        response = request.contentType("application/json").when().get(ENDPOINT_DISHES + "/{id}");
    }

    @Then("^the dish is returned$")
    public void theDishIsReturned() throws Throwable {
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        String dishId = JSONResponseBody.getString("id");
        Assert.assertEquals(dishId, id);
        System.out.println("ID is" + id);
        deleteDish(id);
    }


    @Given("^I want to delete a dish$")
    public void iWantToDeleteADish() throws Throwable {
        iWantToFindADish();
    }

    @When("^I delete a dish$")
    public void iDeleteADish() throws Throwable {
        deleteDish(id);
    }

    @Then("^the dish is deleted$")
    public void theDishIsDeleted() throws Throwable {
        HelperMethods.checkStatusIs200(response);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        int count = JSONResponseBody.getInt("count");
        Assert.assertEquals(count, 1);
    }

    private void deleteDish(String id) {
        request = given().pathParam("id", id);
        response = request.contentType("application/json").when().delete(ENDPOINT_DISHES + "/{id}");
    }
}
package restaurant.menu.steps.service;

import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class HelperMethods {

    public static void checkStatusIs200(Response res) {
        assertEquals("Status Check Failed!", 200, res.getStatusCode());
    }
}

package base.services;

import base.BaseApi;
import base.model.User;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * This Class contains all methods/actions needed by functions related to Subscription Service
 */
public class SubscriptionService {

    /**
     * Logger by Log4j2 declaration and initialization
     */
    private static final Logger LOGGER = LogManager.getLogger(SubscriptionService.class);

    /**
     * Constants definitions
     */
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String SUBSCRIPTION_CONTENT_TYPE = "application/json";

    /**
     * Local variables
     */
    private Response response;


    /**
     * This method send a GET request bases on an endpoint
     * Using get function from SerenityREST
     *
     * @param endpoint (String)
     */
    @Step("I get the endpoint by resource {string}")
    public void sendRequestByGet(String endpoint) {
        SerenityRest.get(endpoint);
    }

    /**
     * This method is used to send a GET request based on an endpoint
     *
     * @param endpoint (String)
     */
    @Step("I get the endpoint {string}")
    public void sendGetRequest(String endpoint) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .when()
                .get(endpoint);

        LOGGER.info("Send GET request --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method send a POST query based on a body request
     *
     * @param bodyRequest (String map that should contain the body request)
     */
    @Step("I send a POST query to {string} with header {string} and body {string}")
    public void sendPostQuery(String bodyRequest) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(bodyRequest)
                .post(new BaseApi().getEndpointByKey("my_endpoint"));

        LOGGER.info("Send POST request --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method send a POST query based on a body from JSON resource
     *
     * @param action
     * @param key
     */
    @Step("I send a POST query using resource with key {key}")
    public void sendPostQueryWithKey(String action, String key) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(new BaseApi().createRequestByJsonFile(action, key))
                .post(new BaseApi().getEndpointByKey("my_endpoint"));

        LOGGER.info("Send POST Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method send a POST query based on a body as String
     *
     * @param body
     */
    @Step("I send a POST query using body")
    public void sendPostQueryWithBody(Object body) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(body)
                .post(new BaseApi().getEndpointByKey("my_endpoint"));

        LOGGER.info("Send POST Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method DELETE a user by id returning the Response to compare the status code
     *
     * @param id
     * @return Response object to assert/compare response code
     */
    @Step("I send a DELETE query by id {int}")
    public Response sendDeleteQueryById(int id) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .when()
                .delete(new BaseApi().getEndpointByKey("my_endpoint") + "/" + id);

        LOGGER.info("Send DELETE Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
        return response;
    }

    /**
     * @param bodyRequest
     */
    @Step("I send a DELETE query to {string} with header {string} and body {string}")
    public void sendDeleteQuery(Map<String, String> bodyRequest) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(bodyRequest)
                .delete(new BaseApi().getEndpointByKey("my_endpoint"));

        LOGGER.info("Send DELETE Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method returns the list of users from the main service with all contained elements
     *
     * @return List of users from class User
     */
    @Step("I get the list of users from service")
    public List<User> getUserListFromService() {
        return SerenityRest.lastResponse().jsonPath().getList(".", User.class);
    }

    /**
     * This method returns the last user created with all content
     *
     * @return Last user created as User object
     */
    @Step("I Get last user from user list")
    public User getLastCreatedUser() {
        List<User> userListResponse = getUserListFromService();
        return userListResponse.get(userListResponse.size() - 1);
    }

    /**
     * This method updates a User by Id using body information
     *
     * @param body
     * @param id
     * @return Response Object - Response code
     */
    @Step("I UPDATE User by id using information")
    public Response updateUserById(Object body, int id) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(body)
                .put(new BaseApi().getEndpointByKey("my_endpoint") + "/" + id);

        LOGGER.info("Send UPDATE Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());

        return response;
    }

}

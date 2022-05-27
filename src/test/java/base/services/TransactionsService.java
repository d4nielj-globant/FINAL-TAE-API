package base.services;

import base.BaseApi;
import base.model.Transaction;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

// @TODO: Missing try catch block almost everywhere, requests will fail if they exceed the rate limit from the API.

public class TransactionsService {
    /**
     * Logger by Log4j2 declaration and initialization
     */
    private static final Logger LOGGER = LogManager.getLogger(TransactionsService.class);

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
     * List of all previous responses made during a given scenario
     */
    private static List<Response> responses = new ArrayList<>();
    public void addResponse(Response response) {
        responses.add(response);
    }
    public void cleanResponsesLists() {
        responses = new ArrayList<>();
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

        addResponse(response);
        LOGGER.info("Send GET request --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
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
                .post(new BaseApi().getEndpointByKey("transactions"));

        addResponse(response);
        LOGGER.info("Send POST Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * Updates a transaction on the transactions endpoint
     *
     * @param body : Transaction
     * @param id : int
     *
     * @author Daniel.Jaramillo01
     */
    public void sendPutQueryWithBody(Transaction body, int id) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(body)
                .put(new BaseApi().getEndpointByKey("transactions") + "/" + id);

        addResponse(response);
        LOGGER.info("Send POST Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * Returns the list of transactions from the main service with all contained elements
     *
     * @return List of transactions from class User
     * @todo missing try - catch block, exception will be raised if deserialization fails.
     */
    @Step("I get the list of transactions from service")
    public List<Transaction> getTransactionListFromService() {
        return SerenityRest.lastResponse().jsonPath().getList(".", Transaction.class);
    }

    /**
     * Deletes a transaction by ID in the transactions endpoint
     *
     * @param id : int
     * @return Response response
     */
    @Step("I DELETE a transaction by id {int}")
    public Response deleteTransactionById(int id) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .when()
                .delete(new BaseApi().getEndpointByKey("transactions") + "/" + id);

        addResponse(response);
        LOGGER.info("Send DELETE Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());

        return response;
    }

    /**
     * Verifies that all responses in the list match a given status code
     *
     * @param code : int
     * @return boolean
     */
    public boolean verifyResponsesCodeMatch(int code) {
        return responses.stream().allMatch(r -> r.statusCode() == code);
    }

    /**
     * creates a Transaction instance of the last response sent
     *
     * @return Transaction
     * @todo missing try - catch block, exception will be raised if deserialization fails.
     */
    public Transaction getLastTransactionSent() {
        Response lastResponse = responses.get(responses.size() - 1);
        return lastResponse.jsonPath().getObject(".", Transaction.class);
    }


}

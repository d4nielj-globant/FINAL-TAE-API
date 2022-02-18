package steps;

import base.BaseApi;
import base.model.User;
import base.services.SubscriptionService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.assertj.core.api.Assertions.assertThat;

public class SubscriptionSteps {

    @Steps
    SubscriptionService subscriptionService;

    @Given("I get the response from the endpoint {string}")
    public void iGetTheResponseFromTheEndpoint(String endpoint) {
        subscriptionService.sendGetRequest(endpoint);
    }

    @Given("I get the response from the endpoint")
    public void iGetTheResponseFromTheEndpoint() {
        subscriptionService.sendGetRequest("https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data");
    }

    @Given("I get the response from the endpoint file with key {string}")
    public void iGetTheResponseFromTheEndpointWithKey(String key) {
        subscriptionService.sendGetRequest(new BaseApi().getEndpointByKey(key));
    }

    @Given("I get the endpoint by resource {string}")
    public void iGetEndpointByResource(String key) {
        subscriptionService.sendRequestByGet(new BaseApi().getEndpointByKey(key));
    }

    @When("I compare following data against subscribed users")
    public void iCompareDataWithSubscribedUsers(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<User> user_list = subscriptionService.getUserListFromService();

        for (Map<String, String> columns : rows) {
            assertThat(user_list.stream().anyMatch(userFound -> userFound.getUser().equals(columns.get("user"))));
            assertThat(user_list.stream().anyMatch(userFound -> userFound.getEmail().equals(columns.get("email"))));
            assertThat(user_list.stream().anyMatch(userFound -> String.valueOf(userFound.isSubscription()).equals(columns.get("subscription"))));
        }
    }

    @Then("I get the response code equals to {}")
    public void iGetTheResponseCodeEqualsTo(int responseCode) {
        restAssuredThat(response -> response.statusCode(responseCode));
    }

    @When("I create a new user using POST request body based on data table")
    public void iSendAPOSTQueryToCreateANewUserFromDataTable(DataTable dataTable) {
        User userBody = new User();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            userBody.setUser(columns.get("user"));
            userBody.setEmail(columns.get("email"));
            userBody.setSubscription(Boolean.parseBoolean(columns.get("subscription")));

            subscriptionService.sendPostQueryWithBody(userBody);
        }
    }

    @When("I create a new user using POST request body string {string}")
    public void iSendAPOSTQueryToCreateANewUser(String requestBody) {
        subscriptionService.sendPostQuery(requestBody);
    }

    @When("I create a new user using resources with key {string}")
    public void iSendTheResponseFromTheEndpointFileWithKey(String key) {
        subscriptionService.sendPostQueryWithKey("create", key);
    }

    @When("I DELETE a user by id")
    public void iDeleteUserByIdDataTable(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            assertThat(subscriptionService.sendDeleteQueryById(Integer.parseInt(columns.get("id"))).equals("200"));
        }
    }

    @Then("I DELETE the last user created")
    public void iDeleteTheLastUserCreated() {
        assertThat(subscriptionService.sendDeleteQueryById(subscriptionService.getLastCreatedUser().getId()).equals("200"));
    }

    @Then("I UPDATE the user by id with information")
    public void iUpdateTheUserByIdWithInformation(DataTable dataTable) {
        User userBody = new User();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            userBody.setUser(columns.get("user"));
            userBody.setEmail(columns.get("email"));
            userBody.setSubscription(Boolean.parseBoolean(columns.get("subscription")));
            userBody.setId(Integer.parseInt(columns.get("id")));

            assertThat(subscriptionService.updateUserById(userBody, Integer.parseInt(columns.get("id"))).equals("200"));
        }
    }
}

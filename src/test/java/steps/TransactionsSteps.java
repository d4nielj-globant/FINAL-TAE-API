package steps;

import base.BaseApi;
import base.model.Transaction;
import base.services.TransactionsService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionsSteps {
    /**
     * Logger by Log4j2 declaration and initialization
     */
    private static final Logger LOGGER = LogManager.getLogger(TransactionsSteps.class);

    @Steps
    TransactionsService transactionsService;

    /**
     * Create a get request to endpoint by json key
     *
     * @param key : String, should be a correct endpoint in the resources json
     */
    @Given("I get the response from the endpoint file with key {string}")
    public void iGetTheResponseFromTheEndpointWithKey(String key) {
        transactionsService.sendGetRequest(new BaseApi().getEndpointByKey(key));
    }

    /**
     * Verify that endpoint has no transactions, deletes transactions if there are any.
     */
    @Then("I DELETE all transactions if resource is not empty")
    public void deleteAllUsersFromTransactionsIfNotEmpty() {
        List<Transaction> transactions = transactionsService.getTransactionListFromService();
        if (transactions.size() > 0) {
            for (Transaction transaction : transactions) {
                transactionsService.deleteTransactionById(transaction.getId());
            }
            transactionsService.sendGetRequest(new BaseApi().getEndpointByKey("transactions"));
            transactions = transactionsService.getTransactionListFromService();
        }
        Assert.assertEquals(0, transactions.size());
    }

    /**
     * Asserts that last response has the correct response status code.
     *
     * @param responseCode : int status code
     */
    @Then("I get the response code equals to {}")
    public void iGetTheResponseCodeEqualsTo(int responseCode) {
        restAssuredThat(response -> response.statusCode(responseCode));
    }

    /**
     * Creates n random new transactions
     *
     * @param n : int
     */
    @Given("I have {int} random transactions from different users")
    public void iHaveRandomTransactionsFromDifferentUsers(int n) {
        for (int i = 0; i < n; i++) {
            new Transaction();
        }
    }

    /**
     * Post all transactions that have been created as POJO's to the API
     */
    @When("I post the transactions to the resource")
    public void iPostTheTransactionsToTheResource() {
        List<Transaction> transactions = Transaction.getTransactions();
        for (Transaction transaction : transactions) {
            transactionsService.sendPostQueryWithBody(transaction);
        }
    }

    /**
     * Verifies that all responses have the expected status code
     * @param code : int status code
     */
    @Then("I verify that all transactions response codes equals {int}")
    public void iVerifyThatAllTransactionsResponseCodesEquals(int code) {
        assertTrue(transactionsService.verifyResponsesCodeMatch(code));
    }

    /**
     * Verifies that all transactions created as POJO's have different email accounts
     */
    @And("I verify that all transactions have different email accounts")
    public void iVerifyThatAllTransactionsHaveDifferentEmailAccounts() {
        Set<String> emails = new HashSet<>();
        boolean noRepeats = true;
        for (Transaction transaction : Transaction.getTransactions()) {
            if (!emails.contains(transaction.getEmail())) {
                emails.add(transaction.getEmail());
            } else {
                noRepeats = false;
                break;
            }
        }
        assertTrue(noRepeats);
    }

    /**
     * Sends a new transaction to the API to update in next steps
     */
    @Given("I have a transaction in the API that I want to update")
    public void iHaveATransactionInTheAPIThatIWantToUpdate() {
        Transaction transaction = new Transaction();
        transactionsService.sendPostQueryWithBody(transaction);
    }

    /**
     * Updates last transaction sent to the API with a different account number
     * @param accountNumber : String
     */
    @When("I update the account number with {string}")
    public void iUpdateTheAccountNumberWith(String accountNumber) {
        Transaction transaction = transactionsService.getLastTransactionSent();
        transaction.setAccountNumber(accountNumber);
        transactionsService.sendPutQueryWithBody(transaction, transaction.getId());
    }

    /**
     * Asserts that the last response from the API has indeed the new account number
     * @param accountNumber : String
     */
    @Then("I get the response with the updated account number {string}")
    public void iGetTheResponseWithTheUpdatedTransaction(String accountNumber) {
        Transaction transaction = transactionsService.getLastTransactionSent();
        assertEquals(transaction.getAccountNumber(), accountNumber);
    }


}

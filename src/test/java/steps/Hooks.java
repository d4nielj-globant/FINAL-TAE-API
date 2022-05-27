package steps;

import base.model.Transaction;
import base.services.TransactionsService;
import io.cucumber.java.Before;
import net.thucydides.core.annotations.Steps;

public class Hooks {
    @Steps
    TransactionsService transactionsService;

    /**
     * Set up for each scenario
     *
     * Cleaning both lists generated to keep track of responses and transactions
     * @author Daniel.Jaramillo01
     */
    @Before
    public void setUpScenario() {
        Transaction.cleanListOfTransactions();
        transactionsService.cleanResponsesLists();
    }
}

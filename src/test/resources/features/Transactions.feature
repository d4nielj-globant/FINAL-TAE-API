# POC Based on mock service simulating a bank transactions service

@transactionsFeature
Feature: POC Example - Bank transactions

  The transactions endpoint gives access to all transactions made from
  previous users. Including all CRUD methods.

  Scenario: Testing the API base endpoint
    Given I get the response from the endpoint file with key "transactions"
    Then I get the response code equals to 200

  Scenario: Verify accounts endpoint is empty, delete all data if need it
    Given I get the response from the endpoint file with key "transactions"
    When I get the response code equals to 200
    Then I DELETE all transactions if resource is not empty

  Scenario: Send given amount of random transactions to transactions endpoint
    Given I have 10 random transactions from different users
    When I post the transactions to the resource
    Then I verify that all transactions response codes equals 201
    And I verify that all transactions have different email accounts

  Scenario: Verify all transactions on endpoint have different emails
    Given I get the response from the endpoint file with key "transactions"
    When I get the response code equals to 200
    Then I verify that all transactions have different email accounts

  Scenario Outline: Update an existing transaction Account Number
    Given I have a transaction in the API that I want to update
    When I update the account number with <accountNumber>
    Then I get the response with the updated account number <accountNumber>

    Examples:
      | accountNumber  |
      | "30-9123-0912" |
      | "123120-33232" |
      | "19231293102"  |
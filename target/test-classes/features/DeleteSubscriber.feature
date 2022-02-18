# POC Based on mock service simulating a Subscription Service

# Delete information from API
Feature: POC Example - Delete actions


  # DELETE Request based on a key and using JSON resource
  Scenario: Delete the last user created
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I DELETE the last user created

  # DELETE Request based on a key and using JSON resource
  Scenario: Delete users by id
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I DELETE a user by id
      | id |
      | 1  |
      | 2  |
      | 3  |
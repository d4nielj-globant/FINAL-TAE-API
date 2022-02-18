# POC Based on mock service simulating a Subscription Service

# Update information from API
Feature: POC Example - Update actions


  # UPDATE User by data table
  Scenario: Update User based by id with data table information
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I UPDATE the user by id with information
      | user    | email                   | subscription | id |
      | Madison | Van_Windler@hotmail.com | false        | 11 |
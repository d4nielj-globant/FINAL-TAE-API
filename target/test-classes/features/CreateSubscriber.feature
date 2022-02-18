# POC Based on mock service simulating a Subscription Service

# User creation
Feature: POC Example - Put actions

  # Create a user based on a String - Request body
  Scenario: Create a new user based on body request
    When I create a new user using POST request body string "{\"user\": \"User\", \"email\": \"userfromjson@test.com\", \"subscription\": \"true\"}"
    Then I get the response code equals to 201

  # Create a user based on a data table
  Scenario: Create a new user based on body request
    When I create a new user using POST request body based on data table
      | user  | email            | subscription |
      | User1 | myUser1@test.com | true         |
      | User2 | myUser2@test.com | false        |
      | User3 | myUser3@test.com | true         |
    Then I get the response code equals to 201

  # Create a user based on resources by key (JSON file)
  Scenario: Create a new user based on JSON file (resource)
    When I create a new user using resources with key "userCreation"
    Then I get the response code equals to 201

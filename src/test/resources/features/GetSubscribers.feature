# POC Based on mock service simulating a Subscription Service

# Get information from API
Feature: POC Example - Get actions

  # Get request scenario using a implicit endpoint
  @myScenario
  Scenario: Testing an Endpoint - Get action using JSON resource
    Given I get the response from the endpoint
    Then I get the response code equals to 400

  # Get request scenario using endpoint by parameter
  Scenario: Testing an Endpoint - Get action using string parameter
    Given I get the response from the endpoint "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data"
    Then I get the response code equals to 200

  # Get request scenario using endpoint by parameter
  Scenario: Testing an Endpoint - Get action using string parameter by resource
    Given I get the endpoint by resource "my_endpoint"
    Then I get the response code equals to 200

  # Get request scenario using data table by parameter
  Scenario Outline: Testing an Endpoint - Get action using example table
    Given I get the response from the endpoint <Endpoint>
    Then I get the response code equals to <Status>

    Examples:
      | Endpoint                                                  | Status |
      | "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data" | 200    |
      | "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data" | 200    |
      | "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data" | 200    |


  # Get request scenario using data table by parameter with key
  Scenario Outline: Testing an Endpoint - Get action using example table with key
    Given I get the response from the endpoint file with key <Key>
    Then I get the response code equals to <Status>

    Examples:
      | Key            | Status |
      | "my_endpoint"  | 200    |
      | "my_endpoint2" | 200    |
      | "my_endpoint3" | 200    |


  # Get request scenario using data table comparing against service response body
  Scenario: Testing an Endpoint - Get action using example table comparing values
    Given I get the response from the endpoint "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data"
    When I get the response code equals to 200
    Then I compare following data against subscribed users
      | user  | email            | subscription |
      | User1 | myUser1@test.com | true         |
      | User2 | myUser2@test.com | false        |
      | User3 | myUser3@test.com | true         |
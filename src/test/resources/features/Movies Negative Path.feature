@API @NegativePath
Feature: Movies Negative Path

  Scenario: Verify user is unable to GET Movies with not matching methods
    When user verifies inability to retrieve movies with "POST" method
    And user verifies inability to retrieve movies with "PUT" method
    And user verifies inability to retrieve movies with "DELETE" method
    And user verifies inability to retrieve movies with "PATCH" method

    # Each test case sends request with invalid method and validates
    # that response code was 405 and message was 'Method not allowed'

  Scenario: Verify user is unable to get Movies with invalid parameters
    And user verifies getting 0 items when searching for "The 30-minute Java tutorial"
    And user verifies getting 0 items when skipping 300 items

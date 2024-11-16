@API @HappyPath
Feature: Movies Happy Path

  Scenario: Verify user is able to GET Movies
    Given user gets movies
    Then user verifies movies status code is 200
    And user verifies movies response structure and data types
    And user verifies movies total value is 96
    And user verifies all IDs are unique
    And user verifies titles may have duplicates
    And user verifies movies total quantity is equal to total value
    And user verifies IDs follow a valid UUID format for all items
    And user verifies getting 3 items when searching for "The Matrix"
    And user verifies getting 93 items when skipping 3 items

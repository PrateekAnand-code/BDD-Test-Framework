@api @smoke
Feature: Create users
  Scenario Outline: Create a valid user
    When I create a user named "<name>" with job "<job>"
    Then the response status is 201
    And the response contains user "<name>" with job "<job>"

    Examples:
      | name  | job       |
      | Jerry | Developer |
      | Tom   | Tester    |

  Scenario Outline: Reject incomplete users
    When I create a user named "<name>" with job "<job>"
    Then the response status is 400
    And the API error is "name and job are required"

    Examples:
      | name | job       |
      |      | Developer |
      | Tom  |           |

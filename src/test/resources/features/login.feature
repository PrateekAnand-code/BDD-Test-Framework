@ui @smoke
Feature: Login
  Scenario: Valid credentials grant access
    Given I open the login page
    When I sign in as "demo" with password "demo-password"
    Then the login message is "Welcome, demo"

  Scenario Outline: Invalid credentials are rejected
    Given I open the login page
    When I sign in as "<username>" with password "<password>"
    Then the login message is "Invalid credentials"

    Examples:
      | username | password      |
      | demo     | wrong         |
      | unknown  | demo-password |

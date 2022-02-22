Feature: API Demo

  @APIDemo
  Scenario Outline: API test demo
    When I create data with "<name>" and "<job>"
    Then I should get response "201"
    Then I should see the response that data is created with "<name>"

    Examples:
      | name  | job       |
      | jerry | developer |
      | tom   | tester    |
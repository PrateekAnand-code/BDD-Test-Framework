Feature: UI Demo

 @Demo
Scenario Outline: UI test demo
 Given I navigate to "https://the-internet.herokuapp.com/login"
 When I login with <username> and <password>
 Then I should see home page

 Examples:
 |username|password|
 |"tomsmith"|"SuperSecretPassword!"|
 |"invalidUser"|"pass"|
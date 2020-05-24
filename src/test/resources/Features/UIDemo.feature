Feature: UI Demo

 @Demo
Scenario Outline: UI test demo
 Given I navigate to "https://the-internet.herokuapp.com/login"
 When I login with <credentials>
 Then I should see home page

 Examples:
 |credentials|
 |"Valid Login"|
 |"InValid Login"|
@Ignore
Feature: In this Feature we are validating the tokenize payment with diffrent type of cards in chase payment API
 Description: In this Feature file we are validating the tokenize payment with chase API and Validate data in DataBase paymentRecons table of chase payment
  ScenarioID-SC_Billing_003
  1.In the Background we are Adding All type of card before the scenarios
  
  Background: User is Logged In
 Given User add Credit Card of Visa,Master and Discover type
 When user calls "PaymentProfile"  and "POST" http request
  Then the API call get with status codes "200"
  
  
  
   @Billing @Sanity
  Scenario Outline: Verify user able to add diffrent type of credit card payment method
    Given User add credit card with details to profile "<utilityAccNum>" and "<userName>" and "<cardType>" and "<cardNum>"
    When user calls "PaymentProfile"  and "POST" http request
    Then the API call get with status codes "200"

    Examples: 
      | utilityAccNum | userName       | cardType | cardNum          |
      |  411003717203 | john_richarson | Visa     | 4012888888881881 |
      |  411003717203 | john_richarson | Master   | 5454545454545454 |
      |  411003717203 | john_richarson | Discover | 6011000990139424 |

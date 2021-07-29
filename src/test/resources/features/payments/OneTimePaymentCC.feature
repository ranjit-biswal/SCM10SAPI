@Ignore
Feature: Validating One Time CC payment StepDefinationFile:StepDefinationOTPCC
  
  Description: In this Feature file we are validating one time payment with chase API and Validate data in DataBase paymentRecons table of chase payment
  ScenarioID-SC_Billing_001

    @Billing @Sanity
  Scenario Outline: Verify One time payment is Successful with Different Types of Credit card
    Given Add OneTimePayment Payload with "<CardType>" and "<ccAccountNum>" and "<ccExp>" and "<cardCVV>" and "<amount>" and "<serviceAccountNumber>"
    When user calls "AddOneTimePaymentCC" with "POST" http request
    Then the API call got success with status code "200"
    And User validate the "content.responseText" from the Response Message "<responseText>"
    And User validate the "content.successful" from the Response Message "<successful>"
    Then "statusCode" in response body "<statusCodes>"
    Then User validate "content.orderId" for "<statusCodes>" with DB details "<amount>"
    And User validate the "<CardType>" in DataBase paymentRecons Table
    And User validate the "content.convenienceFee" from Response with the DataBase paymentRecons Table with column "<ServiceFee_DB>"
    And User validate the "<serviceAccountNumber>" with the DataBase paymentRecons Table with column "<ServiceAccountNumber_DB>"
    And User validate the customerName with the DataBase paymentRecons Table with column "<CustomerName_DB>"

    Examples: 
      | CardType                  | ccAccountNum     | ccExp  | cardCVV | amount | serviceAccountNumber | statusCodes | responseText | successful | CustomerName_DB | OrderID_DB | ServiceAccountNumber_DB | ServiceFee_DB    |
      | Credit Card - VISA        | 4112344112344113 | 201212 |    1234 |  20.90 | R023069070           |         200 | Approved     | true       | CustomerName    | OrderId    | ServiceAccountNumber    | ServiceFeeAmount |
      | Credit Card - MASTER CARD | 5454545454545454 | 202011 |    1234 |  50.34 | R023069070           |         200 | Approved     | true       | CustomerName    | OrderId    | ServiceAccountNumber    | ServiceFeeAmount |
      | Credit Card - Discover    | 6011000990139424 | 202010 |    1234 |  38.46 | R023069070           |         200 | Approved     | true       | CustomerName    | OrderId    | ServiceAccountNumber    | ServiceFeeAmount |

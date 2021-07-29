@Ignore
Feature: Validating One Time Bank payment
  
   Description:In this Feature file we are validating one time payment Bank with chase API
  Validate data in DataBase paymentRecons table of chase payment
  ScenarioID-SC_Billing_002

  @Billing @Sanity
  Scenario Outline: Verify One time payment is Successful with Different Types of Bank
    Given Add OneTimePayment Payload with  "<amount>"
    When user calls "AddOneTimePaymentBank" with "POST"  http request
    And the API call got success with status code  "200"
    Then "statusCode" in response body is  "<statusCodes>"
    Then User validate  "content.orderId" for "<statusCodes>" with DB details "<amount>"
    And User  validate the "content.convenienceFee" from Response with the DataBase paymentRecons Table with column "<ServiceFee_DB>"
    And User  validate the customerName with the DataBase paymentRecons Table with column "<CustomerName_DB>"

    Examples: 
      | amount | statusCodes | CustomerName_DB | ServiceFee_DB    |
      |   3.56 |         200 | CustomerName    | ServiceFeeAmount |
      |   5.98 |         200 | CustomerName    | ServiceFeeAmount |

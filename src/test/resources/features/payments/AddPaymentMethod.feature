@Ignore
Feature: Validate user able to add payment methods and make Tokenize payment with the added card
  Description: In this Feature file we are validating Insert payment mode(Delete/Add) and make payment by the added card
  We are validating the tokenize payment API also on this Feature file

  @DeleteCreditCard @Billing @Sanity
  Scenario Outline: Verify user able to add diffrent type of credit card payment method
    Given User add credit card with details to profile "<utilityAccNum>" and "<userName>" and "<cardType>" and "<cardNum>"
    When user calls "PaymentProfile"  and "POST" http request
    Then the API call get with status codes "200"
    Then User validate the Message after adding card to the payment profile of the user"<message>"
    Given User Make payment with card type : "Visa" with amount : "20.89"
    When user calls "AddTokenizedPayment"  and "POST" http request
    Then the API call get with status codes "200"

    Examples: 
      | utilityAccNum | userName       | cardType | cardNum          | message                                                                                                 |
      |  411003717203 | john_richarson | Visa     | 4012888888881881 | Your card details have been added successfully and will be validated once you choose to make a payment. |

  @DeleteCreditCard @Billing @Sanity
  Scenario Outline: Verify user able to Master type of credit card payment method
    Given User add credit card with details to profile "<utilityAccNum>" and "<userName>" and "<cardType>" and "<cardNum>"
    When user calls "PaymentProfile"  and "POST" http request
    Then the API call get with status codes "200"
    Then User validate the Message after adding card to the payment profile of the user"<message>"
    Given User Make payment with card type : "Master" with amount : "20.89"
    When user calls "AddTokenizedPayment"  and "POST" http request
    Then the API call get with status codes "200"

    Examples: 
      | utilityAccNum | userName       | cardType | cardNum          | message                                                                                                 |
      |  411003717203 | john_richarson | Master   | 5454545454545454 | Your card details have been added successfully and will be validated once you choose to make a payment. |

  @DeleteCreditCard @Billing @Sanity
  Scenario Outline: Verify user able to Discover type of credit card payment method
    Given User add credit card with details to profile "<utilityAccNum>" and "<userName>" and "<cardType>" and "<cardNum>"
    When user calls "PaymentProfile"  and "POST" http request
    Then the API call get with status codes "200"
    Then User validate the Message after adding card to the payment profile of the user"<message>"
    Given User Make payment with card type : "Discover" with amount : "20.89"
    When user calls "AddTokenizedPayment"  and "POST" http request
    Then the API call get with status codes "200"

    Examples: 
      | utilityAccNum | userName       | cardType | cardNum          | message                                                                                                 |
      |  411003717203 | john_richarson | Discover | 6011000990139424 | Your card details have been added successfully and will be validated once you choose to make a payment. |

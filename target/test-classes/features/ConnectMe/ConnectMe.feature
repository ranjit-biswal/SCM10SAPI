#@Ignore
Feature: Validating Create Connect Me program type from 10S SCM API

  @ConnectMe @Sanity
  Scenario Outline: User validate Create connect Me after successfully getting Login token
    Given User enters Valid LoginID "<UserName>" and "<PassWord>" and "<AccountNumber>" and get the get the Login Token
    When User enters details for Connect me "SetConnectMeRequest" and "POST" the request for API
    And user validate API call got success  with status  code "<StatusCode>"

    Examples: 
      | UserName      | PassWord  | AccountNumber | StatusCode |
      | AutoUser17371 | Demo@12345 |  411002248606 |     200 |

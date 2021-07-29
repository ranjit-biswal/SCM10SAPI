@Ignore
Feature: Create and update outage and validate the message and DB

  # In this feature we are creating and updating outage and validating the responce
  # as well as the database whether it is created and updating in the database or
  # not
  @Outage @Sanity
  Scenario Outline: User should be able to create outage and validate the message and DB
    Given User create outage with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" and "<IsResolved>"
    When User calls  "CreateUpadateOutage" with "POST" http request
    Then API call got success for creating outage with status code "<StatusCode>" and "<Message>"
    And Outage info get saved with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" in database

    Examples: 
      | OutageInfo   | OutageCause    | OutageMessage                | StartTime           | EndTime             | IsResolved | StatusCode | Message                      |
      | Power Outage | Power Failure  | Outage due to windstorm      | 2020-09-26T13:36:55 | 2020-10-27T13:36:55 |          0 |        200 | Outage inserted successfully |
      | Water Outage | Water Shortage | Outage due to water shortage | 2020-09-26T13:36:55 | 2020-10-27T13:36:55 |          1 |        200 | Outage inserted successfully |
      | Gas Outage   | Gas Shortage   | Outage due to gas leakage    | 2020-09-26T13:36:55 | 2020-10-27T13:36:55 |          0 |        200 | Outage inserted successfully |

  @Outage
  Scenario Outline: User should be able to update outage and validate the message and DB
    Given User update outage with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" and "<IsResolved>"
    When User calls "CreateUpadateOutage" with the existing outage id and "POST" http request
    Then API call got success for updating outage with status code "<StatusCode>" and "<Message>"
    And Outage info get updated with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" in database.

    Examples: 
      | OutageInfo         | OutageCause    | OutageMessage                 | StartTime           | EndTime             | IsResolved | StatusCode | Message                     |
      | Power Outage 23455 | Power Failure  | Outage due to windstorm 23455 | 2020-09-26T13:36:55 | 2020-10-27T13:36:55 |          0 |        200 | Outage updated successfully |
      | Water Outage       | Water Shortage | Outage due to water shortage  | 2020-09-26T13:36:55 | 2020-10-27T13:36:55 |          0 |        200 | Outage updated successfully |
      | Gas Outage         | Gas Shortage   | Outage due to gas leakage     | 2020-09-26T13:36:55 | 2020-10-27T13:36:55 |          0 |        200 | Outage updated successfully |

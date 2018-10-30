Feature: OHS Tests

  Scenario: As a system (FWMT Gateway) I can receive final outcome of cases from TM
    Given TM sends a "LMS" case outcome to the Job Service
    And the response contains the outcome and caseId
    Then the message will be put on the queue to RM
    And the message is in the RM composite format




#    Given: the case outcome and the caseid has been passed from TM to the Gateway
#    When: The Gateway receives the case outcome
#    And: convert the outcome from TM message format to RM format  (composite message)
#    Then: the Gateway will immediately send the outcome to RM with the case id
Feature: OHS Tests

  Scenario: As a system (FWMT Gateway) I can receive final outcome of cases from TM
    Given the response contains the outcome and caseId
    And TM sends a "LMS" case outcome to the Job Service
    Then the message is in the RM composite format
    And the message will be put on the queue to RM



#    Given: the case outcome and the caseid has been passed from TM to the Gateway
#    When: The Gateway receives the case outcome
#    And: convert the outcome from TM message format to RM format  (composite message)
#    Then: the Gateway will immediately send the outcome to RM with the case id
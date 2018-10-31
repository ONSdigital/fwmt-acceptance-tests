Feature: Census Tests

  @Census @Acceptance
  Scenario: As a system (FWMT Gateway) I can receive final outcome of cases from TM
    Given TM sends a Census case outcome to the Job Service
    And the response is an Census job
    And the response contains the outcome and caseId
    Then the message will be put on the queue to RM
    And the message is in the RM composite format
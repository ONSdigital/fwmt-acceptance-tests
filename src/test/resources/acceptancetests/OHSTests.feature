Feature: OHS Tests

  Scenario: As a system (FWMT Gateway) I can receive final outcome of cases from TM
    Given TM sends a "LMS" case outcome to the Job Service
    And the response contains the outcome and caseId
    Then the message is in the RM composite format
    And the message will be put on the queue to RM

  @OHS
  Scenario: Process OHS Requests From RM
    Given RM sends LMS 5 case samples to the Gateway
    When Gateway transforms the data
    Then an LMS job is created
    And loaded in TM

  @OHS, @FWMT-261
  Scenario: Online completion calls Cancel OHS Job
    Given the respondent has completed online
    And the outcome is 'fully completed'
    When RM will notify gateway to all 'Stop visit'
    Then Gateway will instruct TM to 'stop all visits' related to the case

  @OHS
  Scenario: Process TM Final Outcome for OHS
    Given the case outcome and the caseid has been passed from TM to the Gateway
    When : The Gateway receives the case outcome
    And convert the outcome from TM message format to RM format
    Then the Gateway will immediately send the outcome to RM with the case id



#    Given: the case outcome and the caseid has been passed from TM to the Gateway
#    When: The Gateway receives the case outcome
#    And: convert the outcome from TM message format to RM format  (composite message)
#    Then: the Gateway will immediately send the outcome to RM with the case id
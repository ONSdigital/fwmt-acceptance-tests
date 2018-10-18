Feature: OHS Tests

  @OHS
  Scenario: Process OHS Requests From RM
    Given RM sends LMS 50 "create" case samples to the Gateway
    Then loaded in TM 50

  @OHS
  Scenario: Online completion calls Cancel OHS Job
    Given RM sends LMS 10 "cancel" case samples to the Gateway
    Then loaded in TM 10

  #@OHS
  #Scenario: Process TM Final Outcome for OHS
  #  Given the case outcome and the caseid has been passed from TM to the Gateway
  #  Then the Gateway will immediately send the outcome to RM with the case id
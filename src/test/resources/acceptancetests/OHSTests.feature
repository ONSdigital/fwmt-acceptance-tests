Feature: OHS Tests

  @OHS
  Scenario Outline: Process OHS Requests From RM
    Given RM sends OHS <jobs> <type> case samples to the Gateway
    Then loaded in TM <jobs>

  Examples:
  |type|jobs|
  |"create"|20|
  |"cancel"|5|

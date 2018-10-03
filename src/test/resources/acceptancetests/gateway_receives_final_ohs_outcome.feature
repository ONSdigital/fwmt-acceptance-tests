#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@OHS, @FWMT-454
Feature: gateway receives final ohs outcome
  As a system (FWMT Gateway) I can receive 'LMS' case outcomes from Totalmobile
	In order to: Update cases in RM with the final outcomes
  
  @OHS
  Scenario: Process TM Final Outcome for OHS 
    Given the case outcome and the caseid has been passed from TM to the Gateway
    When : The Gateway receives the case outcome
    And convert the outcome from TM message format to RM format  
    Then the Gateway will immediately send the outcome to RM with the case id

#    Examples: 
#      | name  | value | status  |
#      | name1 |     5 | success |
#      | name2 |     7 | Fail    |

#LMS Outcome types
#
#• Non-contact (Left contact card or not)
#• Ineligible/unknown eligibility (Reason)
#• Contact Made
#o Have completed
#o Will complete
#o Would complete, but needed a UAC NOTE – this is an outcome and does not generate a new UAC
#o Refuse




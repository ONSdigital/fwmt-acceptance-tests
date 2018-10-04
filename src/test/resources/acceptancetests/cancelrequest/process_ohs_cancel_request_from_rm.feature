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
@OHS, @FWMT-261
Feature: [OHS] As a system(Gateway) I can receive case update from RM to stop all visit (Case Completed Online)
This story covers the scenario where RM receives a 'completed' outcome from EQ or elsewhere, and no further field visit should occur in the field
In order to stop all visits

  @OHS, @FWMT-261
  Scenario: Online completion calls Cancel OHS Job 
    Given the respondent has completed online
    And the outcome is 'fully completed'
    When RM will notify gateway to all 'Stop visit'
    Then Gateway will instruct TM to 'stop all visits' related to the case

# Acceptance Criteria
# 
# The case will be removed from the interviewer’s worklist
# Report as part of MI
# The case ‘completed online’ are not deleted
# The solution shall ensure Gateway and TM interface synchronises within xx-sec for case completed online status update


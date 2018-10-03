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
@OHS, @FWMT-462
Feature: Receive and process OHS actions instructions from RM
  The gateway can receive 500 case samples (action Instructions) from RM in order to create OHS jobs 

  @OHS
  Scenario: Process OHS Requests From RM
    Given RM sends LMS 500 case samples to the Gateway
    When Gateway transforms the data
    Then an LMS job is created
    And loaded in TM

#Acceptance Criteria
#
#a) Convert RM feeds to TM job message
#b) Send created jobs to TM (MoD)
#c) The job will contain a job or caseid
#d) The job will have a start and end date
#e)  A job will be allocated to the right interviewer via TM MoD

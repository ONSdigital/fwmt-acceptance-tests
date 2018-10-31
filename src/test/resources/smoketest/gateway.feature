Feature: Smoke test for the FWMT gateway version 2

 @smoke1
 Scenario: smoke test gateway
    When Check RM Rabbit is running
    When Check Tmoblie is running
    When Check Rabbit is running
    When Check Job Service is running
    When Check RmAdpater is running




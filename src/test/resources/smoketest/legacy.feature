Feature: Smoke test for the FWMT gateway version 2

 @smoke2
 Scenario: smoke test legacy gateway
   When Check "ss" is running
   When Check "rs" is running
   When Check "tm" is running
   When Check "ls" is running




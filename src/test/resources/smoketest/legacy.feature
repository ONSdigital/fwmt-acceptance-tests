Feature: Smoke test for the FWMT gateway version 2

 @smoke2
 Scenario: smoke test legacy gateway
   When Check Staff Service is running
   When Check Resource Service is running
   When Check Tmoblie is running
   When Check Legacy Jobservice is running




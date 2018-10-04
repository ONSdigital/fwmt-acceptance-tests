Feature: Smoke test for the FWMT gateway version 2

 #@smoke1
 #Scenario: smoke test gateway
 #   When Check Tmoblie is running
 #   When Check Rabbit is running
 #   When Check Job Service is running
 #   When Check RmAdpater is running

 @smoke2
 Scenario: smoke test legacy gateway
   When Check Staff Service is running
   When Check Resource Service is running
   When Check Tmoblie is running
   When Check Legacy Jobservice is running




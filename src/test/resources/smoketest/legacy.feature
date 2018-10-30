Feature: Smoke test for the FWMT gateway version 2

 @smoke2
 Scenario: smoke test legacy gateway
   When Check "Staff Serivce" is running
   When Check "Resource Serivce" is running
   When Check "TMoblie" is running
   When Check "Legacy Job Serivce" is running




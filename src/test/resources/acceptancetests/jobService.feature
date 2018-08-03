Feature: Job service test when a CSV is submitted to the endpoint. The various responses that can be had with service.

  Scenario: A CSV file of type LFS will be submit, it will successfully be validated and ingested creating jobs of type LFS
    Given I have created a user in the gateway
    When I submit a sample CSV of type LFS named "sample_LFS_2018-06-01T01-01-01Z.csv"
    Then A database record for the job should contain TM id "quota_1 1 1 1 1 1 1 1 - 81K [Rissue_no_1]"
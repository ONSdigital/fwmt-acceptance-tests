Feature: Job service test when a CSV is submitted to the endpoint. The various responses that can be had with service.

  Scenario: A CSV file of type LFS will be submit, it will successfully be validated and ingested creating jobs of type LFS
    Given I have created the following test user in the gateway with authno "INT0" and TM username "user1"
    When I submit a sample CSV of type LFS named "sample_LFS_2018-06-01T01-01-01Z.csv"
    Then A database record for the job should contain TM id "quota_1 1 1 1 1 1 1 1 - 81K [Rissue_no_1]"

  Scenario: A CSV file of type LFS will be submit, it will successfully be validated and ingested creating jobs of type LFS
    Given I have created the following test user in the gateway with authno "INT1" and TM username "user2"
    When I submit a sample CSV of type LFS named "sample_GFF_2018-06-01T03-03-03Z"
    Then A database record for the job should contain TM id "tla_1-REISS1-1-826"
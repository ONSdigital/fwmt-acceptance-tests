Feature: Job service test when it gets a file or something

  Scenario: A CSV file of type LFS will be submit, it will successfully be validated and ingested creating jobs of type LFS
    Given I have submitted a sample CSV of type LFS named "sample_LFS_2018-06-01T01-01-01Z.csv"
    Then A database record for the job should contain TM id "LFS-"
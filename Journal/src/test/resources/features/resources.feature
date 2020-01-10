Feature: View & create resources
  Scenario: Get the list of resources
    When a get request is made to ' "/resources" '
    Then a response code of 200
Feature: Find restaurant dishes from the menu

  Scenario: Find all dishes
    Given I want to get all the dishes in the restaurant
    When I request the dishes
    Then the dishes are returned
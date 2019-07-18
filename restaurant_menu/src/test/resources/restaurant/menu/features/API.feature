Feature: Find restaurant dishes from the menu

  Scenario: Find all dishes
    Given I want to get all the dishes in the restaurant
    When I request the dishes
    Then the dishes are returned

  Scenario: Add a dish to the menu
    Given I want to add a dish
    When I add a dish
    Then the dish is added

  Scenario: Add a dish with to the menu (parametised)
    Given I want to add a dish
    When I add the dish Prawn Curry at a price of 7 pounds
    Then the dish Prawn Curry at a price of 7 pounds is added

  Scenario: Find an added dish
    Given I want to find a dish
    When I attempt to find a dish
    Then the dish is returned

  Scenario: Delete a dish from the menu
    Given I want to delete a dish
    When I delete a dish
    Then the dish is deleted
# language: en
@descriptionlists
Feature: Description lists
  In order to describe terms
  As a writer
  I want to be able to create description lists

  Scenario: Render a description list
    Given the Markdown source
    """
    Apple
    :   Pomaceous fruit of plants of the genus Malus in
    the family Rosaceae.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Apple::
      Pomaceous fruit of plants of the genus Malus in
      the family Rosaceae.
    """

  Scenario: Render a multiple description lists
    Given the Markdown source
    """
    Apple
    :   Pomaceous fruit of plants of the genus Malus in
    the family Rosaceae.

    Orange
    :   The fruit of an evergreen tree of the genus Citrus.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Apple::
      Pomaceous fruit of plants of the genus Malus in
      the family Rosaceae.
    Orange::
      The fruit of an evergreen tree of the genus Citrus.
    """

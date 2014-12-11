# language: en
@definitionlists
Feature: Definition lists
  In order to group content
  As a writer
  I want to be able to create definition lists

  Scenario: Render a definition list
    Given the Markdown source
    """
    Apple
    :   Pomaceous fruit of plants of the genus Malus in
    the family Rosaceae.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    [glossary]
    Apple::
        Pomaceous fruit of plants of the genus Malus in
    the family Rosaceae.
    """

  Scenario: Render a multiple definition lists
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
    [glossary]
    Apple::
        Pomaceous fruit of plants of the genus Malus in
    the family Rosaceae.
    Orange::
        The fruit of an evergreen tree of the genus Citrus.
    """




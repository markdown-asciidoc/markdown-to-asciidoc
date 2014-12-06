# language: en
@lists
Feature: Lists
  In order to group content
  As a writer
  I want to be able to create lists

  Scenario: Render an unordered list
    Given the Markdown source
    """
    * Item 1
    * Item 2
    * Item 3
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    * Item 1
    * Item 2
    * Item 3
    """

#  Scenario: Render an unordered nested list
#    Given the Markdown source
#    """
#    * Item 1
#        * Item 2
#        * Item 3
#    """
#    When it is converted to AsciiDoc
#    Then the result should match the AsciiDoc source
#    """
#    * Item 1
#    * Item 2
#    ** Item 2a
#    ** Item 2b
#    """

  Scenario: Render an ordered list
    Given the Markdown source
    """
    1. Item 1
    1. Item 2
    1. Item 3
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    1. Item 1
    1. Item 2
    1. Item 3
    """

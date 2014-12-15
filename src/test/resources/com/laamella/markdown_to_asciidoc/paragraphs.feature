# language: en
@paragraphs
Feature: Paragraphs
  In order to write normal content
  As a writer
  I want to be able to create discrete paragraphs

  Scenario: Render a paragraph with a single line
    Given the Markdown source
    """
    A paragraph with a single line.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    A paragraph with a single line.
    """

  Scenario: Render a paragraph with multiple lines
    Given the Markdown source
    """
    First line of paragraph.
    Second line of paragraph.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    First line of paragraph.
    Second line of paragraph.
    """

  Scenario: Render multiple paragraphs seperated by a blank line
    Given the Markdown source
    """
    First paragraph.

    Second paragraph.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    First paragraph.

    Second paragraph.
    """

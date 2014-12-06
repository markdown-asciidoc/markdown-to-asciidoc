# language: en
@tables
Feature: Tables
  In order to group content
  As a writer
  I want to be able to create tables

  Scenario: Render a table
    Given the Markdown source
    """
    | Name of Column 1 | Name of Column 2|
    | ---------------- | --------------- |
    | Cell in column 1, row 1 | Cell in column 2, row 1|
    | Cell in column 1, row 2 | Cell in column 2, row 2|
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    |===
    |Name of Column 1 |Name of Column 2

    |Cell in column 1, row 1 |Cell in column 2, row 1
    |Cell in column 1, row 2 |Cell in column 2, row 2
    |===
    """


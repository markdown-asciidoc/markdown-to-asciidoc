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

  Scenario: Render a table with left, center and right align columns
    Given the Markdown source
    """
    | Tables        | Are           |  Cool|
    | ------------- |:-------------:| ----:|
    | col 3 is      | right-aligned | $1600|
    | col 2 is      | centered      |   $12|
    | zebra stripes | are neat      |    $1|
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    [cols="<,^,>"]
    |===
    |Tables |Are |Cool

    |col 3 is |right-aligned |$1600
    |col 2 is |centered |$12
    |zebra stripes |are neat |$1
    |===
    """


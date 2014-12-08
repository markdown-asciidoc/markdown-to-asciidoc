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

#  @fixme @bug
#  Scenario: Render a markdown HTML table
#    Given the Markdown source
#    """
#    Care must be taken with slashes when specifying both the base URL and the relative URL as trailing and leading slashes have significant meaning. The following table illustrates the resolution of different types of URLs.
#
#    <table class="graybox" border="0" cellspacing="0" cellpadding="5">
#        <tr><th>Base</th><th>Navigating To</th><th>Result</th></tr>
#        <tr><td>http://myapp.com/</td><td>abc</td><td>http://myapp.com/abc</td></tr>
#        <tr><td>http://myapp.com</td><td>abc</td><td>http://myapp.comabc</td></tr>
#    </table>
#
#    It is usually most desirable to define your base urls with trailing slashes and not to use leading slashes on relative URLs.
#    """
#    When it is converted to AsciiDoc
#    Then the result should match the AsciiDoc source
#    """
#    Care must be taken with slashes when specifying both the base URL and the relative URL as trailing and leading slashes have significant meaning. The following table illustrates the resolution of different types of URLs.
#
#    |===
#    |Base |Navigating To |Result
#
#    |http://myapp.com/ | abc | http://myapp.com/abc
#    |http://myapp.com | abc | http://myapp.comabc
#    |===
#
#    It is usually most desirable to define your base urls with trailing slashes and not to use leading slashes on relative URLs.
#    """

#    @table
#  Scenario: Render a table with left, center and right align columns
#    Given the Markdown source
#    """
#    |              | Grouping                    ||
#    | First Header | Second Header | Third Header |
#    | ------------ | :-----------: | -----------: |
#    | Content      | *Long Cell*                 ||
#    | Content      | **Cell**      | Cell         |
#    | New section  | More          | Data         |
#    """
#    When it is converted to AsciiDoc
#    Then the result should match the AsciiDoc source
#    """
#    [cols="<,^,>"]
#    |===
#    |  2+| Grouping
#
#    | Content 2+| _Long Cell_
#    | Content   | *Cell* | Cell
#    | New section | More | Data
#    |===
#    """


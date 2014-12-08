# language: en
@code
Feature: Code
  In order to group content
  As a writer
  I want to be able to create code blocks

  Scenario: Render a code block without language
    Given the Markdown source
    """
    ```
    summary(cars$dist)
    summary(cars$speed)
    ```
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    [source]
    ----
    summary(cars$dist)
    summary(cars$speed)
    ----

    """

  Scenario: Render a code block without language with HTML
    Given the Markdown source
    """
    ```
    No language indicated, so no syntax highlighting.
    But let's throw in a <b>tag</b>.
    ```
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    [source]
    ----
    No language indicated, so no syntax highlighting.
    But let's throw in a <b>tag</b>.
    ----

    """

  Scenario: Render a javascript code block
    Given the Markdown source
    """
    ```javascript
    var s = "JavaScript syntax highlighting";
    alert(s);
    ```
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    [source,javascript]
    ----
    var s = "JavaScript syntax highlighting";
    alert(s);
    ----

    """

  Scenario: Render a python code block
    Given the Markdown source
    """
    ```python
    s = "Python syntax highlighting"
    print s
    ```
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    [source,python]
    ----
    s = "Python syntax highlighting"
    print s
    ----

    """

  Scenario: Render inline code
    Given the Markdown source
    """
    We defined the `add` function to
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    We defined the `add` function to
    """


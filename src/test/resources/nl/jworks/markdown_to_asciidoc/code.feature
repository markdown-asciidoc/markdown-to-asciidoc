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
    ----
    summary(cars$dist)
    summary(cars$speed)
    ----
    """

  Scenario: Render a code block without language containing HTML
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

  Scenario: Render an indented code block
    Given the Markdown source
    """
        $ gem install asciidoctor
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    ----
    $ gem install asciidoctor
    ----
    """

  Scenario: Render code block adjacent to preceding paragraph
    Given the Markdown source
    """
    Here's an example:
    ```javascript
    var s = "JavaScript syntax highlighting";
    alert(s);
    ```
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Here's an example:

    [source,javascript]
    ----
    var s = "JavaScript syntax highlighting";
    alert(s);
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

  Scenario: Render inline code with ellipsis using pass-through
    Given the Markdown source
    """
    Use `foo(...)` for that
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Use `+foo(...)+` for that
    """

  Scenario: Render inline code with arrow using pass-through
    Given the Markdown source
    """
    The `->` operator
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    The `+->+` operator
    """

  Scenario: Render inline code with double plus using pass macro
    Given the Markdown source
    """
    Use `a ++ b` in code
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Use `pass:c[a ++ b]` in code
    """

  Scenario: Render inline code with attribute reference using pass-through
    Given the Markdown source
    """
    Set `{foo}` value
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Set `+{foo}+` value
    """

  Scenario: Render no extra lines
    Given the Markdown source
    """
    foo

    ```kotlin
    println("bar")
    ```

    baz

    ```kotlin
    println("bam")
    ```
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    foo

    [source,kotlin]
    ----
    println("bar")
    ----

    baz

    [source,kotlin]
    ----
    println("bam")
    ----
    """


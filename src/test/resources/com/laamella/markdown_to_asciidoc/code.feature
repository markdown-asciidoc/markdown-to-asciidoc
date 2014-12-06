# language: en
@code
Feature: Code
  In order to group content
  As a writer
  I want to be able to create code blocks

  Scenario: Render a code block
    Given the Markdown source
    """
    ```{r}
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

  Scenario: Render inline code
    Given the Markdown source
    """
    We defined the `add` function to
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    We defined the +add+ function to
    """


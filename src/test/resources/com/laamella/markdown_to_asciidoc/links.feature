# language: en
@links
Feature: Links
  In order to group content
  As a writer
  I want to be able to create links

  Scenario: Render a link
    Given the Markdown source
    """
    This is [an example](http://example.com/) inline link.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    This is http://example.com/[an example] inline link.
    """

  Scenario: Render a link with a footnote
    Given the Markdown source
    """
    The [syntax page] [s] provides complete, detailed documentation for

    [s]: /projects/markdown/syntax  "Markdown Syntax"
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    The link:/projects/markdown/syntax[syntax page] provides complete, detailed documentation for
    """

  Scenario: Render a link with parameters
    Given the Markdown source
    """
    ![Alt text](images/icons/home.png)
    ![Alt text](images/icons/home.png?width=100)
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    image:images/icons/home.png[Alt text]
    image:images/icons/home.png?width=100[Alt text]
    """

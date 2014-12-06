# language: en
Feature: Markup
  In order to group content
  As a writer
  I want to be able to apply markup to text

  Scenario: Don't apply formatting
    Given the Markdown source
    """
    Normal text
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Normal text
    """

  Scenario: Make text bold
    Given the Markdown source
    """
    **Bold text**
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    *Bold text*
    """

  Scenario: Make text italic
    Given the Markdown source
    """
    *Italic text*
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    _Italic text_
    """

  Scenario: Make text mono
    Given the Markdown source
    """
    `Mono text`
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    +Mono text+
    """

  Scenario: Make text bold and italic
    Given the Markdown source
    """
    This is ***bold and italic*** text
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    This is *_bold and italic_* text
    """

  Scenario: Double angle bracket quoting
    Given the Markdown source
    """
    <<hello>>
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    «hello»
    """

  Scenario: Double quoting
    Given the Markdown source
    """
    "hello"
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    “hello”
    """


  Scenario: Single quoting
    Given the Markdown source
    """
    'hello'
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    ‘hello’
    """

  Scenario: Apostroph
    Given the Markdown source
    """
    a'a
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    a’a
    """

  Scenario: Ellipsis two
    Given the Markdown source
    """
    a...a
    a. . .a
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    a…a
    a…a
    """

  Scenario: Em Dash
    Given the Markdown source
    """
    a---a
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    a—a
    """

  Scenario: En Dash
    Given the Markdown source
    """
    a--a
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    a–a
    """

  Scenario: Nbsp
    Given the Markdown source
    """
    << a a >>
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    «{nbsp}a a{nbsp}»
    """


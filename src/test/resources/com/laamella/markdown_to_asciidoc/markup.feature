# language: en
@markup
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

#  @fixme
#  Scenario: Escaped characters
#    Given the Markdown source
#    """
#    \*this text is surrounded by literal asterisks\*
#    """
#    When it is converted to AsciiDoc
#    Then the result should match the AsciiDoc source
#    """
#    +++*this text is surrounded by literal asterisks*+++
#    """

  Scenario: Make text bold
    Given the Markdown source
    """
    **Bold text**
    __Bold text__
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    *Bold text*
    *Bold text*
    """

  Scenario: Make text italic
    Given the Markdown source
    """
    *Italic text*
    _Italic text_
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    _Italic text_
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
    `Mono text`
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

  Scenario: Blockquotes
    Given the Markdown source
    """
    > Blockquotes are very handy in email to emulate reply text.
    > This line is part of the same quote.

    Quote break.

    > This is a very long line that will still be quoted properly when it wraps. Oh boy let's keep writing to make sure this is long enough to actually wrap for everyone. Oh, you can *put* **Markdown** into a blockquote.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    ____
    Blockquotes are very handy in email to emulate reply text.
    This line is part of the same quote.
    ____

    Quote break.
    ____
    This is a very long line that will still be quoted properly when it wraps. Oh boy let's keep writing to make sure this is long enough to actually wrap for everyone. Oh, you can _put_ *Markdown* into a blockquote.
    ____

    """

  @blockquotes
  Scenario: Nested Blockquotes
    Given the Markdown source
    """
    > > What's new?
    >
    > I've got Markdown in my AsciiDoc!
    >
    > > Like what?
    >
    > * Blockquotes
    > * Headings
    > * Fenced code blocks
    >
    > > Is there more?
    >
    > Yep. AsciiDoc and Markdown share a lot of common syntax already.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    ____
    ________
    What's new?
    ________

    I've got Markdown in my AsciiDoc!
    ________
    Like what?
    ________

    * Blockquotes
    * Headings
    * Fenced code blocks
    ________
    Is there more?
    ________

    Yep. AsciiDoc and Markdown share a lot of common syntax already.
    ____

    """

  Scenario: Superscript
    Given the Markdown source
    """
    superscript^2^
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    superscript^2^
    """

  Scenario: Subscript
    Given the Markdown source
    """
    CO~2~
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    CO~2~
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
    "hello"
    """


  Scenario: Single quoting
    Given the Markdown source
    """
    'hello'
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    'hello'
    """

  Scenario: Apostroph
    Given the Markdown source
    """
    a'a
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    a'a
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

  Scenario: Strikethrough
    Given the Markdown source
    """
    This is ~~striked~~ text
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    This is [line-through]#striked# text
    """


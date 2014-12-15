# language: en
@links
Feature: Links
  In order to group content
  As a writer
  I want to be able to create links

  Scenario: Render an implicit inline link
    Given the Markdown source
    """
    Use [http://example.com](http://example.com) for sample links in documentation.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Use http://example.com for sample links in documentation.
    """

  Scenario: Render an inline link
    Given the Markdown source
    """
    This is [an example](http://example.com/) inline link.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    This is http://example.com/[an example] inline link.
    """

  Scenario: Render a reference style link with link definition
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

  Scenario: Render a reference style link with link text
    Given the Markdown source
    """
    The [syntax page] provides complete, detailed documentation for

    [syntax page]: http://www.syntaxpage.com
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    The http://www.syntaxpage.com[syntax page] provides complete, detailed documentation for
    """

  Scenario: Render an internal link using the cross reference syntax
    Given the Markdown source
    """
    Refer to [Quick start](#quick-start) to learn how to get started.
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    Refer to <<quick-start,Quick start>> to learn how to get started.
    """

  Scenario: Render an reference style image
    Given the Markdown source
    """
    ![Alt text][logo]

    [logo]: images/icons/home.png
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    image:images/icons/home.png[Alt text]
    """

  Scenario: Render an inline image with parameters
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

  Scenario: Render a hyperlinked inline image with alt text
    Given the Markdown source
    """
    [![Build Status](https://travis-ci.org/asciidoctor/asciidoctor.png)](https://travis-ci.org/asciidoctor/asciidoctor)
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    image:https://travis-ci.org/asciidoctor/asciidoctor.png[Build Status,link=https://travis-ci.org/asciidoctor/asciidoctor]
    """

  #failing
  #Scenario: Render a hyperlinked inline image with no alt text
  #  Given the Markdown source
  #  """
  #  [![](https://travis-ci.org/asciidoctor/asciidoctor.png)](https://travis-ci.org/asciidoctor/asciidoctor)
  #  """
  #  When it is converted to AsciiDoc
  #  Then the result should match the AsciiDoc source
  #  """
  #  image:https://travis-ci.org/asciidoctor/asciidoctor.png[link=https://travis-ci.org/asciidoctor/asciidoctor]
  #  """

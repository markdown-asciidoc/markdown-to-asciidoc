# language: en
@lists
Feature: Lists
  In order to group content
  As a writer
  I want to be able to create lists

  Scenario: Render an unordered list
    Given the Markdown source
    """
    * Item 1
    * Item 2
    * Item 3
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    * Item 1
    * Item 2
    * Item 3
    """

  Scenario: Render an unordered list of paragraphs
    Given the Markdown source
    """
    * Paragraph 1

    * Paragraph 2
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    * Paragraph 1

    * Paragraph 2
    """

  Scenario: Render an unordered nested list
    Given the Markdown source
    """
    * Item 1
        * Item 1_1
        * Item 1_2
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    * Item 1
    ** Item 1_1
    ** Item 1_2
    """

  Scenario: Render an ordered list
    Given the Markdown source
    """
    1. Item 1
    1. Item 2
    1. Item 3
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    . Item 1
    . Item 2
    . Item 3
    """

  Scenario: Render a nested ordered list
    Given the Markdown source
    """
    1. Item 1
        1. Item 1.1
        1. Item 1.2
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    . Item 1
    .. Item 1.1
    .. Item 1.2
    """

  Scenario: Render a nested ordered list combined with unordered list
    Given the Markdown source
    """
    1. Item 1
        1. Item 11
            * bullet 111
            * bullet 112
                * bullet 1121
                    1. Item 11211
        1. Item 12
    1. Item 2
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    . Item 1
    .. Item 11
    *** bullet 111
    *** bullet 112
    **** bullet 1121
    ..... Item 11211
    .. Item 12
    . Item 2
    """

  Scenario: Render an ordered list combined with a nested unordered list
    Given the Markdown source
    """
    1. Item 1

    2. Item 2

        * Subitem of Item 2

    3. Item 3
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    . Item 1

    . Item 2

    ** Subitem of Item 2

    . Item 3
    """

  Scenario: Render an ordered list of paragraphs
    Given the Markdown source
    """
    . Paragraph 1

    . Paragraph 2
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    . Paragraph 1

    . Paragraph 2
    """

  Scenario: Render an unordered list with a link
    Given the Markdown source
    """
    There is a Maven example project available.

    * [http://github.com/geb/geb-example-maven](https://github.com/geb/geb-example-maven)
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    There is a Maven example project available.

    * https://github.com/geb/geb-example-maven[http://github.com/geb/geb-example-maven]
    """

  Scenario: Render a list item that ends with a blank
    Given the Markdown source
    """
    -{sp}
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    *
    """

  Scenario: Render a multi-line list item with left-aligned continuation
    Given the Markdown source
    """
    * foo
        * bar
          baz
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    * foo
    ** bar
    baz
    """

  Scenario: Render a list without separating blank line
    Given the Markdown source
    """
    This is my typical list without any empty line for separation
    - first item
    - second item
    - third item
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    This is my typical list without any empty line for separation

    * first item
    * second item
    * third item
    """

#@@knownissue This doesn't work. Item 4 is contains 3 para nodes instead of a code block
#  Scenario: Render 4 numbered items with a code block
#    Given the Markdown source
#    """
#    1. Use the `browser` object explicitly (made available by the testing adapters)
#    2. Use the page instance returned by the `to()` and `at()` methods instead of calling through the browser
#    3. Use methods on the `Page` classes instead of the `content {}` block and dynamic properties
#    4. If you need to use content definition options like `required:` and `wait:` then you can still reference content elements defined using the DSL in methods on `Page` and `Module` classes as usual, e.g.:
#
#        static content = {
#            async(wait: true) { $(".async") }
#        }
#
#        String asyncText() {
#            async.text() // Wait here for the async definition to return a non-empty Navigator...
#        }
#    Using this “typed” style is not an all or nothing proposition.
#    """
#    When it is converted to AsciiDoc
#    Then the result should match the AsciiDoc source
#    """
#    . Use the `browser` object explicitly (made available by the testing adapters)
#    . Use the page instance returned by the `to()` and `at()` methods instead of calling through the browser
#    . Use methods on the `Page` classes instead of the `content {}` block and dynamic properties
#    . If you need to use content definition options like `required:` and `wait:` then you can still reference content elements defined using the DSL in methods on `Page` and `Module` classes as usual, e.g.:
#
#        static content = {
#            async(wait: true) { $(".async") }
#        }
#
#        String asyncText() {
#            async.text() // Wait here for the async definition to return a non-empty Navigator...
#        }
#
#    Using this “typed” style is not an all or nothing proposition.
#    """

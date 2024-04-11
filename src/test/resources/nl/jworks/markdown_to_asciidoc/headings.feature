# language: en
@headings
Feature: Headings
  In order to group content
  As a writer
  I want to be able to style heading

  Scenario: Render a level 1 heading
    Given the Markdown source
    """
    # Title #
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    = Title
    """

  Scenario: Render a level 1 underscored heading
    Given the Markdown source
    """
    Title
    =====
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    = Title
    """

  Scenario: Render a level 2 heading
    Given the Markdown source
    """
    ## Title
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    == Title
    """

  Scenario: Render a level 2 underscored heading
    Given the Markdown source
    """
    Title
    -----
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    == Title
    """

  Scenario: Render a level 3 heading
    Given the Markdown source
    """
    ## Title
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    == Title
    """
    
  Scenario: Render a level 4 heading
    Given the Markdown source
    """
    #### Title
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    ==== Title
    """
    
  Scenario: Render a level 5 heading
    Given the Markdown source
    """
    ##### Title
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    ===== Title
    """
    
  Scenario: Render a level 6 heading
    Given the Markdown source
    """
    ###### Title
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    ====== Title
    """

  Scenario: Render a heading with different styling
    Given the Markdown source
    """
    # Title ####
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    = Title
    """

# Doesn't work. See https://github.com/markdown-asciidoc/markdown-to-asciidoc/issues/60
#  Scenario: Render a heading with backticks
#    Given the Markdown source
#    """
    ## `what.ever.Foo`
#    """
#    When it is converted to AsciiDoc
#    Then the result should match the AsciiDoc source
#    """
#    === `what.ever.Foo`
#    """

# language: en
@lines
Feature: Lines
  In order to group content
  As a writer
  I want to be able to create horizontal lines

  Scenario: Create 4 horizontal lines
    Given the Markdown source
    """
    ---

    - - -

    ***

    * * *
    """
    When it is converted to AsciiDoc
    Then the result should match the AsciiDoc source
    """
    '''
    '''
    '''
    '''
    """

package nl.jworks.markdown_to_asciidoc.html;

import org.junit.Test;

import static org.junit.Assert.*;

public class TableToAsciiDocTest {

    @Test
    public void convertTable() {
        String html = """
                <table class="graybox" border="0" cellspacing="0" cellpadding="5">
                        <tr><th>Base</th><th>Navigating To</th><th>Result</th></tr>
                        <tr><td>http://myapp.com/</td><td>abc</td><td>http://myapp.com/abc</td></tr>
                        <tr><td>http://myapp.com</td><td>abc</td><td>http://myapp.comabc</td></tr>
                    </table>
                """;

        String expected = """
                |===
                |Base |Navigating To |Result

                |http://myapp.com/ |abc |http://myapp.com/abc
                |http://myapp.com |abc |http://myapp.comabc
                |===
                """;

        String actual = TableToAsciiDoc.convert(html);

        assertEquals(expected, actual);

    }

    @Test
    public void convertTableWithCode() {
        String html = """
                <table class="graybox" border="0" cellspacing="0" cellpadding="5">
                    <tr><th>Case Sensitive</th><th>Case Insensitive</th><th>Description</th></tr>
                    <tr><td><code>startsWith</code></td><td><code>iStartsWith</code></td><td>Matches values that start with the given value</td>
                    <tr><td><code>contains</code></td><td><code>iContains</code></td><td>Matches values that contain the given value anywhere</td>
                    <tr><td><code>endsWith</code></td><td><code>iEndsWith</code></td><td>Matches values that end with the given value</td>
                    <tr><td><code>containsWord</code></td><td><code>iContainsWord</code></td><td>Matches values that contain the given value surrounded by either whitespace or the beginning or end of the value</td>
                    <tr><td><code>notStartsWith</code></td><td><code>iNotStartsWith</code></td><td>Matches values that DO NOT start with the given value</td>
                    <tr><td><code>notContains</code></td><td><code>iNotContains</code></td><td>Matches values that DO NOT contain the given value anywhere</td>
                    <tr><td><code>notEndsWith</code></td><td><code>iNotEndsWith</code></td><td>Matches values that DO NOT end with the given value</td>
                    <tr><td><code>notContainsWord</code></td><td><code>iNotContainsWord</code></td><td>Matches values that DO NOT contain the given value surrounded by either whitespace or the beginning or end of the value</td>
                       \s
                </table>""";

        String expected = """
                |===
                |Case Sensitive |Case Insensitive |Description

                |`startsWith` |`iStartsWith` |Matches values that start with the given value
                |`contains` |`iContains` |Matches values that contain the given value anywhere
                |`endsWith` |`iEndsWith` |Matches values that end with the given value
                |`containsWord` |`iContainsWord` |Matches values that contain the given value surrounded by either whitespace or the beginning or end of the value
                |`notStartsWith` |`iNotStartsWith` |Matches values that DO NOT start with the given value
                |`notContains` |`iNotContains` |Matches values that DO NOT contain the given value anywhere
                |`notEndsWith` |`iNotEndsWith` |Matches values that DO NOT end with the given value
                |`notContainsWord` |`iNotContainsWord` |Matches values that DO NOT contain the given value surrounded by either whitespace or the beginning or end of the value
                |===
                """;

        String actual = TableToAsciiDoc.convert(html);

        assertEquals(expected, actual);

    }

    @Test
    public void convertTableWithLinks() {
        String html = """
                <table class="graybox" border="0" cellspacing="0" cellpadding="5">
                    <tr><th>Short Name</th><th>Driver</th></tr>
                    <tr>
                        <td><code>htmlunit</code></td>
                        <td><a href="http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/htmlunit/HtmlUnitDriver.html">org.openqa.selenium.htmlunit.HtmlUnitDriver</a></td>
                    </tr>
                    <tr>
                        <td><code>firefox</code></td>
                        <td><a href="http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/firefox/FirefoxDriver.html">org.openqa.selenium.firefox.FirefoxDriver</a></td>
                    </tr>
                    <tr>
                        <td><code>ie</code></td>
                        <td><a href="http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/ie/InternetExplorerDriver.html">org.openqa.selenium.ie.InternetExplorerDriver</a></td>
                    </tr>
                    <tr>
                        <td><code>chrome</code></td>
                        <td><a href="http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html">org.openqa.selenium.chrome.ChromeDriver</a></td>
                    </tr>
                </table>""";

        String expected = """
                |===
                |Short Name |Driver

                |`htmlunit` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/htmlunit/HtmlUnitDriver.html[org.openqa.selenium.htmlunit.HtmlUnitDriver]
                |`firefox` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/firefox/FirefoxDriver.html[org.openqa.selenium.firefox.FirefoxDriver]
                |`ie` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/ie/InternetExplorerDriver.html[org.openqa.selenium.ie.InternetExplorerDriver]
                |`chrome` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html[org.openqa.selenium.chrome.ChromeDriver]
                |===
                """;

        String actual = TableToAsciiDoc.convert(html);

        assertEquals(expected, actual);

    }
}
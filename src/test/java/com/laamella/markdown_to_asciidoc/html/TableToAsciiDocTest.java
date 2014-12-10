package com.laamella.markdown_to_asciidoc.html;

import org.junit.Test;

import static org.junit.Assert.*;

public class TableToAsciiDocTest {

    @Test
    public void convertTable() throws Exception {
        String html = "<table class=\"graybox\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
                "        <tr><th>Base</th><th>Navigating To</th><th>Result</th></tr>\n" +
                "        <tr><td>http://myapp.com/</td><td>abc</td><td>http://myapp.com/abc</td></tr>\n" +
                "        <tr><td>http://myapp.com</td><td>abc</td><td>http://myapp.comabc</td></tr>\n" +
                "    </table>\n";

        String expected = "|===\n" +
                "|Base |Navigating To |Result\n" +
                "\n" +
                "|http://myapp.com/ |abc |http://myapp.com/abc\n" +
                "|http://myapp.com |abc |http://myapp.comabc\n" +
                "|===\n";

        String actual = TableToAsciiDoc.convert(html);

        assertEquals(expected, actual);

    }

    @Test
    public void convertTableWithCode() throws Exception {
        String html = "<table class=\"graybox\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
                "    <tr><th>Case Sensitive</th><th>Case Insensitive</th><th>Description</th></tr>\n" +
                "    <tr><td><code>startsWith</code></td><td><code>iStartsWith</code></td><td>Matches values that start with the given value</td>\n" +
                "    <tr><td><code>contains</code></td><td><code>iContains</code></td><td>Matches values that contain the given value anywhere</td>\n" +
                "    <tr><td><code>endsWith</code></td><td><code>iEndsWith</code></td><td>Matches values that end with the given value</td>\n" +
                "    <tr><td><code>containsWord</code></td><td><code>iContainsWord</code></td><td>Matches values that contain the given value surrounded by either whitespace or the beginning or end of the value</td>\n" +
                "    <tr><td><code>notStartsWith</code></td><td><code>iNotStartsWith</code></td><td>Matches values that DO NOT start with the given value</td>\n" +
                "    <tr><td><code>notContains</code></td><td><code>iNotContains</code></td><td>Matches values that DO NOT contain the given value anywhere</td>\n" +
                "    <tr><td><code>notEndsWith</code></td><td><code>iNotEndsWith</code></td><td>Matches values that DO NOT end with the given value</td>\n" +
                "    <tr><td><code>notContainsWord</code></td><td><code>iNotContainsWord</code></td><td>Matches values that DO NOT contain the given value surrounded by either whitespace or the beginning or end of the value</td>\n" +
                "        \n" +
                "</table>";

        String expected = "|===\n" +
                "|Case Sensitive |Case Insensitive |Description\n" +
                "\n" +
                "|`startsWith` |`iStartsWith` |Matches values that start with the given value\n" +
                "|`contains` |`iContains` |Matches values that contain the given value anywhere\n" +
                "|`endsWith` |`iEndsWith` |Matches values that end with the given value\n" +
                "|`containsWord` |`iContainsWord` |Matches values that contain the given value surrounded by either whitespace or the beginning or end of the value\n" +
                "|`notStartsWith` |`iNotStartsWith` |Matches values that DO NOT start with the given value\n" +
                "|`notContains` |`iNotContains` |Matches values that DO NOT contain the given value anywhere\n" +
                "|`notEndsWith` |`iNotEndsWith` |Matches values that DO NOT end with the given value\n" +
                "|`notContainsWord` |`iNotContainsWord` |Matches values that DO NOT contain the given value surrounded by either whitespace or the beginning or end of the value\n" +
                "|===\n";

        String actual = TableToAsciiDoc.convert(html);

        assertEquals(expected, actual);

    }

    @Test
    public void convertTableWithLinks() throws Exception {
        String html = "<table class=\"graybox\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
                "    <tr><th>Short Name</th><th>Driver</th></tr>\n" +
                "    <tr>\n" +
                "        <td><code>htmlunit</code></td>\n" +
                "        <td><a href=\"http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/htmlunit/HtmlUnitDriver.html\">org.openqa.selenium.htmlunit.HtmlUnitDriver</a></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><code>firefox</code></td>\n" +
                "        <td><a href=\"http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/firefox/FirefoxDriver.html\">org.openqa.selenium.firefox.FirefoxDriver</a></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><code>ie</code></td>\n" +
                "        <td><a href=\"http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/ie/InternetExplorerDriver.html\">org.openqa.selenium.ie.InternetExplorerDriver</a></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><code>chrome</code></td>\n" +
                "        <td><a href=\"http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html\">org.openqa.selenium.chrome.ChromeDriver</a></td>\n" +
                "    </tr>\n" +
                "</table>";

        String expected = "|===\n" +
                "|Short Name |Driver\n" +
                "\n" +
                "|`htmlunit` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/htmlunit/HtmlUnitDriver.html[org.openqa.selenium.htmlunit.HtmlUnitDriver]\n" +
                "|`firefox` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/firefox/FirefoxDriver.html[org.openqa.selenium.firefox.FirefoxDriver]\n" +
                "|`ie` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/ie/InternetExplorerDriver.html[org.openqa.selenium.ie.InternetExplorerDriver]\n" +
                "|`chrome` |http://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html[org.openqa.selenium.chrome.ChromeDriver]\n" +
                "|===\n";

        String actual = TableToAsciiDoc.convert(html);

        assertEquals(expected, actual);

    }
}
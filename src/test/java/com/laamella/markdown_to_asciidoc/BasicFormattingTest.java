package com.laamella.markdown_to_asciidoc;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import static com.laamella.markdown_to_asciidoc.AsciiDocProcessor.convertMarkdownToAsciiDoc;
import static org.junit.Assert.assertEquals;

/**
 * See:
 *   http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/#horizontal-rules-and-page-breaks
 *   http://geog.uoregon.edu/bartlein/courses/geog607/Rmd/MDquick-refcard.pdf
 *   https://github.com/sirthias/pegdown/tree/master/src/test/resources/MarkdownTest103
 *
 * Created by erikp on 01/12/14.
 */
public class BasicFormattingTest {

    @Test
    public void testParagraph() {
        assertEquals("Hello world", convertMarkdownToAsciiDoc("Hello world"));
        assertEquals("Hello world\nHello world", convertMarkdownToAsciiDoc("Hello world\nHello world"));
        assertEquals("Hello world\n\nHello world", convertMarkdownToAsciiDoc("Hello world\n\nHello world"));
    }

    @Test
    public void testHeader1() {
        assertEquals("= This is an H1", convertMarkdownToAsciiDoc("# This is an H1"));
        assertEquals("= This is an H1", convertMarkdownToAsciiDoc("# This is an H1 #"));
        assertEquals("= This is an H1", convertMarkdownToAsciiDoc("This is an H1\n============="));
    }

    @Test
    public void testHeader2() {
        assertEquals("== This is an H2", convertMarkdownToAsciiDoc("## This is an H2"));
        assertEquals("== This is an H2", convertMarkdownToAsciiDoc("## This is an H2 ##"));
        assertEquals("== This is an H2", convertMarkdownToAsciiDoc("## This is an H2 #######"));
        assertEquals("== This is an H2", convertMarkdownToAsciiDoc("This is an H2\n-------------"));
    }

    @Test
    public void testHeader3() {
        assertEquals("=== This is an H3", convertMarkdownToAsciiDoc("### This is an H3"));
    }

    @Test
    public void testHeader4() {
        assertEquals("==== This is an H4", convertMarkdownToAsciiDoc("#### This is an H4"));
    }

    @Test
    public void testHeader5() {
        assertEquals("===== This is an H5", convertMarkdownToAsciiDoc("##### This is an H5"));
    }

    @Test
    public void testHeader6() {
        assertEquals("====== This is an H6", convertMarkdownToAsciiDoc("###### This is an H6"));
    }

    @Ignore // http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/#paragraphs.
    // Not sure how this translates. A single space seems fine, ==== might be okay too. Ideas?
    public void testBlockquotes() {
        assertEquals(" This is a blockquote", convertMarkdownToAsciiDoc("> This is a blockquote"));
    }

    @Test
    public void testBold() {
        assertEquals("*This text will be bold*", convertMarkdownToAsciiDoc("**This text will be bold**"));
    }
    @Test
    public void testItalic() {
        assertEquals("_This text will be italic_", convertMarkdownToAsciiDoc("*This text will be italic*"));
    }

    @Test
    public void testHorizontalLine() {
        assertEquals("'''", convertMarkdownToAsciiDoc("---"));
        assertEquals("'''", convertMarkdownToAsciiDoc("- - -"));
        assertEquals("'''", convertMarkdownToAsciiDoc("***"));
        assertEquals("'''", convertMarkdownToAsciiDoc("* * *"));
    }

    @Test
    public void testUnorderLists() {
        assertEquals("Test:\n" +
                "\n" +
                "* Item 1\n" +
                "* Item 2\n" +
                "* Item 3", convertMarkdownToAsciiDoc("Test:\n\n* Item 1\n* Item 2\n* Item 3"));
        assertEquals("* Item 1\n" +
                "* Item 2\n" +
                "  * Item 2a\n" +
                "  * Item 2b", convertMarkdownToAsciiDoc("* Item 1\n* Item 2\n  * Item 2a\n  * Item 2b"));
    }

    @Test
    public void testOrderedLists() {
        assertEquals("Test:\n\n1. Item 1", convertMarkdownToAsciiDoc("Test:\n\n1. Item 1"));
        assertEquals("Test:\n" +
                "\n" +
                "1. Item 1\n" +
                "1. Item 2\n" +
                "1. Item 3", convertMarkdownToAsciiDoc("Test:\n\n1. Item 1\n2. Item 2\n3. Item 3"));
        assertEquals("Test:\n" +
                "\n" +
                "1. Item 1\n" +
                "1. Item 2\n" +
                "1. Item 3", convertMarkdownToAsciiDoc("Test:\n\n1. Item 1\n1. Item 2\n1. Item 3"));
        assertEquals("1. Item 1\n" +
                "1. Item 2\n" +
                "1. Item 3\n" +
                "   * Item 3a\n" +
                "   * Item 3b", convertMarkdownToAsciiDoc("1. Item 1\n2. Item 2\n3. Item 3\n   * Item 3a\n   * Item 3b"));
    }

    @Test
    public void testLinks() {
        assertEquals("This is http://example.com/[an example] inline link.", convertMarkdownToAsciiDoc("This is [an example](http://example.com/) inline link."));
    }

    @Test
    public void testLinksWithParameters() {
        assertEquals("This is http://example.com?name=asciidoc&features=all[an example] inline link.", convertMarkdownToAsciiDoc("This is [an example](http://example.com?name=asciidoc&features=all) inline link."));
    }

    @Test
    public void testImage() {
        assertEquals("image:images/icons/home.png[Alt text]", convertMarkdownToAsciiDoc("![Alt text](images/icons/home.png)"));
        assertEquals("image:images/icons/home.png?width=100[Alt text]", convertMarkdownToAsciiDoc("![Alt text](images/icons/home.png?width=100)"));
    }

    @Test
    public void testCodeBlocks() {
        assertEquals("[source]\n----\nsummary(cars$dist)\nsummary(cars$speed)\n----\n", convertMarkdownToAsciiDoc("```{r}\n" +
                "summary(cars$dist)\n" +
                "summary(cars$speed)\n" +
                "```"));
    }

    @Test
    public void testInlineCode() {
        assertEquals("We defined the `add` function to", convertMarkdownToAsciiDoc("We defined the `add` function to"));
    }

    @Ignore
    public void testPlainTextBlocks() {
        assertEquals("", convertMarkdownToAsciiDoc(""));
    }

    @Ignore
    public void testReferenceStyleLinksAndImages () {
        assertEquals("", convertMarkdownToAsciiDoc("A [linked phrase][id].  \n" +
                "At the bottom of the document:\n" +
                "[id]: http://example.com/ \"Title\""));
    }

    @Test
    public void testTables() {
        String asciiDocTable = "|===\n" +
                "|Name of Column 1 |Name of Column 2 \n" +
                "\n" +
                "|Cell in column 1, row 1 |Cell in column 2, row 1 \n" +
                "|Cell in column 1, row 2 |Cell in column 2, row 2 \n" +
                "|===";

        assertEquals(asciiDocTable, convertMarkdownToAsciiDoc("| Name of Column 1 | Name of Column 2 |\n" +
                "| ---- | ----- |\n" +
                "| Cell in column 1, row 1 | Cell in column 2, row 1 |\n" +
                "| Cell in column 1, row 2 | Cell in column 2, row 2 |"));
    }

    @Test
    public void testSuperScript() {
        assertEquals("superscript^2^", convertMarkdownToAsciiDoc("superscript^2^"));
    }

    @Test
    public void testSubScript() {
        assertEquals("CO~2~", convertMarkdownToAsciiDoc("CO~2~"));
    }

    @Test
    public void testStrikeThrough() {
        // asciidoc doesn't support strike-through: http://asciidoc.googlecode.com/hg-history/8.2.6/doc/faq.txt
    }
}

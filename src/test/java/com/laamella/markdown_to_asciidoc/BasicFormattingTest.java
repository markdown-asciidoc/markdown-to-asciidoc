package com.laamella.markdown_to_asciidoc;

import org.junit.Test;

import static com.laamella.markdown_to_asciidoc.AsciiDocProcessor.convertMarkdownToAsciiDoc;
import static org.junit.Assert.assertEquals;

/**
 * Created by erikp on 01/12/14.
 */
public class BasicFormattingTest {

    @Test
    public void testParagraph() {
        assertEquals("Hello world", convertMarkdownToAsciiDoc("Hello world"));
        assertEquals("Hello world Hello world", convertMarkdownToAsciiDoc("Hello world\nHello world"));
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

    @Test
    public void testBold() {
        assertEquals("*This text will be bold*", convertMarkdownToAsciiDoc("**This text will be bold**"));
    }
    @Test
    public void testItalic() {
        assertEquals("_This text will be italic_", convertMarkdownToAsciiDoc("*This text will be italic*"));
    }

}

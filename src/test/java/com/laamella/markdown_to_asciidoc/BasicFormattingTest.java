package com.laamella.markdown_to_asciidoc;

import org.junit.Test;

import static com.laamella.markdown_to_asciidoc.AsciiDocProcessor.convertMarkdownToAsciiDoc;
import static org.junit.Assert.assertEquals;

/**
 * Created by erikp on 01/12/14.
 */
public class BasicFormattingTest {

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
}

package com.laamella.markdown_to_asciidoc;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Converts the testsuite from https://github.com/karlcow/markdown-testsuite (run cat-all.py) to asciidoc, checks the output
 */
public class TestSuite {
    @Test
    public void test() {
        PegDownProcessor processor = new PegDownProcessor();
        char[] markDown = readToCharArray("testsuite.md");
        RootNode rootNode = processor.parseMarkdown(markDown);
        String asciiDoc = new ToAsciiDocSerializer(new LinkRenderer()).toAsciiDoc(rootNode);
        assertEquals(readToString("testsuite.txt"), asciiDoc);
    }

    private char[] readToCharArray(String resourceName) {
        URL url = getClass().getResource("/" + resourceName);
        try {
            return IOUtils.toCharArray(url.openStream());
        } catch (IOException e) {
            fail();
            return null;
        }
    }

    private String readToString(String resourceName) {
        URL url = getClass().getResource("/" + resourceName);
        try {
            return IOUtils.toString(url.openStream());
        } catch (IOException e) {
            fail();
            return null;
        }
    }
}

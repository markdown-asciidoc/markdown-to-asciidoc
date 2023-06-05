package nl.jworks.markdown_to_asciidoc;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Converts the testsuite from https://github.com/karlcow/markdown-testsuite (run cat-all.py) to asciidoc, checks the output
 */
public class TestSuite {
    @Ignore
    @Test
    public void test() {
        String markDown = readToString("testsuite.md");
        String asciiDoc = Converter.convertMarkdownToAsciiDoc(markDown);
        assertEquals(readToString("testsuite.adoc"), asciiDoc);
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

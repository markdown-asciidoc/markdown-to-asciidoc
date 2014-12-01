package com.laamella.markdown_to_asciidoc;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/**
 * Created by erikp on 01/12/14.
 */
public class AsciiDocProcessor {

    public static String convertMarkdownToAsciiDoc(String markdown) {
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);
        char[] markDown = markdown.toCharArray();
        RootNode rootNode = processor.parseMarkdown(markDown);
        return new ToAsciiDocSerializer().toAsciiDoc(rootNode);
    }
}

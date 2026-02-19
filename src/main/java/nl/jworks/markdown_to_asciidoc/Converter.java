package nl.jworks.markdown_to_asciidoc;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

import java.io.*;
import java.util.regex.Pattern;

public class Converter {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("markdown_to_asciidoc: Please specify a file to convert");
            return;
        }

        File input = new File(args[0]);
        if (!input.exists()) {
            System.err.println("markdown_to_asciidoc: Cannot find the specified file to convert");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            reader.close();
            System.out.println(convertMarkdownToAsciiDoc(buffer.toString().trim()));
        } catch (IOException e) {
            System.err.println("markdown_to_asciidoc: An error occurred while reading the input file");
        }
    }

    // Matches lines that start a list item: -, *, +, or ordered (1. 2. etc.)
    private static final Pattern LIST_ITEM_PATTERN = Pattern.compile(
            "^(\\s*)([-*+]|\\d+\\.)\\s");

    public static String convertMarkdownToAsciiDoc(String markdown) {
        markdown = ensureBlankLineBeforeLists(markdown);
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL & ~Extensions.ANCHORLINKS);
        char[] markDown = markdown.toCharArray();
        RootNode rootNode = processor.parseMarkdown(markDown);
        return new ToAsciiDocSerializer(rootNode, markdown).toAsciiDoc();
    }

    /**
     * Inserts a blank line before a list that immediately follows a non-blank,
     * non-list line, so that pegdown recognizes it as a list.
     */
    static String ensureBlankLineBeforeLists(String markdown) {
        String[] lines = markdown.split("\n", -1);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            if (i > 0 && LIST_ITEM_PATTERN.matcher(lines[i]).find()) {
                String prevLine = lines[i - 1];
                // Insert blank line if previous line is non-blank and not itself a list item
                if (!prevLine.trim().isEmpty() && !LIST_ITEM_PATTERN.matcher(prevLine).find()) {
                    result.append('\n');
                }
            }
            if (i > 0) result.append('\n');
            result.append(lines[i]);
        }

        return result.toString();
    }
}

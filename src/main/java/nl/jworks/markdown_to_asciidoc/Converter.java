package nl.jworks.markdown_to_asciidoc;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

import java.io.*;

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

    public static String convertMarkdownToAsciiDoc(String markdown) {
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);
        char[] markDown = markdown.toCharArray();
        RootNode rootNode = processor.parseMarkdown(markDown);
        return new ToAsciiDocSerializer(rootNode, markdown).toAsciiDoc();
    }
}

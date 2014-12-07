package com.laamella.markdown_to_asciidoc;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;
import java.io.*;

public class Converter {

    public static void main(String[] args) {
        File input = null;
        if (args.length == 0) {
            System.err.println("markdown_to_asciidoc: Please specify a file to convert");
            return;
        }

        input = new File(args[0]);

        if (!input.exists()) {
            System.err.println("markdown_to_asciidoc: Cannot find the specified file to convert");
            return;
        }

        BufferedReader reader = null;
        try {
          reader = new BufferedReader(new FileReader(input));
          StringBuffer buffer = new StringBuffer();
          String line = null;
          while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
          }

          reader.close();
          System.out.println(convertMarkdownToAsciiDoc(buffer.toString().trim()));
        } catch (IOException e) {
          System.err.println("markdown_to_asciidoc: An error occurred while reading the input file");
        }
        finally {
          if (reader != null) {
            try {
              reader.close();
            }
            catch (IOException e2) {}
          }
        }
    }

    public static String convertMarkdownToAsciiDoc(String markdown) {
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);
        char[] markDown = markdown.toCharArray();
        RootNode rootNode = processor.parseMarkdown(markDown);
        return new ToAsciiDocSerializer().toAsciiDoc(rootNode);
    }
}

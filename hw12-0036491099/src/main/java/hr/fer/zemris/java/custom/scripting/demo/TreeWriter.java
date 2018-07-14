package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is used for testing SmartScriptParser. It gets paths to
 * SmartScript file through command line arguments. It parses SmartScript
 * file into syntax tree and then print file from the tree to console.
 */
public class TreeWriter {

    /**
     * Main mathod.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Argumenti nisu ispravno zadani!");
            System.exit(1);
        }

        String docBody = null;

        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get(args[0])),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            System.out.println("Putanja do datoteke nije ispravna!");
            System.exit(1);
        }

        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed " +
                    "this class!");
            System.exit(1);
        }
        DocumentNode document = parser.getDocumentNode();
        WriterVisitor visitor = new WriterVisitor();
        document.accept(visitor);

    }


    /**
     * This class represents implementation of {@link INodeVisitor} that is used for writing .smscr files to console.
     */
    private static class WriterVisitor implements INodeVisitor {

        /**
         * StringBuilder for constructing .smscr file.
         */
        StringBuilder sb = new StringBuilder();

        @Override
        public void visitTextNode(TextNode node) {
            sb.append(node.getText());
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            sb.append("{$ FOR ");
            sb.append(node.getVariable().asText());
            sb.append(" ");
            sb.append(node.getStartExpression().asText());
            sb.append(" ");
            sb.append(node.getEndExpression().asText());
            sb.append(" ");
            if (node.getStepExpression() != null) {
                sb.append(node.getStepExpression().asText());
                sb.append(" ");
            }
            sb.append("$}");

            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }

            sb.append("{$ END $}");

        }

        @Override
        public void visitEchoNode(EchoNode node) {

            sb.append("{$= ");

            Element[] echoNodeElements = node.getElements();

            for (Element echoNodeElement : echoNodeElements) {
                sb.append(echoNodeElement.asText());
                sb.append(" ");
            }

            sb.append("$}");
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
            System.out.println(sb.toString());
        }
    }


}

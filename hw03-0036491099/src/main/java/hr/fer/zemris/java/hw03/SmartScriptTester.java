package hr.fer.zemris.java.hw03;

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
public class SmartScriptTester {

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
        String originalDocumentBody = createOriginalDocumentBody(document);
        System.out.println(originalDocumentBody);

    }

    /**
     * This method is used for creating original document body from
     * DocumentNode.
     *
     * @param document DocumentNode
     * @return Original document file
     */
    public static String createOriginalDocumentBody(DocumentNode document) {
        return parseChildren(document);
    }


    /**
     * This method is used for creating original for loop from ForLoopNode.
     *
     * @param forLoop ForLoopNode
     * @return Original for loop
     */
    private static String parseForLoopNode(ForLoopNode forLoop) {
        StringBuilder sb = new StringBuilder();
        sb.append("{$ FOR ");
        sb.append(forLoop.getVariable().asText());
        sb.append(" ");
        sb.append(forLoop.getStartExpression().asText());
        sb.append(" ");
        sb.append(forLoop.getEndExpression().asText());
        sb.append(" ");
        if (forLoop.getStepExpression() != null) {
            sb.append(forLoop.getStepExpression().asText());
            sb.append(" ");
        }
        sb.append("$}");

        sb.append(parseChildren(forLoop));

        sb.append("{$ END $}");

        return sb.toString();
    }

    /**
     * This method is used for creating original children of given Node.
     *
     * @param node Parent node
     * @return Original string representation of children
     */
    private static String parseChildren(Node node) {
        StringBuilder sb = new StringBuilder();

        if (!(node instanceof DocumentNode || node instanceof ForLoopNode)) {
            System.out.println("Neispravno sintaksno stablo");
            System.exit(1);
        }

        for (int i = 0; i < node.numberOfChildren(); i++) {
            Node child = node.getChild(i);
            if (child instanceof ForLoopNode) {
                sb.append(parseForLoopNode((ForLoopNode) child));
            } else if (child instanceof TextNode) {
                sb.append(((TextNode) child).getText());
            } else if (child instanceof EchoNode) {
                sb.append(parseEchoNode((EchoNode) child));
            } else {
                System.out.println("Neispravno sintaksno stablo!");
                System.exit(1);
            }

        }

        return sb.toString();
    }

    /**
     * This method is used for creating original echo from EchoNode.
     *
     * @param echo EchoNode
     * @return Original String representation of echo Node
     */
    private static String parseEchoNode(EchoNode echo) {
        StringBuilder sb = new StringBuilder();
        sb.append("{$= ");

        Element[] echoNodeElements = echo.getElements();

        for (Element echoNodeElement : echoNodeElements) {
            sb.append(echoNodeElement.asText());
            sb.append(" ");
        }

        sb.append("$}");
        return sb.toString();
    }

}

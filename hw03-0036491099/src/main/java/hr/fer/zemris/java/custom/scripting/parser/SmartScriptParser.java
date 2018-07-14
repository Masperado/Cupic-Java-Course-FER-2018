package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents SmartScriptParser. It is used for parsing
 * SmartScript texts. It parses given SmartScript text into syntax tree which
 * are built from {@link Node} elements. Root node can be accessed from
 * {@link #getDocumentNode()}.
 */
public class SmartScriptParser {

    /**
     * Stack which is used when parsing Nodes that can have children.
     */
    private ObjectStack stack;

    /**
     * Lexer for generating tokens from text.
     */
    private SmartScriptLexer lexer;

    /**
     * Root node of generated syntax tree.
     */
    private DocumentNode document;

    /**
     * Constructor which parses SmartScript text into syntax tree.
     *
     * @param text SmartScript text
     */
    public SmartScriptParser(String text) {
        Objects.requireNonNull(text, "Text ne smije biti null!");

        this.stack = new ObjectStack();
        this.lexer = new SmartScriptLexer(text);
        this.document = new DocumentNode();
        this.stack.push(document);

        try {
            parse();
        } catch (Exception e) {
            throw new SmartScriptParserException(e.getMessage());
        }
    }

    /**
     * This method is used for parsing text into nodes.
     */
    private void parse() {

        // Infinite loop which reads token until EOF token ocurred.
        while (true) {
            if (lexer.getState() == SmartScriptLexerState.TEXT) {
                // Parsing in text state
                SmartScriptToken nextToken = lexer.nextToken();

                if (nextToken.getType() == SmartScriptTokenType.EOF) {
                    break;

                } else if (nextToken.getType() == SmartScriptTokenType.TEXT) {
                    // Parsing TextNode
                    TextNode textNode = new TextNode((String) nextToken
                            .getValue());
                    Node stackNode = (Node) stack.peek();
                    stackNode.addChildNode(textNode);

                } else if (nextToken.getType() == SmartScriptTokenType
                        .BEGINTAG) {
                    // When BEGINTAG token occured change lexer state into TAG
                    lexer.setState(SmartScriptLexerState.TAG);

                } else {
                    throw new SmartScriptParserException("Krivi TokenType " +
                            "unutar texta!");
                }
            } else if (lexer.getState() == SmartScriptLexerState.TAG) {
                // Parse tag
                parseTag();
            } else {
                throw new SmartScriptParserException("Nepoznati state!");
            }
        }

        // If there isn't only DocumentNode on stack when parsing is done,
        // given SmartScript text has invalid syntax.
        if (stack.size() != 1) {
            throw new SmartScriptParserException("Skripta ima nezatvorenih " +
                    "blokova!");
        }
    }

    /**
     * This method is used for parsing tags.
     */
    private void parseTag() {
        SmartScriptToken nextToken = lexer.nextToken();

        // Check type of tag
        if (nextToken.getType() == SmartScriptTokenType.TEXT) {
            String tagName = (String) nextToken.getValue();

            switch (tagName) {
                case "FOR":
                    // Parse for tag
                    parseFor();
                    break;
                case "=":
                    // Parse echo tag
                    parseEcho();
                    break;
                case "END":
                    // Parse echo tag
                    SmartScriptToken endToken = lexer.nextToken();
                    if (endToken.getType() == SmartScriptTokenType.ENDTAG) {
                        lexer.setState(SmartScriptLexerState.TEXT);
                        stack.pop();
                    } else {
                        throw new SmartScriptLexerException("End tag nije " +
                                "prazan!");
                    }
                    break;
                default:
                    throw new SmartScriptParserException("Nepoznati tag!");
            }
        } else {
            throw new SmartScriptParserException("Nije zadano ime taga!");
        }

        lexer.setState(SmartScriptLexerState.TEXT);
    }

    /**
     * This method is used for parsing for tag.
     */
    private void parseFor() {

        // Parse variable
        SmartScriptToken variableToken = lexer.nextToken();
        if (variableToken.getType() != SmartScriptTokenType.TEXT) {
            throw new SmartScriptParserException("FOR varijabla nije dobro " +
                    "zadana");
        }
        String variableName = (String) variableToken.getValue();

        if (!Character.isLetter(variableName.charAt(0))) {
            throw new SmartScriptParserException("Varijabla krivo započinje!");
        }

        ElementVariable variable = new ElementVariable(variableName);
        Element[] forLoopElements = new Element[3];

        // Parse elements and end tag
        for (int i = 0; i < 4; i++) {
            SmartScriptToken token = lexer.nextToken();

            if (i == 3 && token.getType() != SmartScriptTokenType.ENDTAG) {
                throw new SmartScriptParserException("Previše elemenata u FOR" +
                        " loopu!");
            }

            if (token.getType() == SmartScriptTokenType.ENDTAG) {
                if (i > 1) {
                    break;
                } else {
                    throw new SmartScriptParserException("Premalo elemenata u" +
                            " FOR loopu!");
                }
            } else {
                forLoopElements[i] = parseElement(token);
            }
        }

        // Create for loop node
        ForLoopNode forLoopNode = new ForLoopNode(variable,
                forLoopElements[0], forLoopElements[1], forLoopElements[2]);

        // Push it onto stack
        Node stackNode = (Node) stack.peek();
        stackNode.addChildNode(forLoopNode);
        stack.push(forLoopNode);
    }

    /**
     * This method is used for parsing echo tags.
     */
    private void parseEcho() {

        // Elements collection
        Collection elements = new ArrayIndexedCollection();

        // Parse elements until ENDTAG is occurred
        while (true) {
            SmartScriptToken token = lexer.nextToken();

            if (token.getType() == SmartScriptTokenType.ENDTAG) {
                if (elements.size() == 0) {
                    throw new SmartScriptParserException("Echo tag ne može " +
                            "biti prazan!");
                } else {
                    break;
                }
            }
            elements.add(parseElement(token));
        }

        // Create echo node and add it to stack
        Element[] echoElements = Arrays.copyOf(elements.toArray(), elements
                .size(), Element[].class);
        EchoNode echoNode = new EchoNode(echoElements);
        Node stackNode = (Node) stack.peek();
        stackNode.addChildNode(echoNode);

    }

    /**
     * This method is used for parsing {@link Element} from
     * {@link SmartScriptToken}.
     *
     * @param token {@link SmartScriptToken}
     * @return parsed {@link Element}
     */
    private Element parseElement(SmartScriptToken token) {
        if (token.getType() == SmartScriptTokenType.INTEGER) {
            int number = (int) token.getValue();
            return new ElementConstantInteger(number);
        } else if (token.getType() == SmartScriptTokenType.DOUBLE) {
            double number = (double) token.getValue();
            return new ElementConstantDouble(number);
        } else if (token.getType() == SmartScriptTokenType.TEXT) {
            String text = (String) token.getValue();

            if (text.equals("+") || text.equals("-") || text.equals("*") ||
                    text.equals("/") || text.equals("^")) {
                return new ElementOperator(text);
            } else if (text.startsWith("@")) {
                if (text.length() > 1 && Character.isLetter(text.charAt(1))) {
                    return new ElementFunction(text);
                }
            } else if (text.startsWith("\"")) {
                return new ElementString(text);
            } else {
                if (!Character.isLetter(text.charAt(0))) {
                    throw new SmartScriptParserException("Varijabla krivo " +
                            "započinje!");
                }

                return new ElementVariable(text);
            }
        }

        throw new SmartScriptParserException("Token nije dobrog tipa!");


    }

    /**
     * Getter for document(root) node.
     *
     * @return Document)root= node
     */
    public DocumentNode getDocumentNode() {
        return document;
    }
}

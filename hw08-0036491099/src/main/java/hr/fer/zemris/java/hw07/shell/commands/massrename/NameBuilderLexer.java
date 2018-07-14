package hr.fer.zemris.java.hw07.shell.commands.massrename;

import hr.fer.zemris.java.hw07.shell.ShellIOException;

/**
 * This class represents lexer for {@link NameBuilder}.
 */
public class NameBuilderLexer {

    /**
     * Data of lexer.
     */
    private char[] data;

    /**
     * Last generated token.
     */
    private NameBuilderToken currentToken;

    /**
     * Current index of lexer.
     */
    private int currentIndex;

    /**
     * Boolean flag for switching states between inside and outside of command.
     */
    private boolean insideCommand;

    /**
     * Basic constructor.
     *
     * @param izraz Izraz that will be lexed
     */
    public NameBuilderLexer(String izraz) {
        this.data = izraz.toCharArray();
    }

    /**
     * This method is used for generating next token.
     *
     * @return Next {@link NameBuilderToken}
     */
    public NameBuilderToken nextToken() {
        if (currentToken != null && currentToken.getType() == NameBuilderTokenType.EOF) {
            throw new ShellIOException("Can't generate tokens after EOF");
        }

        StringBuilder sb = new StringBuilder();
        NameBuilderTokenType currentType = NameBuilderTokenType.EOF;

        while (true) {
            if (currentIndex == data.length) {
                if (currentType != NameBuilderTokenType.EOF) break;

                currentToken = new NameBuilderToken(null, NameBuilderTokenType.EOF);
                return currentToken;
            }

            if (insideCommand) {
                skipSpaces();
                if (data[currentIndex] == ',') {
                    if (currentType != NameBuilderTokenType.EOF) {
                        break;
                    }
                    currentIndex++;
                    currentToken = new NameBuilderToken(",", NameBuilderTokenType.COMMA);
                    return currentToken;
                } else if (data[currentIndex] == '}') {
                    if (currentType != NameBuilderTokenType.EOF) {
                        break;
                    }
                    currentIndex++;
                    currentToken = new NameBuilderToken("}", NameBuilderTokenType.ENDCOMMAND);
                    return currentToken;
                } else if (Character.isDigit(data[currentIndex])) {
                    currentType = NameBuilderTokenType.NUMBER;
                    sb.append(data[currentIndex++]);
                } else {
                    throw new ShellIOException("Invalid command!");
                }
            } else {
                if (data[currentIndex] == '$') {
                    if (currentType != NameBuilderTokenType.EOF) {
                        break;
                    }
                    currentIndex++;
                    checkValidIndex();
                    if (data[currentIndex] != '{') {
                        throw new ShellIOException("Invalid start of command!");
                    }
                    currentIndex++;
                    currentToken = new NameBuilderToken("${", NameBuilderTokenType.BEGINCOMMAND);
                    return currentToken;
                }
                currentType = NameBuilderTokenType.STRING;
                sb.append(data[currentIndex++]);
            }

        }

        currentToken = new NameBuilderToken(sb.toString(), currentType);

        return currentToken;
    }

    /**
     * Setter for inside command flag.
     *
     * @param insideCommand New value of inside command flag
     */
    public void setInsideCommand(boolean insideCommand) {
        this.insideCommand = insideCommand;
    }

    /**
     * This method checks if we are still in valid index.
     */
    private void checkValidIndex() {
        if (currentIndex > data.length) {
            throw new ShellIOException("Index out of bounds in izraz!");
        }

    }

    /**
     * This method is used to check if data at current index is space character
     *
     * @return True if it is, false otherwise
     */
    private boolean checkIfSpace() {
        return data[currentIndex] == ' ' || data[currentIndex] == '\r' ||
                data[currentIndex] ==
                        '\t' ||
                data[currentIndex] == '\n';
    }

    /**
     * This method is used for skipping spaces.
     */
    private void skipSpaces() {
        while (true) {
            if (currentIndex < data.length && checkIfSpace()) {
                currentIndex++;
            } else {
                break;
            }
        }
    }


}

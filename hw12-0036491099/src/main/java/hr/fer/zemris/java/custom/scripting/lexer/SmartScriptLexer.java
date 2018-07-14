package hr.fer.zemris.java.custom.scripting.lexer;


import java.util.Objects;

/**
 * This class represents SmartScriptLexer. It is used for parsing text into
 * SmartScriptTokens following lexer rules specified in Problem 2. It
 * supports two states, TEXT and TAG. Lexer is lazy, which
 * means it will only tokenize input when needed.
 */
public class SmartScriptLexer {

    /**
     * Last generated token.
     */
    private SmartScriptToken token;

    /**
     * Char array of input text.
     */
    private char[] data;

    /**
     * Current lexer state.
     */
    private SmartScriptLexerState state;

    /**
     * Index of first not parsed character in array.
     */
    private int currentIndex;

    /**
     * Basic constructor.
     *
     * @param text Text that will be parsed into tokens
     */
    public SmartScriptLexer(String text) {
        Objects.requireNonNull(text, "Data ne smije biti null!");

        this.data = text.toCharArray();
        this.state = SmartScriptLexerState.TEXT;
    }

    /**
     * This method is used for generating nextToken based on current state.
     *
     * @return Generated token
     */
    public SmartScriptToken nextToken() {
        switch (state) {
            case TAG:
                return parseTag();
            case TEXT:
                return parseText();
        }

        throw new SmartScriptLexerException("State nije poznat!");

    }

    /**
     * This method is used for generating token when lexer is in TEXT state.
     *
     * @return Generated token
     */
    private SmartScriptToken parseText() {

        // Check if we want to generate tokens after EOF
        if (token != null && token.getType() == SmartScriptTokenType.EOF) {
            throw new SmartScriptLexerException(("Nije moguća daljnje " +
                    "generiranje tokena nakon EOF!"));
        }

        StringBuilder sb = new StringBuilder();

        while (true) {

            // Check if there isn't more input
            if (currentIndex == data.length) {
                break;
            }

            if (data[currentIndex] == '\\') {
                // Parsing escape sequences
                currentIndex++;
                checkValidIndex();

                if (data[currentIndex] == '\\' || data[currentIndex] == '{') {
                    sb.append(data[currentIndex++]);
                } else {
                    throw new SmartScriptLexerException("Nepravilno " +
                            "escapanje!");
                }

            } else if (data[currentIndex] == '{') {
                // Parsing tag begging

                if (sb.length() > 0) {
                    break;
                }

                sb.append(data[currentIndex++]);
                checkValidIndex();

                if (data[currentIndex] == '$') {
                    sb.append(data[currentIndex++]);
                    break;
                } else {
                    throw new SmartScriptLexerException("Nepravilan ulazak u " +
                            "tag!");
                }
            } else {
                sb.append(data[currentIndex++]);
            }
        }

        // Converting parsed characters into tokens
        if (sb.length() == 0) {
            token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
        } else {
            if (sb.toString().equals("{$")) {
                token = new SmartScriptToken(SmartScriptTokenType.BEGINTAG,
                        sb.toString());
            } else {
                token = new SmartScriptToken(SmartScriptTokenType.TEXT, sb
                        .toString());
            }
        }

        return token;

    }

    /**
     * This method is used for generating tokens when lexer is in EXTENDED
     * state.
     *
     * @return Generated token
     */
    private SmartScriptToken parseTag() {

        // Check if we want to generate token after EOF
        if (token != null && token.getType() == SmartScriptTokenType.EOF) {
            throw new SmartScriptLexerException(("Nije moguća daljnje " +
                    "generiranje tokena nakon EOF!"));
        }

        StringBuilder sb = new StringBuilder();
        SmartScriptTokenType currentType = SmartScriptTokenType.EOF;

        // Flag which tells us if we are inside quotes
        boolean insideQuotes = false;

        // Skip empty lines
        skipSpaces();

        // Parsing echo tag name
        if (currentIndex < data.length && data[currentIndex] == '=') {
            token = new SmartScriptToken(SmartScriptTokenType.TEXT, "=");
            currentIndex++;
            return token;
        }

        while (true) {

            // Check if there isn't more input
            if (currentIndex == data.length) {
                break;
            }

            if (insideQuotes) {
                // Parsing inside qoutes

                if (data[currentIndex] == '\\') {
                    currentIndex++;
                    checkValidIndex();
                    if (data[currentIndex] == '\\' || data[currentIndex] ==
                            '"') {
                        sb.append(data[currentIndex++]);
                    } else if (data[currentIndex] == 'n') {
                        sb.append("\n");
                        currentIndex++;
                    } else if (data[currentIndex] == 'r') {
                        sb.append("\r");
                        currentIndex++;
                    } else if (data[currentIndex] == 't') {
                        sb.append("\t");
                        currentIndex++;
                    } else {
                        throw new SmartScriptLexerException("Nepravilno " +
                                "escapanje!");
                    }
                } else if (data[currentIndex] == '"') {
                    sb.append(data[currentIndex++]);
                    break;
                } else {
                    sb.append(data[currentIndex++]);
                }

            } else if (data[currentIndex] == '$') {
                // Parsing end of tag
                if (currentType != SmartScriptTokenType.EOF) {
                    break;
                }

                sb.append(data[currentIndex++]);
                checkValidIndex();

                if (data[currentIndex] == '}') {
                    sb.append(data[currentIndex++]);
                    currentType = SmartScriptTokenType.ENDTAG;
                    break;
                } else {
                    throw new SmartScriptLexerException("Nepravilan izlazak " +
                            "iz taga!");
                }

            } else if (data[currentIndex] == '.' && currentType ==
                    SmartScriptTokenType.INTEGER) {
                // Parsing double constant
                sb.append(data[currentIndex++]);
                currentType = SmartScriptTokenType.DOUBLE;

            } else if (Character.isDigit(data[currentIndex])) {
                // Parsing integer or double constant
                if (currentType == SmartScriptTokenType.EOF) {
                    currentType = SmartScriptTokenType.INTEGER;
                } else if (!(currentType == SmartScriptTokenType.INTEGER ||
                        currentType == SmartScriptTokenType.DOUBLE)) {
                    break;
                }

                sb.append(data[currentIndex++]);

            } else if (data[currentIndex] == '"') {
                // Parsing start of qoute
                if (currentType == SmartScriptTokenType.EOF) {
                    currentType = SmartScriptTokenType.TEXT;
                } else if (currentType != SmartScriptTokenType.TEXT) {
                    break;
                }
                insideQuotes = true;
                sb.append(data[currentIndex++]);
            } else if (checkIfSpace()) {
                break;
            } else {
                // Parsing text
                if (currentType == SmartScriptTokenType.EOF) {
                    currentType = SmartScriptTokenType.TEXT;
                } else if (currentType != SmartScriptTokenType.TEXT) {
                    break;
                }

                sb.append(data[currentIndex++]);
            }
        }

        // Converting parsed characters into tokens
        switch (currentType) {
            case TEXT:
                token = new SmartScriptToken(currentType, sb.toString());
                break;
            case INTEGER:
                try {
                    int number = Integer.parseInt(sb.toString());
                    token = new SmartScriptToken(currentType, number);
                } catch (NumberFormatException ex) {
                    throw new SmartScriptLexerException("Nepravilan integer!");
                }
                break;
            case DOUBLE:
                try {
                    double number = Double.parseDouble(sb.toString());
                    token = new SmartScriptToken(currentType, number);
                } catch (NumberFormatException ex) {
                    throw new SmartScriptLexerException("Nepravilan double!");
                }
                break;
            case ENDTAG:
                token = new SmartScriptToken(currentType, sb.toString());
                break;
            case EOF:
                throw new SmartScriptLexerException("Nepravilan tag!");
        }

        return token;

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

    /**
     * This method is used to check if we are still in valid index
     */
    private void checkValidIndex() {
        if (currentIndex >= data.length) {
            throw new SmartScriptLexerException("Tokeniziranje nakon kraja " +
                    "texta!");
        }
    }

    /**
     * This method is used for getting last generated token.
     *
     * @return Last generated token
     */
    public SmartScriptToken getToken() {
        return token;
    }

    /**
     * Setter for lexer state.
     *
     * @param state New state
     */
    public void setState(SmartScriptLexerState state) {
        Objects.requireNonNull(state, "State ne može biti null!");

        this.state = state;
    }

    /**
     * Getter for lexer state.
     *
     * @return Current state
     */
    public SmartScriptLexerState getState() {
        return state;
    }
}

package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents lexer. It is used for parsing text into Tokens
 * following lexer rules specified in Problem 1. It supports two states,
 * BASIC and EXTENDED. Lexer is lazy, which
 * means it will only tokenize input when needed.
 */
public class Lexer {

    /**
     * Char array of input text.
     */
    private char[] data;

    /**
     * Last token that was generated.
     */
    private Token token;

    /**
     * Index of first not parsed character in array.
     */
    private int currentIndex;

    /**
     * Current lexer state.
     */
    private LexerState state;

    /**
     * Basic constructor.
     *
     * @param text Text that will be parsed into tokens
     */
    public Lexer(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text ne smije biti null!");
        }

        this.data = text.toCharArray();
        this.state = LexerState.BASIC;
    }

    /**
     * This method is used for generating nextToken based on current state.
     *
     * @return Generated token
     */
    public Token nextToken() {
        switch (state) {
            case BASIC:
                return parseBasic();
            case EXTENDED:
                return parseExtended();
        }

        throw new LexerException("State nije poznat!");
    }

    /**
     * This method is used for generating tokens when lexer is in BASIC state.
     *
     * @return Generated token
     */
    private Token parseBasic() {

        // Check if we want to generate tokens after EOF
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException("Nije moguća daljnje generiranje tokena " +
                    "nakon EOF!");
        }

        // Skip empty lines
        skipSpaces();

        // Default tokenType is EOF
        StringBuilder sb = new StringBuilder();
        TokenType currentType = TokenType.EOF;

        // Read character until you are sure which token is generated
        while (true) {
            // Check if there isn't more input
            if (currentIndex == data.length) {
                break;
            }

            if (Character.isLetter(data[currentIndex])) {
                //Parsing words
                if (currentType == TokenType.EOF) {
                    currentType = TokenType.WORD;
                } else if (currentType != TokenType.WORD) {
                    break;
                }

                sb.append(data[currentIndex++]);

            } else if (data[currentIndex] == '\\') {
                //parsing escape sequences
                if (currentType == TokenType.EOF) {
                    currentType = TokenType.WORD;
                } else if (currentType != TokenType.WORD) {
                    break;
                }

                currentIndex++;
                checkValidIndex();

                if (Character.isDigit(data[currentIndex]) ||
                        data[currentIndex] == '\\') {
                    sb.append(data[currentIndex++]);
                } else {
                    throw new LexerException("Nepravilno escapanje!");
                }

            } else if (Character.isDigit(data[currentIndex])) {
                // Parsing numbers
                if (currentType == TokenType.EOF) {
                    currentType = TokenType.NUMBER;
                } else if (currentType != TokenType.NUMBER) {
                    break;
                }

                sb.append(data[currentIndex++]);

            } else if (!checkIfSpace()) {
                // Parsing symbools
                if (currentType == TokenType.EOF) {
                    currentType = TokenType.SYMBOL;
                    sb.append(data[currentIndex++]);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // Converting parsed characters into tokens
        switch (currentType) {
            case WORD:
                token = new Token(currentType, sb.toString());
                break;
            case NUMBER:
                try {
                    long number = Long.parseLong(sb.toString());
                    token = new Token(currentType, number);
                } catch (NumberFormatException ex) {
                    throw new LexerException("Broj nije u ispravnom formatu!");
                }
                break;
            case SYMBOL:
                if (sb.length() != 1) {
                    throw new LexerException("Specijalni znakovi se " +
                            "pojavljuju u tekstu!");
                } else {
                    token = new Token(currentType, sb.toString().charAt(0));
                }
                break;
            case EOF:
                token = new Token(currentType, null);
                break;
        }

        return getToken();

    }

    /**
     * This method is used for parsing tokens when lexer is in EXTENDED state
     *
     * @return Generated token
     */
    private Token parseExtended() {

        // Checking if we want to generate token after EOF
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException("Nije moguća daljnje generiranje tokena " +
                    "nakon EOF!");
        }

        // Skipping blank spaces
        skipSpaces();

        StringBuilder sb = new StringBuilder();

        while (true) {

            // Checking if we are reached end of data
            if (currentIndex == data.length) {
                break;
            }

            // Checking if end tag
            if (checkIfSpace() || data[currentIndex] == '#') {
                break;
            } else {
                sb.append(data[currentIndex++]);
            }
        }

        if (sb.length() > 0) {
            token = new Token(TokenType.WORD, sb.toString());
        } else if (currentIndex == data.length) {
            token = new Token(TokenType.EOF, sb.toString());
        } else {
            token = new Token(TokenType.SYMBOL, data[currentIndex++]);
        }

        return token;
    }

    /**
     * This method is used to check if we are still in valid index
     */
    private void checkValidIndex() {
        if (currentIndex >= data.length) {
            throw new LexerException("Tokeniziranje nakon kraja texta!");
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

    /**
     * This method is used for getting last generated token.
     *
     * @return Last generated token
     */
    public Token getToken() {
        return token;

    }

    /**
     * Setter for lexer state.
     *
     * @param state New state
     */
    public void setState(LexerState state) {
        if (state == null) {
            throw new IllegalArgumentException("State ne smije biti null!");
        }

        this.state = state;
    }
}
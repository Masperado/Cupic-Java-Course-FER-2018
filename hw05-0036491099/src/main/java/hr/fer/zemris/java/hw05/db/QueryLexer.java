package hr.fer.zemris.java.hw05.db;

/**
 * This class represents QueryLexer. It is used for parsing text into
 * {@link QueryToken} Lexer is lazy, which means it will only tokenize input when needed.
 */
public class QueryLexer {

    /**
     * Char array of input text.
     */
    private char[] data;

    /**
     * Last generated token.
     */
    private QueryToken token;

    /**
     * Index of first not parsed character in array.
     */
    private int currentIndex;

    /**
     * Basic constructor.
     *
     * @param input Text that will be parsed into tokens
     */
    public QueryLexer(String input) {
        this.data = input.toCharArray();
    }

    /**
     * This method is used for generating new tokens.
     *
     * @return New token
     */
    public QueryToken nextToken() {

        if (token != null && token.getType() == QueryTokenType.EOF) {
            throw new RuntimeException("Nije moguće daljnje generiranje tokena nakon EOF!");
        }

        StringBuilder sb = new StringBuilder();
        QueryTokenType currentType = QueryTokenType.EOF;
        boolean insideQuotes = false;

        skipSpaces();


        while (true) {

            if (currentIndex == data.length) {
                break;
            }

            if (insideQuotes) {
                if (data[currentIndex] == '"') {
                    sb.append(data[currentIndex++]);
                    break;
                }
                sb.append(data[currentIndex++]);
            } else {
                if (isOperator()) {
                    if (currentType == QueryTokenType.EOF) {
                        currentType = QueryTokenType.OPERATOR;
                    } else {
                        break;
                    }

                    sb.append(data[currentIndex++]);

                    checkValidIndex();

                    if (data[currentIndex] == '=') {
                        sb.append(data[currentIndex++]);
                    }

                    break;


                } else if (data[currentIndex] == '"') {
                    if (currentType == QueryTokenType.EOF) {
                        currentType = QueryTokenType.STRING;
                    } else {
                        break;
                    }

                    sb.append(data[currentIndex++]);
                    insideQuotes = true;
                } else if (checkIfSpace()) {
                    break;
                } else {
                    sb.append(data[currentIndex++]);
                    currentType = QueryTokenType.FIELD;
                }
            }

        }

        switch (currentType) {
            case EOF:
                if (sb.length() != 0) {
                    throw new RuntimeException("EOF token ima podataka u sebi!");
                }
                token = new QueryToken(null, QueryTokenType.EOF);
                return token;
            case OPERATOR:
                token = new QueryToken(sb.toString(), QueryTokenType.OPERATOR);
                return token;
            case STRING:
                token = new QueryToken(sb.toString(), QueryTokenType.STRING);
                return token;
            case FIELD:
                String value = sb.toString();
                switch (value.toUpperCase()) {
                    case "AND":
                        token = new QueryToken(value.toUpperCase(), QueryTokenType.AND);
                        return token;
                    case "LIKE":
                        token = new QueryToken(value.toUpperCase(), QueryTokenType.OPERATOR);
                        return token;
                    default:
                        token = new QueryToken(value, QueryTokenType.FIELD);
                        return token;
                }
        }

        throw new RuntimeException("Nepoznati tip tokena!");

    }

    /**
     * This method is used to check if data at current index is operator character.
     *
     * @return True if it is, false otherwise
     */
    private boolean isOperator() {
        return data[currentIndex] == '=' || data[currentIndex] == '>' || data[currentIndex] == '<' || data[currentIndex] == '!';
    }

    /**
     * This method is used for getting last generated token.
     *
     * @return Last generated token
     */
    public QueryToken getToken() {
        return token;
    }

    /**
     * This method is used to check if data at current index is space character.
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
            throw new RuntimeException("Tokeniziranje nakon kraja texta!");
        }
    }
}

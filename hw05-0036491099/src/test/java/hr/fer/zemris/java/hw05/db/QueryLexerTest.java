package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryLexerTest {

    @Test
    public void queryBasic() {
        QueryLexer lexer = new QueryLexer("jmbag=\"0000000003\"");

        QueryToken correctData[] = {
                new QueryToken("jmbag", QueryTokenType.FIELD),
                new QueryToken("=", QueryTokenType.OPERATOR),
                new QueryToken("\"0000000003\"", QueryTokenType.STRING),
                new QueryToken(null, QueryTokenType.EOF)
        };
        checkQueryTokenStream(lexer, correctData);
    }

    @Test
    public void querySpaces() {
        QueryLexer lexer = new QueryLexer(" lastName = \"Blažić\"");

        QueryToken correctData[] = {
                new QueryToken("lastName", QueryTokenType.FIELD),
                new QueryToken("=", QueryTokenType.OPERATOR),
                new QueryToken("\"Blažić\"", QueryTokenType.STRING),
                new QueryToken(null, QueryTokenType.EOF)
        };
        checkQueryTokenStream(lexer, correctData);
    }

    @Test
    public void querySingleAnd() {
        QueryLexer lexer = new QueryLexer("firstName>\"A\" and lastName LIKE \"B*ć\"");

        QueryToken correctData[] = {
                new QueryToken("firstName", QueryTokenType.FIELD),
                new QueryToken(">", QueryTokenType.OPERATOR),
                new QueryToken("\"A\"", QueryTokenType.STRING),
                new QueryToken("AND", QueryTokenType.AND),
                new QueryToken("lastName", QueryTokenType.FIELD),
                new QueryToken("LIKE", QueryTokenType.OPERATOR),
                new QueryToken("\"B*ć\"", QueryTokenType.STRING),

                new QueryToken(null, QueryTokenType.EOF)
        };
        checkQueryTokenStream(lexer, correctData);
    }

    @Test
    public void queryMultipleAnd() {
        QueryLexer lexer = new QueryLexer("firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

        QueryToken correctData[] = {
                new QueryToken("firstName", QueryTokenType.FIELD),
                new QueryToken(">", QueryTokenType.OPERATOR),
                new QueryToken("\"A\"", QueryTokenType.STRING),
                new QueryToken("AND", QueryTokenType.AND),
                new QueryToken("firstName", QueryTokenType.FIELD),
                new QueryToken("<", QueryTokenType.OPERATOR),
                new QueryToken("\"C\"", QueryTokenType.STRING),
                new QueryToken("AND", QueryTokenType.AND),
                new QueryToken("lastName", QueryTokenType.FIELD),
                new QueryToken("LIKE", QueryTokenType.OPERATOR),
                new QueryToken("\"B*ć\"", QueryTokenType.STRING),
                new QueryToken("AND", QueryTokenType.AND),
                new QueryToken("jmbag", QueryTokenType.FIELD),
                new QueryToken(">", QueryTokenType.OPERATOR),
                new QueryToken("\"0000000002\"", QueryTokenType.STRING),


                new QueryToken(null, QueryTokenType.EOF)
        };
        checkQueryTokenStream(lexer, correctData);
    }


    private void checkQueryTokenStream(QueryLexer lexer,
                                       QueryToken[] correctData) {
        int counter = 0;
        for (QueryToken expected : correctData) {
            QueryToken actual = lexer.nextToken();
            String msg = "Checking token " + counter + ":";
            assertEquals(msg, expected.getType(), actual.getType());
            assertEquals(msg, expected.getValue(), actual.getValue());
            counter++;
        }
    }
}
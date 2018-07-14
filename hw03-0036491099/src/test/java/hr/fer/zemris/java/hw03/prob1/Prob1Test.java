package hr.fer.zemris.java.hw03.prob1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class Prob1Test {

    @Test
    public void testNotNull() {
        Lexer lexer = new Lexer("");

        assertNotNull("SmartScriptToken was expected but null was returned.",
                lexer.nextToken());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        new Lexer(null);
    }

    @Test
    public void testEmpty() {
        Lexer lexer = new Lexer("");

        assertEquals("Empty input must generate only EOF token.", TokenType
                .EOF, lexer.nextToken().getType());
    }

    @Test
    public void testGetReturnsLastNext() {
        Lexer lexer = new Lexer("");

        Token token = lexer.nextToken();
        assertEquals("getToken returned different token than nextToken.",
                token, lexer.getToken());
        assertEquals("getToken returned different token than nextToken.",
                token, lexer.getToken());
    }

    @Test(expected = LexerException.class)
    public void testRadAfterEOF() {
        Lexer lexer = new Lexer("");

        lexer.nextToken();
        lexer.nextToken();
    }

    @Test
    public void testNoActualContent() {
        Lexer lexer = new Lexer("   \r\n\t    ");

        assertEquals("Input had no content. Lexer should generated only EOF " +
                "token.", TokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testTwoWords() {
        Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil   ");


        Token correctData[] = {
                new Token(TokenType.WORD, "Štefanija"),
                new Token(TokenType.WORD, "Automobil"),
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    public void testWordStartingWithEscape() {
        Lexer lexer = new Lexer("  \\1st  \r\n\t   ");

        Token correctData[] = {
                new Token(TokenType.WORD, "1st"),
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test(expected = LexerException.class)
    public void testInvalidEscapeEnding() {
        Lexer lexer = new Lexer("   \\");

        lexer.nextToken();
    }

    @Test(expected = LexerException.class)
    public void testInvalidEscape() {
        Lexer lexer = new Lexer("   \\a    ");

        lexer.nextToken();
    }

    @Test
    public void testSingleEscapedDigit() {
        Lexer lexer = new Lexer("  \\1  ");

        Token correctData[] = {
                new Token(TokenType.WORD, "1"),
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    public void testWordWithManyEscapes() {
        Lexer lexer = new Lexer("  ab\\1\\2cd\\3 ab\\2\\1cd\\4\\\\ \r\n\t   ");

        Token correctData[] = {
                new Token(TokenType.WORD, "ab12cd3"),
                new Token(TokenType.WORD, "ab21cd4\\"), // this is 8-letter
                // long, not nine! Only single backslash!
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    public void testTwoNumbers() {
        Lexer lexer = new Lexer("  1234\r\n\t 5678   ");

        Token correctData[] = {
                new Token(TokenType.NUMBER, Long.valueOf(1234)),
                new Token(TokenType.NUMBER, Long.valueOf(5678)),
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test(expected = LexerException.class)
    public void testTooBigNumber() {
        Lexer lexer = new Lexer("  12345678912123123432123   ");

        lexer.nextToken();
    }


    @Test
    public void testWordWithManyEscapesAndNumbers() {
        Lexer lexer = new Lexer("  ab\\123cd ab\\2\\1cd\\4\\\\ \r\n\t   ");

        Token correctData[] = {
                new Token(TokenType.WORD, "ab1"),
                new Token(TokenType.NUMBER, Long.valueOf(23)),
                new Token(TokenType.WORD, "cd"),
                new Token(TokenType.WORD, "ab21cd4\\"), // this is 8-letter
                // long, not nine! Only single backslash!
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }


    @Test
    public void testSomeSymbols() {
        Lexer lexer = new Lexer("  -.? \r\n\t ##   ");

        Token correctData[] = {
                new Token(TokenType.SYMBOL, Character.valueOf('-')),
                new Token(TokenType.SYMBOL, Character.valueOf('.')),
                new Token(TokenType.SYMBOL, Character.valueOf('?')),
                new Token(TokenType.SYMBOL, Character.valueOf('#')),
                new Token(TokenType.SYMBOL, Character.valueOf('#')),
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    public void testCombinedInput() {
        Lexer lexer = new Lexer("Janko 3! Jasmina 5; -24");

        Token correctData[] = {
                new Token(TokenType.WORD, "Janko"),
                new Token(TokenType.NUMBER, Long.valueOf(3)),
                new Token(TokenType.SYMBOL, Character.valueOf('!')),
                new Token(TokenType.WORD, "Jasmina"),
                new Token(TokenType.NUMBER, Long.valueOf(5)),
                new Token(TokenType.SYMBOL, Character.valueOf(';')),
                new Token(TokenType.SYMBOL, Character.valueOf('-')),
                new Token(TokenType.NUMBER, Long.valueOf(24)),
                new Token(TokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullState() {
        new Lexer("").setState(null);
    }

    @Test
    public void testNotNullInExtended() {
        Lexer lexer = new Lexer("");
        lexer.setState(LexerState.EXTENDED);

        assertNotNull("SmartScriptToken was expected but null was returned.",
                lexer.nextToken());
    }

    @Test
    public void testEmptyInExtended() {
        Lexer lexer = new Lexer("");
        lexer.setState(LexerState.EXTENDED);

        assertEquals("Empty input must generate only EOF token.", TokenType
                .EOF, lexer.nextToken().getType());
    }

    @Test
    public void testGetReturnsLastNextInExtended() {
        Lexer lexer = new Lexer("");
        lexer.setState(LexerState.EXTENDED);

        Token token = lexer.nextToken();
        assertEquals("getToken returned different token than nextToken.",
                token, lexer.getToken());
        assertEquals("getToken returned different token than nextToken.",
                token, lexer.getToken());
    }

    @Test(expected = LexerException.class)
    public void testRadAfterEOFInExtended() {
        Lexer lexer = new Lexer("");
        lexer.setState(LexerState.EXTENDED);

        lexer.nextToken();
        lexer.nextToken();
    }

    @Test
    public void testNoActualContentInExtended() {
        Lexer lexer = new Lexer("   \r\n\t    ");
        lexer.setState(LexerState.EXTENDED);

        assertEquals("Input had no content. Lexer should generated only EOF " +
                "token.", TokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testMultipartInput() {
        Lexer lexer = new Lexer("Janko 3# Ivana26\\a 463abc#zzz");

        checkToken(lexer.nextToken(), new Token(TokenType.WORD, "Janko"));
        checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, Long
                .valueOf(3)));
        checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character
                .valueOf('#')));

        lexer.setState(LexerState.EXTENDED);

        checkToken(lexer.nextToken(), new Token(TokenType.WORD, "Ivana26\\a"));
        checkToken(lexer.nextToken(), new Token(TokenType.WORD, "463abc"));
        checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character
                .valueOf('#')));

        lexer.setState(LexerState.BASIC);

        checkToken(lexer.nextToken(), new Token(TokenType.WORD, "zzz"));
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

    }

    private void checkTokenStream(Lexer lexer, Token[] correctData) {
        int counter = 0;
        for (Token expected : correctData) {
            Token actual = lexer.nextToken();
            String msg = "Checking token " + counter + ":";
            assertEquals(msg, expected.getType(), actual.getType());
            assertEquals(msg, expected.getValue(), actual.getValue());
            counter++;
        }
    }

    private void checkToken(Token actual, Token expected) {
        String msg = "SmartScriptToken are not equal.";
        assertEquals(msg, expected.getType(), actual.getType());
        assertEquals(msg, expected.getValue(), actual.getValue());
    }

}

package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Test;

import static org.junit.Assert.*;

public class SmartScriptLexerTest {

    @Test
    public void testNotNull() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertNotNull("SmartScriptSmartScriptToken was expected but null was " +
                "returned.", lexer.nextToken());
    }

    @Test(expected = NullPointerException.class)
    public void testNullInput() {
        new SmartScriptLexer(null);
    }

    @Test
    public void testEmpty() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertEquals("Empty input must generate only EOF token.",
                SmartScriptTokenType.EOF, lexer
                        .nextToken().getType());
    }

    @Test
    public void testGetReturnsLastNext() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        SmartScriptToken token = lexer.nextToken();
        assertEquals("getSmartScriptToken returned different token than " +
                "nextToken.", token, lexer.getToken());
        assertEquals("getSmartScriptToken returned different token than " +
                "nextToken.", token, lexer.getToken());
    }

    @Test(expected = SmartScriptLexerException.class)
    public void testRadAfterEOF() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        lexer.nextToken();
        lexer.nextToken();
    }

    @Test
    public void testTextBasic() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    Upregnimo" +
                " kočije blještavih snova");

        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "   \r\n\t   " +
                        " Upregnimo kočije blještavih snova"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkSmartScriptTokenStream(lexer, correctData);
    }

    @Test
    public void testTextWithEscaping() {
        SmartScriptLexer lexer = new SmartScriptLexer("\\\\ \\{ \\\\ Nek " +
                "započne pomamni trk");


        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "\\ { \\ Nek " +
                        "započne pomamni trk"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkSmartScriptTokenStream(lexer, correctData);
    }

    @Test
    public void testTextWithBeginTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("Brže od snježne " +
                "vijavice bile   {$");

        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "Brže od " +
                        "snježne vijavice bile   "),
                new SmartScriptToken(SmartScriptTokenType.BEGINTAG, "{$"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkSmartScriptTokenStream(lexer, correctData);
    }

    @Test(expected = SmartScriptLexerException.class)
    public void testInvalidEscapeEnding() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \\");  // this is
        lexer.nextToken();
    }

    @Test(expected = SmartScriptLexerException.class)
    public void testInvalidEscape() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");

        lexer.nextToken();
    }

    @Test(expected = SmartScriptLexerException.class)
    public void testInvalidBeginTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("{  ");

        lexer.nextToken();
    }

    @Test(expected = NullPointerException.class)
    public void testNullState() {
        new SmartScriptLexer("").setState(null);
    }

    @Test(expected = SmartScriptLexerException.class)
    public void testEmptyTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        lexer.setState(SmartScriptLexerState.TAG);

        lexer.nextToken();

    }

    @Test
    public void testGetReturnsLastNextInExtended() {
        SmartScriptLexer lexer = new SmartScriptLexer("FOR i -1 10 1 $}");
        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken token = lexer.nextToken();
        assertEquals("getSmartScriptToken returned different token than " +
                "nextToken.", token, lexer.getToken());
        assertEquals("getSmartScriptToken returned different token than " +
                "nextToken.", token, lexer.getToken());
    }

    @Test
    public void testNoActualContentInExtended() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    $}");
        lexer.setState(SmartScriptLexerState.TAG);

        assertEquals("Input had no content. SmartScriptLexer should generated" +
                " only EOF token.", SmartScriptTokenType
                .ENDTAG, lexer.nextToken().getType());
    }

    @Test
    public void testMultipartInput() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text" +
                ".\r\n" +
                "{$ FOR i 1 10 1 $}\r\n" +
                "\tThis is {$= i $}-th time this message is generated.\r\n" +
                "{$END$}\r\n" +
                "{$FOR i 0 10 2 $}\r\n" +
                "\tsin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" +
                "{$END$}");

        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "This is sample " +
                        "text.\r\n"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.BEGINTAG, "{$"));

        lexer.setState(SmartScriptLexerState.TAG);

        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "FOR"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "i"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.INTEGER, 1));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.INTEGER, 10));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.INTEGER, 1));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.ENDTAG, "$}"));

        lexer.setState(SmartScriptLexerState.TEXT);

        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "\r\n\tThis is "));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.BEGINTAG, "{$"));

        lexer.setState(SmartScriptLexerState.TAG);

        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "="));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "i"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.ENDTAG, "$}"));

        lexer.setState(SmartScriptLexerState.TEXT);

        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "-th time this " +
                        "message is generated.\r\n"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.BEGINTAG, "{$"));

        lexer.setState(SmartScriptLexerState.TAG);

        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "END"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.ENDTAG, "$}"));


    }

    private void checkSmartScriptTokenStream(SmartScriptLexer lexer,
                                             SmartScriptToken[] correctData) {
        int counter = 0;
        for (SmartScriptToken expected : correctData) {
            SmartScriptToken actual = lexer.nextToken();
            String msg = "Checking token " + counter + ":";
            assertEquals(msg, expected.getType(), actual.getType());
            assertEquals(msg, expected.getValue(), actual.getValue());
            counter++;
        }
    }

    private void checkSmartScriptToken(SmartScriptToken actual,
                                       SmartScriptToken expected) {
        String msg = "SmartScriptSmartScriptToken are not equal.";
        assertEquals(msg, expected.getType(), actual.getType());
        assertEquals(msg, expected.getValue(), actual.getValue());
    }

}


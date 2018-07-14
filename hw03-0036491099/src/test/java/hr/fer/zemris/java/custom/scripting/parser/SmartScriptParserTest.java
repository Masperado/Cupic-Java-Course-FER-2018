package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static hr.fer.zemris.java.hw03.SmartScriptTester
        .createOriginalDocumentBody;
import static org.junit.Assert.*;

public class SmartScriptParserTest {

    @Test(expected = NullPointerException.class)
    public void nullTextTest() {
        checkIfGood(null);
    }

    @Test
    public void doc1Test() {
        String doc1 = loader("doc1.txt");
        assertTrue(checkIfGood(doc1));
    }

    @Test(expected = SmartScriptParserException.class)
    public void invalidTagTest() {
        String invalidTag = loader("invalidTag.txt");
        checkIfGood(invalidTag);
    }

    @Test(expected = SmartScriptParserException.class)
    public void invalidForLoopTest() {
        String invalidForLoop = loader("invalidForLoopTag.txt");
        checkIfGood(invalidForLoop);
    }

    @Test(expected = SmartScriptParserException.class)
    public void unclosedForLoopTest() {
        String unclosedForLoop = loader("unclosedForLoop.txt");
        checkIfGood(unclosedForLoop);
    }

    @Test
    public void zbrajanjeTest() {
        String zbrajanje = loader("zbrajanje.txt");
        assertTrue(checkIfGood(zbrajanje));
    }

    @Test
    public void brojPozivaTest() {
        String brojPoziva = loader("brojPoziva.txt");
        assertTrue(checkIfGood(brojPoziva));
    }

    @Test
    public void fibonacciTest() {
        String fibonacci = loader("fibonacci.txt");
        assertTrue(checkIfGood(fibonacci));
    }

    public boolean checkIfGood(String text) {
        SmartScriptParser parser = new SmartScriptParser(text);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document);

        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        String originalDocumentBody2 = createOriginalDocumentBody(document2);

        return originalDocumentBody.equals(originalDocumentBody2);
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (InputStream is =
                     this.getClass().getClassLoader().getResourceAsStream
                             (filename)) {
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                if (read < 1) break;
                bos.write(buffer, 0, read);
            }
            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return null;
        }
    }

}
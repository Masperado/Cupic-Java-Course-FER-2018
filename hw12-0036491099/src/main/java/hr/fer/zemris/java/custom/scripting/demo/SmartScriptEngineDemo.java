package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used for demonstrating use of {@link SmartScriptEngine}.
 */
public class SmartScriptEngineDemo {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        osnovni();
        System.out.println("\n--------------\n");

        zbrajanje();

        System.out.println("\n--------------\n");

        brojPoziva();

        System.out.println("\n--------------\n");

        fibonacci();

        System.out.println("\n--------------\n");

        fibonaccih();


    }

    /**
     * This method is used for testing html fibonacci.
     */
    private static void fibonaccih() {
        String docBody = null;
        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get("src/main/resources/webroot/scripts/fibonaccih.smscr")),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            System.out.println("Putanja do datoteke nije ispravna!");
            System.exit(1);
        }
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();
        new SmartScriptEngine(
                new SmartScriptParser(docBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }


    /**
     * This method is used for testing fibonacci.
     */
    private static void fibonacci() {
        String docBody = null;
        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get("src/main/resources/webroot/scripts/fibonacci.smscr")),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            System.out.println("Putanja do datoteke nije ispravna!");
            System.exit(1);
        }
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();
        new SmartScriptEngine(
                new SmartScriptParser(docBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }

    /**
     * This method is used for testing broj poziva.
     */
    private static void brojPoziva() {
        String docBody = null;
        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get("src/main/resources/webroot/scripts/brojPoziva.smscr")),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            System.out.println("Putanja do datoteke nije ispravna!");
            System.exit(1);
        }
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();
        persistentParameters.put("brojPoziva", "3");
        RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
                cookies);
        new SmartScriptEngine(
                new SmartScriptParser(docBody).getDocumentNode(), rc
        ).execute();
        System.out.println("\nVrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
    }

    /**
     * This method is used for testing zbrajanje.
     */
    private static void zbrajanje() {
        String docBody = null;
        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get("src/main/resources/webroot/scripts/zbrajanje.smscr")),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            System.out.println("Putanja do datoteke nije ispravna!");
            System.exit(1);
        }
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();
        parameters.put("a", "4");
        parameters.put("b", "2");
        new SmartScriptEngine(
                new SmartScriptParser(docBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }

    /**
     * This method is used for testing osnovni.
     */
    private static void osnovni() {
        String docBody = null;

        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get("src/main/resources/webroot/scripts/osnovni.smscr")),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            System.out.println("Putanja do datoteke nije ispravna!");
            System.exit(1);
        }
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();
        new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), new RequestContext(System.out,
                parameters, persistentParameters, cookies)).execute();
    }


}

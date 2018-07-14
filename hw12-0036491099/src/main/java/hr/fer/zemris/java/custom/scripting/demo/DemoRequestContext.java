package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used for demonstrating use of {@link RequestContext}.
 */
public class DemoRequestContext {

    /**
     * Main method.
     *
     * @param args Command line arguments
     * @throws IOException Input output exception
     */
    public static void main(String[] args) throws IOException {
        demo1("primjer1.txt", "ISO-8859-2");
        demo1("primjer2.txt", "UTF-8");
        demo2("primjer3.txt", "UTF-8");
    }

    /**
     * First example.
     *
     * @param filePath Path to file
     * @param encoding Encoding
     * @throws IOException Input output exception
     */
    private static void demo1(String filePath, String encoding) throws IOException {
        OutputStream os = Files.newOutputStream(Paths.get(filePath));
        RequestContext rc = new RequestContext(os, new HashMap<>(),
                new HashMap<>(),
                new ArrayList<>());
        rc.setEncoding(encoding);
        rc.setMimeType("text/plain");
        rc.setStatusCode(205);
        rc.setStatusText("Idemo dalje");
        rc.write("Čevapčići i Šiščevapčići.");
        os.close();
    }

    /**
     * Second example.
     *
     * @param filePath Path to file
     * @param encoding Encoding
     * @throws IOException Input output exception
     */
    private static void demo2(String filePath, String encoding) throws IOException {
        OutputStream os = Files.newOutputStream(Paths.get(filePath));
        RequestContext rc = new RequestContext(os, new HashMap<>(),
                new HashMap<>(),
                new ArrayList<>());
        rc.setEncoding(encoding);
        rc.setMimeType("text/plain");
        rc.setStatusCode(205);
        rc.setStatusText("Idemo dalje");
        rc.addRCCookie(new RCCookie("korisnik", "perica", "127.0.0.1",
                "/", 3600));
        rc.addRCCookie(new RCCookie("zgrada", "B4", null, "/", null));
        rc.write("Čevapčići i Šiščevapčići.");
        os.close();
    }
}
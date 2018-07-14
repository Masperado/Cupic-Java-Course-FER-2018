package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents SmartHttpServer. It is used for recieving HTTP requests and processing them. It listens of
 * port 5721 of localhost. Home page can be accessed of http://localhost:5721/index2.html
 */
public class SmartHttpServer {

    /**
     * Address of server.
     */
    private String address;

    /**
     * Domain name of server.
     */
    private String domainName;

    /**
     * Port of server.
     */
    private int port;

    /**
     * Number of worker threads.
     */
    private int workerThreads;

    /**
     * Session timeout length.
     */
    private int sessionTimeout;

    /**
     * Mime types of server.
     */
    private Map<String, String> mimeTypes = new HashMap<>();

    /**
     * Thread for doing server work.
     */
    private ServerThread serverThread;

    /**
     * Thread pool of server.
     */
    private ExecutorService threadPool;

    /**
     * Document root of server.
     */
    private Path documentRoot;

    /**
     * {@link IWebWorker} map of server.
     */
    private Map<String, IWebWorker> workersMap = new HashMap<>();

    /**
     * {@link SessionMapEntry} map of server.
     */
    private Map<String, SessionMapEntry> sessions =
            new ConcurrentHashMap<>();

    /**
     * Random of server.
     */
    private Random sessionRandom = new Random();

    /**
     * Basic constructor.
     *
     * @param configFileName Server configuration file name
     */
    public SmartHttpServer(String configFileName) {
        InputStream input = SmartHttpServer.class.getClassLoader().getResourceAsStream(configFileName);
        Properties prop = new Properties();
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        address = prop.getProperty("server.address");
        domainName = prop.getProperty("server.domainName");
        port = Integer.parseInt(prop.getProperty("server.port"));
        workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
        sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
        documentRoot = Paths.get(prop.getProperty("server.documentRoot"));

        List<String> mimes = null;
        try {
            mimes = Files.readAllLines(Paths.get(prop.getProperty("server.mimeConfig")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : mimes) {
            String[] parts = s.split("=");
            mimeTypes.put(parts[0].trim(), parts[1].trim());
        }

        List<String> workersLines = null;
        try {
            workersLines = Files.readAllLines(Paths.get(prop.getProperty("server.workers")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : workersLines) {
            String[] parts = s.split("=");

            Class<?> referenceToClass = null;
            try {
                referenceToClass = this.getClass().getClassLoader().loadClass(parts[1].trim());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Object newObject = null;
            try {
                newObject = referenceToClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            IWebWorker iww = (IWebWorker) newObject;
            workersMap.put(parts[0].trim(), iww);
        }

    }

    /**
     * This method is used for starting server.
     */
    protected synchronized void start() {
        serverThread = new ServerThread();
        serverThread.start();
        threadPool = Executors.newFixedThreadPool(workerThreads);

        Thread cleaningThread = new Thread(() -> {
            while (true) {
                for (Map.Entry<String, SessionMapEntry> entry : sessions.entrySet()) {
                    String sid = entry.getKey();
                    SessionMapEntry session = entry.getValue();
                    if (session.validUntil < System.currentTimeMillis() / 1000) {
                        sessions.remove(sid);
                    }
                }
                try {
                    Thread.sleep(1000 * 60 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        cleaningThread.setDaemon(true);
        cleaningThread.start();
    }

    /**
     * This method is used for stopping server.
     */
    protected synchronized void stop() {
        serverThread.stop();
        threadPool.shutdown();
    }

    /**
     * This class represents thread used for running server.
     */
    protected class ServerThread extends Thread {
        @Override
        public void run() {
            try {
                ServerSocket socket = new ServerSocket();
                socket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
                while (true) {
                    Socket client = socket.accept();
                    ClientWorker cw = new ClientWorker(client);
                    threadPool.submit(cw);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This class represents session map entry. It is used for storing sessions.
     */
    private static class SessionMapEntry {

        /**
         * Session id.
         */
        String sid;

        /**
         * Session host.
         */
        String host;

        /**
         * UNIX timestamp of validness.
         */
        long validUntil;

        /**
         * Map of parameters.
         */
        Map<String, String> map;

        /**
         * Basic constructor.
         *
         * @param sid        Session id
         * @param host       Host
         * @param validUntil Timestamp
         * @param map        Map of parameters
         */
        public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
            this.sid = sid;
            this.host = host;
            this.validUntil = validUntil;
            this.map = map;
        }
    }

    /**
     * This class represents ClientWorker that is used for dispatching http requests.
     */
    private class ClientWorker implements Runnable, IDispatcher {

        /**
         * Socket of request.
         */
        private Socket csocket;

        /**
         * Input stream.
         */
        private PushbackInputStream istream;

        /**
         * Output stream.
         */
        private OutputStream ostream;

        /**
         * Version of request.
         */
        private String version;

        /**
         * Method of request.
         */
        private String method;

        /**
         * Host of request.
         */
        private String host;

        /**
         * Params map.
         */
        private Map<String, String> params = new HashMap<>();

        /**
         * Temporary params map.
         */
        private Map<String, String> tempParams = new HashMap<>();

        /**
         * Permament params map.
         */
        private Map<String, String> permPrams = new HashMap<>();

        /**
         * Output cookies list.
         */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();

        /**
         * Session id.
         */
        private String SID;

        /**
         * Request context.
         */
        private RequestContext context = null;

        /**
         * Basic constructor.
         *
         * @param csocket Socket
         */
        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        /**
         * This method is used for closing socket.
         *
         * @throws IOException Input output exception
         */
        public void closeSocket() throws IOException {
            ostream.flush();
            csocket.close();
        }

        /**
         * This method is used for internally dispatching requests.
         *
         * @param urlPath    Url path
         * @param directCall True if direct call, false otherwise
         * @throws Exception Exception
         */
        public void internalDispatchRequest(String urlPath, boolean directCall)
                throws Exception {


            if (context == null) {
                context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
            }

            if (urlPath.equals("/private") || urlPath.startsWith("/private")) {
                if (directCall) {
                    sendError(ostream, 404, "File not Found");
                    closeSocket();
                    return;
                }

            }

            if (urlPath.startsWith("/ext/")) {
                Class<?> referenceToClass = null;
                try {
                    referenceToClass = this.getClass().getClassLoader().loadClass("hr.fer.zemris.java.webserver" +
                            ".workers." + urlPath.substring(5));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Object newObject = null;
                try {
                    newObject = referenceToClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                IWebWorker iww = (IWebWorker) newObject;
                iww.processRequest(context);
                closeSocket();
                return;
            }

            if (workersMap.containsKey(urlPath)) {
                workersMap.get(urlPath).processRequest(context);
                closeSocket();
                return;
            }

            Path requestedFile = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();

            if (!requestedFile.startsWith(documentRoot)) {
                sendError(ostream, 403, "Forbidden");
                closeSocket();
                return;
            }


            if (!Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
                sendError(ostream, 404, "File not Found");
                closeSocket();
                return;
            }


            if (requestedFile.toString().endsWith(".smscr")) {
                String docBody = new String(
                        Files.readAllBytes(requestedFile),
                        StandardCharsets.UTF_8
                );
                new SmartScriptEngine(
                        new SmartScriptParser(docBody).getDocumentNode(),
                        context
                ).execute();
                closeSocket();
                return;
            }


            InputStream fis = Files.newInputStream(requestedFile);
            String mimeType = getMime(requestedFile);
            context.setMimeType(mimeType);
            context.setContentLength(Files.size(requestedFile));


            byte[] buf = new byte[1024];
            while (true) {
                int r = fis.read(buf);
                if (r < 1) break;
                context.write(Arrays.copyOfRange(buf, 0, r));
            }

            closeSocket();
        }

        @Override
        public void dispatchRequest(String urlPath) throws Exception {
            internalDispatchRequest(urlPath, false);
        }

        @Override
        public void run() {
            try {
                istream = new PushbackInputStream(csocket.getInputStream());
                ostream = csocket.getOutputStream();
                byte[] request = readRequest(istream);
                if (request == null) {
                    sendError(ostream, 400, "Bad request");
                    closeSocket();
                    return;
                }
                String requestStr = new String(
                        request,
                        StandardCharsets.US_ASCII
                );

                List<String> headers = extractHeaders(requestStr);
                String[] firstLine = headers.isEmpty() ?
                        null : headers.get(0).split(" ");
                if (firstLine == null || firstLine.length != 3) {
                    sendError(ostream, 400, "Bad request");
                    closeSocket();
                    return;
                }

                method = firstLine[0].toUpperCase();
                if (!method.equals("GET")) {
                    sendError(ostream, 405, "Method Not Allowed");
                    closeSocket();
                    return;
                }

                version = firstLine[2].toUpperCase();
                if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
                    sendError(ostream, 505, "HTTP Version Not Supported");
                    closeSocket();
                    return;
                }

                for (String header : headers) {
                    if (header.startsWith("Host: ")) {
                        host = header.split(":")[1].trim();
                    }
                }

                String[] pathParts = firstLine[1].split("\\?");
                String path = pathParts[0];

                checkSession(headers);

                if (pathParts.length == 2) {
                    parseParameters(pathParts[1]);
                }


                internalDispatchRequest(path, true);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * This method is used for checking session.
         *
         * @param headers List of headers
         */
        private synchronized void checkSession(List<String> headers) {
            String sidCandidate = null;
            for (String header : headers) {
                if (header.startsWith("Cookie: ")) {
                    String[] cookies = header.substring(8).split(";");
                    for (String cookie : cookies) {
                        if (cookie.startsWith("sid=")) {
                            sidCandidate = cookie.substring(5, cookie.length() - 1);
                        }
                    }
                }
            }
            if (sidCandidate != null) {
                SessionMapEntry session = sessions.get(sidCandidate);
                if (session != null) {
                    if (session.host.equals(host)) {
                        if (session.validUntil > System.currentTimeMillis() / 1000) {
                            session.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
                            permPrams = session.map;
                            return;
                        } else {
                            sessions.remove(sidCandidate);
                        }
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 20; i++) {
                    sb.append((char) ((int) 'A' + sessionRandom.nextDouble() * ((int) 'Z' - (int) 'A' + 1)));
                }
                sidCandidate = sb.toString();
            }
            SessionMapEntry newSession = new SessionMapEntry(sidCandidate, host, System.currentTimeMillis() / 1000 + sessionTimeout,
                    new ConcurrentHashMap<>());
            sessions.put(sidCandidate, newSession);
            outputCookies.add(new RequestContext.RCCookie("sid", sidCandidate, host, "/", null));
            permPrams = newSession.map;

        }

        /**
         * This method is used for getting mime type of file.
         *
         * @param requestedFile File
         * @return Mime type
         */
        private String getMime(Path requestedFile) {
            int i = requestedFile.toString().lastIndexOf(".");

            if (i > 0) {
                String extension = requestedFile.toString().substring(i + 1);
                String mimeType = mimeTypes.get(extension);

                if (mimeType != null) {
                    return mimeType;
                }
            }
            return "application/octet-stream";
        }

        /**
         * This method is used for parsing parameters.
         *
         * @param paramsUrl Paramaters url
         */
        private void parseParameters(String paramsUrl) {
            String[] paramsStr = paramsUrl.split("&");
            for (String param : paramsStr) {
                String[] parts = param.split("=");
                if (parts.length != 2) {
                    continue;
                }
                params.put(parts[0], parts[1]);
            }

        }

        /**
         * This method is used for sending error.
         *
         * @param ostream    Output stream
         * @param statusCode Status code of error
         * @param statusText Status text
         * @throws IOException
         */
        private void sendError(OutputStream ostream, int statusCode, String statusText) throws IOException {
            ostream.write(
                    ("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                            "Server: simple java server\r\n" +
                            "Content-Type: text/plain;charset=UTF-8\r\n" +
                            "Content-Length: 0\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes(StandardCharsets.US_ASCII)
            );
        }

        /**
         * This method is used for reading request.
         *
         * @param is InputStream
         * @return Byte array of request
         * @throws IOException Input output exception
         */
        private byte[] readRequest(InputStream is) throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l:
            while (true) {
                int b = is.read();
                if (b == -1) return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                    case 0:
                        if (b == 13) {
                            state = 1;
                        } else if (b == 10) state = 4;
                        break;
                    case 1:
                        if (b == 10) {
                            state = 2;
                        } else state = 0;
                        break;
                    case 2:
                        if (b == 13) {
                            state = 3;
                        } else state = 0;
                        break;
                    case 3:
                        if (b == 10) {
                            break l;
                        } else state = 0;
                        break;
                    case 4:
                        if (b == 10) {
                            break l;
                        } else state = 0;
                        break;
                }
            }
            return bos.toByteArray();
        }

        /**
         * This method is used for extracting headers from request.
         *
         * @param requestHeader Request header string
         * @return List of headers
         */
        private List<String> extractHeaders(String requestHeader) {
            List<String> headers = new ArrayList<>();
            StringBuilder currentLine = null;
            for (String s : requestHeader.split("\n")) {
                if (s.isEmpty()) break;
                char c = s.charAt(0);
                if (c == 9 || c == 32) {
                    currentLine.append(s);
                } else {
                    if (currentLine != null) {
                        headers.add(currentLine.toString());
                    }
                    currentLine = new StringBuilder(s);
                }
            }
            if (currentLine.length() > 0) {
                headers.add(currentLine.toString());
            }
            return headers;
        }
    }

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You must provide path to server.properties");
            System.exit(1);
        }

        new SmartHttpServer(args[0]).start();
    }


}

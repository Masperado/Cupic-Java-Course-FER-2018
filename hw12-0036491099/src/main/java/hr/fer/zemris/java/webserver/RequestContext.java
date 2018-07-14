package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

/**
 * This class represents RequestContext. It is used for storing parameters, writing http headers and writing to
 * output stream.
 */
public class RequestContext {

    /**
     * Output stream used for writing data.
     */
    private OutputStream outputStream;

    /**
     * Charset used for converting bytes to characters.
     */
    private Charset charset;

    /**
     * Encoding used for converting bytes to characters.
     */
    public String encoding = "UTF-8";

    /**
     * Status code of HTTP header.
     */
    public int statusCode = 200;

    /**
     * Status text of HTTP header.
     */
    public String statusText = "OK";

    /**
     * Mime type of HTTP header.
     */
    private String mimeType = "text/html";

    /**
     * Parameters of context.
     */
    private Map<String, String> parameters;

    /**
     * Temporary parameters of context.
     */
    private Map<String, String> temporaryParameters;

    /**
     * Persistent parametes of context.
     */
    private Map<String, String> persistentParameters;

    /**
     * Output cookies of context.
     */
    private List<RCCookie> outputCookies;

    /**
     * Flag for telling if header has been generated.
     */
    private boolean headerGenerated = false;

    /**
     * Content length of HTTP header.
     */
    private long contentLength = 0;

    /**
     * {@link IDispatcher} used for dispatching requests.
     */
    private IDispatcher dispatcher;

    /**
     * Basic constructor.
     *
     * @param outputStream         Output stream
     * @param parameters           Parameters
     * @param persistentParameters Persistent parameters
     * @param outputCookies        Output cookies
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
        this(outputStream, parameters, persistentParameters, outputCookies, null, null);

    }

    /**
     * Constructor with additional parameters.
     *
     * @param outputStream         Output stream
     * @param parameters           Parameters
     * @param persistentParameters Persistent parameters
     * @param outputCookies        Output cookies
     * @param temporaryParameters  Temporary parameters
     * @param dispatcher           {@link IDispatcher}
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String>
            persistentParameters, List<RCCookie> outputCookies, Map<String, String> temporaryParameters, IDispatcher
                                  dispatcher) {
        Objects.requireNonNull(outputStream);
        this.outputStream = outputStream;

        this.parameters = Objects.requireNonNullElseGet(parameters, () -> Collections.unmodifiableMap(new HashMap<>()));

        this.persistentParameters = Objects.requireNonNullElseGet(persistentParameters, HashMap::new);

        this.outputCookies = Objects.requireNonNullElseGet(outputCookies, ArrayList::new);

        this.temporaryParameters = Objects.requireNonNullElseGet(temporaryParameters, HashMap::new);

        this.dispatcher = dispatcher;

    }

    /**
     * Getter for parameter by name.
     *
     * @param name Name
     * @return Parameter
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Getter for set of all parameter names.
     *
     * @return Set of all parameter names
     */
    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    /**
     * Getter for persistent parameter by name.
     *
     * @param name Name
     * @return parameter
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Getter for set of all persistent parameter names.
     *
     * @return Set of all persistent parameter names
     */
    public Set<String> getPeristentParameterNames() {
        return Collections.unmodifiableSet(persistentParameters.keySet());
    }

    /**
     * Getter for temporary parameter by name.
     *
     * @param name Name
     * @return Parameter
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Getter for set of all temporary parameter names.
     *
     * @return Set of all temporary parameter names
     */
    public Set<String> getTemporaryParameterNames() {
        return Collections.unmodifiableSet(temporaryParameters.keySet());
    }

    /**
     * Getter for {@link IDispatcher}
     *
     * @return
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }


    /**
     * Setter for persistent parameter.
     *
     * @param name  Name
     * @param value Value
     */
    public void setPersistentParameters(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * Setter for encoding.
     *
     * @param encoding Encoding
     */
    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("Changing header after creation!");
        }
        this.encoding = encoding;
    }

    /**
     * Setter for status code.
     *
     * @param statusCode Status code
     */
    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("Changing header after creation!");
        }
        this.statusCode = statusCode;
    }

    /**
     * Setter for status text.
     *
     * @param statusText Status text
     */
    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("Changing header after creation!");
        }
        this.statusText = statusText;
    }

    /**
     * Setter for mime type.
     *
     * @param mimeType Mime type
     */
    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("Changing header after creation!");
        }
        this.mimeType = mimeType;
    }

    /**
     * Setter for content length.
     *
     * @param contentLength Content length
     */
    public void setContentLength(long contentLength) {
        if (headerGenerated) {
            throw new RuntimeException("Changing header after creation!");
        }
        this.contentLength = contentLength;
    }

    /**
     * Setter for temporary parameter.
     *
     * @param name  Name
     * @param value Value
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    /**
     * This method is used for removing temporary parameter.
     *
     * @param name Name
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * This method is used for removing persistent parameter.
     *
     * @param name Name
     */
    public void removePersistentParameters(String name) {
        persistentParameters.remove(name);
    }

    /**
     * This method is used for adding RCCookie to output cookies list.
     *
     * @param cookie {@link RCCookie}
     */
    public void addRCCookie(RCCookie cookie) {
        if (headerGenerated) {
            throw new RuntimeException("Changing header after creation!");
        }
        outputCookies.add(cookie);
    }

    /**
     * This method is used for writing data to output stream.
     *
     * @param data data
     * @return This
     * @throws IOException Input output exception
     */
    public RequestContext write(byte[] data) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(data);
        return this;
    }

    /**
     * This method is used for writing String to output stream.
     *
     * @param text String
     * @return This
     * @throws IOException Input output exception
     */
    public RequestContext write(String text) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(text.getBytes(charset));
        return this;
    }

    /**
     * This method is used for generating header.
     *
     * @throws IOException Input output exception
     */
    private void generateHeader() throws IOException {
        try {
            charset = Charset.forName(encoding);
        } catch (UnsupportedCharsetException ex) {
            throw new IOException(ex);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ");
        sb.append(statusCode);
        sb.append(" ");
        sb.append(statusText);
        sb.append("\r\n");
        sb.append("Content-Type: ");
        sb.append(mimeType);
        if (mimeType.startsWith("text/")) {
            sb.append("; charset=");
            sb.append(encoding);
        }
        if (contentLength != 0) {
            sb.append("\r\n");
            sb.append("Content-Length: ");
            sb.append(contentLength);
        }
        sb.append("\r\n");
        for (RCCookie cookie : outputCookies) {
            sb.append("Set-Cookie: ");
            sb.append(cookie.getName());
            sb.append("=\"");
            sb.append(cookie.getValue());
            sb.append("\"");
            if (cookie.getDomain() != null) {
                sb.append("; Domain=");
                sb.append(cookie.getDomain());
            }
            if (cookie.getPath() != null) {
                sb.append("; Path=");
                sb.append(cookie.getPath());
            }
            if (cookie.getMaxAge() != null) {
                sb.append("; Max-Age=");
                sb.append(cookie.getMaxAge());
            }
            sb.append("\r\n");
        }
        sb.append("\r\n");

        outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
        headerGenerated = true;
    }

    /**
     * This class represents RCCookie. It is used as a cookie for context.
     */
    public static class RCCookie {

        /**
         * Name of cookie.
         */
        private String name;

        /**
         * Value of cookie.
         */
        private String value;

        /**
         * Domain of cookie.
         */
        private String domain;

        /**
         * Path of cookie.
         */
        private String path;

        /**
         * Max age of cookie.
         */
        private Integer maxAge;

        /**
         * Basic constructor.
         *
         * @param name   Name
         * @param value  Value
         * @param domain Domain
         * @param path   Path
         * @param maxAge Max age
         */
        public RCCookie(String name, String value, String domain, String path, Integer maxAge) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
        }

        /**
         * Getter for name.
         *
         * @return Name
         */
        public String getName() {
            return name;
        }

        /**
         * Getter for value.
         *
         * @return Value
         */
        public String getValue() {
            return value;
        }

        /**
         * Getter for domain.
         *
         * @return Domain
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Getter for path.
         *
         * @return Path
         */
        public String getPath() {
            return path;
        }

        /**
         * Getter for max age.
         *
         * @return Max age
         */
        public Integer getMaxAge() {
            return maxAge;
        }
    }
}

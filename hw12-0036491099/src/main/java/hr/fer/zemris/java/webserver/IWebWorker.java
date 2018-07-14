package hr.fer.zemris.java.webserver;

/**
 * This interface represents IWebWorker. It is used for processing request in given context.
 */
public interface IWebWorker {

    /**
     * This method is used for processing request in given context.
     *
     * @param context Context
     * @throws Exception Exception
     */
    void processRequest(RequestContext context) throws Exception;
}

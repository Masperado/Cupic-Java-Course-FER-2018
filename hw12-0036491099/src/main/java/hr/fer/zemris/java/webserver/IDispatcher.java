package hr.fer.zemris.java.webserver;

/**
 * This interface represents IDispatcher. It is used for dispatching requests for given urlPath.
 */
public interface IDispatcher {

    /**
     * This method is used for dispatching requests for given url path.
     *
     * @param urlPath Url path
     * @throws Exception Exception if anything goes wrong
     */
    void dispatchRequest(String urlPath) throws Exception;
}

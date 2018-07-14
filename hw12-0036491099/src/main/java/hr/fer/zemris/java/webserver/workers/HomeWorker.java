package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * This class represents {@link IWebWorker} used for displaying homepage.
 */
public class HomeWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        String background = context.getPersistentParameter("bgcolor");
        if (background == null) {
            background = "7F7F7F";
        }
        context.setTemporaryParameter("background", background);
        context.getDispatcher().dispatchRequest("/private/home.smscr");
    }
}

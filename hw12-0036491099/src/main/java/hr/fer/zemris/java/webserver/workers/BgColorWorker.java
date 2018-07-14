package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents {@link IWebWorker} used for changing background color.
 */
public class BgColorWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        String bgcolor = context.getParameter("bgcolor");

        if (bgcolor == null) {
            context.setTemporaryParameter("message", "Color is not updated!");
            context.getDispatcher().dispatchRequest("/private/color.smscr");
        } else {
            context.setPersistentParameters("bgcolor", bgcolor);
            context.setTemporaryParameter("message", "Color is updated!");
            context.getDispatcher().dispatchRequest("/private/color.smscr");
        }
    }
}

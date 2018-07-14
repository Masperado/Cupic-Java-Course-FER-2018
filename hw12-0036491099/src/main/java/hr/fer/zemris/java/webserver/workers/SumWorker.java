package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * This class represents {@link IWebWorker} used for calculating sum of two numbers.
 */
public class SumWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        int a = 1;
        int b = 2;
        try {
            a = Integer.parseInt(context.getParameter("a"));
        } catch (NumberFormatException | NullPointerException ignorable) {
        }

        try {
            b = Integer.parseInt(context.getParameter("b"));
        } catch (NumberFormatException | NullPointerException ignorable) {
        }

        String sum = String.valueOf(a + b);
        context.setTemporaryParameter("zbroj", sum);
        context.getDispatcher().dispatchRequest("/private/calc.smscr");
    }
}

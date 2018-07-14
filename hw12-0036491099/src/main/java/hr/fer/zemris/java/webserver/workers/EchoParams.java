package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * This class represents {@link IWebWorker} used for displaying parameters of request in a table.
 */
public class EchoParams implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("    <title>Pink Floyd - Echoes</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<table>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Naziv parametra</th>");
        sb.append("<th>Vrijednost parametra</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        for (String s : context.getParameterNames()) {
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(s);
            sb.append("</td>");
            sb.append("<td>");
            sb.append(context.getParameter(s));
            sb.append("</td>");
        }
        sb.append("</tbody>");
        sb.append("</table>");

        sb.append("</body>\n");
        sb.append("</html>");

        context.write(sb.toString());

    }
}

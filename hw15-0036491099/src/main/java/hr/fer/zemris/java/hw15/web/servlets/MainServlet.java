package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.form.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * This servlet represents main servlet of our web application.
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BlogUser user = new BlogUser();
        LoginForm form = new LoginForm();
        form.fromUser(user);

        req.setAttribute("form", form);

        List<BlogUser> authors = DAOProvider.getDAO().getUsers();

        req.setAttribute("authors", authors);


        req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        List<BlogUser> authors = DAOProvider.getDAO().getUsers();

        req.setAttribute("authors", authors);

        req.setCharacterEncoding("UTF-8");

        LoginForm form = new LoginForm();
        form.fromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            form.setPasswordHash("");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
            return;
        }

        BlogUser user = new BlogUser();
        form.toUser(user);

        BlogUser databaseUser = DAOProvider.getDAO().getUser(user.getNick());

        if (databaseUser == null) {
            form.setPasswordHash("");
            form.setError("nick", "Korisnik ne postoji!");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
        } else if (!databaseUser.getPasswordHash().equals(user.getPasswordHash())) {
            form.setPasswordHash("");
            form.setError("password", "Lozinka nije ispravna!");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("current.user.id", databaseUser.getId());
            req.getSession().setAttribute("current.user.fn", databaseUser.getFirstName());
            req.getSession().setAttribute("current.user.ln", databaseUser.getLastName());
            req.getSession().setAttribute("current.user.nick", databaseUser.getNick());
            req.getSession().setAttribute("current.user.email", databaseUser.getEmail());
            resp.sendRedirect("main");
        }


    }
}

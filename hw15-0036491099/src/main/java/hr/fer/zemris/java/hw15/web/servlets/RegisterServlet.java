package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.form.LoginForm;
import hr.fer.zemris.java.hw15.model.form.RegisterForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * This servlet represents register servlet. It is used for registering new users.
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BlogUser user = new BlogUser();
        RegisterForm form = new RegisterForm();
        form.fromUser(user);

        req.setAttribute("form", form);

        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        RegisterForm form = new RegisterForm();
        form.fromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            form.setPasswordHash("");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            return;
        }

        BlogUser user = new BlogUser();
        form.toUser(user);

        BlogUser databaseUser = DAOProvider.getDAO().getUser(user.getNick());

        if (databaseUser != null) {
            form.setPasswordHash("");
            form.setError("nick", "Nadimak već postoji!");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            return;

        }

        DAOProvider.getDAO().persistUser(user);

        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());

        resp.sendRedirect("register");

    }
}

package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.form.CommentForm;
import hr.fer.zemris.java.hw15.model.form.EntryForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * This class represents author servlet. It is used for displaying author and entry pages.
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo().substring(1);


        String[] parts = path.split("/");

        BlogUser author = DAOProvider.getDAO().getUser(parts[0]);

        if (author == null) {
            req.setAttribute("error", "Traženi autor ne postoji");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }


        req.setAttribute("author", author);

        if (parts.length == 1) {
            authorPage(req, resp, author);
        } else if (parts.length == 2) {
            if (parts[1].equals("new")) {
                editPage(req, resp, author, null);
            } else {
                entryPage(req, resp, author, parts[1]);
            }
        } else if (parts.length == 3) {
            if (parts[1].equals("edit")) {
                editPage(req, resp, author, parts[2]);
            } else {
                req.setAttribute("error", "Nepravilan url!");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Nepravilan url!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo().substring(1);

        String[] parts = path.split("/");

        BlogUser author = DAOProvider.getDAO().getUser(parts[0]);

        if (author == null) {
            req.setAttribute("error", "Traženi autor ne postoji");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("author", author);


        if (parts.length == 2) {
            if (parts[1].equals("new")) {
                editPagePost(req, resp, author, null);
            } else {
                entryPagePost(req, resp, author, parts[1]);
            }
        } else if (parts.length == 3) {
            if (parts[1].equals("edit")) {
                editPagePost(req, resp, author, parts[2]);
            } else {
                req.setAttribute("error", "Nepravilan url!");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Nepravilan url!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    /**
     * This method is used for processing post request for editing entry.
     *
     * @param req    Request
     * @param resp   Response
     * @param author Author of entry
     * @param id     Id of entry
     * @throws ServletException Servlet exception
     * @throws IOException      Input output exception
     */
    private void editPagePost(HttpServletRequest req, HttpServletResponse resp, BlogUser author, String id) throws ServletException, IOException {
        if (!req.getSession().getAttribute("current.user.id").equals(author.getId())) {
            req.setAttribute("error", "Zabranjen pristup!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        EntryForm form = new EntryForm();
        BlogEntry entry = new BlogEntry();

        if (id != null) {
            try {
                entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(id));
                entry.setLastModifiedAt(new Date());
                req.setAttribute("id", id);
            } catch (NumberFormatException ex) {
                req.setAttribute("error", "Neispravan id posta");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                return;
            }
        } else {
            entry.setCreatedAt(new Date());
            entry.setLastModifiedAt(new Date());
        }

        if (entry == null) {
            req.setAttribute("error", "Pogrešan id posta!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        form.fromRequest(req);

        form.validate();


        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
            return;
        }

        form.toEntry(entry);
        entry.setCreator(author);


        DAOProvider.getDAO().persistEntry(entry);

        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + author.getNick());

    }

    /**
     * This method is used for processing post request for adding comment.
     *
     * @param req    Request
     * @param resp   Response
     * @param author Author of entry
     * @param id     Id of entry
     * @throws ServletException Servlet exception
     * @throws IOException      Input output exception
     */
    private void entryPagePost(HttpServletRequest req, HttpServletResponse resp, BlogUser author, String id) throws ServletException, IOException {
        BlogEntry entry = new BlogEntry();

        if (id != null) {
            try {
                entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(id));
            } catch (NumberFormatException ex) {
                req.setAttribute("error", "Pogrešan id posta");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                return;
            }
        }

        if (entry == null) {
            req.setAttribute("error", "Pogrešan id posta!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (!entry.getCreator().getId().equals(author.getId())) {
            req.setAttribute("error", "Neispravan id posta!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        CommentForm form = new CommentForm();

        form.fromRequest(req);

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.setAttribute("entry", entry);
            req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
            return;
        }

        BlogComment comment = new BlogComment();
        form.toComment(comment);
        comment.setBlogEntry(entry);
        comment.setPostedOn(new Date());

        DAOProvider.getDAO().persistComment(comment);

        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + author.getNick() + "/" + entry.getId());


    }

    /**
     * This method is used for processing get request for editing entry.
     *
     * @param req    Request
     * @param resp   Response
     * @param author Author of entry
     * @param id     Id of entry
     * @throws ServletException Servlet exception
     * @throws IOException      Input output exception
     */
    private void editPage(HttpServletRequest req, HttpServletResponse resp, BlogUser author, String id) throws ServletException, IOException {
        if (req.getSession().getAttribute("current.user.id") == null || !req.getSession().getAttribute("current.user.id")
                .equals(author.getId()
                )) {
            req.setAttribute("error", "zabranjen pristup!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        EntryForm form = new EntryForm();
        BlogEntry entry = new BlogEntry();

        if (id != null) {
            try {
                entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(id));
                req.setAttribute("id", id);
            } catch (NumberFormatException ex) {
                req.setAttribute("error", "Neispravan id posta!");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                return;
            }
        }

        if (entry == null) {
            req.setAttribute("error", "Pogrešan id posta!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (entry.getCreator() != null && !author.getId().equals(entry.getCreator().getId())) {
            req.setAttribute("error", "zabranjen pristup!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        form.fromEntry(entry);

        req.setAttribute("form", form);

        req.setCharacterEncoding("UTF-8");

        if (id == null) {
            req.setAttribute("action", req.getContextPath() + "/servleti/author/" + author.getNick() + "/new");
        } else {
            req.setAttribute("action", req.getContextPath() + "/servleti/author/" + author.getNick() + "/edit/" + id);
        }

        req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);

    }

    /**
     * This method is used for processing get request for entry page.
     *
     * @param req    Request
     * @param resp   Response
     * @param author Author of entry
     * @param id     Id of entry
     * @throws ServletException Servlet exception
     * @throws IOException      Input output exception
     */
    private void entryPage(HttpServletRequest req, HttpServletResponse resp, BlogUser author, String id) throws ServletException, IOException {

        BlogEntry entry = new BlogEntry();

        if (id != null) {
            try {
                entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(id));
            } catch (NumberFormatException ex) {
                req.setAttribute("error", "Pogrešan id posta!");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                return;
            }
        }

        if (entry == null) {
            req.setAttribute("error", "Pogrešan id posta!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (!entry.getCreator().getId().equals(author.getId())) {
            req.setAttribute("error", "Neispravan id posta!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        CommentForm form = new CommentForm();

        req.setAttribute("form", form);
        req.setAttribute("entry", entry);
        req.setAttribute("author", author);
        req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);

    }

    /**
     * This method is used for processing get request for author page.
     *
     * @param req    Request
     * @param resp   Response
     * @param author Author of entry
     * @throws ServletException Servlet exception
     * @throws IOException      Input output exception
     */
    private void authorPage(HttpServletRequest req, HttpServletResponse resp, BlogUser author) throws ServletException, IOException {

        List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntryForUser(author);
        req.setAttribute("entries", entries);
        req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
    }
}

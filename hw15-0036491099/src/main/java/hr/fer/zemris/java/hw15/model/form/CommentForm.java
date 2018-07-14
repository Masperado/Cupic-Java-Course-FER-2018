package hr.fer.zemris.java.hw15.model.form;

import hr.fer.zemris.java.hw15.model.BlogComment;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents comment form. It is used for inputting {@link BlogComment}.
 */
public class CommentForm {

    /**
     * Comment email.
     */
    private String email;

    /**
     * Comment text.
     */
    private String text;

    /**
     * Errors map.
     */
    Map<String, String> errors = new HashMap<>();

    /**
     * Basic constructor.
     */
    public CommentForm() {
    }

    /**
     * This method is used for getting error for given name.
     *
     * @param name Name
     * @return Error
     */
    public String getError(String name) {
        return errors.get(name);
    }

    /**
     * This method is used for checking if there is any errors.
     *
     * @return True if no errors, false otherwise
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * This method is used for checking if there is error for given name.
     *
     * @param name Name of error
     * @return True if there is error, false otherwise
     */
    public boolean hasError(String name) {
        return errors.containsKey(name);
    }

    /**
     * This method is used for filling form from request.
     *
     * @param req {@link HttpServletRequest}
     */
    public void fromRequest(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.email = prepare(req.getParameter("email"));
        this.text = prepare(req.getParameter("text"));
    }

    /**
     * This method is used for filling form from comment.
     *
     * @param comment {@link BlogComment}
     */
    public void fromComment(BlogComment comment) {
        this.email = comment.getUsersEMail();
        this.text = comment.getMessage();
    }

    /**
     * This method is used for filling comment from this form.
     *
     * @param comment {@link BlogComment}
     */
    public void toComment(BlogComment comment) {
        comment.setUsersEMail(email);
        comment.setMessage(text);
    }

    /**
     * This method is used for validating form.
     */
    public void validate() {
        errors.clear();

        if (this.email.isEmpty()) {
            errors.put("email", "E-Mail je obavezan!");
        } else {
            int l = email.length();
            int p = email.indexOf('@');
            if (l < 3 || p == -1 || p == 0 || p == l - 1) {
                errors.put("email", "E-Mail nije ispravan!");
            }
        }

        if (this.text.isEmpty()) {
            errors.put("text", "Tekst je obavezan!");
        }

        if (this.text.length() > 4096) {
            errors.put("text", "Tekst je predugačak!");
        }

    }

    /**
     * This method is used for preparing string for form.
     *
     * @param s String
     * @return Prepared string
     */
    private String prepare(String s) {
        if (s == null) return "";
        return s.trim();
    }

    /**
     * Getter for email.
     *
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for text.
     *
     * @return Text
     */
    public String getText() {
        return text;
    }

    /**
     * Getter for error maps.
     *
     * @return Error map
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Setter for email.
     *
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for text.
     *
     * @param text Text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Setter for errors map.
     *
     * @param errors Errors map
     */
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    /**
     * Setter for error for given name.
     *
     * @param key   Name of error
     * @param error Value of error
     */
    public void setError(String key, String error) {
        errors.put(key, error);
    }
}

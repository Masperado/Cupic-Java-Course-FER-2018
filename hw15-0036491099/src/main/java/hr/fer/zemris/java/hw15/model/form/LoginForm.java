package hr.fer.zemris.java.hw15.model.form;

import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static hr.fer.zemris.java.hw15.model.form.RegisterForm.crypt;


/**
 * This class represents login form. It is used for inputting {@link BlogUser} for logging in.
 */
public class LoginForm {


    /**
     * Nickname.
     */
    private String nick;

    /**
     * Password hash.
     */
    private String passwordHash;

    /**
     * Errors map.
     */
    Map<String, String> errors = new HashMap<>();

    /**
     * Basic constructor.
     */
    public LoginForm() {
    }

    /**
     * Getter for error.
     *
     * @param name Name of error
     * @return Error
     */
    public String getError(String name) {
        return errors.get(name);
    }

    /**
     * This method checks if there is any error in form.
     *
     * @return True if there is, false otherwise
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * This method checks if there is error in form.
     *
     * @param name Name of error
     * @return True if there is, false otherwise
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


        String password = req.getParameter("password");
        if (password==null){
            password="";
        }



        this.nick = prepare(req.getParameter("nick"));
        this.passwordHash = crpyt(password);
    }

    /**
     * This method is used for crypting password.
     *
     * @param password Password
     * @return Crypted password
     */
    private String crpyt(String password) {
        return crypt(password);
    }

    /**
     * This method is used filling form from {@link BlogUser}.
     *
     * @param user {@link BlogUser}
     */
    public void fromUser(BlogUser user) {
        this.nick = user.getNick();
    }

    /**
     * This method is used for filling user from form.
     *
     * @param user {@link BlogUser}
     */
    public void toUser(BlogUser user) {
        user.setNick(nick);
        user.setPasswordHash(passwordHash);
    }

    /**
     * This method is used for validating form.
     */
    public void validate() {
        errors.clear();

        if (this.nick.isEmpty()) {
            errors.put("nick", "Nadimak je obavezan!");
        }

        if (this.nick.length() > 200) {
            errors.put("nick", "Nadimak je predugačak!");
        }

        if (this.passwordHash.isEmpty()) {
            errors.put("password", "Password je obavezan!");
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
     * Getter for nickname.
     *
     * @return Nickname
     */
    public String getNick() {
        return nick;
    }

    /**
     * Getter for password hash.
     *
     * @return Password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Getter for errors.
     *
     * @return Errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Setter for nickname.
     *
     * @param nick Nickname
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Setter for password hash.
     *
     * @param passwordHash Password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
     * Setter for error.
     *
     * @param key   Name of error
     * @param error Value of error
     */
    public void setError(String key, String error) {
        errors.put(key, error);
    }
}

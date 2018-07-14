package hr.fer.zemris.java.hw15.model.form;

import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents register form. It is used for inputting {@link BlogUser} for registering.
 */
public class RegisterForm {


    /**
     * Nickname.
     */
    private String nick;

    /**
     * Password hash.
     */
    private String passwordHash;

    /**
     * First name.
     */
    private String firstName;

    /**
     * Last name.
     */
    private String lastName;

    /**
     * Email.
     */
    private String email;


    /**
     * Errors map.
     */
    Map<String, String> errors = new HashMap<>();

    /**
     * Basic constructor.
     */
    public RegisterForm() {
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
        this.firstName = prepare(req.getParameter("firstName"));
        this.lastName = prepare(req.getParameter("lastName"));
        this.email = prepare(req.getParameter("email"));
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
     * This method is used for crypting password.
     *
     * @param password Password
     * @return Crypted password
     */
    static String crypt(String password) {
        byte[] buff = password.getBytes();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        digest.update(buff, 0, buff.length);

        byte[] sum = digest.digest();

        StringBuilder sb = new StringBuilder();

        for (Byte b : sum) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    /**
     * This method is used filling form from {@link BlogUser}.
     *
     * @param user {@link BlogUser}
     */
    public void fromUser(BlogUser user) {
        this.nick = user.getNick();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

    /**
     * This method is used for filling user from form.
     *
     * @param user {@link BlogUser}
     */
    public void toUser(BlogUser user) {
        user.setNick(nick);
        user.setPasswordHash(passwordHash);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
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
            errors.put("password", "Lozinka je obavezna!");
        }

        if (this.firstName.isEmpty()) {
            errors.put("firstName", "Ime je obavezno!");
        }

        if (this.firstName.length() > 200) {
            errors.put("firstName", "Ime je predugačko!");
        }

        if (this.lastName.isEmpty()) {
            errors.put("lastName", "Prezime je obavezno!");
        }

        if (this.lastName.length() > 200) {
            errors.put("lastName", "Prezime je predugačko!");
        }

        if (this.email.isEmpty()) {
            errors.put("email", "E-Mail je obavezan!");
        } else {
            int l = email.length();
            int p = email.indexOf('@');
            if (l < 3 || p == -1 || p == 0 || p == l - 1) {
                errors.put("email", "E-Mail nije ispravnog formata.");
            }
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
     * Getter for first name.
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for last name.
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
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
     * Getter for errors.
     *
     * @return Errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Setter for nick.
     *
     * @param nick Nick
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
     * Setter for first name.
     *
     * @param firstName First name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for last name.
     *
     * @param lastName Last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Setter for errors.
     *
     * @param errors Errors
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

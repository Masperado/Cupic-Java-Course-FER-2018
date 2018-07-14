package hr.fer.zemris.java.hw15.model.form;

import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * This class represents entry form. It is used for inputting {@link BlogEntry}.
 */
public class EntryForm {

    /**
     * Title of form.
     */
    private String title;

    /**
     * Tex of form.
     */
    private String text;

    /**
     * Errors map.
     */
    Map<String, String> errors = new HashMap<>();

    /**
     * Basic constructor.
     */
    public EntryForm() {
    }

    /**
     * Getter for errors for given name.
     *
     * @param name Name
     * @return Error
     */
    public String getError(String name) {
        return errors.get(name);
    }

    /**
     * This method is used for checking if there is error in form.
     *
     * @return True if it is, false otherwise
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * This method is used for checking if there is error for given name.
     *
     * @param name Name of error
     * @return True if it is, false otherwise
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
        this.title = prepare(req.getParameter("title"));
        this.text = prepare(req.getParameter("text"));
    }

    /**
     * This method is used for filling form from {@link BlogEntry}.
     *
     * @param entry {@link BlogEntry}
     */
    public void fromEntry(BlogEntry entry) {
        this.title = entry.getTitle();
        this.text = entry.getText();
    }

    /**
     * This method is used for filling entry from form.
     *
     * @param entry {@link BlogEntry}
     */
    public void toEntry(BlogEntry entry) {
        entry.setTitle(title);
        entry.setText(text);
    }

    /**
     * This method is used for validating form.
     */
    public void validate() {
        errors.clear();

        if (this.title.isEmpty()) {
            errors.put("title", "Naziv je obavezan!");
        }

        if (this.title.length() > 200) {
            errors.put("title", "Naziv je predugačak!");
        }

        if (this.text.isEmpty()) {
            errors.put("text", "Tekst je obavezan!");
        }

        if (this.title.length() > 4096) {
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
     * Getter for title.
     *
     * @return Title
     */
    public String getTitle() {
        return title;
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
     * Getter for errors.
     *
     * @return Errors map
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Setter for title.
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Setter for errors.
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

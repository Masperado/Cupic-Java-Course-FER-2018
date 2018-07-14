package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is used as a provider for language. It is used as a singleton.
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * Current language.
     */
    private String language;

    /**
     * Resource bundle for providing language translations.
     */
    private ResourceBundle bundle;

    /**
     * Instance of this class.
     */
    private static LocalizationProvider instance;

    /**
     * Private constructor.
     */
    private LocalizationProvider() {
        language = "en";
        setBundle();
    }

    /**
     * Getter for instance of this class.
     *
     * @return Instance of this class
     */
    public static LocalizationProvider getInstance() {
        if (instance == null) {
            instance = new LocalizationProvider();
        }
        return instance;
    }

    /**
     * Setter for language.
     *
     * @param language Language
     */
    public void setLanguage(String language) {
        this.language = language;
        setBundle();
        fire();
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }


    /**
     * Setter for resource bundle.
     */
    private void setBundle() {
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
    }

    /**
     * Getter for current language.
     *
     * @return Current language
     */
    public String getLanguage() {
        return language;
    }
}

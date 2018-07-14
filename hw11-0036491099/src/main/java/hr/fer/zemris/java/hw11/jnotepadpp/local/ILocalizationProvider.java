package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This interface represents provider for language.
 */
public interface ILocalizationProvider {

    /**
     * This method is used for adding listener.
     *
     * @param listener Listener
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * This method is used for removing listener.
     *
     * @param listener Listener to be removed
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * This method returns string for given key.
     *
     * @param key Key
     * @return String for given key
     */
    String getString(String key);

}

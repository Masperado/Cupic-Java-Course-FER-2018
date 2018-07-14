package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class is used as a listener for {@link ILocalizationProvider}.
 */
public interface ILocalizationListener {

    /**
     * This method is called each time change has happened.
     */
    void localizationChanged();
}

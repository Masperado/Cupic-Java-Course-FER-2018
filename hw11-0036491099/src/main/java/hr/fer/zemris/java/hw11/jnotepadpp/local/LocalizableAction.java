package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * This class represents localized {@link AbstractAction}.
 */
public abstract class LocalizableAction extends AbstractAction {

    /**
     * Listener for changes of language.
     */
    private ILocalizationListener listener;

    /**
     * Provider for language.
     */
    private ILocalizationProvider prov;

    /**
     * Key of name of action.
     */
    private String key;

    /**
     * Basic constructor.
     *
     * @param prov Provider for language
     * @param key  Key of name of action
     */
    public LocalizableAction(ILocalizationProvider prov, String key) {
        this.prov = prov;
        this.key = key;
        this.listener = this::update;
        update();
        prov.addLocalizationListener(listener);
    }

    /**
     * This method is used for updating name and description of action.
     */
    private void update() {
        putValue(NAME, prov.getString(key));
        putValue(Action.SHORT_DESCRIPTION, prov.getString(key + "desc"));
    }
}

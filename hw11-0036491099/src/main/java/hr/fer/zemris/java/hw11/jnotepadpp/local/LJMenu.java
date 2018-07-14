package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * This class represents localized {@link JMenu}.
 */
public class LJMenu extends JMenu {

    /**
     * Listener for changes.
     */
    private ILocalizationListener listener;

    /**
     * Provider for language.
     */
    private ILocalizationProvider prov;

    /**
     * Key of name for this menu
     */
    private String key;

    /**
     * Basic constructor.
     *
     * @param prov Provider of language
     * @param key  Key of name for this menu
     */
    public LJMenu(ILocalizationProvider prov, String key) {
        this.prov = prov;
        this.key = key;
        this.listener = this::updateName;
        updateName();
        prov.addLocalizationListener(listener);
    }

    /**
     * THis method is used for updating name of this menu.
     */
    private void updateName() {
        setText(prov.getString(key));
    }
}

package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class is used as a bridge to {@link LocalizationProvider}.
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * Connected flag.
     */
    private boolean connected;

    /**
     * Listener for changes.
     */
    private ILocalizationListener listener;

    /**
     * Provider for language.
     */
    private ILocalizationProvider parent;


    /**
     * Basic constructor.
     *
     * @param parent Provider for language
     */
    public LocalizationProviderBridge(ILocalizationProvider parent) {
        this.parent = parent;

        listener = this::fire;
    }

    /**
     * This method is used for connecting to provider.
     */
    public void connect() {
        if (!connected) {
            parent.addLocalizationListener(listener);
            connected = true;
        }
    }

    /**
     * This class is used for disconnecting from provider.
     */
    public void disconnect() {
        parent.removeLocalizationListener(listener);
        connected = false;
        System.exit(0);
    }


    @Override
    public String getString(String key) {
        return parent.getString(key);
    }
}

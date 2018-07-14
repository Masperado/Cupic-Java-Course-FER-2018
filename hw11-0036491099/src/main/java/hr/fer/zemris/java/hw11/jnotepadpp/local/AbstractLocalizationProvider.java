package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used as a abstract implementation of {@link ILocalizationProvider}. It takes care of subject pattern
 * for localization provider.
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * List of listeners.
     */
    private List<ILocalizationListener> listeners = new ArrayList<>();

    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }

    /**
     * This method is called each time change has happened.
     */
    public void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }

}

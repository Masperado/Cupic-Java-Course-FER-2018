package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class is used as a proxy to {@link LocalizationProvider} for {@link JFrame}.
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Basic constructor.
     *
     * @param parent {@link ILocalizationProvider} provider
     * @param frame  {@link JFrame} frame
     */
    public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
        super(parent);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });

    }
}

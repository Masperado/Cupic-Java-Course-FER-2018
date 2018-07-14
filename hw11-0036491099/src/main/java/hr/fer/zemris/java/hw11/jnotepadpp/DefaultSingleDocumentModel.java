package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents default implementation of {@link SingleDocumentModel}.
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * Text area for writing text.
     */
    private JTextArea textArea;

    /**
     * File path of model.
     */
    private Path filePath;

    /**
     * List of listeners.
     */
    private List<SingleDocumentListener> listeners = new ArrayList<>();

    /**
     * Changed flag.
     */
    private boolean changed = false;

    /**
     * Basic constructor.
     *
     * @param filePath Path of model
     * @param text     Text of model
     */
    public DefaultSingleDocumentModel(Path filePath, String text) {
        this.filePath = filePath;
        this.textArea = new JTextArea(text);
        this.textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed = true;
                notifyListenersForStatus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed = true;
                notifyListenersForStatus();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed = true;
                notifyListenersForStatus();
            }
        });
    }

    /**
     * This method is used for notifying listeners that status is changed.
     */
    private void notifyListenersForStatus() {
        for (SingleDocumentListener l : listeners) {
            l.documentModifyStatusUpdated(this);
        }
    }

    /**
     * This method is used for notifying listeners that path is changed.
     */
    private void notifyListenersForPath() {
        for (SingleDocumentListener l : listeners) {
            l.documentFilePathUpdated(this);
        }
    }

    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        Objects.requireNonNull(path, "Path can't be null!");

        this.filePath = path;
        notifyListenersForPath();
    }

    @Override
    public boolean isModified() {
        return changed;
    }

    @Override
    public void setModified(boolean modified) {
        this.changed = modified;
        notifyListenersForStatus();
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }
}

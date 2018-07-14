package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;

/**
 * This interface represents single document model. It is used for represent one document in
 * {@link MultipleDocumentModel}.
 */
public interface SingleDocumentModel {

    /**
     * This method is used for getting {@link JTextArea} that is used for writing text into this model.
     *
     * @return {@link JTextArea}
     */
    JTextArea getTextComponent();

    /**
     * This method is used for getting file path.
     *
     * @return File path
     */
    Path getFilePath();

    /**
     * This method is used for setting file path.
     *
     * @param path New file path
     */
    void setFilePath(Path path);

    /**
     * This method is used for getting modified status.
     *
     * @return Modified status
     */
    boolean isModified();

    /**
     * This method is used for setting modified status.
     *
     * @param modified Modified status
     */
    void setModified(boolean modified);

    /**
     * This method is used for adding listener to this model.
     *
     * @param l Listener
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * This method is used for removing listener from this model.
     *
     * @param l Listener to be removed
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
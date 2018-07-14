package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This inteface represents multiple document listener. It is uses as a listener for {@link MultipleDocumentModel}.
 */
public interface MultipleDocumentListener {

    /**
     * This method is called everytime current document is changed.
     *
     * @param previousModel Previous current document
     * @param currentModel  Next current document
     */
    void currentDocumentChanged(SingleDocumentModel previousModel,
                                SingleDocumentModel currentModel);

    /**
     * This method is called everytime new document is added.
     *
     * @param model Added document
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * This method is called everytime documents is removed.
     *
     * @param model Removed document
     */
    void documentRemoved(SingleDocumentModel model);
}
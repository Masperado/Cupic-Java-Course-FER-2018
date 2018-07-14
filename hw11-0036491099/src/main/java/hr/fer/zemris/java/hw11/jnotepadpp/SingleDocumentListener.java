package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This interface represents listener for {@link SingleDocumentModel}.
 */
public interface SingleDocumentListener {

    /**
     * This method is called when modify status of model is updated.
     *
     * @param model Model
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * This method is called when file path of model is updated.
     *
     * @param model Model
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
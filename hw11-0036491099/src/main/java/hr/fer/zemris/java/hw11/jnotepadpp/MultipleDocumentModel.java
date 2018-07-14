package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * This interface represents multiple document. It is used as a logic for {@link JNotepadPP}. It represents
 * collection of {@link SingleDocumentModel} and is aware of current document selected.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

    /**
     * This method is used for creating new document.
     *
     * @return New document
     */
    SingleDocumentModel createNewDocument();

    /**
     * This method is used for getting current document.
     *
     * @return Current document
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * This method is used for loading document from path.
     *
     * @param path Path
     * @return Loaded document
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * This method is used for saving document to path.
     *
     * @param model   Document to be saved
     * @param newPath Path to be saved to
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * This method is used for closing document.
     *
     * @param model Document to be closed
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * This method is used for adding listeners to this model which reprsents subject.
     *
     * @param l Listener
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * This method is used for removing listeners from this model.
     *
     * @param l Listener to be removed
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * This method is used for getting current number of documents.
     *
     * @return Number of documents
     */
    int getNumberOfDocuments();

    /**
     * This method is used for getting document at given index.đ
     *
     * @param index Index of document
     * @return Document at given index
     */
    SingleDocumentModel getDocument(int index);
}
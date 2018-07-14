package hr.fer.zemris.java.hw11.jnotepadpp;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This class represents default implementation of {@link MultipleDocumentModel}. It also extends {@link JTabbedPane}.
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    /**
     * List of documents.
     */
    private List<SingleDocumentModel> documents = new ArrayList<>();

    /**
     * Current document.
     */
    private SingleDocumentModel currentDocument;

    /**
     * List of listeners.
     */
    private List<MultipleDocumentListener> listeners = new ArrayList<>();


    /**
     * Basic constructor.
     */
    public DefaultMultipleDocumentModel() {
        addChangeListener(e -> {
            SingleDocumentModel oldDocument = currentDocument;
            currentDocument = documents.get(getSelectedIndex());
            notifyChangedCurrentDocument(oldDocument, currentDocument);
        });

    }

    @Override
    public SingleDocumentModel createNewDocument() {

        return addDocument(null, "");
    }


    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        Objects.requireNonNull(path, "Path can't be null!");

        for (SingleDocumentModel model : documents) {
            if (model.getFilePath() != null && model.getFilePath().equals(path)) {
                notifyChangedCurrentDocument(currentDocument, model);
                currentDocument = model;
                return model;
            }
        }

        if (!Files.isReadable(path)) {
            throw new RuntimeException("File is not readable!");
        }

        byte[] okteti;

        try {
            okteti = Files.readAllBytes(path);
        } catch (Exception ex) {
            throw new RuntimeException("File is not readable");
        }

        return addDocument(path, new String(okteti, StandardCharsets.UTF_8));

    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {

        for (SingleDocumentModel document : documents) {
            if (document.equals(model)) {
                continue;
            }

            if (document.getFilePath()!=null && document.getFilePath().equals(newPath) && !document.equals(model)) {
                throw new RuntimeException("File is already opened!");
            }
        }

        byte[] podaci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

        if (newPath == null) {
            newPath = model.getFilePath();
        } else {
            currentDocument.setFilePath(newPath);
        }

        try {
            Files.write(newPath, podaci);
        } catch (IOException e1) {
            throw new RuntimeException("Error while writing to file!");
        }

        currentDocument.setModified(false);


    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (documents.size() == 1) {
            createNewDocument();
        }

        int newIndex = documents.indexOf(model) - 1;
        if (newIndex < 0) {
            newIndex = 0;
        }

        removeTabAt(documents.indexOf(model));
        setSelectedIndex(newIndex);
        documents.remove(model);
        currentDocument = documents.get(newIndex);
        notifyRemoved(model);

    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

    /**
     * This method is used for adding new document to this model.
     *
     * @param path Path of new document
     * @param s    Content of new document
     * @return New document
     */
    private SingleDocumentModel addDocument(Path path, String s) {
        SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, s);
        documents.add(newDocument);
        notifyAdded(newDocument);
        notifyChangedCurrentDocument(currentDocument, newDocument);
        currentDocument = newDocument;
        newDocument.addSingleDocumentListener(new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                changeIcon(model);
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                changePath(model);
            }
        });

        addToTabPane(newDocument);
        return newDocument;
    }

    /**
     * This method is used for notifying listeners that new document is added.
     *
     * @param document New document
     */
    private void notifyAdded(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners) {
            l.documentAdded(document);
        }
    }

    /**
     * This method is used for notifying listeners that document is removed.
     *
     * @param document Removed document
     */
    private void notifyRemoved(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners) {
            l.documentRemoved(document);
        }
    }

    /**
     * This method is used for notifying listeners that current document is changed.
     *
     * @param oldDocument Previous current document
     * @param newDocument New current document
     */
    private void notifyChangedCurrentDocument(SingleDocumentModel oldDocument, SingleDocumentModel newDocument) {
        for (MultipleDocumentListener l : listeners) {
            l.currentDocumentChanged(oldDocument, newDocument);
        }
    }

    /**
     * This method is used for getting {@link ImageIcon} for given string.
     *
     * @param s Name of icon
     * @return {@link ImageIcon}
     */
    private ImageIcon getIcon(String s) {
        InputStream is = JNotepadPP.class.getResourceAsStream("icons/" + s);
        if (is == null) {
            throw new RuntimeException("Icon " + s + " does not exist!");
        }
        byte[] bytes;
        try {
            bytes = is.readAllBytes();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading icon!");
        }

        Image img = new ImageIcon(bytes).getImage();
        Image newImg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

        return new ImageIcon(newImg);
    }

    /**
     * This method is used for adding document to pane of this model.
     *
     * @param singleDocument Document to be added
     */
    private void addToTabPane(SingleDocumentModel singleDocument) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(singleDocument.getTextComponent()), BorderLayout.CENTER);

        String fileName = "";
        String fullName = "";

        if (singleDocument.getFilePath() != null) {
            fileName = singleDocument.getFilePath().getFileName().toString();
            fullName = singleDocument.getFilePath().toAbsolutePath().toString();
        }


        addTab(fileName, getIcon("greenDisk.png"), panel, fullName);
        setSelectedComponent(panel);
    }

    /**
     * This method is used for changing icon of given model.
     *
     * @param singleDocument Model of which icon will be changed
     */
    private void changeIcon(SingleDocumentModel singleDocument) {
        if (singleDocument.isModified()) {
            setIconAt(documents.indexOf(singleDocument), getIcon("redDisk.png"));
        } else {
            setIconAt(documents.indexOf(singleDocument), getIcon("greenDisk.png"));
        }
    }

    /**
     * This method is used for changing path of given document.
     *
     * @param singleDocument Document which path will be changed
     */
    private void changePath(SingleDocumentModel singleDocument) {
        setTitleAt(documents.indexOf(singleDocument), singleDocument.getFilePath().getFileName().toString());
        setToolTipTextAt(documents.indexOf(singleDocument), singleDocument.getFilePath().toString());
    }
}

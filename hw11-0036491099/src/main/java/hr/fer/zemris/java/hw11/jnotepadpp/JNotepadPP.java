package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

/**
 * This class represents JNotepadPP. It is used as a notepad with many additional functionalities like statistical
 * info, sorting lines, removing duplicates, etc. It also support three languages, english, croatian and serbian,
 * which can me easily switched with Languages menu.
 */
public class JNotepadPP extends JFrame {

    /**
     * Model used for logic of notepad.
     */
    private MultipleDocumentModel model;

    /**
     * Length label.
     */
    private JLabel length = new JLabel();

    /**
     * Info label.
     */
    private JLabel info = new JLabel();

    /**
     * Time label.
     */
    private JLabel time = new JLabel();

    /**
     * Provider for localization which is used for switch between languages.
     */
    private ILocalizationProvider provider;

    /**
     * Action for creating new documents.
     */
    private Action createBlankDocumentAction;

    /**
     * Action for opening files from computer.
     */
    private Action openDocumentAction;

    /**
     * Action for saving files.
     */
    private Action saveDocumentAction;

    /**
     * Action for saving files as a new file.
     */
    private Action saveAsDocumentAction;

    /**
     * Action for closing current tab.
     */
    private Action closeTabAction;

    /**
     * Action for cutting selected text.
     */
    private Action cutAction;

    /**
     * Action for copying selected text.
     */
    private Action copyAction;

    /**
     * Action for pasting text from clipboard.
     */
    private Action pasteAction;

    /**
     * Action for showing statistics.
     */
    private Action statisticalAction;

    /**
     * Action for exiting application.
     */
    private Action exitAction;

    /**
     * Action for switching to english language.
     */
    private Action engAction;

    /**
     * Action for switching to croatian language.
     */
    private Action hrAction;

    /**
     * Action for switching to serbian language.
     */
    private Action srbAction;

    /**
     * Action for converting selected text to upper case.
     */
    private Action upperCaseAction;

    /**
     * Action for converting selected text to lower case.
     */
    private Action lowerCaseAction;

    /**
     * Action for inverting case of selected case.
     */
    private Action invertCaseAction;

    /**
     * Action for sorting selected lines ascending.
     */
    private Action ascendingAction;

    /**
     * Action for sorting selected lines descending.
     */
    private Action descendingAction;

    /**
     * Action for removing duplicate lines.
     */
    private Action uniqueAction;


    /**
     * Basic constructor.
     */
    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });
        setLocation(0, 0);
        setSize(1000, 600);
        this.provider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
        initGUI();
    }

    /**
     * This method is used for initalizing graphical user interface.
     */
    private void initGUI() {
        DefaultMultipleDocumentModel tabbedModel = new DefaultMultipleDocumentModel();

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(
                tabbedModel, BorderLayout.CENTER
        );

        this.model = tabbedModel;

        model.createNewDocument();

        initializeActions();
        createActions();
        createMenus();
        createToolbars();

        String path = "Untitled";

        SingleDocumentModel currentModel = model.getCurrentDocument();

        if (currentModel.getFilePath() != null) {
            path = currentModel.getFilePath().getFileName().toString();
        }

        setTitle(path + " - JNotepad++");

        model.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                String path = "Untitled";

                if (currentModel.getFilePath() != null) {
                    path = currentModel.getFilePath().getFileName().toString();
                }

                setTitle(path + " - JNotepad++");

                currentModel.getTextComponent().addCaretListener(e -> statusChanged());

                statusChanged();
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {

            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {

            }
        });

        new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();

            int year = now.getYear();
            int month = now.getMonth().getValue();
            int day = now.getDayOfMonth();

            int hour = now.getHour();
            int minute = now.getMinute();
            int second = now.getSecond();

            time.setText(String.format(" %d/%02d/%02d %02d:%02d:%02d ", year, day, month, hour, minute, second));

        }).start();

        model.getCurrentDocument().getTextComponent().addCaretListener(e -> statusChanged());

        statusChanged();
    }

    /**
     * This method is used for initializing actions.
     */
    private void initializeActions() {
        createBlankDocumentAction = new LocalizableAction(provider, "new") {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.createNewDocument();
                statusChanged();
            }
        };

        openDocumentAction = new LocalizableAction(provider, "open") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Open file");
                if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                File fileName = fc.getSelectedFile();
                Path filePath = fileName.toPath();

                try {
                    model.loadDocument(filePath);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, "Pogreška pri čitanju datoteke " + fileName.getAbsolutePath(),
                            "Pogreška", JOptionPane.ERROR_MESSAGE);
                }
                statusChanged();
            }
        };

        saveDocumentAction = new LocalizableAction(provider, "save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCurrentDocument().getFilePath() == null) {
                    saveAsDocumentAction.actionPerformed(e);
                    return;
                }

                try {
                    model.saveDocument(model.getCurrentDocument(), null);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, "Pogreška pri spremanju u " + model.getCurrentDocument()
                                    .getFilePath(),
                            "Pogreška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(JNotepadPP.this, "Datoteka je snimljena.", "Informacija", JOptionPane
                        .INFORMATION_MESSAGE);
            }
        };

        saveAsDocumentAction = new LocalizableAction(provider, "saveas") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Save document");
                if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, "Ništa nije snimljeno.", "Upozorenje", JOptionPane
                            .WARNING_MESSAGE);
                    return;
                }
                Path savePath = jfc.getSelectedFile().toPath();

                if (Files.exists(savePath)) {
                    int pressed = JOptionPane.showConfirmDialog(JNotepadPP.this,
                            "File already exists. Are you sure you want to save to selected file?", "Saving",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (pressed == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                try {
                    model.saveDocument(model.getCurrentDocument(), savePath);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, "Pogreška pri spremanju u " + savePath,
                            "Pogreška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(JNotepadPP.this, "Datoteka je snimljena.", "Informacija", JOptionPane
                        .INFORMATION_MESSAGE);
            }
        };

        closeTabAction = new LocalizableAction(provider, "closetab") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!model.getCurrentDocument().isModified()) {
                    model.closeDocument(model.getCurrentDocument());
                    return;
                }

                int pressed = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        "There is unsaved work! Do you want to close tab?", "Exiting",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (pressed == JOptionPane.YES_OPTION) {
                    model.closeDocument(model.getCurrentDocument());
                }

                statusChanged();
            }
        };

        cutAction = new LocalizableAction(provider, "cut") {
            Action action = new DefaultEditorKit.CutAction();

            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        };

        copyAction = new LocalizableAction(provider, "copy") {
            Action action = new DefaultEditorKit.CopyAction();

            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        };

        pasteAction = new LocalizableAction(provider, "paste") {
            Action action = new DefaultEditorKit.PasteAction();

            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        };


        statisticalAction = new LocalizableAction(provider, "stats") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = model.getCurrentDocument().getTextComponent().getText();

                int numberOfCharacters = text.length();
                int numberOfNonBlankCharacters = text.replaceAll("\\s+", "").length();
                int numberOfLines = text.length() - text.replaceAll("\n", "").length() + 1;

                String message = "Number of characters: " + numberOfCharacters + "\n" + "Number of non blank characters: " +
                        "" + numberOfNonBlankCharacters + "\n" + "Number of lines: " + numberOfLines;

                JOptionPane.showMessageDialog(JNotepadPP.this, message, "Informacija", JOptionPane
                        .INFORMATION_MESSAGE);
            }
        };

        exitAction = new LocalizableAction(provider, "exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < model.getNumberOfDocuments(); i++) {
                    if (model.getDocument(i).isModified()) {
                        int pressed = JOptionPane.showConfirmDialog(JNotepadPP.this,
                                "There is unsaved work! Do you want to close tab" + i + " ?", "Exiting",
                                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                        if (pressed == JOptionPane.YES_OPTION) {
                            model.closeDocument(model.getDocument(i));
                        } else {
                            return;
                        }

                        statusChanged();
                    }
                }
                dispose();
            }
        };

        engAction = new LocalizableAction(provider, "en") {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("en");
            }
        };

        hrAction = new LocalizableAction(provider, "hr") {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("hr");
            }
        };

        srbAction = new LocalizableAction(provider, "srb") {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("srb");
            }
        };

        upperCaseAction = new LocalizableAction(provider, "upper") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = 0;
                if (len != 0) {
                    offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
                } else {
                    len = doc.getLength();
                }

                try {
                    String text = doc.getText(offset, len).toUpperCase();
                    doc.remove(offset, len);
                    doc.insertString(offset, text, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };

        lowerCaseAction = new LocalizableAction(provider, "lower") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = 0;
                if (len != 0) {
                    offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
                } else {
                    len = doc.getLength();
                }

                try {
                    String text = doc.getText(offset, len).toLowerCase();
                    doc.remove(offset, len);
                    doc.insertString(offset, text, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };

        invertCaseAction = new LocalizableAction(provider, "invert") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = 0;
                if (len != 0) {
                    offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
                } else {
                    len = doc.getLength();
                }

                try {
                    String text = doc.getText(offset, len);
                    text = changeCase(text);
                    doc.remove(offset, len);
                    doc.insertString(offset, text, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

            private String changeCase(String text) {
                char[] znakovi = text.toCharArray();
                for (int i = 0; i < znakovi.length; i++) {
                    char c = znakovi[i];
                    if (Character.isLowerCase(c)) {
                        znakovi[i] = Character.toUpperCase(c);
                    } else if (Character.isUpperCase(c)) {
                        znakovi[i] = Character.toLowerCase(c);
                    }
                }
                return new String(znakovi);
            }
        };

        ascendingAction = new LocalizableAction(provider, "asc") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = 0;
                if (len != 0) {
                    offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
                } else {
                    len = doc.getLength();
                }

                try {
                    offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
                    len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));

                    String text = doc.getText(offset, len - offset);

                    doc.remove(offset, len);
                    doc.insertString(offset, sort(text, true), null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };

        descendingAction = new LocalizableAction(provider, "desc") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = 0;
                if (len != 0) {
                    offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
                } else {
                    len = doc.getLength();
                }

                try {
                    offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
                    len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));

                    String text = doc.getText(offset, len - offset);

                    doc.remove(offset, len);
                    doc.insertString(offset, sort(text, false), null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };

        uniqueAction = new LocalizableAction(provider, "uniq") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = 0;
                if (len != 0) {
                    offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
                } else {
                    len = doc.getLength();
                }

                try {
                    offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
                    len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));

                    String text = doc.getText(offset, len - offset);

                    Set<String> lines = new LinkedHashSet<>(Arrays.asList(text.split("\\r?\\n")));

                    StringBuilder sb = new StringBuilder();
                    for (String line : lines) {
                        sb.append(line).append("\n");
                    }
                    sb.setLength(sb.length() - 1);

                    doc.remove(offset, len);
                    doc.insertString(offset, sb.toString(), null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };

    }

    /**
     * This method is used for sorting lines of text.
     *
     * @param text Text
     * @param asc  Sorted ascending if true, false otherwise
     * @return Sorted text
     */
    private String sort(String text, boolean asc) {

        StringBuilder sb = new StringBuilder();
        Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
        Collator collator = Collator.getInstance(locale);
        List<String> lines = new ArrayList<>(Arrays.asList(text.split("\\r?\\n")));
        lines.sort(asc ? collator : collator.reversed());
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }


    /**
     * This method is used for putting accelerator and mnemonic keys to actions.
     */
    private void createActions() {

        createBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        createBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

        openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

        closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
        closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

        copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

        pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

        statisticalAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        statisticalAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);


        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

        engAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control J"));
        engAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);

        hrAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
        hrAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);

        srbAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
        srbAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

        upperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
        upperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

        lowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
        lowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

        invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
        invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

        ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
        ascendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);

        descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
        descendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);

        uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
        uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);

    }

    /**
     * This method is used for creating menus.
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LJMenu(provider, "file");
        menuBar.add(fileMenu);

        fileMenu.add(new JMenuItem(createBlankDocumentAction));
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveAsDocumentAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(closeTabAction));
        fileMenu.add(new JMenuItem(exitAction));

        JMenu editMenu = new LJMenu(provider, "edit");
        menuBar.add(editMenu);

        editMenu.add(new JMenuItem(cutAction));
        editMenu.add(new JMenuItem(copyAction));
        editMenu.add(new JMenuItem(pasteAction));
        editMenu.add(new JMenuItem(statisticalAction));


        JMenu languageMenu = new LJMenu(provider, "languages");
        menuBar.add(languageMenu);

        languageMenu.add(new JMenuItem(engAction));
        languageMenu.add(new JMenuItem(hrAction));
        languageMenu.add(new JMenuItem(srbAction));

        JMenu toolsMenu = new LJMenu(provider, "tools");
        menuBar.add(toolsMenu);

        toolsMenu.add(new JMenuItem(upperCaseAction));
        toolsMenu.add(new JMenuItem(lowerCaseAction));
        toolsMenu.add(new JMenuItem(invertCaseAction));
        toolsMenu.addSeparator();
        toolsMenu.add(new JMenuItem(ascendingAction));
        toolsMenu.add(new JMenuItem(descendingAction));
        toolsMenu.add(new JMenuItem(uniqueAction));

        this.setJMenuBar(menuBar);
    }

    /**
     * This method is used for creating toolbars.
     */
    private void createToolbars() {
        JToolBar toolBar = new JToolBar(provider.getString("tools"));
        toolBar.setFloatable(true);

        toolBar.add(new JButton(createBlankDocumentAction));
        toolBar.add(new JButton(openDocumentAction));
        toolBar.add(new JButton(saveDocumentAction));
        toolBar.add(new JButton(saveAsDocumentAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(closeTabAction));
        toolBar.add(new JButton(exitAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(cutAction));
        toolBar.add(new JButton(copyAction));
        toolBar.add(new JButton(pasteAction));
        toolBar.add(new JButton(statisticalAction));
        toolBar.addSeparator();

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);


        JToolBar status = new JToolBar(provider.getString("stats"));
        status.setFloatable(true);
        status.setLayout(new BorderLayout());

        status.add(length, BorderLayout.WEST);
        status.add(info, BorderLayout.CENTER);
        status.add(time, BorderLayout.EAST);

        this.getContentPane().add(status, BorderLayout.PAGE_END);
    }

    /**
     * This method is called everytime status is changed so status bar is updated.
     */
    private void statusChanged() {
        JTextArea editor = model.getCurrentDocument().getTextComponent();
        length.setText(String.format("%s: %d", provider.getString("length"), editor.getText().length()));
        try {
            int currentLine = editor.getLineOfOffset(editor.getCaretPosition());
            int currentColumn = editor.getCaretPosition() - editor.getLineStartOffset(currentLine);
            int selected = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
            info.setText(String.format("    %s:%d %s:%d %s:%d", provider.getString("ln"), currentLine+1,
                    provider.getString("col"), currentColumn, provider.getString("sel"), selected));

        } catch (BadLocationException ignored) {
        }

    }

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }
}

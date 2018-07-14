package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.components.*;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw16.jvdraw.objects.*;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledPolygonTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents JVDraw. It is used as a simple Paint application. It support drawing line, circles and
 * filled circles. It supports saving drawings to disk a .jvd files, opening .jvd files to disk and exporting picture
 * as .jpg, .png or .gif.
 */
public class JVDraw extends JFrame {

    /**
     * Foreground color provider.
     */
    private IColorProvider fgColorProvider;

    /**
     * Background color provider.
     */
    private IColorProvider bgColorProvider;

    /**
     * JList of {@link GeometricalObject}.
     */
    private JList<GeometricalObject> list;

    /**
     * {@link DrawingModel} used in application.
     */
    private DrawingModel drawingModel;

    /**
     * {@link JDrawingCanvas} used in application.
     */
    private JDrawingCanvas canvas;

    /**
     * Current selected {@link Tool}.
     */
    private Tool currentTool;

    /**
     * Tool for drawing line.
     */
    private Tool lineTool;

    /**
     * Tool for drawing circle.
     */
    private Tool circleTool;

    /**
     * Tool for drawing filled circle.
     */
    private Tool filledCircleTool;

    private Tool filledPolygonTool;

    /**
     * Open action.
     */
    private Action openAction;

    /**
     * Save action.
     */
    private Action saveAction;

    /**
     * Save as action.
     */
    private Action saveAsAction;

    /**
     * Export action.
     */
    private Action exportAction;

    /**
     * Exit action.
     */
    private Action exitAction;

    /**
     * Current path.
     */
    private Path currentPath;

    /**
     * Unsaved changes flag.
     */
    private boolean unsavedChanges;

    /**
     * Basic constructor.
     */
    public JVDraw() {

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });

        setLocation(0, 0);
        setSize(1200, 600);
        setTitle("JVDraw");

        initGui();
    }

    /**
     * This method is used for initializing graphical user interface.
     */
    private void initGui() {
        getContentPane().setLayout(new BorderLayout());

        initToolbar();

        drawingModel = new DrawingModelImpl();

        ListModel<GeometricalObject> listModel = new DrawingObjectsListModel(drawingModel);

        list = new JList<>(listModel);

        list.setFixedCellWidth((int) (getWidth() * 0.3 - 4));
        list.setVisible(true);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }
                int index = list.getSelectedIndex();
                if (index == -1) {
                    return;
                }

                GeometricalObjectEditor editor = drawingModel.getObject(index).createGeometricalObjectEditor();

                if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit object", JOptionPane.YES_NO_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {
                    try {
                        editor.checkEditing();
                        editor.acceptEditing();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(JVDraw.this, "Error in object parameters!");
                    }
                }
            }
        });

        list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int index = list.getSelectedIndex();
                if (index == -1) {
                    return;
                }
                if (e.getKeyCode() == 127) {
                    drawingModel.remove(index);
                } else if (e.getKeyCode() == 107) {
                    drawingModel.changeOrder(drawingModel.getObject(index), 1);
                    if ((index + 1) > list.getMaxSelectionIndex()) {
                        list.setSelectedIndex(index + 1);
                    }
                } else if (e.getKeyCode() == 109) {
                    drawingModel.changeOrder(drawingModel.getObject(index), -1);
                    if ((index - 1) < list.getMinSelectionIndex()) {
                        list.setSelectedIndex(index - 1);
                    }
                }
            }
        });

        JScrollPane listPane = new JScrollPane(list);

        listPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        getContentPane().add(listPane, BorderLayout.EAST);

        lineTool = new LineTool(fgColorProvider, drawingModel);

        circleTool = new CircleTool(fgColorProvider, drawingModel);

        filledCircleTool = new FilledCircleTool(fgColorProvider, bgColorProvider, drawingModel);

        filledPolygonTool = new FilledPolygonTool(fgColorProvider, bgColorProvider, drawingModel,this);

        currentTool = filledPolygonTool;

        canvas = new JDrawingCanvas(drawingModel, currentTool, (int) (getWidth() * 0.7 - 4));


        getContentPane().add(canvas, BorderLayout.WEST);


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvas.setWidth((int) (getWidth() * 0.7 - 4));
                list.setFixedCellWidth((int) (getWidth() * 0.3 - 4));
            }
        });


        initializeActions();
        createActions();
        createMenus();

        drawingModel.addDrawingModelListener(new DrawingModelListener() {
            @Override
            public void objectsAdded(DrawingModel source, int index0, int index1) {
                unsavedChanges = true;
            }

            @Override
            public void objectsRemoved(DrawingModel source, int index0, int index1) {
                unsavedChanges = true;
            }

            @Override
            public void objectsChanged(DrawingModel source, int index0, int index1) {
                unsavedChanges = true;
            }
        });
    }


    /**
     * This method is used for initializing toolbars.
     */
    private void initToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(true);

        fgColorProvider = new JColorArea(Color.RED);
        bgColorProvider = new JColorArea(Color.BLUE);
        toolBar.add((JColorArea) fgColorProvider);
        toolBar.addSeparator();
        toolBar.add((JColorArea) bgColorProvider);

        toolBar.addSeparator();
        ButtonGroup group = new ButtonGroup();

        JToggleButton line = new JToggleButton("Line");
        line.addActionListener(l -> {
            currentTool = lineTool;
            canvas.setCurrentTool(lineTool);
        });
        group.add(line);
        toolBar.add(line);

        JToggleButton circle = new JToggleButton("Circle");
        circle.addActionListener(l -> {
            currentTool = circleTool;
            canvas.setCurrentTool(circleTool);
        });
        group.add(circle);
        toolBar.add(circle);

        JToggleButton filledCircle = new JToggleButton("Filled circle");
        filledCircle.addActionListener(l -> {
            currentTool = filledCircleTool;
            canvas.setCurrentTool(filledCircleTool);
        });
        group.add(filledCircle);
        toolBar.add(filledCircle);

        JToggleButton filledPolygon = new JToggleButton("Filled polygon");
        filledPolygon.addActionListener(l -> {
            currentTool = filledPolygonTool;
            canvas.setCurrentTool(filledPolygonTool);
        });
        group.add(filledPolygon);
        toolBar.add(filledPolygon);

        filledPolygon.setSelected(true);


        getContentPane().add(toolBar, BorderLayout.PAGE_START);

        JToolBar status = new JToolBar();
        status.setFloatable(true);

        JColorLabel colorLabel = new JColorLabel(fgColorProvider, bgColorProvider);

        status.add(colorLabel);

        getContentPane().add(status, BorderLayout.PAGE_END);


    }


    /**
     * This method is used for initializing actions.
     */
    private void initializeActions() {
        openAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (unsavedChanges) {
                    int pressed = JOptionPane.showConfirmDialog(JVDraw.this,
                            "There are unsaved changes. Do you want to save current work?", "Opening",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (pressed == JOptionPane.YES_OPTION) {
                        if (currentPath == null) {
                            saveAsAction.actionPerformed(null);
                        } else {
                            saveAction.actionPerformed(null);
                        }
                    } else if (pressed == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files", "jvd");
                fc.setFileFilter(filter);
                fc.setDialogTitle("Open file");
                if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                File fileName = fc.getSelectedFile();
                Path filePath = fileName.toPath();

                try {
                    List<String> lines = Files.readAllLines(filePath);


                    int size = drawingModel.getSize();
                    for (int i = 0; i < size; i++) {
                        drawingModel.remove(0);
                    }

                    List<GeometricalObject> objects = new ArrayList<>();
                    for (String line : lines) {
                        if (line.startsWith("LINE")) {
                            objects.add(Line.parse(line));
                        } else if (line.startsWith("CIRCLE")) {
                            objects.add(Circle.parse(line));
                        } else if (line.startsWith("FCIRCLE")) {
                            objects.add(FilledCircle.parse(line));
                        } else if (line.startsWith("FPOLY")) {
                            objects.add(FilledPolygon.parse(line));
                        } else {
                            throw new RuntimeException();
                        }
                    }
                    for (GeometricalObject object : objects) {
                        drawingModel.add(object);
                    }
                    currentPath = filePath;
                    setTitle("JVDraw " + filePath);
                    unsavedChanges = false;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Pogreška pri čitanju datoteke " + fileName
                                    .getAbsolutePath(),
                            "Pogreška", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        saveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPath == null) {
                    saveAsAction.actionPerformed(e);
                    return;
                }

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < drawingModel.getSize(); i++) {
                    GeometricalObject object = drawingModel.getObject(i);
                    if (object instanceof Line) {
                        sb.append(Line.writeToString((Line) object));
                    } else if (object instanceof Circle) {
                        sb.append(Circle.writeToString((Circle) object));
                    } else if (object instanceof FilledCircle) {
                        sb.append(FilledCircle.writeToString((FilledCircle) object));
                    } else if (object instanceof FilledPolygon) {
                        sb.append(FilledPolygon.writeToString((FilledPolygon) object));
                    } else {
                        throw new RuntimeException("This should never happen!");
                    }
                }

                try {
                    Files.write(currentPath, sb.toString().getBytes(StandardCharsets.UTF_8));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Pogreška pri spremanju u " + currentPath,
                            "Pogreška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                unsavedChanges = false;
                JOptionPane.showMessageDialog(JVDraw.this, "Datoteka je snimljena.", "Informacija", JOptionPane
                        .INFORMATION_MESSAGE);

            }
        };

        saveAsAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Save document");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files", "jvd");
                jfc.setFileFilter(filter);
                if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Ništa nije snimljeno.", "Upozorenje", JOptionPane
                            .WARNING_MESSAGE);
                    return;
                }
                Path savePath = jfc.getSelectedFile().toPath();

                String extension = savePath.toString().substring(savePath.toString().length() - 3);
                if (!extension.equals("jvd")) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Kriva ekstenzija datoteke " + savePath,
                            "Pogreška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Files.exists(savePath)) {
                    int pressed = JOptionPane.showConfirmDialog(JVDraw.this,
                            "File already exists. Are you sure you want to save to selected file?", "Saving",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (pressed == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                currentPath = savePath;
                setTitle("JVDraw " + savePath);
                saveAction.actionPerformed(e);
            }
        };

        exportAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
                for (int i = 0; i < drawingModel.getSize(); i++) {
                    GeometricalObject object = drawingModel.getObject(i);
                    object.accept(bbcalc);
                }
                Rectangle box = bbcalc.getBoundingBox();
                BufferedImage image = new BufferedImage(
                        box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
                );
                Graphics2D g = image.createGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, box.width, box.height);
                g.translate(-box.x, -box.y);
                GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
                for (int i = 0; i < drawingModel.getSize(); i++) {
                    GeometricalObject object = drawingModel.getObject(i);
                    object.accept(painter);
                }
                g.dispose();
                JFileChooser jfc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
                jfc.setFileFilter(filter);
                jfc.setDialogTitle("Export image");
                if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Ništa nije snimljeno.", "Upozorenje", JOptionPane
                            .WARNING_MESSAGE);
                    return;
                }
                Path savePath = jfc.getSelectedFile().toPath();

                if (Files.exists(savePath)) {
                    int pressed = JOptionPane.showConfirmDialog(JVDraw.this,
                            "File already exists. Are you sure you want to save to selected file?", "Saving",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (pressed == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                File file = savePath.toFile();
                try {
                    String extension = savePath.toString().substring(savePath.toString().length() - 3);
                    if (!extension.equals("jpg") && !extension.equals("png") && !extension.equals("gif")) {
                        throw new RuntimeException("Kriva ekstenzija");
                    }
                    ImageIO.write(image, extension, file);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Pogreška pri spremanju u " + savePath,
                            "Pogreška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(JVDraw.this, "Slika je exportana.", "Informacija", JOptionPane
                        .INFORMATION_MESSAGE);


            }

        };

        exitAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (unsavedChanges) {
                    int pressed = JOptionPane.showConfirmDialog(JVDraw.this,
                            "There are unsaved changes. Do you want to save before exiting?", "Exiting",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (pressed == JOptionPane.YES_OPTION) {
                        if (currentPath == null) {
                            saveAsAction.actionPerformed(null);
                        } else {
                            saveAction.actionPerformed(null);
                        }
                    } else if (pressed == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                JVDraw.this.dispose();
            }
        };
    }


    /**
     * This method is used for creating actions.
     */
    private void createActions() {
        openAction.putValue(Action.NAME, "Open");
        openAction.putValue(Action.SHORT_DESCRIPTION, "Open .jvd files from disk");
        openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        saveAction.putValue(Action.NAME, "Save");
        saveAction.putValue(Action.SHORT_DESCRIPTION, "Save drawing to disk");
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        saveAsAction.putValue(Action.NAME, "Save as");
        saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Save drawing to disk");
        saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

        exportAction.putValue(Action.NAME, "Export");
        exportAction.putValue(Action.SHORT_DESCRIPTION, "Export drawing to disk");
        exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
        exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);

        exitAction.putValue(Action.NAME, "Exit");
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit from application");
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
    }

    /**
     * This method is used for creating menus.
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        fileMenu.add(new JMenuItem(openAction));
        fileMenu.add(new JMenuItem(saveAction));
        fileMenu.add(new JMenuItem(saveAsAction));
        fileMenu.add(new JMenuItem(exportAction));
        fileMenu.add(new JMenuItem(exitAction));

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }


    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));

    }
}

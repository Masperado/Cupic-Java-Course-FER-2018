package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.IColorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents JColorArea. It is used for choosing colors.
 */
public class JColorArea extends JComponent implements IColorProvider {

    /**
     * Selected color.
     */
    private Color selectedColor;

    /**
     * List of {@link ColorChangeListener}.
     */
    private List<ColorChangeListener> listeners = new ArrayList<>();

    /**
     * Basic constructor.
     *
     * @param selectedColor Selected color
     */
    public JColorArea(Color selectedColor) {
        this.selectedColor = selectedColor;


        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor = JColorChooser.showDialog(JColorArea.this, "Please select new color", selectedColor);
                if (newColor != null) {

                    Color oldColor = new Color(JColorArea.this.selectedColor.getRGB());
                    JColorArea.this.selectedColor = newColor;
                    notifyListeners(oldColor, newColor);
                    repaint();
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(15, 15);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Dimension dim = getSize();
        Insets ins = getInsets();

        g2d.setColor(selectedColor);
        g2d.fillRect(ins.left, ins.top, dim.width, dim.height);
    }


    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    @Override
    public void addColorChangeListener(ColorChangeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }


    /**
     * This method is used for notifying listeners.
     *
     * @param oldColor Old color
     * @param newColor New color
     */
    private void notifyListeners(Color oldColor, Color newColor) {
        for (ColorChangeListener l : listeners) {
            l.newColorSelected(this, oldColor, newColor);
        }
    }
}

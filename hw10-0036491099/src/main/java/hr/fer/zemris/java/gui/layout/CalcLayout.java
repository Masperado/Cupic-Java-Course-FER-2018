package hr.fer.zemris.java.gui.layout;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents calc layout. It is used as a layout for that has 5 rows and 7 columns, and cells from (1,1)
 * to (1,5) are merged. For referencing position {@link RCPosition} is used.
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * Map used for storing components.
     */
    private Map<RCPosition, Component> calc = new HashMap<>();

    /**
     * Space between cells.
     */
    private int spaceBetween = 0;

    /**
     * Basic constructor.
     */
    public CalcLayout() {
    }

    /**
     * Constructor with space between.
     *
     * @param spaceBetween Space between
     */
    public CalcLayout(int spaceBetween) {
        this.spaceBetween = spaceBetween;
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof RCPosition) {
            RCPosition position = (RCPosition) constraints;

            checkValidPosition(position.getRow(), position.getColumn());

            if (calc.get(position) != null) {
                throw new CalcLayoutException("Na zadanoj poziciji već postoji komponenta!");
            }

            calc.put(position, comp);
        } else if (constraints instanceof String) {
            String stringPosition = (String) constraints;
            if (stringPosition.length() != 3) {
                throw new CalcLayoutException("Neispravan String pozicije!");
            }
            try {
                RCPosition position = new RCPosition(Integer.parseInt(String.valueOf(stringPosition.charAt(0))), Integer.parseInt
                        (String.valueOf(stringPosition.charAt(2))));

                checkValidPosition(position.getRow(), position.getColumn());

                if (calc.get(position) != null) {
                    throw new CalcLayoutException("Na zadanoj poziciji već postoji komponenta!");
                }

                calc.put(position, comp);

            } catch (NumberFormatException ex) {
                throw new CalcLayoutException("Neispravan String pozicije!");
            }
        } else {
            throw new CalcLayoutException("Neispravan object constraintsa!");
        }
    }

    /**
     * This method is used for checking if given position is valid.
     *
     * @param row    Row
     * @param column Column
     */
    private void checkValidPosition(int row, int column) {
        if (row < 1 || row > 5 || column < 1 || column > 7) {
            throw new CalcLayoutException("Redak/stupac izvan layouta!");
        } else if (row == 1 && column > 1 && column < 6) {
            throw new CalcLayoutException("Prva ćelija se mora referencirati kao (1,1)!");
        }
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        calc.remove(comp);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return getLayoutSize(target, "maximum");
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getLayoutSize(parent, "preferred");
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getLayoutSize(parent, "minimum");
    }

    /**
     * This method is used for getting layout size based of type (preferred, maximum or minimum).
     *
     * @param parent Parent container
     * @param type   Type
     * @return Dimension of layout
     */
    private Dimension getLayoutSize(Container parent, String type) {
        int height = 0;
        int width = 0;
        switch (type) {
            case "preferred":
                for (Map.Entry<RCPosition, Component> entries: calc.entrySet()){
                    int entryHeight=entries.getValue().getPreferredSize().height;
                    int entryWidth = entries.getValue().getPreferredSize().width;
                    if (entries.getKey().getColumn()==1 && entries.getKey().getRow()==1){
                        entryWidth= entryWidth-4*spaceBetween;
                        entryWidth/=5;
                    }

                    if (entryHeight>height){
                        height = entryHeight;
                    }
                    if (entryWidth>width){
                        width = entryWidth;
                    }
                }
                break;
            case "maximum":
                for (Map.Entry<RCPosition, Component> entries: calc.entrySet()){
                    int entryHeight=entries.getValue().getMaximumSize().height;
                    int entryWidth = entries.getValue().getMaximumSize().width;
                    if (entries.getKey().getColumn()==1 && entries.getKey().getRow()==1){
                        entryWidth= entryWidth-4*spaceBetween;
                        entryWidth/=5;
                    }

                    if (entryHeight>height){
                        height = entryHeight;
                    }
                    if (entryWidth>width){
                        width = entryWidth;
                    }
                }
                break;
            case "minimum":
                for (Map.Entry<RCPosition, Component> entries: calc.entrySet()){
                    int entryHeight=entries.getValue().getMinimumSize().height;
                    int entryWidth = entries.getValue().getMinimumSize().width;
                    if (entries.getKey().getColumn()==1 && entries.getKey().getRow()==1){
                        entryWidth= entryWidth-4*spaceBetween;
                        entryWidth/=5;
                    }

                    if (entryHeight>height){
                        height = entryHeight;
                    }
                    if (entryWidth>width){
                        width = entryWidth;
                    }
                }
                break;
        }

        Insets ins = parent.getInsets();

        return new Dimension(width * 7 + spaceBetween * 6 + ins.right + ins.left, height * 5 + spaceBetween * 4 + ins.top + ins.bottom);
    }


    @Override
    public void layoutContainer(Container parent) {

        int height = calc.values().stream().map(e -> e.getMaximumSize().height).max(Integer::compareTo).get();
        int width = calc.values().stream().map(e -> e.getMaximumSize().width).max(Integer::compareTo).get();
        width *= parent.getWidth() / preferredLayoutSize(parent).getWidth();
        height *= parent.getHeight() / preferredLayoutSize(parent).getHeight();
        width += 2;
        height += 1;

        for (Map.Entry<RCPosition, Component> field : calc.entrySet()) {
            RCPosition position = field.getKey();


            if (position.getColumn() == 1 && position.getRow() == 1) {
                field.getValue().setBounds(0, 0, 5 * width + 4 * spaceBetween, height);
                continue;
            }

            field.getValue().setBounds((position.getColumn() - 1) * (width + spaceBetween), (position.getRow() - 1) *
                    (height + spaceBetween), width, height);
        }

    }
}

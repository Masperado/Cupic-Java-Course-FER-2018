package hr.fer.zemris.java.gui.calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class represents operator button. It is extension of {@link JButton} that has two action listeners which only
 * one at the moment. Action listeners are switch through setInverse method.
 */
public class OperatorButton extends JButton {

    /**
     * Text of button.
     */
    private String text;

    /**
     * Action listener.
     */
    private ActionListener action;

    /**
     * Inverse action listener.
     */
    private ActionListener inverseAction;

    /**
     * Basic constructor.
     *
     * @param text          Text
     * @param action        Action listener
     * @param inverseAction Inverse action listener
     */
    public OperatorButton(String text, ActionListener action, ActionListener inverseAction) {
        this.text = text;
        this.action = action;

        if (inverseAction == null) {
            this.inverseAction = action;
        } else {
            this.inverseAction = inverseAction;
        }

        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setText(text);
        addActionListener(action);
    }

    /**
     * This method is used for checking active action listener.
     *
     * @param inverse Flag for inversing action
     */
    public void setInverse(boolean inverse) {
        if (inverse) {
            removeActionListener(action);
            addActionListener(inverseAction);
        } else {
            removeActionListener(inverseAction);
            addActionListener(action);
        }
    }
}

package hr.fer.zemris.java.gui.layout;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertEquals;

public class CalcLayoutTest {

    @Test(expected = CalcLayoutException.class)
    public void invalidConstraint1() {
        JPanel p = new JPanel(new CalcLayout(2));
        p.add(new JButton("Stipe"), new RCPosition(0, 5));
    }

    @Test(expected = CalcLayoutException.class)
    public void invalidConstraint2() {
        JPanel p = new JPanel(new CalcLayout(2));
        p.add(new JButton("Stipe"), new RCPosition(1, 5));
    }

    @Test(expected = CalcLayoutException.class)
    public void invalidConstraint3() {
        JPanel p = new JPanel(new CalcLayout(2));
        p.add(new JButton("Stipe"), new RCPosition(2, 5));
        p.add(new JButton("Mile"), new RCPosition(2, 5));
    }


    @Test
    public void dimensionTest1() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    public void dimensionTest2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }


}
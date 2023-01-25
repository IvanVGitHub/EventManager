package com.ivank.fraui;

import javax.swing.*;
import java.awt.*;

public class TestFrame1 extends JFrame {
    public TestFrame1() {
        super("Test");
        setPreferredSize(new Dimension(640, 480));
        pack();
//        setLocationRelativeTo(null);
        setVisible(true);

        JLabel label = new JLabel();
        label.setText("<html><font size=\"30\">my Formatted text</font></html>");
        label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        add(label);
    }
}

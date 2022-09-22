package com.ivank.fraui;

import javax.swing.*;
import java.awt.*;

public class WindowSetMediaCurrentEvent extends JFrame {
    public WindowSetMediaCurrentEvent() {
        super("Кадры события");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(300, 300));

        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

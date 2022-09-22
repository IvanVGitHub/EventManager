package com.ivank.fraui;

import javax.swing.*;
import java.awt.*;

public class WindowAllEventsCamera extends JFrame {
    public WindowAllEventsCamera() {
        super("Окно заглушка");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(300, 300));

        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

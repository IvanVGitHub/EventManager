package com.ivank.fraui;

import com.ivank.fraui.components.Content;

import javax.swing.*;
import java.awt.*;

public class WindowAllEventsCamera extends JFrame {
    public WindowAllEventsCamera(int idCamera) {
        super("Все события камеры");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(300, 300));

        JLabel label = new JLabel();
        label.setText("<html><h1>Coming soon...</h1></html>");
        label.setBounds(0, 20, 200, 50);

        panelMain.add(label);
        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

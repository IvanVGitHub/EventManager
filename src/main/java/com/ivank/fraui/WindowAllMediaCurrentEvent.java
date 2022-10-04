package com.ivank.fraui;

import com.ivank.fraui.components.Content;

import javax.swing.*;
import java.awt.*;

public class WindowAllMediaCurrentEvent extends JFrame {
    public WindowAllMediaCurrentEvent() {
        super("Кадры события");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new Content.PanelFlexContent();
        panelMain.setPreferredSize(new Dimension(300, 300));

        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
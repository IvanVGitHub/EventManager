package com.ivank.fraui.components;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    public StatusBar() {
        JPanel panelMain = new JPanel();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel label = new JLabel();
        panelMain.add(label);
        this.add(panelMain);
        setBackground(Color.GREEN);
    }
}

package com.ivank.fraui.components;

import javax.swing.*;
import java.awt.*;

public class Reserved extends JPanel {
    public Reserved() {
        JPanel panelMain = new JPanel();
        this.setBackground(Color.BLUE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel label = new JLabel();
        panelMain.add(label);
        this.add(panelMain);
    }
}

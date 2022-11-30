package com.ivank.fraui.utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RoundButton1 extends JButton {
    public RoundButton1() {
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY);
//            setForeground(Color.RED);
        } else {
            g.setColor(getBackground());
            setForeground(Color.BLACK);
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void paintBorder(Graphics g) {
//        LineBorder thick = new LineBorder(Color.BLACK,5);
//        setBorder(thick);
        g.setColor(getForeground());
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }
}

package com.ivank.fraui;

import com.ivank.fraui.components.Content;
import com.ivank.fraui.db.QueryNEWEventImages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WindowAllMediaCurrentEvent extends JFrame {
    public WindowAllMediaCurrentEvent(int event_id) {
        super("Кадры события");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new Content.PanelFlex();
        panelMain.setPreferredSize(new Dimension(300, 300));

        ArrayList<ImageIcon> listImage = QueryNEWEventImages.getListEventImages(event_id);

        for (ImageIcon event : listImage) {
            JLabel label = new JLabel(new ImageIcon(event.getImage().getScaledInstance(
                    250,
                    250,
                    java.awt.Image.SCALE_SMOOTH
            )));
            panelMain.add(label);
        }

        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

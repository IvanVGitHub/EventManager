package com.ivank.fraui;

import com.ivank.fraui.db.QueryCameras;

import javax.swing.*;
import java.awt.*;

public class WindowAddCamera extends JFrame {
    public WindowAddCamera() {
        super("Список камер");

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(0, 1));

        JList list = new JList<>(QueryCameras.getListCameras().toArray());
        list.setCellRenderer(new CheckboxListCellRenderer());

        panelMain.add(list);
        add(panelMain, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

package com.ivank.fraui.components;

import com.ivank.fraui.db.QueryCameras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel {
    public ToolBar() {
        JPanel panelMain = new JPanel();
        this.setBackground(Color.ORANGE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JButton buttonAdd = new JButton("Сколько камер?");
        buttonAdd.setFocusable(false);
        JButton buttonRem = new JButton("заглушка");
        buttonRem.setFocusable(false);
        //кнопка не активна
        buttonRem.setEnabled(false);
        panelMain.add(buttonAdd);
        panelMain.add(buttonRem);
        this.add(panelMain);

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAdd.setText(String.valueOf("Отслеживается камер: " + QueryCameras.getListMdlCameras().size()));
            }
        });
    }
}

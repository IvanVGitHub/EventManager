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
        JButton buttonAdd = new JButton("+");
        buttonAdd.setFocusable(false);
        JButton buttonRem = new JButton("-");
        buttonRem.setFocusable(false);
        panelMain.add(buttonAdd);
        panelMain.add(buttonRem);
        this.add(panelMain);

        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                QueryCameras.listCameras();

                buttonAdd.setText(String.valueOf(QueryCameras.listNamesCameras.size()));
                buttonRem.setText(String.valueOf(QueryCameras.listEventCamera.size()));
            }
        });
    }
}

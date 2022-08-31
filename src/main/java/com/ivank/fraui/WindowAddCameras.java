package com.ivank.fraui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.ivank.fraui.Content.countCameras;

public class WindowAddCameras {
    public WindowAddCameras() {
        JFrame windowAddCameras = new JFrame();

        Container container = new  Container();
        container.setLayout(new FlowLayout());

        //window in the center of the screen
        windowAddCameras.setLocationRelativeTo(null);
        windowAddCameras.setSize(200, 200);
        JButton buttonAddCamera = new JButton("Добавить камеру");
        buttonAddCamera.setBounds(7, 25, 150, 50);
        container.add(buttonAddCamera);

        buttonAddCamera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countCameras++;
            }
        });

        windowAddCameras.add(container);
        windowAddCameras.setVisible(true); //делаю форму 2 видимой
    }
}

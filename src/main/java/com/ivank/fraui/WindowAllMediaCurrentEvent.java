package com.ivank.fraui;

import com.ivank.fraui.components.Content;
import com.ivank.fraui.db.QueryNEWEventImages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class WindowAllMediaCurrentEvent extends JFrame {
    public WindowAllMediaCurrentEvent(int event_id) throws InterruptedException {
        super("Кадры события");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new Content.PanelFlex();
        panelMain.setPreferredSize(new Dimension(800, 600));

        ArrayList<ImageIcon> listImage = QueryNEWEventImages.getListEventImages(event_id);

        JLabel label = new JLabel();
        panelMain.add(label);
        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        final int[] index = {0};
        Timer timer = null;
        Timer finalTimer = timer;
        timer = new Timer(200, (evt)->{
            if(index[0] >= listImage.size())
                index[0] = 0;
            ImageIcon event = listImage.get(index[0]);
            index[0]++;
            label.setIcon(new ImageIcon(event.getImage().getScaledInstance(
                    800,
                    600,
                    java.awt.Image.SCALE_SMOOTH
            )));

            panelMain.revalidate();
            panelMain.repaint();
        });
        timer.start();
//        for (ImageIcon event : listImage) {
//            label.setIcon(new ImageIcon(event.getImage().getScaledInstance(
//                    800,
//                    600,
//                    java.awt.Image.SCALE_SMOOTH
//            )));
//
//            panelMain.revalidate();
//            panelMain.repaint();
//
//            sleep(1000);
//        }
    }
}

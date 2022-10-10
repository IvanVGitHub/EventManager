package com.ivank.fraui;

import com.ivank.fraui.components.Content;
import com.ivank.fraui.db.QueryEventImages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class WindowAllMediaCurrentEvent extends JFrame {
    Timer timer;
    ArrayList<ImageIcon> listImage;
    JPanel panelMain;

    public WindowAllMediaCurrentEvent(int event_id) throws InterruptedException {
        super("Кадры события");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //действия при нажатии кнопки закрытия
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
                listImage.clear();
            }
        });

        panelMain = new Content.PanelFlex();
        panelMain.setPreferredSize(new Dimension(800, 600));

        listImage = QueryEventImages.getListEventImages(event_id);

        JLabel label = new JLabel();
        panelMain.add(label);
        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        final int[] index = {0};
        timer = new Timer(200, (evt)-> {
            if(listImage.size() > 0) {
                if (index[0] >= listImage.size())
                    index[0] = 0;
                ImageIcon event = listImage.get(index[0]);
                index[0]++;
                label.setIcon(new ImageIcon(event.getImage().getScaledInstance(
                        800,
                        600,
                        java.awt.Image.SCALE_SMOOTH
                )));
            }
            panelMain.revalidate();
            panelMain.repaint();
        });
        timer.start();
    }
}

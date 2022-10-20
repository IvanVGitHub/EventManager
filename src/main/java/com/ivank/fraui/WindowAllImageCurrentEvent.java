package com.ivank.fraui;

import com.ivank.fraui.components.Content;
import com.ivank.fraui.db.QueryEventImages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class WindowAllImageCurrentEvent extends JFrame {
    Timer timer;
    ArrayList<ImageIcon> listImage;
    JPanel panelMain;
    int width = 800;
    int height = 600;

    public WindowAllImageCurrentEvent(int event_id, String time) throws InterruptedException {
        super(time);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //действия при нажатии кнопки закрытия
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
                listImage.clear();
            }
        });

        panelMain = new Content.PanelFlex();
        panelMain.setPreferredSize(new Dimension(width, height));

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
                        width,
                        height,
                        java.awt.Image.SCALE_SMOOTH
                )));
            }
            panelMain.revalidate();
            panelMain.repaint();
        });
        timer.start();
    }
}

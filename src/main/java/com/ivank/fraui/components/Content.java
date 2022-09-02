package com.ivank.fraui.components;

import com.ivank.fraui.WindowAddCameras;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;


public class Content extends JPanel {
    public int countCameras = 4;
    //for random color
    Random rand = new Random();

    WindowMain windowMain;
    WindowAddCameras windowAddCameras;

    public void setCountCameras(int countCameras) {
        this.countCameras = countCameras;
    }

    public int getCountCameras() {
        return countCameras;
    }

    public JFrame getWindowMain() {
        return WindowMain.instance;
    }

    public Content() {
        this.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        //puts elements vertically
//        this.setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());

        final JPanel externalPanel = new JPanel();
        final JScrollPane scrollPane = new JScrollPane();
        externalPanel.setLayout(new BorderLayout(0, 0));
        scrollPane.setViewportView(externalPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel internalPanel = new JPanel();
        externalPanel.add(internalPanel, BorderLayout.NORTH);
        internalPanel.setLayout(new GridLayout(0, 1, 0, 0));

        //size icon event
        Dimension labelSize = new Dimension(80, 80);

        //image to icon even for TEST
        URL url = getClass().getResource("../img/event.jpg");
        ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));

        //отрисовывать группы событий в цикле неопределённое количество раз
        for (int i = 0; i < getCountCameras(); i++) {
            TEST eventGroups = new TEST();

            //add event to group event
            for(int a = 0; a < 20; a++) {
                //random color border event for TEST
                Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
                eventGroups.createEventLabel("Камера " + i, labelSize, randomColor, image);
            }

            this.add(eventGroups);

            JScrollPane scrollPaneEvent = new JScrollPane(
                    eventGroups,
                    JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
            this.add(scrollPaneEvent);
        }

    }
}

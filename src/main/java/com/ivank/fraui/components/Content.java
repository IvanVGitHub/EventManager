package com.ivank.fraui.components;

import com.ivank.fraui.db.QueryCameras;
import com.ivank.fraui.db.QueryEventPicture;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Content extends JPanel {
    public int countCameras = QueryCameras.getListNamesCameras().size();

    //for random color
    Random rand = new Random();

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

        JPanel internalPanel = new JPanel();
        internalPanel.setLayout(new GridLayout(0, 1));

        JPanel externalPanel = new JPanel();
        externalPanel.setLayout(new BorderLayout(0, 0));
        externalPanel.add(internalPanel, BorderLayout.NORTH);

        JScrollPane scrollPaneGroupEvent = new JScrollPane(
                externalPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        //size icon event
        Dimension labelSize = new Dimension(80, 80);

        //image to icon event for test
        /*
//        //image to icon event for test
//        URL url = getClass().getResource("../img/event.jpg");
//        ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));

//        //image to icon event for test
//        String base64string = "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";
//        byte[] byteImageBase64 = Base64.getDecoder().decode(base64string);
//        ImageIcon imageIcon = new ImageIcon(byteImageBase64);
        */

        //отрисовывать группы событий в цикле неопределённое количество раз
        for (int i = 0; i < getCountCameras(); i++) {
            EventAdd eventAdd = new EventAdd();

            ArrayList<ImageIcon> listImage = QueryEventPicture.imageIcon(i);

            //add picture options
            eventAdd.createOptionsButton();
            //add event to group event
            for(int a = 0; a < listImage.size(); a++) {
                //random color border event for TEST
                Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
                eventAdd.createEventLabel("Камера " + String.valueOf(QueryCameras.getListNamesCameras().get(i).camera_name), labelSize, randomColor, listImage.get(a));
            }

            JScrollPane scrollPaneEvent = new JScrollPane(
                    eventAdd,
                    JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            );

            internalPanel.add(scrollPaneEvent);
        }

        this.setLayout(new BorderLayout());
        this.add(scrollPaneGroupEvent, BorderLayout.CENTER);
    }
}

package com.ivank.fraui;

import com.ivank.fraui.db.QueryTEST;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WindowAllEventsCamera extends JFrame {
    private static ArrayList<JLabel> labels = new ArrayList<>();

    public WindowAllEventsCamera(int idCamera) {
        super("Все события камеры");
        setPreferredSize(new Dimension(300, 300));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new JPanel();
//        panelMain.setPreferredSize(new Dimension(300, 300));
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(
                panelMain,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        ArrayList<String> listTimeStampEvents = QueryTEST.getListTimeStampEvents(idCamera);
        int index = listTimeStampEvents.size();

        for (String element : listTimeStampEvents) {
            JLabel labelEvent = new JLabel(element);

            labels.add(labelEvent);
            panelMain.add(labelEvent);
        }

        //TODO: testing threads
/*        JLabel label2 = new JLabel();
        label2.setText("<html><h1>Loading...</h1></html>");
        label2.setBounds(0, 70, 200, 50);
        panelMain.add(label2);

        (new Thread(()->{
            try {
                Thread.sleep(4000);
                label2.setText("<html><h1>Loaded.</h1></html>");
            } catch (Exception ex){ex.printStackTrace();}
        })).start();*/

        getContentPane().add(scrollPane);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

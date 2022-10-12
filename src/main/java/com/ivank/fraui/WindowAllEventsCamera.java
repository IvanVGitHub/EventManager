package com.ivank.fraui;

import com.ivank.fraui.components.Content;
import com.ivank.fraui.db.QueryEvent;
import com.ivank.fraui.db.QueryTEST;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class WindowAllEventsCamera extends JFrame {
    public WindowAllEventsCamera(int idCamera) {
        super("Все события камеры");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setPreferredSize(new Dimension(300, 300));

        JLabel label = new JLabel();
        label.setText("<html><h1>Coming soon...</h1></html>");
        label.setBounds(0, 20, 200, 50);
        panelMain.add(label);

        ArrayList<String> listTimeStampEvents = QueryTEST.getListTimeStampEvents(idCamera);
        int index = listTimeStampEvents.size();

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

        add(panelMain);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

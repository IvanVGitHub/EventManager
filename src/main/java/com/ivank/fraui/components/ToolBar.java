package com.ivank.fraui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

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

//        buttonAdd.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int number = Content.labels.size() + 1;
//                EventAdd eventAdd = new EventAdd();
////                p.setBorder(BorderFactory.createTitledBorder("Камера " + number));
//                URL url = getClass().getResource("./img/event.jpg");
//                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
//                Dimension labelSize = new Dimension(80, 80);
//
//                //add event to group event
//                Random rand = new Random();
//                for (int a = 0; a < 20; a++) {
//                    //random color border event for TEST
//                    Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
//                    eventAdd.createEventLabel("Камера " + number, labelSize, randomColor, image);
//                }
//
//                Content.labels.add(eventAdd);
////                p.setAlignmentX(JLabel.CENTER_ALIGNMENT);
//                internalPanel.add(eventAdd, BorderLayout.NORTH);
////                internalPanel.add(p, new FlowLayout(FlowLayout.LEFT));
//                scrollPane.revalidate();
//            }
//        });
//
//        buttonRem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if (Content.labels.size() > 0) {
//                    int index = Content.labels.size() - 1;
//                    JPanel p = Content.labels.remove(index);
//                    WindowAddCameras.internalPanel.remove(p);
//                    internalPanel.repaint();
//                    scrollPane.revalidate();
//                }
//            }
//        });
    }
}

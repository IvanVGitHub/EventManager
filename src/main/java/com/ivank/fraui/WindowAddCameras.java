package com.ivank.fraui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class WindowAddCameras {
    Content content = new Content();

    private static final List<JPanel> labels = new ArrayList<JPanel>();

    public WindowAddCameras() {
        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel butPanel = new JPanel();

        JButton addButton = new JButton("+");
        addButton.setFocusable(false);
        butPanel.add(addButton);

        JButton remButton = new JButton("-");
        remButton.setFocusable(false);
        butPanel.add(remButton);

        final JPanel labPanel = new JPanel();
        final JScrollPane scrollPane = new JScrollPane();
        labPanel.setLayout(new BorderLayout(0, 0));
        scrollPane.setViewportView(labPanel);

        JPanel columnpanel = new JPanel();
        labPanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int number = labels.size() + 1;
                CameraEventsPanel p = new CameraEventsPanel("Камера " + number);
//                p.setLayout(null);
//                p.setPreferredSize(new Dimension(300,30));

                labels.add(p);
                p.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                columnpanel.add(p, BorderLayout.NORTH);
                scrollPane.revalidate();
            }
        });

        remButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(labels.size() > 0) {
                    int index = labels.size() - 1;
                    JPanel p = labels.remove(index);
                    columnpanel.remove(p);
                    columnpanel.repaint();
                    scrollPane.revalidate();
                }
            }
        });

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(butPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
//    public WindowAddCameras() {
//        JFrame windowAddCameras = new JFrame();
//        JPanel addCamerasPanel = new JPanel();
//
//        addCamerasPanel.setLayout(new FlowLayout());
//
//        //window in the center of the screen
//        windowAddCameras.setLocationRelativeTo(null);
//        windowAddCameras.setPreferredSize(new Dimension(250, 200));
//        JButton buttonAddCamera = new JButton("Добавить камеру");
//        buttonAddCamera.setBounds(7, 25, 150, 50);
//
//        buttonAddCamera.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                content.setCountCameras(content.getCountCameras() + 1);
//
//
////                CameraEventsPanel p = new CameraEventsPanel("Камера ");
////                Dimension labelSize = new Dimension(80, 80);
////                //image to icon even for TEST
////                URL url = getClass().getResource("img/event.jpg");
////                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
////                //add event to group event
////                for(int j = 0; j < 20; j++) {
////                    //random color border event for TEST
////                    p.createEventLabel(j, labelSize, Color.BLUE, image);
////                }
////
////                content.mainPanel.add(p);
////
////                JScrollPane scrollPaneEvent = new JScrollPane(
////                        p,
////                        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
////                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
////                );
////                scrollPaneEvent.revalidate();
////                content.mainPanel.add(scrollPaneEvent);
//            }
//        });
//
//        addCamerasPanel.add(buttonAddCamera);
//        windowAddCameras.pack();
//        windowAddCameras.getContentPane().add(addCamerasPanel);
//        windowAddCameras.setVisible(true);
//    }
}

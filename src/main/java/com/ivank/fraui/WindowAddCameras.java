package com.ivank.fraui;

import com.ivank.fraui.components.Content;
import com.ivank.fraui.components.TEST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class WindowAddCameras {
    private static final List<JPanel> labels = new ArrayList<JPanel>();

    public WindowAddCameras() {
        JFrame frameAddCameras = new JFrame("Test frame");
        frameAddCameras.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelMain = new JPanel();

        JButton addButton = new JButton("+");
        addButton.setFocusable(false);
        panelMain.add(addButton);

        JButton remButton = new JButton("-");
        remButton.setFocusable(false);
        panelMain.add(remButton);

        final JPanel externalPanel = new JPanel();
        final JScrollPane scrollPane = new JScrollPane();
        externalPanel.setLayout(new BorderLayout(0, 0));
        scrollPane.setViewportView(externalPanel);

        JPanel internalPanel = new JPanel();
        externalPanel.add(internalPanel, BorderLayout.NORTH);
        internalPanel.setLayout(new GridLayout(0, 1, 0, 0));

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int number = labels.size() + 1;
                TEST p = new TEST();
                p.setBorder(BorderFactory.createTitledBorder("Камера " + number));
//                URL url = getClass().getResource("../img/event.jpg");
//                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
//                p.createEventLabel("Камера " + number, new Dimension(80, 80), Color.RED, image);
                JButton button = new JButton("New button");
                button.setBounds(20, 5, 89, 23);
                p.add(button);
                labels.add(p);
//                p.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                internalPanel.add(p, BorderLayout.NORTH);
                scrollPane.revalidate();
            }
        });

        remButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(labels.size() > 0) {
                    int index = labels.size() - 1;
                    JPanel p = labels.remove(index);
                    internalPanel.remove(p);
                    internalPanel.repaint();
                    scrollPane.revalidate();
                }
            }
        });

        frameAddCameras.getContentPane().setLayout(new BorderLayout());
        frameAddCameras.getContentPane().add(panelMain, BorderLayout.NORTH);
        frameAddCameras.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frameAddCameras.setPreferredSize(new Dimension(600, 400));
        frameAddCameras.pack();
        frameAddCameras.setLocationRelativeTo(null);
        frameAddCameras.setVisible(true);
    }
}

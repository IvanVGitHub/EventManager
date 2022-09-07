package com.ivank.fraui;

import com.ivank.fraui.components.EventAdd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WindowAddCameras extends JFrame {
    public static final List<JPanel> labels = new ArrayList<JPanel>();

    public WindowAddCameras() {
        super("Добавить камеру");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelButton = new JPanel();

        JButton addButton = new JButton("+");
        addButton.setFocusable(false);
        panelButton.add(addButton);

        JButton remButton = new JButton("-");
        remButton.setFocusable(false);
        panelButton.add(remButton);

        JPanel externalPanel = new JPanel();
        externalPanel.setLayout(new BorderLayout(0, 0));
        JScrollPane scrollPaneGroupEvent = new JScrollPane(
                externalPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        JPanel internalPanel = new JPanel();
        internalPanel.setLayout(new GridLayout(0, 1, 0, 0));

        JScrollPane scrollPaneEvent = new JScrollPane(internalPanel);
        scrollPaneEvent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneEvent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        externalPanel.add(scrollPaneEvent, BorderLayout.NORTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int number = labels.size() + 1;
                EventAdd eventAdd = new EventAdd();
                URL url = getClass().getResource("./img/event.jpg");
                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
                Dimension labelSize = new Dimension(80, 80);

                //add event to group event
                Random rand = new Random();
                for(int a = 0; a < 20; a++) {
                    //random color border event for TEST
                    Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
                    eventAdd.createEventLabel("Камера " + number, labelSize, randomColor, image);
                }

                labels.add(eventAdd);
                internalPanel.add(eventAdd, BorderLayout.NORTH);
                scrollPaneGroupEvent.revalidate();
            }
        });

        remButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(labels.size() > 0) {
                    int index = labels.size() - 1;
                    JPanel panel = labels.remove(index);
                    internalPanel.remove(panel);
                    internalPanel.repaint();
                    scrollPaneGroupEvent.revalidate();
                }
            }
        });

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panelButton, BorderLayout.NORTH);
        this.getContentPane().add(scrollPaneGroupEvent, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(600, 400));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

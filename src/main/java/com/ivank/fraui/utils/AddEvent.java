package com.ivank.fraui.utils;

import com.ivank.fraui.WindowAllMediaCurrentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddEvent extends JPanel {
    public AddEvent() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public JComponent createLabelEvent(String name, Dimension labelSize, Color color, ImageIcon icon) {
        this.setBorder(BorderFactory.createTitledBorder(name));
        //paste image with the specific dimensions
        JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(labelSize.width, labelSize.height, java.awt.Image.SCALE_SMOOTH)));
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBorder(BorderFactory.createLineBorder(color, 5));
        this.add(label);

        //При нажатии на Событие/Event открывается окно со всеми медиа, относящимися к этому Событию
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new WindowAllMediaCurrentEvent();
            }
        });

        return label;
    }
}
package com.ivank.fraui.utils;

import com.ivank.fraui.WindowAllImageCurrentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddEvent extends JPanel {
    public AddEvent() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public JComponent createLabelEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time) {
        JPanel p = new JPanel();
        p.setLayout (new BoxLayout (p, BoxLayout.Y_AXIS));
        JLabel text = new JLabel();
        text.setText(time);
        p.add(text);

        //paste image with the specific dimensions
        JLabel label = new JLabel(new ImageIcon(image.getImage().getScaledInstance(
                labelSize.width,
                labelSize.height,
                java.awt.Image.SCALE_SMOOTH
        )));
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBorder(BorderFactory.createLineBorder(color, 5));

        //При нажатии на Событие/Event открывается окно со всеми медиа, относящимися к этому Событию
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    new WindowAllImageCurrentEvent(event_id, time);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        p.add( label);
        p.setSize(labelSize.width,labelSize.height + 40);
//        p.setVisible(true);
        this.add(p);
        return p;
//        return label;
    }
}

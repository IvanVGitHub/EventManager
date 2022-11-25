package com.ivank.fraui.utils;

import com.ivank.fraui.WindowAllImageCurrentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.ivank.fraui.settings.AppConfig.getScale;

public class AddEvent extends JPanel {
    public AddEvent() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public JComponent createLabelEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time) {
        JPanel panel = new JPanel();
        panel.setLayout (new BoxLayout (panel, BoxLayout.Y_AXIS));
        JLabel text = new JLabel();
        text.setText(time);
        panel.add(text);

        //вставляем изображение с определенными размерами
        JLabel label = new JLabel(new ImageIcon(image.getImage().getScaledInstance(
                labelSize.width,
                labelSize.height,
                java.awt.Image.SCALE_SMOOTH
        )));
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(color, (int)(getScale() * 5)));

        //При нажатии на Событие/Event открывается окно со всеми медиа, относящимися к этому Событию
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    WindowAllImageCurrentEvent window = new WindowAllImageCurrentEvent(panel, event_id, time);
                } catch (InterruptedException ex) {throw new RuntimeException(ex);}
            }
        });

        panel.add(label);
//        panel.setSize(labelSize.width,labelSize.height + 40);
        add(panel);

        return panel;
    }
}

package com.ivank.fraui.utils;

import com.ivank.fraui.WindowViewImageCurrentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.ivank.fraui.settings.AppConfig.getScale;

public class AddEvent extends JPanel {
    boolean status = false;

    public AddEvent() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public void createLabelEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time) {
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

        JPanel newPanel = new JPanel();
        JLabel newLabel = new JLabel();
        JButton newButton = new JButton("Кнопка");
        newLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        newPanel.add(newButton);
        newPanel.add(newLabel);
        newLabel.setVisible(status);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isVisible(status);
            }
        });

        panel.add(newPanel);
        panel.add(label);
//        panel.setSize(labelSize.width,labelSize.height + 40);
        add(panel);

        //При нажатии на Событие/Event открывается окно со всеми медиа, относящимися к этому Событию
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new WindowViewImageCurrentEvent(panel, event_id, time);
            }
        });
    }

    public void isVisible(boolean status1) {
        status = !status1;
    }
}

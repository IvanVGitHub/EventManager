package com.ivank.fraui.components;

import com.ivank.fraui.db.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

public class EventAdd extends JPanel {
    public EventAdd() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public JComponent createOptionsButton() {
        byte[] byteImageBase64 = Base64.getDecoder().decode(Settings.getOptionsLabel());
        JButton button = new JButton(new ImageIcon(byteImageBase64));
        button.setPreferredSize(new Dimension(30, 30));

        this.add(button);

        return button;
    }

    public JComponent createEventLabel(String name, Dimension labelSize, Color randomColor, ImageIcon icon) {
        this.setBorder(BorderFactory.createTitledBorder(name));
        JLabel label = new JLabel(icon);
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBorder(BorderFactory.createLineBorder(randomColor, 5));
        this.add(label);

        return label;
    }
}

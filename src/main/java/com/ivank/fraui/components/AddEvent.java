package com.ivank.fraui.components;

import com.ivank.fraui.db.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

public class AddEvent extends JPanel {
    public AddEvent() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public JComponent createButtonOptions() {
        byte[] byteImageBase64 = Base64.getDecoder().decode(Settings.getLabelOptions());
        JButton button = new JButton(new ImageIcon(byteImageBase64));
        button.setPreferredSize(new Dimension(30, 30));

        this.add(button);

        return button;
    }

    public JComponent createButtonAllImgEvents() {
        byte[] byteImageBase64 = Base64.getDecoder().decode(Settings.getButtonAllImgEvents());
        JButton button = new JButton(new ImageIcon(byteImageBase64));
        button.setPreferredSize(new Dimension(50, 50));

        this.add(button);

        return button;
    }

    public JComponent createLabelEvent(String name, Dimension labelSize, Color color, ImageIcon icon) {
        this.setBorder(BorderFactory.createTitledBorder(name));
        //paste image with the specific dimensions
        JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(labelSize.width, labelSize.height, java.awt.Image.SCALE_SMOOTH)));
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBorder(BorderFactory.createLineBorder(color, 5));
        this.add(label);

        return label;
    }
}

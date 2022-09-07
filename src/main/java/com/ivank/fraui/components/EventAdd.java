package com.ivank.fraui.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventAdd extends JPanel {
//    private ArrayList<JComponent> labels = new ArrayList<>();
//
//    public ArrayList<JComponent> getLabels() {
//        return labels;
//    }

    public EventAdd() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public JComponent createEventLabel(String name, Dimension labelSize, Color randomColor, ImageIcon icon) {
        this.setBorder(BorderFactory.createTitledBorder(name));
        JLabel label = new JLabel(icon);
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBorder(BorderFactory.createLineBorder(randomColor, 5));
        this.add(label);
//        getLabels().add(label);

        return label;
    }
}
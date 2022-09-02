package com.ivank.fraui.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class EventGroupsPanel extends JPanel {
    private ArrayList<JComponent> labels = new ArrayList<>();

    public ArrayList<JComponent> getLabels() {
        return labels;
    }

    public EventGroupsPanel() {
        super(new FlowLayout(FlowLayout.LEFT));
    }

    public JComponent createEventLabel(int index, String name, Dimension labelSize, Color randomColor, ImageIcon icon) {
        setBorder(BorderFactory.createTitledBorder(name));
        JLabel label = new JLabel(icon);
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBorder(BorderFactory.createLineBorder(randomColor, 5));
        this.add(label);
        getLabels().add(label);

        return label;
    }
}

package com.ivank.fraui;

import com.ivank.fraui.db.ModelCamera;

import javax.swing.*;
import java.awt.*;

public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        setComponentOrientation(list.getComponentOrientation());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setSelected(isSelected);
        setEnabled(list.isEnabled());

        ModelCamera cm = (ModelCamera) value;
        setText(value == null ? "БЕЗ ИМЕНИ" : cm.camera_name);

        return this;
    }
}

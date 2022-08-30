package ivanK.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CameraEventsPanel  extends JPanel {
    private ArrayList<JComponent> labels = new ArrayList<>();

    public ArrayList<JComponent> getLabels() {
        return labels;
    }

    public CameraEventsPanel(String name) {
        super(new FlowLayout(FlowLayout.LEFT));
        setName(name);
        this.setBounds(this.getX(), this.getY(), this.getWidth(), 100);
    }

    public void setName(String name) {
        setBorder(BorderFactory.createTitledBorder(name));
    }
    @Override
    public Dimension getMinimumSize(){
        return new Dimension(250, 100);
    }

    public JComponent createEventLabel(int index, Dimension labelSize, Color randomColor, ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBorder(BorderFactory.createLineBorder(randomColor, 3));
        this.add(label);
        getLabels().add(label);

        return label;
    }
}

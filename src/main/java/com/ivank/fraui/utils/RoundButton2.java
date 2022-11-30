package com.ivank.fraui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton2 extends JButton {
    public RoundButton2() {
        this(null, null);
    }
    public RoundButton2(Icon icon) {
        this(null, icon);
    }
    public RoundButton2(String text) {
        this(text, null);
    }
    public RoundButton2(Action a) {
        this();
        setAction(a);
    }
    public RoundButton2(String text, Icon icon) {
        setModel(new DefaultButtonModel());
        init(text, icon);
        if(icon==null) {
            return;
        }
        setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        setBackground(Color.BLACK);
        setContentAreaFilled(false);
        setFocusPainted(false);
        //setVerticalAlignment(SwingConstants.TOP);
        setAlignmentY(Component.TOP_ALIGNMENT);
        initShape();
    }
    protected Shape shape, base;
    protected void initShape() {
        if(!getBounds().equals(base)) {
            Dimension s = getPreferredSize();
            base = getBounds();
            shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
        }
    }
    @Override protected void paintBorder(Graphics g) {
        initShape();
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(getBackground());
        g2.setStroke(new BasicStroke(1.0f));
        g2.draw(shape);
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        setContentAreaFilled(false);
    }
}

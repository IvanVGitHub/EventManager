package com.ivank.fraui;

import javax.swing.*;
import java.awt.*;

import static com.ivank.fraui.settings.AppConfig.getScale;

public class WindowWorkIntervalCamera extends JFrame {
    //размер окна, подстраивается под разрешение экрана
    final int width = (int)(getScale() * 500);
    final int height = (int)(getScale() * 400);
    public static int x[] =  {50, 100, 150, 200, 250};
    public static int y[] =  {80, 200, 150, 320, 100};
    public static int n = 5;

    public WindowWorkIntervalCamera() {
        super("График по точкам");
        JPanel jcp = new JPanel(new BorderLayout());
        setContentPane(jcp);
        jcp.add(new DrawingComponent (), BorderLayout.CENTER);
        jcp.setBackground(Color.GREEN);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }
}

class DrawingComponent extends JPanel {
    int xg[] =  WindowWorkIntervalCamera.x;
    int yg[] =  WindowWorkIntervalCamera.y;
    int ng = WindowWorkIntervalCamera.n;

    @Override
    protected void paintComponent(Graphics gh) {
        Graphics2D drp = (Graphics2D)gh;
        drp.drawLine(20, 340, 20, 20);
        drp.drawLine(20, 340, 460, 340);
        drp.drawPolyline(xg, yg, ng);
    }
}
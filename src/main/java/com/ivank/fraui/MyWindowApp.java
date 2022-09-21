package com.ivank.fraui;

import javax.swing.*;

public class MyWindowApp extends JFrame {
    public MyWindowApp() {
        super("My First Window");
        setBounds(100, 100, 200, 200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setVisible(true);
        setLocation(null);
    }
}

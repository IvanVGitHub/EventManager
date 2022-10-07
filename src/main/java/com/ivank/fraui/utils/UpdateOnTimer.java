package com.ivank.fraui.utils;

import com.ivank.fraui.Application;

import javax.swing.*;

public class UpdateOnTimer {
    static Timer timer;

    //перерисовываем/обновляем Content (JPanel) в основном окне (WindowMain) каждые 60 секунд
    public static void updateContent() {
        timer = new Timer(60000, (evt)-> {
            Application.windowMain().getContent().setCameraView();
        });
        timer.start();
    }
}

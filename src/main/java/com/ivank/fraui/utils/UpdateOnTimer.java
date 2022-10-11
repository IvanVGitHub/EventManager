package com.ivank.fraui.utils;

import com.ivank.fraui.Application;
import com.ivank.fraui.db.QueryEvent;

import javax.swing.*;

public class UpdateOnTimer {
    static Timer timer;
    public static int oldIdEvent = 0;
    static int newIdEvent = 0;

    //перерисовываем/обновляем Content (JPanel) в основном окне (WindowMain) каждые 60 секунд
    public static void updateContent() {
        timer = new Timer(60000, (evt)-> {
            newIdEvent = QueryEvent.getLastAddIdEvent();

            if (oldIdEvent != QueryEvent.getLastAddIdEvent()) {
                oldIdEvent = newIdEvent;
                //перерисовываем -//-
                Application.windowMain().getContent().setCameraView();
            }
        });
        timer.start();
    }
}

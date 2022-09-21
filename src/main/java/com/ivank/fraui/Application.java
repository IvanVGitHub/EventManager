package com.ivank.fraui;

import com.ivank.fraui.db.ConnectDB;

public class Application {
    public static WindowMain windowMain() {
        return mainWindow;
    }

    private static WindowMain mainWindow;
    public static void main(String[] args) {
        //connect to DB
        ConnectDB.init();

        mainWindow = new WindowMain();
        new AppInTray();
    }
}

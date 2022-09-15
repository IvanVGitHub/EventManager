package com.ivank.fraui;

import com.ivank.fraui.db.ConnectDB;

public class Application {
    public static void main(String[] args) {
        //connect to DB
        ConnectDB.init();

        new WindowMain();
        new AppInTray();
    }
}

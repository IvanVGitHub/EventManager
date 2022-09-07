package com.ivank.fraui;

import com.ivank.fraui.components.WindowMain;
import com.ivank.fraui.db.ConnectDB;
import com.ivank.fraui.db.QueryCameras;

public class Application {
    static WindowMain windowMain;
    static AppInTray appInTray;

    public static void main(String[] args) {
        ConnectDB.init();
        QueryCameras.listCameras();//временно
        windowMain = new WindowMain();
        appInTray = new AppInTray();
    }
}

package com.ivank.fraui;

import com.ivank.fraui.components.WindowMain;
import com.ivank.fraui.db.ConnectDB;

public class Application {
    public static void main(String[] args) {
        //load settings, first of all!
        AppConfig conf = AppConfig.loadConfig();
        //connect to DB
        ConnectDB.init();
        new WindowMain();
        new AppInTray();
        new SettingsConnectDBWindow();
    }
}

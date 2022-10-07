package com.ivank.fraui;

import com.ivank.fraui.db.ConnectDB;
import com.ivank.fraui.utils.UpdateOnTimer;

public class Application {
    private static WindowMain windowMain;

    public static WindowMain windowMain() {
        return windowMain;
    }

    public static void main(String[] args) {
        //connect to DB
        ConnectDB.init();

        windowMain = new WindowMain();
        new AppInTray();

        //update Content
        UpdateOnTimer.updateContent();
    }
}

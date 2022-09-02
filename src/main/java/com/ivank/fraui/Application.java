package com.ivank.fraui;


import com.ivank.fraui.components.WindowMain;

public class Application {
    static WindowMain windowMain;
    static AppInTray appInTray;

    public static void main(String[] args) {
        windowMain = new WindowMain();
        appInTray = new AppInTray();
    }
}

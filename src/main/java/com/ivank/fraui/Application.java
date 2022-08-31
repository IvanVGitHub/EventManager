package com.ivank.fraui;


public class Application {
    static AppInTray appInTray;
    static WindowMain mainWindow;

    public static void main(String[] args) {
        mainWindow = new WindowMain();
        appInTray = new AppInTray();
    }
}

package com.ivank.fraui;

import com.ivank.fraui.db.ConnectDB;
import com.ivank.fraui.db.QueryDB;

import javax.swing.*;
import java.awt.*;

public class AppInTray {
    QueryDB queryDB = new QueryDB();
    WindowMain windowMain;

    public AppInTray() {
        windowMain = WindowMain.getInstance();
        SystemTray systemTray = SystemTray.getSystemTray();

        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/logoBig.png")));
        trayIcon.setImageAutoSize(true);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }

        if (SystemTray.isSupported()) {
            //HIDE_ON_CLOSE - when closing WindowMain and reopening across tray, it opens in its previous state
            WindowMain.getInstance().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }

        trayIcon.addActionListener(e -> {
            if(windowMain == null) {
                windowMain = WindowMain.getInstance();
            }
            else {
                windowMain.setVisible(true);
            }
        });

        //create a popup menu components
        popupMenuInTray(trayIcon);

        //connection to DB, query to DB, status notification
        queryDB.testDB(trayIcon);
    }

    private void popupMenuInTray(TrayIcon trayIcon) {
        PopupMenu popupMenu = new PopupMenu();

        MenuItem startApp = new MenuItem("Открыть");
        startApp.addActionListener(e -> {
            if(windowMain == null) {
                windowMain = WindowMain.getInstance();
            }
            else {
                windowMain.setVisible(true);
            }
        });
        MenuItem exit = new MenuItem("Выход");
        exit.addActionListener(e -> {
            try {
                ConnectDB.getConnector().close();
                trayIcon.displayMessage("Соединение c БД",
                        "Соединение с БД успешно закрыто",
                        TrayIcon.MessageType.INFO);
                //delay for wait notification and read his
                Thread.sleep(2000);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                trayIcon.displayMessage("Соединение c БД",
                        "Не удалось закрыть соединеине с БД!",
                        TrayIcon.MessageType.ERROR);
                try {
                    //delay for wait notification and read his
                    Thread.sleep(3000);
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
            }
            System.exit(0);
        });

        popupMenu.add(startApp);
        popupMenu.addSeparator();
        popupMenu.add(exit);

        //to display popup menu
        trayIcon.setPopupMenu(popupMenu);
    }
}

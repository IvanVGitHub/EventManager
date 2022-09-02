package com.ivank.fraui.components;


import com.ivank.fraui.WindowAddCameras;
import com.ivank.fraui.db.QueryDB;

import javax.swing.*;
import java.awt.*;

public class WindowMain extends JFrame {
    public static WindowMain getInstance() {
        return instance;
    }

    static WindowMain instance;

    WindowAddCameras windowAddCameras;
    EventGroupsPanel eventGroupsPanel;
    QueryDB queryDB;
    Content content = new Content();
    ToolBar toolBar = new ToolBar();
    StatusBar statusBar = new StatusBar();
    Tree tree = new Tree();
    Reserved reserved = new Reserved();


    public WindowMain() {
        super("Главное окно");
        instance = this;
        //temporary
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        //parameters window of windowBasic
        //specification work screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle desktopBounds = ge.getMaximumWindowBounds();
        //view screen sizes
//        System.out.println(desktopBounds);
        //size window in "small" mode
        final int width = 600;
        final int height = 400;
        //location app on the screen in "small" mode
        WindowMain.instance.setBounds(
                desktopBounds.x + desktopBounds.width - width,
                desktopBounds.y + desktopBounds.height - height,
                width,
                height
        );
        //open window in "full screen" mode
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        this.getContentPane().add(tree, BorderLayout.WEST);
        this.getContentPane().add(reserved, BorderLayout.EAST);

        this.setVisible(true);

        //create a menubar components
        menuBarInWindowBasic();
    }

    void menuBarInWindowBasic() {
        MenuBar menuBar = new MenuBar();
        Menu parameters = new Menu("Параметры");

        MenuItem addCamera = new MenuItem("+ камера");
        parameters.add(addCamera);

        parameters.addActionListener(e -> {
            windowAddCameras = new WindowAddCameras();
        });

        menuBar.add(parameters);

        //to display menubar
        this.setMenuBar(menuBar);
    }
}

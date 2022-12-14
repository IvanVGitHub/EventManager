package com.ivank.fraui;

import com.ivank.fraui.components.*;

import javax.swing.*;
import java.awt.*;

import static com.ivank.fraui.settings.AppConfig.getScale;

//Главное окно приложения
public class WindowMain extends JFrame {
    private static WindowMain instance;
    public static WindowMain getInstance() {
        return instance;
    }

    public Content getContent() {
        return content;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public Tree getTree() {
        return tree;
    }

    public Reserved getReserved() {
        return reserved;
    }

    Content content = new Content();
    ToolBar toolBar = new ToolBar();
    StatusBar statusBar = new StatusBar();
    Tree tree = new Tree();
    Reserved reserved = new Reserved();

    public WindowMain() {
        super("Диспетчер событий");
        instance = this;
        //TODO: при добавлении функций трея - установить
//        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        //parameters window of windowBasic
        //specification work screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle desktopBounds = ge.getMaximumWindowBounds();
/*
        //view display sizes
        System.out.println(desktopBounds);
*/
        //size window in "small" mode
        final int width = (int)(getScale() * 600);
        final int height = (int)(getScale() * 400);
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

        MenuItem addCamera = new MenuItem("Список камер");
        parameters.add(addCamera);

        MenuItem settingsConnection = new MenuItem("Параметры подключения");
        parameters.add(settingsConnection);

        MenuItem anySettings = new MenuItem("Настройки");
        parameters.add(anySettings);

        MenuItem addCameraTEST = new MenuItem("(тестовое окно)");
        //делаем кнопку неактивной, потому что блок не для использования
        addCameraTEST.setEnabled(false);
        parameters.add(addCameraTEST);

        //горизонтальная черта для визуального разделения
        parameters.addSeparator();

        MenuItem exit = new MenuItem("Выход");
        parameters.add(exit);

        addCamera.addActionListener(e -> {
            new WindowListCameras();
        });

        settingsConnection.addActionListener(e -> {
            new WindowSettingsConnectDB();
        });

        anySettings.addActionListener(e -> {
            new WindowAnySettings();
        });

        addCameraTEST.addActionListener(e -> {
            new WindowAddCamerasTEST();
        });

        exit.addActionListener(e -> {
            System.exit(0);
        });

        menuBar.add(parameters);

        //to display menubar
        this.setMenuBar(menuBar);
    }
}

package ivanK;

import com.bedivierre.eloquent.DB;
import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;

import java.awt.*;
import javax.swing.*;

public class TESTAppWithTray {
    JFrame windowBasic;
    JFrame windowAddCameras;
    static DB connector;

    public static void main(String[] args) {
        new TESTAppWithTray();
    }

    private static void TestDB(TrayIcon trayIcon) {
        if(trayIcon == null)
            return;
        try {
            connector = new DB("172.20.3.221", "test", "ivanUser", "Qwerty!@#456");

//            TestModel v = connector.find(1, TestModel.class);

            //query to MYSQL
            QueryBuilder<TestModel> query = connector.query(TestModel.class);
            query.where("name", DBWhereOp.LIKE, "%i%");
            ResultSet<TestModel> result = query.get();

            //create string from query result
            StringBuilder sb = new StringBuilder();
            for (TestModel t :
                    result) {
                sb.append(t.name).append(", ");
            }
//            TestModel m = connector.query(TestModel.class).first();
            trayIcon.displayMessage("DB Connection successful!",
//                    "Есть контакт: " + sb.toString(),
                    "Есть контакт: " + sb,
                    TrayIcon.MessageType.INFO);
        } catch (Exception ex) {
            trayIcon.displayMessage("DB Connection failed...",
                    "Не контачит...",
                    TrayIcon.MessageType.ERROR);
        }
    }

    private void WindowBasic() {
        windowBasic = new JFrame("Главное окно");

        Button buttonAdd = new Button("Button");
//        buttonAdd.setFont(new Font("Arial", Font.PLAIN, 50));
        buttonAdd.addActionListener(e -> {
//                windowAddCameras.setVisible(true); //делаю форму 2 видимой
//                CreateWindowAddCameras();
        });
        windowBasic.add(buttonAdd);

        //parameters window of windowBasic
        //specification work screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle desktopBounds = ge.getMaximumWindowBounds();

        //check screen sizes
//        System.out.println( desktopBounds );

        final int width = 600;
        final int height = 400;
        windowBasic.setBounds(
                desktopBounds.x + desktopBounds.width - width,
                desktopBounds.y + desktopBounds.height - height,
                width,
                height
        );
        windowBasic.getContentPane().setLayout(new FlowLayout()); //чтобы кнопка не растянулся на весь экран
        //"стягивает" окно вокруг внутренних элементов
//        windowBasic.pack();
//        windowBasic.
        windowBasic.setVisible(true);

        //create a menubar components
        MenuBarInWindowBasic(windowBasic);
    }

    private void WindowAddCameras() {
        windowAddCameras = new JFrame();

        windowAddCameras.setSize(200, 200);
        Button buttonAddCamera = new Button("Добавить камеру");
        windowAddCameras.add(buttonAddCamera);

        windowAddCameras.setVisible(true); //делаю форму 2 видимой
//        CreateWindowAddCameras();
    }

    private void PopupMenuInTray(TrayIcon trayIcon) {
        PopupMenu popupMenu = new PopupMenu();

        MenuItem startApp = new MenuItem("Открыть");
        startApp.addActionListener(e -> {
            WindowBasic();
        });
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> {
            try {
                connector.close();
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
                    Thread.sleep(2000);
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

    private void MenuBarInWindowBasic(JFrame windowBasic) {
        MenuBar menuBar = new MenuBar();
        Menu parameters = new Menu("Параметры");
//        menuBar.setFont(new Font("Arial", Font.PLAIN, 50));

        MenuItem addCamera = new MenuItem("+ камера");
        parameters.add(addCamera);

        parameters.addActionListener(e -> {
                WindowAddCameras();
        });

        menuBar.add(parameters);

        //to display menubar
        windowBasic.setMenuBar(menuBar);
    }

    public TESTAppWithTray() {
        SystemTray systemTray = SystemTray.getSystemTray();

        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/logoBig.png")));
        trayIcon.setImageAutoSize(true);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        trayIcon.addActionListener(e -> {
            WindowBasic();
        });

        //create basic window in the app
        WindowBasic();
        if (SystemTray.isSupported()) {
            //app will be closed only from tray
            windowBasic.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }

        //create a popup menu components
        PopupMenuInTray(trayIcon);

        //connection to DB, query to DB, status notification
        TestDB(trayIcon);
    }
}

package ivanK;

import com.bedivierre.eloquent.DB;
import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;
import ivanK.components.CameraEventsPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class TESTAppWithTray {
    JFrame windowBasic;
    JFrame windowAddCameras;
    static DB connector;

    int countCamers = 3;

    public static void main(String[] args) {
        new TESTAppWithTray();
    }

    private static void testDB(TrayIcon trayIcon) {
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

    private void windowMain() {
        windowBasic = new JFrame("Главное окно");

        //parameters window of windowBasic
        //specification work screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle desktopBounds = ge.getMaximumWindowBounds();

        //view screen sizes
//        System.out.println( desktopBounds );

        final int width = 600;
        final int height = 400;
        //location app on the screen
        windowBasic.setBounds(
                desktopBounds.x + desktopBounds.width - width,
                desktopBounds.y + desktopBounds.height - height,
                width,
                height
        );
//        windowBasic.getContentPane().setLayout(new FlowLayout()); //чтобы кнопка не растянулся на весь экран
        //"стягивает" окно вокруг внутренних элементов
//        windowBasic.pack();

        //random color border event for TEST
        Random rand = new Random();

        //size icon event
        Dimension labelSize = new Dimension(80, 80);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        //puts elements vertically
        mainPanel.setLayout(new BoxLayout (mainPanel, BoxLayout.Y_AXIS));

        //image to icon even for TEST
        ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/event.jpg")));

        //отрисовывать группы событий в цикле неопределённое количество раз
        for (int i = 0; i < countCamers; i++) {
            CameraEventsPanel p = new CameraEventsPanel("Камера " + i);

            //add event to group event
            for(int j = 0; j < 8; j++) {
                Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
                p.createEventLabel(j, labelSize, randomColor, image);
            }

            mainPanel.add(p);

            JScrollPane scrollPaneEvent = new JScrollPane(
                    p,
                    JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
            mainPanel.add(scrollPaneEvent);
        }


        JScrollPane scrollPaneGroupEvent = new JScrollPane(
                mainPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        windowBasic.setContentPane(mainPanel);

        windowBasic.setVisible(true);

        //create a menubar components
        menuBarInWindowBasic(windowBasic);
    }

    private void windowAddCameras() {
        windowAddCameras = new JFrame();

        Container container = new  Container();
        container.setLayout(new FlowLayout());

        //window in the center of the screen
        windowAddCameras.setLocationRelativeTo(null);
        windowAddCameras.setSize(200, 200);
        JButton buttonAddCamera = new JButton("Добавить камеру");
        buttonAddCamera.setBounds(7, 25, 150, 50);
        container.add(buttonAddCamera);

        buttonAddCamera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countCamers++;
            }
        });

        windowAddCameras.add(container);
        windowAddCameras.setVisible(true); //делаю форму 2 видимой
    }

    private void popupMenuInTray(TrayIcon trayIcon) {
        PopupMenu popupMenu = new PopupMenu();

        MenuItem startApp = new MenuItem("Открыть");
        startApp.addActionListener(e -> {
            windowMain();
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

    private void menuBarInWindowBasic(JFrame windowBasic) {
        MenuBar menuBar = new MenuBar();
        Menu parameters = new Menu("Параметры");
//        menuBar.setFont(new Font("Arial", Font.PLAIN, 50));

        MenuItem addCamera = new MenuItem("+ камера");
        parameters.add(addCamera);

        parameters.addActionListener(e -> {
            windowAddCameras();
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
            windowMain();
        });

        //create basic window in the app
        windowMain();
        if (SystemTray.isSupported()) {
            //app will be closed only from tray
            windowBasic.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }

        //create a popup menu components
        popupMenuInTray(trayIcon);

        //connection to DB, query to DB, status notification
        testDB(trayIcon);
    }
}

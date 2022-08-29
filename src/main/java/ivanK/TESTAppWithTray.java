package ivanK;

import com.bedivierre.eloquent.DB;
import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class TESTAppWithTray {
    JFrame windowBasic;
    JFrame windowAddCameras;
    static DB connector;

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

        //check screen sizes
//        System.out.println( desktopBounds );

        final int width = 600;
        final int height = 300;
        windowBasic.setBounds(
                desktopBounds.x + desktopBounds.width - width,
                desktopBounds.y + desktopBounds.height - height,
                width,
                height
        );
//        windowBasic.getContentPane().setLayout(new FlowLayout()); //чтобы кнопка не растянулся на весь экран
        //"стягивает" окно вокруг внутренних элементов
//        windowBasic.pack();






    Dimension labelSize = new Dimension(80, 80);

        Border solidBorder = BorderFactory.createLineBorder(Color.GREEN, 1);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        //puts elements vertically
        mainPanel.setLayout(new BoxLayout (mainPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createTitledBorder("topPanel"));

        JLabel centerLabel1 = new JLabel("Center1");
        //не понимаю, почему не работает добавление картинки в Label
//        centerLabel1.setIcon(new ImageIcon("img/logoSmall.png"));
        centerLabel1.setHorizontalAlignment(JLabel.CENTER);
        centerLabel1.setPreferredSize(labelSize);
        centerLabel1.setBorder(solidBorder);
        topPanel.add(centerLabel1);

        JLabel centerLabel2 = new JLabel("Center2");
        centerLabel2.setHorizontalAlignment(JLabel.CENTER);
        centerLabel2.setPreferredSize(labelSize);
        centerLabel2.setBorder(solidBorder);
        topPanel.add(centerLabel2);

        JLabel centerLabel3 = new JLabel("Center3");
        centerLabel3.setHorizontalAlignment(JLabel.CENTER);
        centerLabel3.setPreferredSize(labelSize);
        centerLabel3.setBorder(solidBorder);
        topPanel.add(centerLabel3);

        mainPanel.add(topPanel);
//
        JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        secondPanel.setBorder(BorderFactory.createTitledBorder("secondPanel"));

        JLabel centerLabel4 = new JLabel("Center4");
        centerLabel4.setHorizontalAlignment(JLabel.CENTER);
        centerLabel4.setPreferredSize(labelSize);
        centerLabel4.setBorder(solidBorder);
        secondPanel.add(centerLabel4);

        JLabel centerLabel5 = new JLabel("Center5");
        centerLabel5.setHorizontalAlignment(JLabel.CENTER);
        centerLabel5.setPreferredSize(labelSize);
        centerLabel5.setBorder(solidBorder);
        secondPanel.add(centerLabel5);

        JLabel centerLabel6 = new JLabel("Center6");
        centerLabel6.setHorizontalAlignment(JLabel.CENTER);
        centerLabel6.setPreferredSize(labelSize);
        centerLabel6.setBorder(solidBorder);
        secondPanel.add(centerLabel6);

        mainPanel.add(secondPanel);
//
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("bottomPanel"));

        JLabel centerLabel7 = new JLabel("Center7");
        centerLabel7.setHorizontalAlignment(JLabel.CENTER);
        centerLabel7.setPreferredSize(labelSize);
        centerLabel7.setBorder(solidBorder);
        bottomPanel.add(centerLabel7);

        JLabel centerLabel8 = new JLabel("Center8");
        centerLabel8.setHorizontalAlignment(JLabel.CENTER);
        centerLabel8.setPreferredSize(labelSize);
        centerLabel8.setBorder(solidBorder);
        bottomPanel.add(centerLabel8);

        JLabel centerLabel9 = new JLabel("Center9");
        centerLabel9.setHorizontalAlignment(JLabel.CENTER);
        centerLabel9.setPreferredSize(labelSize);
        centerLabel9.setBorder(solidBorder);
        bottomPanel.add(centerLabel9);

        mainPanel.add(bottomPanel);
        windowBasic.getContentPane().add(mainPanel);





        windowBasic.setVisible(true);

        //create a menubar components
        menuBarInWindowBasic(windowBasic);
    }

    private void windowAddCameras() {
        windowAddCameras = new JFrame();

        windowAddCameras.setSize(200, 200);
        Button buttonAddCamera = new Button("Добавить камеру");
        windowAddCameras.add(buttonAddCamera);

        windowAddCameras.setVisible(true); //делаю форму 2 видимой
//        CreateWindowAddCameras();
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

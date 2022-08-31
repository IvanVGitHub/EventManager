package com.ivank.fraui;

import com.bedivierre.eloquent.DB;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Content {
    static JFrame windowBasic;
    static DB connector;
    static int countCameras = 8;
    //for random color
    Random rand = new Random();

    WindowAddCameras windowAddCameras;

    public void setCountCameras(int countCameras) {
        this.countCameras = countCameras;
    }
    public int getCountCameras() {
        return countCameras;
    }

    public JFrame getWindowBasic() {
        return windowBasic;
    }

    public void windowMain() {
        windowBasic = new JFrame("Главное окно");
        windowBasic.setLayout(new BorderLayout());

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


        //size icon event
        Dimension labelSize = new Dimension(80, 80);

        final JPanel mainPanel = new JPanel();

        GridBagConstraints constrntObj = new GridBagConstraints();
        constrntObj.fill = GridBagConstraints.VERTICAL;

        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        //puts elements vertically
        mainPanel.setLayout(new BoxLayout (mainPanel, BoxLayout.Y_AXIS));

        //image to icon even for TEST
        URL url = getClass().getResource("img/event.jpg");
        ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));

        //отрисовывать группы событий в цикле неопределённое количество раз
        for (int i = 0; i < getCountCameras(); i++) {
            CameraEventsPanel p = new CameraEventsPanel("Камера " + i);

            //add event to group event
            for(int j = 0; j < 20; j++) {
                //random color border event for TEST
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
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        windowBasic.add(scrollPaneGroupEvent); //не работает
        windowBasic.add(mainPanel, BorderLayout.CENTER);

        //open window in full screen
        windowBasic.setExtendedState(JFrame.MAXIMIZED_BOTH);
        windowBasic.setVisible(true);

        //create a menubar components
        menuBarInWindowBasic(windowBasic);
    }

    private void windowAddCameras() {
//        windowAddCameras = new JFrame();
//
//        Container container = new Container();
//        container.setLayout(new FlowLayout());
//
//        //window in the center of the screen
//        windowAddCameras.setLocationRelativeTo(null);
//        windowAddCameras.setSize(200, 200);
//        JButton buttonAddCamera = new JButton("Добавить камеру");
//        buttonAddCamera.setBounds(7, 25, 150, 50);
//        container.add(buttonAddCamera);
//
//        buttonAddCamera.addActionListener(e -> {
//            countCameras++;
//            CameraEventsPanel p = new CameraEventsPanel("Ещё одна камера " + countCameras);
//
//            //image to icon even for TEST
//            URL url = getClass().getResource("img/event.jpg");
//            ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
//            //size icon event
//            Dimension labelSize = new Dimension(80, 80);
//            //add event to group event
//            for(int j = 0; j < 20; j++) {
//                //random color border event for TEST
//                Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
//                p.createEventLabel(j, labelSize, randomColor, image);
//            }
//
//            mainPanel.add(p);
//
//            JScrollPane scrollPaneEvent = new JScrollPane(
//                    p,
//                    JScrollPane.VERTICAL_SCROLLBAR_NEVER,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
//            );
//            mainPanel.add(scrollPaneEvent);
//        });
//
//        windowAddCameras.add(container);
//        windowAddCameras.setVisible(true);
    }

    private void menuBarInWindowBasic(JFrame windowBasic) {
        MenuBar menuBar = new MenuBar();
        Menu parameters = new Menu("Параметры");
//        menuBar.setFont(new Font("Arial", Font.PLAIN, 50));

        MenuItem addCamera = new MenuItem("+ камера");
        parameters.add(addCamera);

        parameters.addActionListener(e -> {
            windowAddCameras = new WindowAddCameras();
        });

        menuBar.add(parameters);

        //to display menubar
        windowBasic.setMenuBar(menuBar);
    }
}

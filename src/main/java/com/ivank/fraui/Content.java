package com.ivank.fraui;

import com.bedivierre.eloquent.DB;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Content {
    static JFrame windowBasic;
    public static JPanel mainPanel;
    public int countCameras = 4;
//    private static final List<JLabel> labels = new ArrayList<JLabel>();
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
        JFrame windowBasic = new JFrame("Главное окно");
        windowBasic.getContentPane().setLayout(new BorderLayout());

        //parameters window of windowBasic
        //specification work screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle desktopBounds = ge.getMaximumWindowBounds();

        //view screen sizes
//        System.out.println(desktopBounds);

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

        mainPanel = new JPanel();

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

        //не работает
        windowBasic.getContentPane().add(scrollPaneGroupEvent);

        windowBasic.getContentPane().add(mainPanel, BorderLayout.CENTER);

        //open window in full screen
        windowBasic.setExtendedState(JFrame.MAXIMIZED_BOTH);
        windowBasic.setVisible(true);

//        windowBasic.pack();

        //create a menubar components
        menuBarInWindowBasic(windowBasic);
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

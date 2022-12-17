package com.ivank.fraui;

import com.ivank.fraui.db.ModelEvent;
import com.ivank.fraui.db.QueryEvent;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.ivank.fraui.settings.AppConfig.getScale;

//Окно просмотра списка событий
public class WindowAllEventsCamera extends JFrame {
    //размер окна, подстраивается под разрешение экрана
    final int width = (int)(getScale() * 300);
    final int height = (int)(getScale() * 300);
    private static ArrayList<JLabel> labels = new ArrayList<>();

    public WindowAllEventsCamera(int idCamera) {
        super("Все события камеры");
        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelMain = new JPanel();
//        panelMain.setPreferredSize(new Dimension(width, height));
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        JScrollPane scrollPane = new JScrollPane(
                panelMain,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        //стилизация текстовых полей с датами
        CompoundBorder inner = new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new EtchedBorder());
        CompoundBorder outer = new CompoundBorder(inner, new EmptyBorder(5, 5, 5, 5));

        //создаём список из текстовых кликабельных элементов
        for (ModelEvent element : QueryEvent.getListModelEventsCamera(idCamera)) {
            JPanel panel = new JPanel();
            JLabel labelEvent = new JLabel(element.time);
            panel.add(labelEvent);
            labelEvent.setBorder(outer);

            //При нажатии на дату открывается окно со всеми медиа, относящимися к этому Событию
            labelEvent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //двойной клик
                    if (e.getClickCount() == 2 && !e.isConsumed()) {
                        e.consume();
                        new WindowViewImageCurrentEvent(panel, element.id, element.time);
                    }
                }
            });

            labels.add(labelEvent);
            panelMain.add(panel);
        }

        //TODO: testing multi-threads
/*        JLabel label2 = new JLabel();
        label2.setText("<html><h1>Loading...</h1></html>");
        label2.setBounds(0, 70, 200, 50);
        panelMain.add(label2);

        (new Thread(()->{
            try {
                Thread.sleep(4000);
                label2.setText("<html><h1>Loaded.</h1></html>");
            } catch (Exception ex){ex.printStackTrace();}
        })).start();*/

        getContentPane().add(scrollPane);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        //создаём элементы меню-бара
        menuBarInWindowBasic(idCamera);
    }

    //меню-бар
    void menuBarInWindowBasic(int idCamera) {
        MenuBar menuBar = new MenuBar();
        Menu parameters = new Menu("Режимы отображения");

        MenuItem workIntervalCamera = new MenuItem("График работы и событий камеры");
        parameters.add(workIntervalCamera);

        workIntervalCamera.addActionListener(e -> {
            new WindowGraphWorkIntervalCamera(idCamera);
        });

        menuBar.add(parameters);

        //отображаем меню-бар
        setMenuBar(menuBar);
    }
}

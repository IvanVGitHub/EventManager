package com.ivank.fraui;

import com.ivank.fraui.db.QueryEventImages;
import com.ivank.fraui.utils.UtilsAny;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;

import static com.ivank.fraui.settings.AppConfig.getScale;

//TODO: утечка памяти при просмотре "гифки" (после закрытия окна, память не очищается)
//Окно просмотра "гифки" События
public class WindowViewGifCurrentEvent extends JFrame {
    //таймер для очерёдного воспроизведения кадров
    Timer timer;
    //список кадров События
    ArrayList<ImageIcon> listImage;
    //размеры окна просмотра "гифки"
    final int width = (int)(getScale() * 1600);
    final int height = (int)(getScale() * 900);

    public WindowViewGifCurrentEvent(JPanel panel, int event_id, String time) {
        super("Событие: #" + event_id + ", время: " + time);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));

        //действия при нажатии кнопки закрытия
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (timer != null)
                    timer.stop();
                //очищаем память
                if (listImage != null) {
                    Collections.fill(listImage, null);
                    listImage.clear();
                }

                //убираем выделение События
                setPanelParams(panel, null, null, 0);
            }
        });

        JLabel label = new JLabel();
        add(label);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        //Событие при изменении размера окна
        label.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                //перерисовываем кадры под размер окна
                rescaleWindow(label.getWidth(), label.getHeight());
            }
        });

        //выделяем запущенное Событие
        setPanelParams(panel, Color.GRAY, Color.GREEN, (int)(getScale() * 2));

        UtilsAny.logHeapSize("\n\n================\nBefore image loading");
        //заполняем список изображениями из БД (кадр, кратный n)
//        listImage = QueryEventImages.getListEventImages(event_id, 5);
        //заполняем список изображениями из БД (все кадры)
        listImage = QueryEventImages.getListEventImages(event_id);
        UtilsAny.logHeapSize("After image loading");
        //перерисовываем кадры под размер окна
        rescaleWindow(label.getWidth(), label.getHeight());
        UtilsAny.logHeapSize("After total image rescaling");

        final int[] index = {0};

        //delay 40 ms = 25 fps
        timer = new Timer(200, (evt)-> {
            if (listImage.size() > 0) {
                if (index[0] >= listImage.size())
                    index[0] = 0;
                ImageIcon event = listImage.get(index[0]);
                index[0]++;
                label.setIcon(event);
            }
            label.revalidate();
            label.repaint();
        });
        timer.start();
    }

    //подгоняем размер кадров под размер окна
    void rescaleWindow(int width, int height) {
        if (timer != null)
            timer.stop();
        //перезаписываем список картинок
        for (int i = 0; i < listImage.size(); i++) {
            listImage.set(i, new ImageIcon((listImage.get(i).getImage().getScaledInstance(
                    width,
                    height,
                    //SCALE_FAST быстрый и довольно качественный метод рескейла, SCALE_SMOOTH качественный, но медленный
                    Image.SCALE_FAST
            ))));

            //лог памяти в консоль
            UtilsAny.logHeapSize("After image[" + i + "] rescaling");
        }
        if (timer != null)
            timer.restart();
    }

    //задаём параметры выделения отображаемого События
    public void setPanelParams(JPanel panel, Color background, Color borderColor, int borderThickness) {
        //устанавливаем фон
        panel.setBackground(background);
        //устанавливаем рамку вокруг События, чтобы понимать что именно мы открыли
        panel.setBorder(BorderFactory.createLineBorder(borderColor, borderThickness));
    }
}

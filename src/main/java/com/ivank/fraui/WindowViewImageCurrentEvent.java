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

//TODO: утечка памяти при просмотре "гифки"
//Окно просмотра "гифки" события
public class WindowViewImageCurrentEvent extends JFrame {
    Timer timer;
    ArrayList<ImageIcon> listImage;
    final int width = (int)(getScale() * 1600);
    final int height = (int)(getScale() * 900);
    JLabel label;


    public WindowViewImageCurrentEvent(JPanel panel, int event_id, String time) {
        super(time);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));

        //действия при нажатии кнопки закрытия
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
                //очищаем память
                Collections.fill(listImage, null);
                listImage.clear();

                //убираем выделение события
                setPanelParams(panel, null, null, 0);
            }
        });


        label = new JLabel();
        add(label);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        //событие при изменении размера окна
        label.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                //перерисовываем кадры под размер окна
                rescaleWindow(label.getWidth(), label.getHeight());
            }
        });

        //выделяем запущенное событие
        setPanelParams(panel, Color.LIGHT_GRAY, Color.GREEN, (int)(getScale() * 2));

        UtilsAny.logHeapSize("\n\n================\nBefore image loading");
        //заполняем список изображениями из БД
        listImage = QueryEventImages.getListEventImages(event_id, 5);
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
                    Image.SCALE_FAST //SCALE_FAST быстрый и довольно качественный метод рескейла, SCALE_SMOOTH качественный, но очень медленный
            ))));

            //лог памяти в консоль
            UtilsAny.logHeapSize("After image[" + i + "] rescaling");
        }
        if (timer != null)
            timer.restart();
    }

    //задаём параметры выделения отображаемого события
    public void setPanelParams(JPanel panel, Color background, Color borderColor, int borderThickness) {
        //устанавливаем фон
        panel.setBackground(background);
        //устанавливаем рамку вокруг события, чтобы понимать что именно мы открыли
        panel.setBorder(BorderFactory.createLineBorder(borderColor, borderThickness));
    }
}

package com.ivank.fraui;

import com.ivank.fraui.db.QueryEventImages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static com.ivank.fraui.settings.AppConfig.getScale;

//TODO: утечка памяти при просмотре "гифки"
//Окно просмотра "гифки" события
public class WindowAllImageCurrentEvent extends JFrame {
    Timer timer;
    ArrayList<ImageIcon> listImage;
    final int width = (int)(getScale() * 800);
    final int height = (int)(getScale() * 600);

    public WindowAllImageCurrentEvent(int event_id, String time) throws InterruptedException {
        super(time);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        //действия при нажатии кнопки закрытия
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
                listImage.clear();
            }
        });

        this.setPreferredSize(new Dimension(width, height));

        listImage = QueryEventImages.getListEventImages(event_id);

        JLabel label = new JLabel();
        add(label);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        final int[] index = {0};
        timer = new Timer(200, (evt)-> {
            if(listImage.size() > 0) {
                if (index[0] >= listImage.size())
                    index[0] = 0;
                ImageIcon event = listImage.get(index[0]);
                index[0]++;
                label.setIcon(new ImageIcon(event.getImage().getScaledInstance(
                        label.getWidth(),
                        label.getHeight(),
                        java.awt.Image.SCALE_SMOOTH
                )));
            }
            label.revalidate();
            label.repaint();
        });
        timer.start();
    }
}

package com.ivank.fraui.utils;

import com.ivank.fraui.WindowViewImageCurrentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.ivank.fraui.settings.AppConfig.getScale;

//Отрисовываем Событие в группе
public class AddEventElement extends JPanel {
    //флаг состояния панели CompreFace: "свёрнуто"
    boolean collapsed = true;
    JLabel collapsedLabel = null;
    JPanel compreFacePanel = new JPanel();

    //отрисовываем новое Событие в группе
    public AddEventElement(Dimension labelSize, Color color, ImageIcon image, int event_id, String time){
        this.setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
        JLabel text = new JLabel();
        text.setText(time);
        this.add(text);

        //вставляем изображение с определенными размерами
        JLabel label = new JLabel(new ImageIcon(image.getImage().getScaledInstance(
                labelSize.width,
                labelSize.height,
                java.awt.Image.SCALE_SMOOTH
        )));
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(color, (int)(getScale() * 5)));

/*        JButton newButton = new JButton("Кнопка");
        this.add(newButton);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collapsed = !collapsed;
                //перерисовываем панель
                redrawCompreFacePanel();
            }
        });*/

        //перерисовываем панель CompreFace
        redrawCompreFacePanel();
        this.add(label);
        this.add(compreFacePanel);

        //При нажатии на Событие/Event открывается окно со всеми медиа, относящимися к этому Событию
        JPanel _this = this;
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new WindowViewImageCurrentEvent(_this, event_id, time);
            }
        });
    }

    //сворачиваем панель CompreFace
    public void collapse() {
        collapsed = true;
        //перерисовываем панель CompreFace
        redrawCompreFacePanel();
    }

    //разворачиваем панель CompreFace
    public void expand() {
        collapsed = false;
        //перерисовываем панель CompreFace
        redrawCompreFacePanel();
    }

    //перерисовываем элемент со всем содержимым
    public void redrawCompreFacePanel() {
        if (collapsedLabel == null) {
            int width = 100, height = 100;
            collapsedLabel = new JLabel();
            collapsedLabel.setMinimumSize(new Dimension(width, height));
            collapsedLabel.setPreferredSize(new Dimension(width, height));
            collapsedLabel.setMaximumSize(new Dimension(width, height));
            collapsedLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
            compreFacePanel.add(collapsedLabel);
        }

        //задаём параметр видимости элемента
        collapsedLabel.setVisible(!collapsed);

        //перерисовываем элемент со всем содержимым
        compreFacePanel.revalidate();
        compreFacePanel.repaint();
    }
}

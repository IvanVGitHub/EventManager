package com.ivank.fraui.utils;

import com.ivank.fraui.WindowViewImageCurrentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import static com.ivank.fraui.settings.AppConfig.getScale;

//Отрисовываем Событие в группе
public class AddEvent extends JPanel {
    //флаг состояния панели CompreFace: "свёрнуто"
    boolean collapsed = true;
    JPanel compreFacePanel = null;

    //создание элемента, включающего Событие и связанные с ним элементы
    public AddEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time) {
        this.setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));

        //заголовок события (время создания события)
        JPanel panelText = new JPanel();
        JLabel labelText = new JLabel();
        labelText.setText(time);
        panelText.add(labelText);
        this.add(panelText);

        //миниатюра события
        JPanel panelLabel = new JPanel();
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

        //панель CompreFace
        drawCompreFacePanel();
        panelLabel.add(label);
        this.add(panelLabel);
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
        //меняем видимость панели CompreFace
        compreFacePanel.setVisible(!collapsed);
    }

    //разворачиваем панель CompreFace
    public void expand() {
        collapsed = false;
        //меняем видимость панели CompreFace
        compreFacePanel.setVisible(!collapsed);
    }

    //отрисовываем и задаём видимость элементу CompreFace со всем содержимым
    public void drawCompreFacePanel() {
        if (compreFacePanel == null) {
            compreFacePanel = new JPanel();
            compreFacePanel.setLayout(new BoxLayout (compreFacePanel, BoxLayout.Y_AXIS));

            JScrollPane scrollPaneCompreFace = new JScrollPane();
            JPanel panelOuter = new JPanel();
            panelOuter.setLayout(new BorderLayout());
            scrollPaneCompreFace.setViewportView(panelOuter);
            scrollPaneCompreFace.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPaneCompreFace.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            for (int i = 0; i < 5; i++) {
                int width = (int)(getScale() * 30), height = (int)(getScale() * 30);
                JPanel panelInner = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel label = new JLabel();
                JTextArea textArea = new JTextArea(3, (int)(getScale() * 9));
                JScrollPane scrollPane = new JScrollPane(
                        textArea,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
                );

                label.setMinimumSize(new Dimension(width, height));
                label.setPreferredSize(new Dimension(width, height));
                label.setMaximumSize(new Dimension(width, height));
                label.setBorder(BorderFactory.createLineBorder(Color.RED));
                //ТЕСТовое изображение
                URL url = getClass().getResource("../img/event.jpg");
                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
                label.setIcon(new ImageIcon(image.getImage().getScaledInstance(
                        width,
                        height,
                        Image.SCALE_FAST
                )));

                textArea.setText("Сотрудник: ФИО\nОтдел: -\nДополнительно: бла-бла-бла-бла");
                //запрещаем пользователю изменять текст
                textArea.setEditable(false);
                //убрать перенос строки
                textArea.setLineWrap(false);

                panelInner.add(label);
                panelInner.add(textArea);
                panelOuter.add(scrollPane);
            }
            compreFacePanel.add(scrollPaneCompreFace);
        }

        compreFacePanel.setVisible(false);
    }
}

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
        drawCompreFacePanel(labelSize);
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
    public void drawCompreFacePanel(Dimension size) {
        if (compreFacePanel == null) {
            compreFacePanel = new JPanel();
            compreFacePanel.setLayout(new BoxLayout (compreFacePanel, BoxLayout.Y_AXIS));
            Dimension dimension = new Dimension((int)(getScale() * 40),(int)(getScale() * 40));

            JScrollPane scrollPaneCompreFace = new JScrollPane();
            JPanel panelOuter = new JPanel();

            panelOuter.setLayout(new BoxLayout (panelOuter, BoxLayout.Y_AXIS));

            scrollPaneCompreFace.setViewportView(panelOuter);
            scrollPaneCompreFace.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPaneCompreFace.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPaneCompreFace.setPreferredSize(new Dimension(size.width, size.height * 2));

            for (int i = 0; i < 5; i++) {
                JPanel panelInner = new JPanel();
                JLabel label = new JLabel();
                JTextArea textArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(
                        textArea,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                );
                //здесь есть особенность: применяется только высота элемента,
                //ширина по-факту получается по внутренней границе внешнего JScrollPane
                scrollPane.setPreferredSize(new Dimension(0, 70));

//                panelInner.setLayout(new FlowLayout(FlowLayout.LEFT));
                panelInner.setLayout(new BoxLayout (panelInner, BoxLayout.Y_AXIS));
//                panelInner.setPreferredSize(new Dimension(size.width, size.height));

                label.setMinimumSize(dimension);
                label.setPreferredSize(dimension);
                label.setMaximumSize(dimension);
                label.setBorder(BorderFactory.createLineBorder(Color.RED));
                //ТЕСТовое изображение
                URL url = getClass().getResource("../img/event.jpg");
                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
                label.setIcon(new ImageIcon(image.getImage().getScaledInstance(
                        dimension.width,
                        dimension.height,
                        Image.SCALE_FAST
                )));

                textArea.setText("Сотрудник: ФИО\nОтдел: -\nДополнительно: бла-бла-бла-бла");
                //запрещаем пользователю изменять текст
                textArea.setEditable(false);
                //убрать перенос строки
                textArea.setLineWrap(false);

                panelInner.add(label);
                panelInner.add(scrollPane);
                panelOuter.add(panelInner);
            }
            compreFacePanel.add(scrollPaneCompreFace);
        }

        compreFacePanel.setVisible(false);
    }
}

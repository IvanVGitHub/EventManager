package com.ivank.fraui.utils;

import com.ivank.fraui.WindowViewGifCurrentEvent;

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
    public AddEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time, int countCompreFace) {
        this.setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));

        //заголовок события (время создания события)
        JPanel panelText = new JPanel();
        JLabel labelText = new JLabel();

        labelText.setText(time);
        panelText.add(labelText);
        this.add(panelText);

        //миниатюра события
        JPanel panelLabel = new JPanel();
        JLabel labelIcon = new JLabel();
        labelIcon.setIcon(new ImageIcon(image.getImage().getScaledInstance(
                labelSize.width,
                labelSize.height,
                java.awt.Image.SCALE_SMOOTH
        )));
        labelIcon.setPreferredSize(labelSize);
        labelIcon.setBorder(BorderFactory.createLineBorder(color, (int)(getScale() * 5)));
        labelIcon.setLayout( new BoxLayout(labelIcon, BoxLayout.Y_AXIS) );

        //надпись в углу миниатюры с количеством событий CompreFace
        JLabel labelIconText = new JLabel();
        Font font = new Font("TimesRoman", Font.BOLD, 30);
        labelIconText.setBackground(Color.ORANGE);
        labelIconText.setLocation(0, 0);
        labelIconText.setFont(font);
        labelIconText.setText(String.valueOf(countCompreFace));
        //непрозрачность фона
        labelIconText.setOpaque(true);
        labelIcon.add(labelIconText);

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
        drawCompreFacePanel(labelSize, countCompreFace);
        panelLabel.add(labelIcon);
        this.add(panelLabel);
        this.add(compreFacePanel);

        //При нажатии на Событие/Event открывается окно со всеми медиа, относящимися к этому Событию
        JPanel _this = this;
        labelIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //двойной клик
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    new WindowViewGifCurrentEvent(_this, event_id, time);
                }
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

    //отрисовываем и задаём видимость панели CompreFace со всем содержимым
    public void drawCompreFacePanel(Dimension size, int countCompreFace) {
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

            for (int i = 0; i < countCompreFace; i++) {
                JPanel panelInner = new JPanel();
                JLabel labelIcon = new JLabel();
                JTextArea textArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(
                        textArea,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                );
                //здесь есть особенность: применяется только высота элемента,
                //ширина по-факту получается по внутренней границе внешнего JScrollPane
                scrollPane.setPreferredSize(new Dimension(0, 70));

                panelInner.setLayout(new BoxLayout (panelInner, BoxLayout.Y_AXIS));

                labelIcon.setPreferredSize(dimension);
                labelIcon.setBorder(BorderFactory.createLineBorder(Color.RED));
                //ТЕСТ демонстрационное изображение
                //для отладки в среде разработки
                URL url = getClass().getResource("../img/event.jpg");
                //для скомпилированного .jar файла
//                URL url = getClass().getResource("/com/ivank/fraui/img/event.jpg");
                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
                labelIcon.setIcon(new ImageIcon(image.getImage().getScaledInstance(
                        dimension.width,
                        dimension.height,
                        Image.SCALE_FAST
                )));

                textArea.setText("Сотрудник: ФИО\nОтдел: -\nДополнительно: бла-бла-бла-бла");
                //запрещаем пользователю изменять текст
                textArea.setEditable(false);
                //убрать побуквенный перенос строки, слово целиком
                textArea.setLineWrap(false);

                panelInner.add(labelIcon);
                panelInner.add(scrollPane);
                panelOuter.add(panelInner);
            }
            compreFacePanel.add(scrollPaneCompreFace);
        }

        compreFacePanel.setVisible(false);
    }
}

package com.ivank.fraui.utils;

import com.ivank.fraui.settings.SettingsDefault;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

import static com.ivank.fraui.settings.AppConfig.getScale;

//Панель CompreFace
public class PanelCompreFace {
    /*private static PanelCompreFace instance;
    public static PanelCompreFace getInstance() {
        return instance;
    }

    public PanelCompreFace() {
        instance = this;

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
//                URL url = getClass().getResource("../img/event.jpg");
                    //для скомпилированного .jar файла
//                URL url = getClass().getResource("/com/ivank/fraui/img/event.jpg");
//                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
                    ImageIcon image = new ImageIcon(Base64.getDecoder().decode(SettingsDefault.getImageDefaultEvent()));
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
    }*/
}

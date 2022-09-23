package com.ivank.fraui;

import com.ivank.fraui.db.QueryCameras;
import com.ivank.fraui.db.QueryPlugins;
import com.ivank.fraui.settings.AppConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WindowSettingsCamera extends JFrame {
    private static ArrayList<String> listChckBxIsSlctName = new ArrayList<>();
    //получаем список плагинов (временно имён камер)
    private static ArrayList<String> listCameraNameALL = new ArrayList<>();
    private static ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

    public WindowSettingsCamera(int idCamera) {
        super("Настройки камеры");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 100));

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        listCameraNameALL.clear();
        listCameraNameALL = QueryPlugins.getListPlaginsOfCamera(idCamera);
        ArrayList<String> allPlugins = QueryPlugins.getListPlaginId();
        for (String element : allPlugins) {

            String name = element + " (не подключен)";
            for (String plugin: listCameraNameALL) {
                if(element.equals(plugin)) {
                    name = element;
                }
            }
            JCheckBox box = new JCheckBox(name);
            //если камера уже есть в списке отображаемых, то помечается "галочкой"
            box.setSelected(QueryCameras.statusChBx(element));

            checkBoxes.add(box);
            panelMain.add(box);
        }

        JButton buttonSave = new JButton("Сохранить");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int countTEST = 0;
                    listChckBxIsSlctName.clear();

                    //считаем количество отмеченных чекбоксов/камер, создаём список из отмеченных камер
                    for (JCheckBox element : checkBoxes) {
                        if (element.isSelected()) {
                            countTEST += 1;

                            listChckBxIsSlctName.add(element.getText());
                        }
                    }

                    //Информационное сообщение для ТЕСТА
                    JOptionPane.showMessageDialog(
                            null,
                            "Выбрано плагинов: " + String.valueOf(countTEST) + " " + listChckBxIsSlctName
                    );

                    //при нажатии на кнопку "Ок" закроется не только диалоговое окно, но и окно выбора камер
                    if (JOptionPane.OK_OPTION == 0) {
                        setVisible(false);
                    }

                    //сохранение списка выбранных камер в файл настроек
                    AppConfig.getInstance().setPluginsIsSlct(idCamera, listChckBxIsSlctName);
                    AppConfig.saveConfig();

                    //перерисовываем Content (JPanel) в основном окне (WindowMain)
                    //(можно оптимизировать, перерисовывая только конкретную группу событий)
                    Application.windowMain().getContent().setCameraView();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(panelMain, BorderLayout.CENTER);
        add(buttonSave, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

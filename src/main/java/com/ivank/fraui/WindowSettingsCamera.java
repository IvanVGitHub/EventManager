package com.ivank.fraui;

import com.ivank.fraui.db.QueryPlugins;
import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.utils.UtilsAny;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WindowSettingsCamera extends JFrame {
    private static ArrayList<String> listChckBxIsSlctName = new ArrayList<>();
    //получаем список плагинов (временно имён камер)
    private static ArrayList<String> listPluginsOfCamera = new ArrayList<>();
    private static ArrayList<JCheckBox> listCheckBoxes = new ArrayList<>();

    public WindowSettingsCamera(int idCamera) {
        super("Настройки камеры");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 100));

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        listChckBxIsSlctName.clear();
        listPluginsOfCamera.clear();
        listPluginsOfCamera = QueryPlugins.getListPluginsOfCamera(idCamera);
        listCheckBoxes.clear();

        ArrayList<String> allPlugins = QueryPlugins.getListPluginIdALL();
        for (String pluginAny : allPlugins) {

            JCheckBox checkBox = new JCheckBox(pluginAny);
            checkBox.setForeground(Color.RED);
            for (String pluginCamera : listPluginsOfCamera) {
                if (pluginAny.equals(pluginCamera)) {
                    checkBox.setForeground(Color.BLACK);
                }
            }

            //если плагин уже есть в списке отображаемых, то помечается "галочкой"
            checkBox.setSelected(UtilsAny.statusChBx(AppConfig.getInstance().getPluginsIsSlct(idCamera), pluginAny));

            listCheckBoxes.add(checkBox);
            panelMain.add(checkBox);
        }

        JButton buttonSave = new JButton("Сохранить");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int countTEST = 0;
                    listChckBxIsSlctName.clear();

                    //считаем количество отмеченных чекбоксов/камер, создаём список из отмеченных камер
                    for (JCheckBox element : listCheckBoxes) {
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

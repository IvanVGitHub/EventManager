package com.ivank.fraui;

import com.ivank.fraui.db.ModelCamera;
import com.ivank.fraui.db.QueryCameras;
import com.ivank.fraui.db.QueryPlugins;
import com.ivank.fraui.settings.CameraSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WindowCameraSettings extends JFrame {
    private static ArrayList<String> listChckBxIsSlctName = new ArrayList<>();

    public WindowCameraSettings(String cameraName) {
        super("Настройки камеры");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //получаем список плагинов (временно имён камер)
        ArrayList<String> listCameraNameALL = new ArrayList<>(QueryPlugins.getListPlaginId());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        for (String element : listCameraNameALL) {
            JCheckBox box = new JCheckBox(element);

            //если камера уже есть в списке отображаемых, то помечается "галочкой"
            box.setSelected(QueryCameras.statusChBx(element));

            checkBoxes.add(box);
            panelMain.add(box);
        }

        JButton buttonAddCameras = new JButton("Сохранить");
        buttonAddCameras.setBounds(100,200,100,30);
        buttonAddCameras.addActionListener(new ActionListener() {
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
                            "Выбрано камер: " + String.valueOf(countTEST) + " " + listChckBxIsSlctName
                    );

                    //при нажатии на кнопку "Ок" закроется не только диалоговое окно, но и окно выбора камер
                    if (JOptionPane.OK_OPTION == 0) {
                        setVisible(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(panelMain, BorderLayout.CENTER);
        add(buttonAddCameras, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

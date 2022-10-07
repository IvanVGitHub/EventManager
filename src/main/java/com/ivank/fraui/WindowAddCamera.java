package com.ivank.fraui;

import com.ivank.fraui.db.QueryCamera;
import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.utils.UtilsAny;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WindowAddCamera extends JFrame {
    private static ArrayList<String> listChckBxIsSlctName = new ArrayList<>();
    //получаем список имён всех камер
    private static ArrayList<String> listCameraNameALL = new ArrayList<>();
    private static ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

    public WindowAddCamera() {
        super("Список камер");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setMinimumSize(new Dimension(250, 100));

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        listChckBxIsSlctName.clear();
        checkBoxes.clear();
        listCameraNameALL = QueryCamera.getListCameraName();

        for (String element : listCameraNameALL) {
            JCheckBox checkBox = new JCheckBox(element);

            //если камера уже есть в списке отображаемых, то помечается "галочкой"
            checkBox.setSelected(UtilsAny.statusChBx(QueryCamera.getListCameraNameIsSelect(), element));

            checkBoxes.add(checkBox);
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

                    //сохранение списка выбранных камер в файл настроек
                    AppConfig.getInstance().setCamerasIsSlct(listChckBxIsSlctName);
                    AppConfig.saveConfig();

                    //перерисовываем/обновляем Content (JPanel) в основном окне (WindowMain)
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

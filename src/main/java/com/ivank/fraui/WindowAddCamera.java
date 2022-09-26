package com.ivank.fraui;

import com.ivank.fraui.db.ModelCamera;
import com.ivank.fraui.db.QueryCameras;
import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.settings.SettingsCamera;
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
        listCameraNameALL.clear();
        checkBoxes.clear();



//        for (ModelCamera event : QueryCameras.getListMdlCamerasALL()) {
//            //дописал Данила, спросить ghj aeyrwbjyfkmyjcnm
//            SettingsCamera sc = AppConfig.getInstance().getCameraByName(event.camera_name);
//            if(sc != null)
//                sc.id = event.id;
//
//            listCameraNameALL.add(String.valueOf(event.camera_name));
//        }



        listCameraNameALL = QueryCameras.getListCameraNameALL();

        for (String element : listCameraNameALL) {
            JCheckBox chBox = new JCheckBox(element);

            //если камера уже есть в списке отображаемых, то помечается "галочкой"
            chBox.setSelected(UtilsAny.statusChBx(QueryCameras.getListCameraName(), element));

            checkBoxes.add(chBox);
            panelMain.add(chBox);
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

                    //перерисовываем Content (JPanel) в основном окне (WindowMain)
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

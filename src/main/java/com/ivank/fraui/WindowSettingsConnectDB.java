package com.ivank.fraui;

import com.ivank.fraui.settings.ConnectionSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowSettingsConnectDB extends JFrame {
    private JTextField textFieldHost;
    private JTextField textFieldDatabase;
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;
    private JButton buttonApply;
    private JPanel panelMain;

    public WindowSettingsConnectDB() {
        super("Подключение к БД");

        textFieldHost.setText(AppConfig.getInstance().getConnection().host);
        textFieldDatabase.setText(AppConfig.getInstance().getConnection().database);
        textFieldUsername.setText(AppConfig.getInstance().getConnection().username);
        textFieldPassword.setText(AppConfig.getInstance().getConnection().password);

        buttonApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //сохранение настроек подключения к БД в файл настроек
                    ConnectionSettings conn = AppConfig.getInstance().getConnection();
                    conn.host = textFieldHost.getText();
                    conn.database = textFieldDatabase.getText();
                    conn.username = textFieldUsername.getText();
                    conn.password = textFieldPassword.getText();
                    AppConfig.saveConfig();

                    try {
                        //диалоговое окно
                        JOptionPane.showMessageDialog(null, "Чтобы изменения вступили в силу, перезапустите приложение!", "Внимание", JOptionPane.INFORMATION_MESSAGE);
                        //при нажатии на кнопку "Ок" закроется не только диалоговое окно, но и окно настроек подкючения
                        if (JOptionPane.OK_OPTION == 0) {
                            setVisible(false);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();

                        JOptionPane.showMessageDialog(null, "При сохранении настроек произошла ошибка! Обратитесь к разработчику.", "Произошла ошибка", JOptionPane.ERROR);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setContentPane(panelMain);
        setVisible(true);
        pack();//окно создаётся по размерам внутренних элементов, а не [0;0] px
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}

package com.ivank.fraui;

import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.settings.SettingsConnection;

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
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);


        //загружаем актуальные json данные
        AppConfig.loadConfig();
        SettingsConnection conn = AppConfig.getInstance().getConnection();

        textFieldHost.setText(conn.host);
        textFieldDatabase.setText(conn.database);
        textFieldUsername.setText(conn.username);
        textFieldPassword.setText(conn.password);

        buttonApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn.host = textFieldHost.getText();
                    conn.database = textFieldDatabase.getText();
                    conn.username = textFieldUsername.getText();
                    conn.password = textFieldPassword.getText();
                    //сохранение локальных настроек
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
        pack();
        setLocationRelativeTo(null);
    }
}

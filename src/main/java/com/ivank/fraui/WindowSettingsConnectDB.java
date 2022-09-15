package com.ivank.fraui;

import com.ivank.fraui.settings.ConnectionSettings;

import javax.swing.*;
import java.awt.*;
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
                    ConnectionSettings conn = AppConfig.getInstance().getConnection();
                    conn.host = textFieldHost.getText();
                    conn.database = textFieldDatabase.getText();
                    conn.username = textFieldUsername.getText();
                    conn.password = textFieldPassword.getText();
                    AppConfig.saveConfig();

                    try {
                        //модальное окно для донесения информации
                        JDialog dialog = new JDialog();
                        JLabel label = new JLabel("Чтобы изменения вступили в силу, перепустите приложение!");

                        dialog.add(label);

                        dialog.setTitle("Внимание");
                        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                        dialog.setModal(true);
                        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dispose();
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

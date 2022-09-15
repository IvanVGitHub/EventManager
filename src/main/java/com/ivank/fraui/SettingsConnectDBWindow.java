package com.ivank.fraui;

import com.ivank.fraui.settings.ConnectionSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsConnectDBWindow extends JFrame {
    private JTextField textFieldHost;
    private JTextField textFieldDatabase;
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;
    private JButton buttonApply;
    private JPanel panelMain;

    public SettingsConnectDBWindow() {
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setContentPane(panelMain);
        setVisible(true);
        pack();//окно создаётся по размерам внутренних элементов, а не [0;0] px

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

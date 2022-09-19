package com.ivank.fraui;

import com.ivank.fraui.db.QueryCameras;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WindowAddCamera extends JFrame {
    public static ArrayList<String> listChckBxIsSlctName = new ArrayList<>();

    public WindowAddCamera() {
        super("Список камер");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        ArrayList<String> myArrayList = new ArrayList<>(QueryCameras.getListCameraName());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ArrayList<JCheckBox> checkboxes = new ArrayList<>();

        for(String element : myArrayList) {
            JCheckBox box = new JCheckBox(element);
            checkboxes.add(box);
            panel.add(box);
        }



        JButton b = new JButton("Order");
        b.setBounds(100,200,80,30);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int amountTEST = 0;
                    listChckBxIsSlctName.clear();
                    for (JCheckBox element : checkboxes) {
                        if (element.isSelected()) {
                            amountTEST += 1;

                            listChckBxIsSlctName.add(element.getText());
                        }
                    }
                    JOptionPane.showMessageDialog(null, listChckBxIsSlctName + " " + String.valueOf(amountTEST));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(b);
        add(panel);



//        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

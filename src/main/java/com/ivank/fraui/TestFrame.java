package com.ivank.fraui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TestFrame extends JFrame {

    private static List<JPanel> listPanels = new ArrayList<>();

    public static void createGUI() {
        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Font font = new Font("Verdana", Font.PLAIN, 25);

        JPanel butPanel = new JPanel();

        JButton addButton = new JButton("+");
        addButton.setFont(font);
        addButton.setFocusable(false);
        butPanel.add(addButton);

        JButton remButton = new JButton("-");
        remButton.setFont(font);
        remButton.setFocusable(false);
        butPanel.add(remButton);

        final JPanel labPanel = new JPanel();
        final JScrollPane scrollPane = new JScrollPane(labPanel);
        labPanel.setLayout(new BoxLayout(labPanel, BoxLayout.Y_AXIS));

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int number = listPanels.size() + 1;
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Label " + number);
                panel.add(label);
                listPanels.add(panel);
                label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                label.setFont(font);
                labPanel.add(panel);
                scrollPane.revalidate();
            }
        });

        remButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(listPanels.size() > 0) {
                    int index = listPanels.size() - 1;
                    JPanel panel = listPanels.remove(index);
                    labPanel.remove(panel);
                    labPanel.repaint();
                    scrollPane.revalidate();
                }
            }
        });

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(butPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(250, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest("abracadabra".getBytes("UTF-8"));
        for (byte b : digest) {
            System.out.printf("%02x", b);
        }
    }
}

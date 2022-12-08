package com.ivank.fraui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

enum ViewState {
    START_STATE, NEXT_STATE;
}

@SuppressWarnings("serial")
class Panel2 extends JPanel {
    public Panel2() {
        JPanel  panel2 = new JPanel();
        JButton button = new JButton("sweet");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyWindowTest.changeState(ViewState.START_STATE);
            }
        });
        panel2.add(button);
        this.add(panel2);
    }
}

@SuppressWarnings("serial")
class Panel1 extends JPanel {
    public Panel1() {
        JPanel panel1 = new JPanel();
        JButton button = new JButton("my button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyWindowTest.changeState(ViewState.NEXT_STATE);
            }
        });
        panel1.add(button);
        this.add(panel1);
    }
}

public class MyWindowTest {
    private static ViewState viewState;
    private static JPanel mpanel;
    private static JPanel panel1;
    private static JPanel panel2;
    private static JFrame frame;

    public MyWindowTest() {
        frame = new JFrame();
        mpanel = new JPanel();
        panel1 = new Panel1();
        panel2 = new Panel2();

        // Sets default state
        changeState(ViewState.START_STATE);

        frame.setSize(100, 100);
        frame.add(mpanel);
        frame.setVisible(true);
    }

    public static void changeState(ViewState state) {
        viewState = state;
        System.out.println("change state: " + viewState);

        switch (state) {
            case START_STATE:
                mpanel.removeAll();
                mpanel.add(panel1);
                mpanel.revalidate();
                mpanel.repaint();
                break;
            case NEXT_STATE:
                mpanel.removeAll();
                mpanel.add(panel2);
                mpanel.revalidate();
                mpanel.repaint();
                break;
            default:
                System.out.println("UNKNOWN STATE!");
                break;
        }
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        MyWindowTest n = new MyWindowTest();

    }
}

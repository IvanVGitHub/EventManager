package com.ivank.fraui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Свой класс круглой кнопки
public class RoundButton extends JButton {
    //флаг наведения курсора на кнопку
    boolean mouseEntered = false;
    public RoundButton() {
        this(null, null);
    }
    public RoundButton(Icon icon) {
        this(null, icon);
    }
    public RoundButton(String text) {
        this(text, null);
    }
    public RoundButton(Action a) {
        this();
        setAction(a);
    }
    public RoundButton(String text, Icon icon) {
        //обработка взаимодействия мыши с кнопкой
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            //навели мышку
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered = true;
            }

            //отвели мышку
            @Override
            public void mouseExited(MouseEvent e) {
                mouseEntered = false;
            }
        });

        //установить стандартный фон кнопки (только прямоугольный)
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        //заливает цветом
//        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);

        super.paintComponent(g);
        //добавим сглаживание контуру прямоугольной кнопки со скруглёнными углами
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if (mouseEntered) {
            g.setColor(Color.BLACK);
            g.drawOval(1, 1, getSize().width - 3, getSize().height - 3);
        } else {
            g.setColor(getForeground());
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }
        //отрисовывает только контур прямоугольной кнопки со скруглёнными углами
//        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
    }
}

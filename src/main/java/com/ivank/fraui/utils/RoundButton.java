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

        setText(text);
        setIcon(icon);

        //установить стандартный фон кнопки (только прямоугольный)
        setContentAreaFilled(false);
    }

    //тело кнопки
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();

        if (getModel().isArmed()) {
            g2.setColor(new Color(184,207,229));
            g2.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        } else {
            //верхняя усечённая сфера
            g2.setPaint(new GradientPaint(
                    new Point(0, 0),
                    new Color(184,207,229),
                    new Point(0, getHeight()/3),
                    Color.WHITE));
            g2.fillArc(0, 0, getWidth(), getHeight(), 20, 140);
            //нижняя усечённая сфера
            g2.setPaint(new GradientPaint(
                    new Point(0, getHeight()/3),
                    Color.WHITE,
                    new Point(0, getHeight()),
                    new Color(184,207,229)));
            g2.fillArc(0, 0, getWidth(), getHeight(), 160, 220);
            g2.dispose();
        }

        //добавим сглаживание контуру прямоугольной кнопки со скруглёнными углами
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //отрисовываем картинку(например)
        super.paintComponent(g);
    }

    //контур кнопки
    @Override
    protected void paintBorder(Graphics g) {
        if (mouseEntered) {
            //малый контур
            g.setColor(new Color(184,207,229));
            g.drawOval(2, 2, getSize().width - 5, getSize().height - 5);
            //средний контур
            g.setColor(new Color(122,138,153));
            g.drawOval(1, 1, getSize().width - 3, getSize().height - 3);
            //большой контур
            g.setColor(new Color(184,207,229));
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        } else {
            //большой контур
            g.setColor(new Color(122,138,153));
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }
    }
}

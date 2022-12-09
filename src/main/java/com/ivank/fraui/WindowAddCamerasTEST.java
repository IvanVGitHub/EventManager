package com.ivank.fraui;

import com.ivank.fraui.utils.AddGroupEvents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ivank.fraui.settings.AppConfig.getScale;

public class WindowAddCamerasTEST extends JFrame {
    //размер окна, подстраивается под разрешение экрана
    final int width = (int)(getScale() * 600);
    final int height = (int)(getScale() * 400);
    public static final List<JPanel> labels = new ArrayList<>();

    private static class CameraListPanel
            extends JPanel
            implements Scrollable {
        CameraListPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect,
                                              int orientation,
                                              int direction) {
            return getScrollableIncrement(30,
                    visibleRect, orientation, direction);
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect,
                                               int orientation,
                                               int direction) {
            return getScrollableIncrement(
                    orientation == SwingConstants.HORIZONTAL ?
                            getWidth() : getHeight(),
                    visibleRect, orientation, direction);
        }

        private int getScrollableIncrement(int amount,
                                           Rectangle visibleRect,
                                           int orientation,
                                           int direction) {
            if (orientation == SwingConstants.HORIZONTAL) {
                return Math.min(amount, direction < 0 ? visibleRect.x :
                        getWidth() - (visibleRect.x + visibleRect.width));
            } else {
                return Math.min(amount, direction < 0 ? visibleRect.y :
                        getHeight() - (visibleRect.y + visibleRect.height));
            }
        }
    }

    public WindowAddCamerasTEST() {
        super("Добавить камеру");

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelButton = new JPanel();

        JButton addButton = new JButton("+");
        addButton.setFocusable(false);
        panelButton.add(addButton);

        JButton remButton = new JButton("-");
        remButton.setFocusable(false);
        panelButton.add(remButton);

        JPanel camerasPanel = new CameraListPanel();
        JScrollPane scrollPaneGroupEvent = new JScrollPane(camerasPanel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number = labels.size() + 1;
                AddGroupEvents eventAdd = new AddGroupEvents();
                URL url = getClass().getResource("./img/event.jpg");
                ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
                Dimension labelSize = new Dimension((int)(getScale() * 80), (int)(getScale() * 80));

                //add event to group event
                Random rand = new Random();
                //создаём рамку группы событий и пишем на ней имя камеры
                eventAdd.setBorder(BorderFactory.createTitledBorder("Камера \"" + number + "\""));
                for(int a = 0; a < 20; a++) {
                    //random color border event for TEST
                    Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
                    eventAdd.createLabelEvent(labelSize, randomColor, image, 0, null);
                }

                labels.add(eventAdd);
                camerasPanel.add(eventAdd, BorderLayout.NORTH);
                scrollPaneGroupEvent.revalidate();
            }
        });

        remButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(labels.size() > 0) {
                    int index = labels.size() - 1;
                    JPanel panel = labels.remove(index);
                    camerasPanel.remove(panel);
                    camerasPanel.repaint();
                    scrollPaneGroupEvent.revalidate();
                }
            }
        });

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panelButton, BorderLayout.NORTH);
        this.getContentPane().add(scrollPaneGroupEvent, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(width, height));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

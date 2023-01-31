package com.ivank.fraui.utils;

import com.ivank.fraui.Application;
import com.ivank.fraui.WindowAllEventsCamera;
import com.ivank.fraui.WindowCameraLiveView;
import com.ivank.fraui.WindowSettingsCamera;
import com.ivank.fraui.components.Content;
import com.ivank.fraui.db.ModelCamera;
import com.ivank.fraui.db.QueryEvent;
import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.settings.SettingsDefault;
import org.bytedeco.javacv.FFmpegFrameGrabber;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Base64;

import static com.ivank.fraui.db.QueryCamera.getModelCameraById;
import static com.ivank.fraui.settings.AppConfig.getScale;
import static com.ivank.fraui.utils.UtilsAny.base64ToImage;

//Отрисовываем группу Событий
public class AddGroupEvents extends JPanel {
    //список Событий в данной группе
    public ArrayList<AddEvent> listEvents = new ArrayList<>();

    //экземпляр класса
    public AddGroupEvents() {
        //задаём ориентацию расположения Событий (слева направо)
        super(new FlowLayout(FlowLayout.LEFT));
    }

    //отрисовываем Событие в группе
    public void createLabelEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time, int countCompreFace) {
        AddEvent panel = new AddEvent(labelSize, color, image, event_id, time, countCompreFace);
        listEvents.add(panel);
        add(panel);
    }

    //"умная" функция разворачивания/сворачивания панели CompreFace
    /*
    если хотя бы одна дополнительная область развёрнута, то сворачиваем все области и, наоборот,
    если хотя бы одна дополнительная область свёрнута, то разворачиваем все области
    */
    public void toggleViewModeAllEvents(JButton button, BufferedImage bufferedImage) {
        boolean hasCollapsed = false;

        for (AddEvent event : listEvents) {
            if (event.collapsed) {
                hasCollapsed = true;
                break;
            }
        }

        if (hasCollapsed)
            expandAllEvents(button, bufferedImage);
        else
            collapseAllEvents(button, bufferedImage);
    }

    //сворачиваем дополнительную область CF у всех Событий
    public void collapseAllEvents(JButton button, BufferedImage bufferedImage) {
        for (AddEvent event: listEvents) {
            //сворачиваем элемент под каждой миниатюрой
            event.collapse();
        }

        //разворачиваем направление стрелки на картинке
        //TODO: не работает более предпочтительный сценарий получения изображения картинки
        //(из кнопки мы получаем чёрный квадрат, а не картинку, которую можно перевернуть)
/*        Icon icon = button.getIcon();
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        //очищаем память
        graphics.dispose();*/

        button.setIcon(new ImageIcon(bufferedImage.getScaledInstance((int)(getScale() * 30), (int)(getScale() * 30), java.awt.Image.SCALE_SMOOTH)));
    }

    //разворачиваем дополнительную область CF у всех Событий
    public void expandAllEvents(JButton button, BufferedImage bufferedImage) {
        for (AddEvent event: listEvents) {
            //сворачиваем элемент под каждой миниатюрой
            event.expand();
        }

        //разворачиваем направление стрелки на картинке
        //TODO: не работает более предпочтительный сценарий получения изображения картинки
        //(из кнопки мы получаем чёрный квадрат, а не картинку, которую можно перевернуть)
/*        Icon icon = button.getIcon();
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        //очищаем память
        graphics.dispose();*/
        bufferedImage = UtilsAny.rotateImage(bufferedImage, 180);

        button.setIcon(new ImageIcon(bufferedImage.getScaledInstance((int)(getScale() * 30), (int)(getScale() * 30), java.awt.Image.SCALE_SMOOTH)));
    }

    //размечаем кнопки слева в группе событий каждой камеры
    public void createButtonsForCamera(AddGroupEvents groupPanel, int idCamera, int firstIdEvent, int lastIdEvent) {
        JPanel parentButtonsPanel = new JPanel();
        parentButtonsPanel.setLayout(new BoxLayout (parentButtonsPanel, BoxLayout.Y_AXIS));
        //не работает (пытаюсь прижать блок с кнопками к верху панели)
//        parentButtonsPanel.setAlignmentY(TOP_ALIGNMENT);

        //добавить кнопку "прямой эфир"
        CamData cd = CamData.getCamDataFromId(idCamera);
        JButton button = new JButton();
        addSidePanelBtn(button, new ImageIcon(Base64.getDecoder().decode(SettingsDefault.getImageLiveView())), parentButtonsPanel,
                e -> new WindowCameraLiveView(cd),
                () -> {
                    //сделаем кнопку неактивной, пока не пройдёт проверка доступности прямого эфира
                    setEnabled(false);
                    //добавим многопоточность
                    (new Thread(()->{
                        try {
                            //устанавливаем красную рамку кнопке
                            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, (int)(getScale() * 2)));
                            //кнопка не кликабельна
                            button.setEnabled(false);
                            //проверяем доступность камеры в сети
                            FFmpegFrameGrabber streamGrabber = new FFmpegFrameGrabber(cd.getConnectionUrl());
                            //ожидание старта подключения, в микросекундах (1 сек)
                            //если не подключились: получаем ошибку в лог и считаем, что камера не доступна
                            streamGrabber.setOption("timeout" , "1000000");
                            streamGrabber.start();
                            //если в результате подключения мы можем получить видеопоток
                            if (streamGrabber.hasVideo()) {
                                //устанавливаем зелёную рамку кнопке
                                button.setBorder(BorderFactory.createLineBorder(Color.GREEN, (int)(getScale() * 2)));
                                //кнопка кликабельна
                                button.setEnabled(true);
                            } else {
                                //устанавливаем красную рамку кнопке
                                button.setBorder(BorderFactory.createLineBorder(Color.RED, (int) (getScale() * 2)));
                                //кнопка не кликабельна
                                button.setEnabled(false);
                            }
                            streamGrabber.stop();
                            streamGrabber.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            //устанавливаем красную рамку кнопке
                            button.setBorder(BorderFactory.createLineBorder(Color.RED, (int)(getScale() * 2)));
                            //кнопка не кликабельна
                            button.setEnabled(false);
                        }
                    })).start();
                }
        );

        //добавить кнопку "все события текущей камеры"
        addSidePanelBtn(new JButton(), new ImageIcon(Base64.getDecoder().decode(SettingsDefault.getImageAllImgEvents())), parentButtonsPanel,
                e -> new WindowAllEventsCamera(idCamera),
                null
        );

        //добавить кнопку "опции камеры"
        addSidePanelBtn(new JButton(), new ImageIcon(Base64.getDecoder().decode(SettingsDefault.getImageOptions())), parentButtonsPanel,
                e -> new WindowSettingsCamera(idCamera),
                null
        );

        //добавить кнопки пагинатора
        JPanel paginatorButtonsPanel = new JPanel();
//        paginatorButtonsPanel.setLayout(new BoxLayout(paginatorButtonsPanel, BoxLayout.X_AXIS));
        parentButtonsPanel.add(paginatorButtonsPanel);
//        AppConfig.getInstance().getEventLimit()
//        Application.windowMain().getContent().drawGroupCameraPanel();
        ModelCamera modelCamera = getModelCameraById(idCamera);
        addSidePanelBtn(new BasicArrowButton(BasicArrowButton.WEST), null, paginatorButtonsPanel,
                e -> Application.windowMain().getContent().drawGroupCameraPanel(
                        modelCamera,
                        QueryEvent.getListModelEventsCamera(modelCamera.id, lastIdEvent, ">", AppConfig.getInstance().getEventLimit()),
                        new Dimension(AppConfig.getInstance().getLabelSize().width, AppConfig.getInstance().getLabelSize().height)),
                null);
        addSidePanelBtn(new BasicArrowButton(BasicArrowButton.EAST), null, paginatorButtonsPanel,
                e -> Application.windowMain().getContent().drawGroupCameraPanel(
                        modelCamera,
                        QueryEvent.getListModelEventsCamera(modelCamera.id, firstIdEvent, "<", AppConfig.getInstance().getEventLimit()),
                        new Dimension(AppConfig.getInstance().getLabelSize().width, AppConfig.getInstance().getLabelSize().height)),
                null);

        //добавить кнопку "раскрыть/скрыть CompreFace"
        //преобразуем картинки в BufferedImage, чтобы именно её передавать в нескольких местах для вращения
        BufferedImage bufferedImage = base64ToImage(SettingsDefault.getImageUnwrap());
        JButton roundButton = new RoundButton();
        addSidePanelBtn(roundButton, new ImageIcon(bufferedImage), parentButtonsPanel,
                e -> groupPanel.toggleViewModeAllEvents(roundButton, bufferedImage),
                null,
                true
        );

        groupPanel.add(parentButtonsPanel);
    }

    //создаём кнопку для группы событий
    void addSidePanelBtn(JButton button, Object stringOrImageIcon, JPanel parentPanel, ActionListener listener, OnActionListener onCreate, Boolean enabled) {
        JPanel childPanel = new JPanel();
        childPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        //если передали картинку
        if (stringOrImageIcon instanceof ImageIcon)
            button.setIcon(new ImageIcon(((ImageIcon) stringOrImageIcon).getImage().getScaledInstance((int)(getScale() * 30), (int)(getScale() * 30), java.awt.Image.SCALE_SMOOTH)));
        //если передали текст
        if (stringOrImageIcon instanceof String)
            button.setText((String) stringOrImageIcon);
        button.setPreferredSize(new Dimension((int)(getScale() * 40), (int)(getScale() * 40)));
        button.addActionListener(listener);
        childPanel.add(button);

        if(onCreate != null)
            onCreate.onAction();

        parentPanel.add(childPanel);

        button.setEnabled(enabled);
    }
    void addSidePanelBtn(JButton button, Object stringOrImageIcon, JPanel parentPanel, ActionListener listener, OnActionListener onCreate) {
        addSidePanelBtn(button, stringOrImageIcon, parentPanel, listener, onCreate, true);
    }

    //АРХИВ
    /*
    //кнопка просмотра прямого эфира камеры
    public JComponent createButtonLiveView(CamData cd) {
        byte[] byteImageBase64 = Base64.getDecoder().decode(SettingsDefault.getImageLiveView());
        ImageIcon imageIcon = new ImageIcon(byteImageBase64);
        //подгоним картинку под нужный размер
        JButton button = new JButton(new ImageIcon(imageIcon.getImage().getScaledInstance((int)(getScale() * 30), (int)(getScale() * 30), java.awt.Image.SCALE_SMOOTH)));
        button.setPreferredSize(new Dimension((int)(getScale() * 40), (int)(getScale() * 40)));

        //сделаем кнопку неактивной, пока не пройдёт проверка доступности прямого эфира
        button.setEnabled(false);

        //добавим многопоточность
//        (new Thread(()->{...})).start();
        (new Thread(()->{
            try {
                //проверяем доступность камеры в сети
                FFmpegFrameGrabber streamGrabber = new FFmpegFrameGrabber(cd.getConnectionUrl());
                //ожидание старта подключения, в микросекундах (1 сек)
                //если не подключились: получаем ошибку в лог и считаем, что камера не доступна
                streamGrabber.setOption("timeout" , "1000000");
                streamGrabber.start();
                //если в результате подключения мы можем получить видеопоток
                if (streamGrabber.hasVideo()) {
                    //устанавливаем зелёную рамку кнопке
                    button.setBorder(BorderFactory.createLineBorder(Color.GREEN, (int)(getScale() * 2)));
                    //кнопка кликабельна
                    button.setEnabled(true);
                } else
                    //устанавливаем красную рамку кнопке
                    button.setBorder(BorderFactory.createLineBorder(Color.RED, (int)(getScale() * 2)));
                streamGrabber.stop();
                streamGrabber.close();
            } catch (Exception ex) {
//                ex.printStackTrace();
                //устанавливаем красную рамку кнопке
                button.setBorder(BorderFactory.createLineBorder(Color.RED, (int)(getScale() * 2)));
            }
        })).start();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WindowCameraLiveView(cd);
            }
        });

        return button;
    }

    //кнопка просмотра всех событий камеры
    public JComponent createButtonAllImgEvents(int idCamera) {
        byte[] byteImageBase64 = Base64.getDecoder().decode(SettingsDefault.getImageAllImgEvents());
        ImageIcon imageIcon = new ImageIcon(byteImageBase64);
        //подгоним картинку под нужный размер
        JButton button = new JButton(new ImageIcon(imageIcon.getImage().getScaledInstance((int)(getScale() * 30), (int)(getScale() * 30), java.awt.Image.SCALE_SMOOTH)));
        button.setPreferredSize(new Dimension((int)(getScale() * 40), (int)(getScale() * 40)));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WindowAllEventsCamera(idCamera);
            }
        });

        return button;
    }

    //кнопка выбора плагинов камеры
    public JComponent createButtonOptions(int idCamera) {
        byte[] byteImageBase64 = Base64.getDecoder().decode(SettingsDefault.getImageOptions());
        ImageIcon imageIcon = new ImageIcon(byteImageBase64);
        //подгоним картинку под нужный размер
        JButton button = new JButton(new ImageIcon(imageIcon.getImage().getScaledInstance((int)(getScale() * 30), (int)(getScale() * 30), java.awt.Image.SCALE_SMOOTH)));
        button.setPreferredSize(new Dimension((int)(getScale() * 40), (int)(getScale() * 40)));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WindowSettingsCamera(idCamera);
            }
        });

        return button;
    }
    */
}

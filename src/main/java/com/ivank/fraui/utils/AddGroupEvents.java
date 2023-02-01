package com.ivank.fraui.utils;

import com.ivank.fraui.WindowAllEventsCamera;
import com.ivank.fraui.WindowCameraLiveView;
import com.ivank.fraui.WindowSettingsCamera;
import com.ivank.fraui.db.ModelCamera;
import com.ivank.fraui.db.ModelEvent;
import com.ivank.fraui.db.QueryEvent;
import com.ivank.fraui.db.QueryEventImages;
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

import static com.ivank.fraui.settings.AppConfig.getScale;
import static com.ivank.fraui.utils.UtilsAny.base64ToImage;
import static com.ivank.fraui.utils.UtilsAny.getCountCF;

//Отрисовываем группу Событий
public class AddGroupEvents extends JPanel {
    public ModelCamera modelCamera;
    public CamData cameraData;
    //смещение результата Событий
    public int offset = 0;
    //количество Событий в группе (задано дефолтное значение на случай ошибки чтения из файла настроек)
    public int limit = 1;

    //размер иконки События
    final Dimension labelSize = new Dimension(AppConfig.getInstance().getLabelSize().width, AppConfig.getInstance().getLabelSize().height);

    //список Событий в данной группе
    public ArrayList<AddEvent> listEvents = new ArrayList<>();

    //экземпляр класса
    public AddGroupEvents(ModelCamera modelCamera) {
        //задаём ориентацию расположения Событий (слева направо)
        super(new FlowLayout(FlowLayout.LEFT));
        this.modelCamera = modelCamera;
        cameraData = CamData.getCamDataFromId(modelCamera.id);
        limit = AppConfig.getInstance().getEventLimit();

        //создаём рамку группы Событий и пишем на ней имя камеры
        setBorder(BorderFactory.createTitledBorder("Камера \"" + modelCamera.camera_name + "\""));
    }

    //отрисовываем Событие в группе
    public void createLabelEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time, int countCompreFace) {
        AddEvent panel = new AddEvent(labelSize, color, image, event_id, time, countCompreFace);
        listEvents.add(panel);
        add(panel);
    }

    /*
    если хотя бы одна дополнительная область развёрнута, то сворачиваем все области и, наоборот,
    если хотя бы одна дополнительная область свёрнута, то разворачиваем все области
    */
    //"умная" функция разворачивания/сворачивания панели CompreFace
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

    //размечаем кнопки слева в группе Событий каждой камеры
    public void createButtonsForCamera(int idCamera) {
        JPanel parentButtonsPanel = new JPanel();
        parentButtonsPanel.setLayout(new BoxLayout (parentButtonsPanel, BoxLayout.Y_AXIS));
        //не работает (пытаюсь прижать блок с кнопками к верху панели)
//        parentButtonsPanel.setAlignmentY(TOP_ALIGNMENT);

        //добавить кнопку "прямой эфир"
        JButton button = new JButton();
        addSidePanelBtn(button, new ImageIcon(Base64.getDecoder().decode(SettingsDefault.getImageLiveView())), parentButtonsPanel,
                e -> new WindowCameraLiveView(cameraData),
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
                            FFmpegFrameGrabber streamGrabber = new FFmpegFrameGrabber(cameraData.getConnectionUrl());
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
        addSidePanelBtn(new BasicArrowButton(BasicArrowButton.WEST), null, paginatorButtonsPanel,
                e -> {
                    offset -= limit;
                    updateEventList();
                },
                null);
        addSidePanelBtn(new BasicArrowButton(BasicArrowButton.EAST), null, paginatorButtonsPanel,
                e -> {
                    offset += limit;
                    updateEventList();
                },
                null);

        //добавить кнопку "раскрыть/скрыть CompreFace"
        //преобразуем картинку в BufferedImage, чтобы именно её передавать в нескольких местах для вращения
        BufferedImage bufferedImage = base64ToImage(SettingsDefault.getImageUnwrap());
        JButton roundButton = new RoundButton();
        addSidePanelBtn(roundButton, new ImageIcon(bufferedImage), parentButtonsPanel,
                e -> this.toggleViewModeAllEvents(roundButton, bufferedImage),
                null,
                true
        );

        add(parentButtonsPanel);
    }

    //заполняем группу Событий
    public void updateEventList() {
        if (offset < 0)
            offset = 0;
        listEvents.clear();
        removeAll();

        //добавляем кнопки взаимодействия с камерой/группой Событий
        createButtonsForCamera(modelCamera.id);

        //список ограниченного количества моделей Событий
        ArrayList<ModelEvent> events = QueryEvent.fetchEvents(modelCamera.id, offset, limit);
        //список id Событий из списка моделей
        ArrayList<Integer> listIndexEventsId = new ArrayList<>();
        //список id Событий из списка моделей
        for (ModelEvent item : events) {
            listIndexEventsId.add(item.id);
        }
        //список из первых кадров Событий одной камеры
        ArrayList<ImageIcon> listEventFirstImages = QueryEventImages.getListEventFirstImages(listIndexEventsId);

        for (int indexEvents = 0; indexEvents < events.size(); indexEvents++) {
            //если в БД отсутствуют кадры События, то не отрисовываем это Событие
            if (listEventFirstImages.isEmpty())
                continue;
            createLabelEvent(
                    labelSize,
                    CalculationEventColor.eventColor(events.get(indexEvents).plugin_id),
                    listEventFirstImages.get(indexEvents), //получаем кадр из списка первых кадров, полученных "большим" SQL запросом
                    events.get(indexEvents).id,
                    events.get(indexEvents).time,
                    getCountCF(events.get(indexEvents).data)
            );
        }
    }

    //создаём кнопку для группы Событий
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

        if (onCreate != null)
            onCreate.onAction();

        parentPanel.add(childPanel);

        button.setEnabled(enabled);
    }
    void addSidePanelBtn(JButton button, Object stringOrImageIcon, JPanel parentPanel, ActionListener listener, OnActionListener onCreate) {
        addSidePanelBtn(button, stringOrImageIcon, parentPanel, listener, onCreate, true);
    }
}

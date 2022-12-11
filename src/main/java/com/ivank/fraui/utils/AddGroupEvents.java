package com.ivank.fraui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.ivank.fraui.settings.AppConfig.getScale;

//Отрисовываем группу Событий
public class AddGroupEvents extends JPanel {
    //список Событий в данной группе
    public ArrayList<AddEventElement> listEvents = new ArrayList<>();

    //экземпляр класса
    public AddGroupEvents() {
        //задаём ориентацию расположения Событий (слева направо)
        super(new FlowLayout(FlowLayout.LEFT));
    }

    //отрисовываем Событие в группе
    public void createLabelEvent(Dimension labelSize, Color color, ImageIcon image, int event_id, String time) {
        AddEventElement panel = new AddEventElement(labelSize, color, image, event_id, time);
        listEvents.add(panel);
        add(panel);
    }

    //"умная" функция разворачивания/сворачивания дополнительной области (для CompreFace) События
    /*
    если хотя бы одна дополнительная область развёрнута, то сворачиваем все области и, наоборот,
    если хотя бы одна дополнительная область свёрнута, то разворачиваем все области
    */
    public void toggleViewModeAllEvents(JButton button, BufferedImage bufferedImage) {
        boolean hasCollapsed = false;

        for (AddEventElement event : listEvents) {
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

    //сворачиваем дополнительную область у всех Событий
    public void collapseAllEvents(JButton button, BufferedImage bufferedImage) {
        for (AddEventElement event: listEvents) {
            //сворачиваем элемент под каждой миниатюрой
            event.collapse();
        }

        //разворачиваем направление стрелки на картинке
        button.setIcon(new ImageIcon(bufferedImage.getScaledInstance((int)(getScale() * 30), (int)(getScale() * 30), java.awt.Image.SCALE_SMOOTH)));
    }

    //разворачиваем дополнительную область у всех Событий
    public void expandAllEvents(JButton button, BufferedImage bufferedImage) {
        for (AddEventElement event: listEvents) {
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
}

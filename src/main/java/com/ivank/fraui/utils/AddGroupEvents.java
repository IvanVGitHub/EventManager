package com.ivank.fraui.utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    public void toggleViewModeAllEvents() {
        boolean hasCollapsed = false;

        for (AddEventElement event : listEvents) {
            if (event.collapsed) {
                hasCollapsed = true;
                break;
            }
        }

        if (hasCollapsed)
            expandAllEvents();
        else
            collapseAllEvents();
    }

    //сворачиваем дополнительную область у всех Событий
    public void collapseAllEvents() {
        for (AddEventElement event: listEvents) {
            event.collapse();
        }
    }

    //разворачиваем дополнительную область у всех Событий
    public void expandAllEvents() {
        for (AddEventElement event: listEvents) {
            event.expand();
        }
    }
}

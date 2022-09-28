package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;
import com.ivank.fraui.components.Content;
import com.ivank.fraui.settings.SettingsDefault;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Base64;

public class QueryEventsCamera {
    private static ArrayList<ImageIcon> listImageIcon = new ArrayList<>();
    private static ResultSet<ModelEvent> result = new ResultSet<>();
    private static ArrayList<String> listEventPicture = new ArrayList<>();



    public static ResultSet<ModelEvent> eventColor(int i) {
        result.clear();

        try {
            //query to MYSQL
            ModelCamera camera = QueryCameras.getListMdlCameras().get(i);
            QueryBuilder<ModelEvent> query = ConnectDB.getConnector().query(ModelEvent.class);
            query.where(
                    "camera_id",
                    DBWhereOp.EQ,
                    camera.id
            ).limit(Content.getLimitEvent());//последние n событий, отсортированных false: от свежих к старым; true: от старых к свежим

            //result to SQL query for test
            String sql = query.toSql();

            result = query.get();
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();
        }

        return result;
    }

    public static ArrayList<String> addEventPictureBase64(int i) {
        listEventPicture.clear();

        try {
            //query to MYSQL
            ModelCamera camera = QueryCameras.getListMdlCameras().get(i);
            QueryBuilder<ModelEvent> query = ConnectDB.getConnector().query(ModelEvent.class);
            query.where(
                    "camera_id",
                    DBWhereOp.EQ,
                    camera.id
            ).limit(Content.getLimitEvent());//последние n событий, отсортированных false: от свежих к старым; true: от старых к свежим

            //result to SQL query for test
            String sql = query.toSql();

            ResultSet<ModelEvent> result = query.get();

            for (ModelEvent event : result) {
                listEventPicture.add(event.photo);
            }
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();
        }

        return listEventPicture;
    }

    public static ArrayList<ImageIcon> getListImageIcon(int i) {
        listImageIcon.clear();

        try {
            //image to icon event
            ArrayList<String> listStringBase64 = addEventPictureBase64(i);

            //проверяем, если событий нет, то добавим одно стандартное событие
            if (listStringBase64.size() == 0) {
                //image to icon empty event
                String strImgDflt = SettingsDefault.getImageDefault();
                listStringBase64.add(strImgDflt);
            }

            for (int a = 0; a < listStringBase64.size(); a++) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(listStringBase64.get(a));
                listImageIcon.add(new ImageIcon(byteImageBase64));
            }
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();
        }

        return listImageIcon;
    }
}

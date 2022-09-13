package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;
import com.ivank.fraui.components.Content;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Base64;

public class QueryEventPicture {
    private static ArrayList<String> listEventPicture = new ArrayList<>();
    public static ArrayList<String> addEventPictureBase64(int i) {
        try {
            //query to MYSQL
            ModelCamera camera = QueryCameras.getListNamesCameras().get(i);
            QueryBuilder<ModelEvent> query = ConnectDB.getConnector().query(ModelEvent.class);
            query.where(
                    "camera_id",
                    DBWhereOp.EQ,
                    camera.id
            ).limit(Content.getLimitEvent());//последние n событий, отсортированных false: от свежих к старым; true: от старых к свежим

            //result to SQL query for test
            String sql = query.toSql();

            ResultSet<ModelEvent> result = query.get();

            listEventPicture.clear();
            for (ModelEvent event : result) {
                listEventPicture.add(event.photo);
            }

            return listEventPicture;
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();

            //image to icon empty event
            String strImgDflt = Settings.getImageDefault();
            listEventPicture.add(strImgDflt);

            return listEventPicture;
        }
    }

    public static ArrayList<ImageIcon> imageIcon(int i) {
        try {
            ArrayList<ImageIcon> listImageIcon = new ArrayList<>();

            //image to icon event
            ArrayList<String> listStringBase64 = addEventPictureBase64(i);

            for (int a = 0; a < listStringBase64.size(); a++) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(listStringBase64.get(a));
                listImageIcon.add(new ImageIcon(byteImageBase64));
            }

            return listImageIcon;
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();

            return null;
        }
    }
}

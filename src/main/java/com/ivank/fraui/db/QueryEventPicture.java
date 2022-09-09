package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
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
            ).limit(30);//последние 30 событий, отсортированных false: от свежих к старым; true: от старых к свежим

            String sql = query.toSql();

            ResultSet<ModelEvent> result = query.get();

            for (ModelEvent event : result) {
                listEventPicture.add(event.photo);
            }

            return listEventPicture;
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();

            //image to icon empty event
            String strImgDflt = Settings.getDefaultImage();
            listEventPicture.add(strImgDflt);
            return null;
        }
    }

    public static ArrayList<ImageIcon> imageIcon(int i) {
        ArrayList<ImageIcon> imageIcon = new ArrayList<>();

        //image to icon event
        ArrayList<String> base64string = new ArrayList<>();
        base64string = addEventPictureBase64(i);
        String str = base64string.get(0);

        for (int a = 0; a < base64string.size(); a++) {
            byte[] byteImageBase64 = Base64.getDecoder().decode(base64string.get(a));
            imageIcon.add(new ImageIcon(byteImageBase64));
        }
        return imageIcon;
    }
}

package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.awt.*;
import java.util.ArrayList;

public class QueryEventColor {
    public static Color addEventColor(int i) {
        try {
            //query to MYSQL
            QueryBuilder<ModelEvent> query = ConnectDB.getConnector().query(ModelEvent.class);
            ResultSet<ModelEvent> result = query.get();

            switch (result.get(i).color) {
                case  ("black"):
                    return Color.BLACK;
                case  ("yellow"):
                    return Color.YELLOW;
                case  ("green"):
                    return Color.GREEN;
                case  ("red"):
                    return Color.RED;
                default:
                    return Color.BLACK;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            return Color.BLACK;
        }

    }
}

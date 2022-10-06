package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryNEWEvent {
    //список моделей конкретной камеры в таблице event
    public static ArrayList<ModelNEWEvent> getModelEventsCamera(int idCamera, int value) {
        try {
            QueryBuilder<ModelNEWEvent> query = ConnectDB.getConnector().query(ModelNEWEvent.class)
                    .where("camera_id", idCamera)
                    .orderBy(false, "time")
                    .limit(value);

            return query.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

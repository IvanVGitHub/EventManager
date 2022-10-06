package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

import java.util.ArrayList;

public class QueryEvent {
    //список моделей конкретной камеры в таблице event
    public static ArrayList<ModelEvent> getModelEventsCamera(int idCamera, int value) {
        try {
            QueryBuilder<ModelEvent> query = ConnectDB.getConnector().query(ModelEvent.class)
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

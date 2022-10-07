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

    //последний добавленный id события
    public static int lastAddIdEvent() {
        try {
            ModelEvent result = ConnectDB.getConnector().query(ModelEvent.class)
                    .orderBy(false, "id")
                    .first();

            return result.id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}

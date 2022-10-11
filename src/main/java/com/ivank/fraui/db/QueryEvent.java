package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryEvent {
    //список записей времени создания события
    private static ArrayList<String> listTimeStampEvents = new ArrayList<>();

    //список моделей конкретной камеры в таблице event
    public static ArrayList<ModelEvent> getListModelEventsCamera(int camera_id, int value) {
        try {
            QueryBuilder<ModelEvent> query = ConnectDB.getConnector().query(ModelEvent.class)
                    .where("camera_id", camera_id)
                    .orderBy(false, "time")
                    .limit(value);

            return query.get();
        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    //список записей времени создания события
    public static ArrayList<String> getListTimeStampEvents(int camera_id) {
        listTimeStampEvents.clear();

        try {
            QueryBuilder<ModelEvent> query = ConnectDB.getConnector().query(ModelEvent.class)
                    .where("camera_id", camera_id)
                    .orderBy(false, "time");
            ResultSet<ModelEvent> result = query.get();

            for (ModelEvent unit : result)
            {
                listTimeStampEvents.add(unit.time);
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listTimeStampEvents;
    }

    //последний добавленный id события
    public static int getLastAddIdEvent() {
        try {
            ModelEvent result = ConnectDB.getConnector().query(ModelEvent.class)
                    .orderBy(false, "id")
                    .first();

            return result.id;
        } catch (Exception ex) {ex.printStackTrace();}

        return 0;
    }
}

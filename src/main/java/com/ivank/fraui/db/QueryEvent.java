package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryEvent {
    //список записей времени создания события
    private static ArrayList<String> listTimeStampEvents = new ArrayList<>();

    //список моделей событий, имеющих image, конкретной камеры
    public static ArrayList<ModelEvent> getListModelEventsCamera(int camera_id, int value) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM event ")
                    .append("WHERE EXISTS (")
                    .append("SELECT event_id ")
                    .append("FROM eventImages ")
                    .append("WHERE event_id = event.id) ")
                    .append("AND camera_id = ").append(camera_id).append(" ")
                    .append("ORDER BY id DESC ")
                    .append("LIMIT ").append(value).append(";");
            String stringSql = sb.toString();
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSql);

            ArrayList<ModelEvent> events = new ArrayList<>();
            while (result.next()) {
                ModelEvent ev = new ModelEvent();
                ev.id = result.getInt("id");
                ev.uuid = result.getString("uuid");
                ev.camera_id = result.getInt("camera_id");
                ev.plugin_id = result.getString("plugin_id");
                ev.data = result.getString("data");
                ev.time = result.getString("time");
                events.add(ev);
            }

            return events;
        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    //список дат событий, имеющих image
    public static ArrayList<String> getListTimeStampEventsSQL(int camera_id) {
        listTimeStampEvents.clear();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT time ")
                    .append("FROM event ")
                    .append("WHERE EXISTS (")
                    .append("SELECT event_id FROM eventImages ")
                    .append("WHERE event_id = event.id) ")
                    .append("AND camera_id = ").append(camera_id).append(" ")
                    .append("ORDER BY id DESC;");
            String stringSql = String.valueOf(sb);
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSql);

            while (result.next()) {
                listTimeStampEvents.add(result.getString("time"));
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

package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryEvent {
    //список записей времени создания события
    private static ArrayList<String> listTimeStampEvents = new ArrayList<>();

    //список моделей событий конкретной камеры, имеющих image
    public static ArrayList<ModelEvent> getListModelEventsCamera(int camera_id, int limit) {
        try {
            String stringSQL = "SELECT * FROM event " +
                    "WHERE EXISTS (" +
                    "SELECT event_id FROM eventImages " +
                    "WHERE event_id = event.id) " +
                    "AND camera_id = " + camera_id + " " +
                    "ORDER BY id DESC " +
                    "LIMIT " + limit + ";";
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

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

    public static ArrayList<ModelEvent> getListModelEventsCamera(int camera_id) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM event ")
                    .append("WHERE EXISTS (")
                    .append("SELECT event_id FROM eventImages ")
                    .append("WHERE event_id = event.id) ")
                    .append("AND camera_id = ").append(camera_id).append(" ")
                    .append("ORDER BY id DESC;");
            String stringSQL = sb.toString();
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

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
    public static ArrayList<String> getListTimeStampEvents(int camera_id) {
        listTimeStampEvents.clear();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT time FROM event ")
                    .append("WHERE EXISTS (")
                    .append("SELECT event_id FROM eventImages ")
                    .append("WHERE event_id = event.id) ")
                    .append("AND camera_id = ").append(camera_id).append(" ")
                    .append("ORDER BY id DESC;");
            String stringSQL = String.valueOf(sb);
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                listTimeStampEvents.add(result.getString("time"));
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listTimeStampEvents;
    }

    //последний добавленный в БД id события
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

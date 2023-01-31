package com.ivank.fraui.db;

import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class QueryEvent {
    //список записей времени создания события
    private static ArrayList<String> listTimeStampEvents = new ArrayList<>();

    //получить список моделей событий конкретной камеры, имеющих image, ограниченного диапазона и количества
    public static ArrayList<ModelEvent> getListModelEventsCamera(int camera_id, int edgeIdEvent, String moreOrLess, int limit) {
        try {
            String stringSQL = "SELECT * FROM event " +
                    "WHERE EXISTS " +
                    "(SELECT event_id FROM eventImages WHERE event_id = event.id) " +
                    "AND camera_id = " + camera_id + " " +
                    (edgeIdEvent != 0 ? "AND id " + (moreOrLess == "" ? ">" : moreOrLess) + " " + edgeIdEvent + " " : "") +
                    "ORDER BY id " + (moreOrLess == ">" ? "" : "DESC") + (limit > 0 ? " LIMIT " + limit + ";" : ";");

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

            if (moreOrLess == ">")
                Collections.reverse(events);
            return events;
        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    public static ArrayList<ModelEvent> getListModelEventsCamera(int camera_id, int limit) {
/*        try {
            String stringSQL = "SELECT * FROM event " +
                    "WHERE EXISTS (" +
                    "SELECT event_id FROM eventImages " +
                    "WHERE event_id = event.id) " +
                    "AND camera_id = " + camera_id + " " +
                    "ORDER BY id DESC" + (limit > 0 ? " LIMIT " + limit + ";" : ";");
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

        return null;*/
        return getListModelEventsCamera(camera_id, 0, "", limit);
    }
    public static ArrayList<ModelEvent> getListModelEventsCamera(int camera_id) {
/*        try {
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

        return null;*/
        return getListModelEventsCamera(camera_id, 0);
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

    //количество событий в сессии камеры
    public static int getCountEventsSessionCamera(UUID uuidSession) {
        try {
            int count = 0;
            String stringSQL = "SELECT COUNT(*) AS _count FROM event " +
                    "WHERE uuid_session = '" + uuidSession + "'";
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                count = result.getInt("_count");
            }

            return count;
        } catch (Exception ex) {ex.printStackTrace();}

        return -5;
    }

    //количество событий в сессии камеры в час
    public static int getCountEventsEveryHourSessionCamera(UUID uuidSession) {
        /*try {
            int count = 0;
            String stringSQL = "SELECT DATE_FORMAT(time,'%Y-%m-%d %H:00:00') AS interval_start, " +
                    "DATE_FORMAT(DATE_ADD(time, INTERVAL 1 HOUR),'%Y-%m-%d %H:00:00') AS interval_end, " +
                    "count(*) AS _count " +
                    "FROM event " +
                    "WHERE uuid_session = '" + uuidSession + "' " +
                    "GROUP BY interval_start " +
                    "ORDER BY id";
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                count = result.getInt("_count");
            }

            return count;
        } catch (Exception ex) {ex.printStackTrace();}*/

        return -5;
    }
}

package com.ivank.fraui.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class QueryEvent {
    //список записей времени создания События
    private static ArrayList<String> listTimeStampEvents = new ArrayList<>();

    //получить список моделей Событий конкретной камеры, имеющих image, ограниченного диапазона и количества
    public static ArrayList<ModelEvent> fetchEvents(int camera_id, int offset, int limit) {
        return fetchEvents(camera_id, offset, limit, false);
    }
    public static ArrayList<ModelEvent> fetchEvents(int camera_id, int offset, int limit, boolean asc) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM event WHERE EXISTS (SELECT event_id FROM eventImages WHERE event_id = event.id) ")
                    .append(" AND camera_id=").append(camera_id)
                    .append(" ORDER BY id ").append(asc ? "" : "DESC")
                    .append(limit > 0 ? " LIMIT " + limit : "")
                    .append(offset > 0 ? " OFFSET " + offset : "")
                    .append(";");

            ResultSet result = ConnectDB.getConnector().executeRaw(sb.toString());

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

    //список дат Событий, имеющих image
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

    //последний добавленный в БД id События
    public static int getLastAddIdEvent() {
        try {
            ModelEvent result = ConnectDB.getConnector().query(ModelEvent.class)
                    .orderBy(false, "id")
                    .first();

            return result.id;
        } catch (Exception ex) {ex.printStackTrace();}

        return 0;
    }

    //количество Событий в сессии камеры
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

    //количество Событий в сессии камеры в час
    public static ArrayList<Integer> getCountEventsEveryHourSessionCamera(UUID uuidSession) {
        try {
            ArrayList<Integer> count = new ArrayList<>();
            String stringSQL = "SELECT DATE_FORMAT(time,'%Y-%m-%d %H:00:00') AS interval_start, " +
                    "DATE_FORMAT(DATE_ADD(time, INTERVAL 1 HOUR),'%Y-%m-%d %H:00:00') AS interval_end, " +
                    "count(*) AS _count " +
                    "FROM event " +
                    "WHERE uuid_session = '" + uuidSession + "' " +
                    "GROUP BY interval_start " +
                    "ORDER BY id";
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                count.add(result.getInt("_count"));
            }

            return count;
        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }
}

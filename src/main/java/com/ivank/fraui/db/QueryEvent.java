package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    //список записей дат создания событий (только событий, имеющих запись в графе image) на чистом SQL
    public static ArrayList<String> getListTimeStampEvents(int camera_id) {
        java.sql.ResultSet resultSet = null;
        listTimeStampEvents.clear();

        try (Statement statement = ConnectDB.getConnectorClearSQL().createStatement()) {
            // Create and execute a SELECT SQL statement.
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT time FROM event WHERE EXISTS(SELECT * FROM eventImages WHERE event.id = eventImages.event_id AND image IS NOT NULL) AND camera_id = ").append(camera_id).append(" ORDER BY time DESC;");
            String stringSql = String.valueOf(sb);
            resultSet = statement.executeQuery(stringSql);
            //обязательный цикл, чтобы получить результаты из запроса и присвоить их переменным
            while (resultSet.next()) {
                listTimeStampEvents.add(resultSet.getString("time"));
            }

            return listTimeStampEvents;
        } catch (SQLException ex) {ex.printStackTrace();}

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

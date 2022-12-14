package com.ivank.fraui.db;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

public class QueryCameraStatus {
    //получаем список времён событий сессии
    public static ArrayList<Timestamp> getListTimesSession(UUID uuid_session) {
        ArrayList<Timestamp> listTime = new ArrayList<>();

        try {
            String stringSQL = "SELECT time FROM cameraStatus WHERE uuid_session = '" + uuid_session + "'";

            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                listTime.add(result.getTimestamp("time"));
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listTime;
    }

    //список сессий (уникальных uuid_session) конкретной камеры
    public static ArrayList<UUID> getListSessionsCamera(int idCamera) {
        ArrayList<UUID> listUUID = new ArrayList<>();

        try {
            String stringSQL = "SELECT DISTINCT uuid_session FROM cameraStatus WHERE camera_id = " + idCamera;

            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                listUUID.add(UUID.fromString(result.getString("uuid_session")));
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listUUID;
    }
}

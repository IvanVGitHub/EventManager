package com.ivank.fraui.db;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

public class QueryCameraStatus {
    //получаем список времён событий сессии
    public static ArrayList<Timestamp> getListTimesSession(String uuid_session) {
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
}

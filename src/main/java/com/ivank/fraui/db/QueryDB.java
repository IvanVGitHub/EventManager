package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;

import java.awt.*;

public class QueryDB {
    public static void testDB(TrayIcon trayIcon) {
        if(trayIcon == null)
            return;
        try {
//            //query to MYSQL
//            QueryBuilder<ModelTest001> query = ConnectDB.getConnector().query(ModelTest001.class);
//            query.where(
//                    "name",
//                    DBWhereOp.LIKE,
//                    "%i%"
//            );
//            ResultSet<ModelTest001> result = query.get();
//
//            //create string from query result
//            StringBuilder sb = new StringBuilder();
//            for (ModelTest001 t : result) {
//                sb.append(t.name).append(", ");
//            }
//            trayIcon.displayMessage(
//                    "DB Connection successful!",
//                    "Есть контакт: " + sb,
//                    TrayIcon.MessageType.INFO
//            );
            trayIcon.displayMessage(
            "DB Connection successful!",
            "Есть контакт!",
            TrayIcon.MessageType.INFO
            );
        } catch (Exception ex) {
            trayIcon.displayMessage(
                    "DB Connection failed...",
                    "Не контачит...",
                    TrayIcon.MessageType.ERROR
            );
        }
    }
}

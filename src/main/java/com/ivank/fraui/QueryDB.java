package com.ivank.fraui;

import com.bedivierre.eloquent.DB;
import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.bedivierre.eloquent.expr.DBWhereOp;

import java.awt.*;


public class QueryDB {
    static DB connector;
    static void testDB(TrayIcon trayIcon) {
        if(trayIcon == null)
            return;
        try {
            connector = new DB("172.20.3.221", "test", "ivanUser", "Qwerty!@#456");

//            TestModel v = connector.find(1, TestModel.class);

            //query to MYSQL
            QueryBuilder<TestModel> query = connector.query(TestModel.class);
            query.where("name", DBWhereOp.LIKE, "%i%");
            ResultSet<TestModel> result = query.get();

            //create string from query result
            StringBuilder sb = new StringBuilder();
            for (TestModel t :
                    result) {
                sb.append(t.name).append(", ");
            }
//            TestModel m = connector.query(TestModel.class).first();
            trayIcon.displayMessage("DB Connection successful!",
//                    "Есть контакт: " + sb.toString(),
                    "Есть контакт: " + sb,
                    TrayIcon.MessageType.INFO);
        } catch (Exception ex) {
            trayIcon.displayMessage("DB Connection failed...",
                    "Не контачит...",
                    TrayIcon.MessageType.ERROR);
        }
    }
}

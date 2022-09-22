package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryPlugins {
    private static ArrayList<String> listPlaginId = new ArrayList<>();

    public static ArrayList<String> getListPlaginId() {
        listPlaginId.clear();

        try {
            //query to MYSQL
            QueryBuilder<ModelPlugins> query = ConnectDB.getConnector().query(ModelPlugins.class);
            ResultSet<ModelPlugins> result = query.get();
            for (ModelPlugins event : result) {
                listPlaginId.add(String.valueOf(event.id));
            }
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();
        }

        return listPlaginId;
    }
}

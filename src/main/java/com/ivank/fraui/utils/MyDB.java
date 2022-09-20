package com.ivank.fraui.utils;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.expr.DBWhereOp;
import com.ivank.fraui.db.ConnectDB;
import com.ivank.fraui.db.ModelCamera;

public class MyDB {
    public static QueryBuilder<ModelCamera> cameraQuery() {
        return ConnectDB.getConnector().query(ModelCamera.class);
    }

    public static QueryBuilder<ModelCamera> cameraQuery(String column, Object value) {
        return cameraQuery().where(column, value);
    }

    public static QueryBuilder<ModelCamera> cameraQuery(String column, DBWhereOp op, Object value) {
        return cameraQuery().where(column, op, value);
    }
}

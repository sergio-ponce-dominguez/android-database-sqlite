package com.sepodo.software.plugins.android.database.sqlite;

import com.getcapacitor.Logger;

public class AndroidDatabaseSqlite {

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }
}

package com.sepodo.software.plugins.android.database.sqlite;

import android.content.Context;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;


@CapacitorPlugin(name = "AndroidDatabaseSqlite")
public class AndroidDatabaseSqlitePlugin extends Plugin {

    private AndroidDatabaseSqlite implementation;

    /**
     * Load Method
     * Load the context
     */
    public void load() {
        Context context = getContext();
        implementation = new AndroidDatabaseSqlite(context);
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod()
    public void openOrCreateDatabase(PluginCall call) {
        String name = call.getString("name", "app.db");
        try {
            implementation.openOrCreateDatabase(name);
        } catch (Exception ex) {
            call.reject("Failed to open database: " + ex.getMessage(), ex);
            return;
        }
        JSObject res = new JSObject();
        res.put("name", name);
        call.resolve(res);
    }

    @PluginMethod
    public void isOpen(PluginCall call) {
        String name = call.getString("name", "app.db");

        JSObject ret = new JSObject();
        ret.put("value", implementation.isOpen(name));
        call.resolve(ret);
    }

    @PluginMethod()
    public void close(PluginCall call) {
        String name = call.getString("name", "app.db");
        String error = null;
        try {
            error = implementation.close(name);
        } catch (Exception ex) {
            call.reject("Failed to close database: " + ex.getMessage(), ex);
            return;
        }
        if (error == null) {
            call.resolve();
        } else {
            call.reject(error);
        }
    }

    @PluginMethod()
    public void execSQL(PluginCall call) {
        String name = call.getString("name", "app.db");
        String sql = call.getString("sql");
        JSArray bindArgs = call.getArray("bindArgs");
        if (sql == null) {
            call.reject("Missing 'sql' parameter");
            return;
        }

        String error = null;
        try {
            error = implementation.execSQL(name, sql, bindArgs);
        } catch (Exception ex) {
            call.reject("execSQL failed: " + ex.getMessage(), ex);
            return;
        }
        if (error == null) {
            call.resolve();
        } else {
            call.reject(error);
        }
    }

    @PluginMethod()
    public void rawQuery(PluginCall call) {
        String name = call.getString("name", "app.db");
        String sql = call.getString("sql");
        JSArray selectionArgs = call.getArray("selectionArgs");
        if (sql == null) {
            call.reject("Missing 'sql' parameter");
            return;
        }

        ArrayResult result = null;
        try {
            result = implementation.rawQuery(name, sql, selectionArgs);
        } catch (Exception ex) {
            call.reject("rawQuery failed: " + ex.getMessage(), ex);
            return;
        }
        if (result.error == null) {
            JSObject res = new JSObject();
            res.put("rows", result.value);
            call.resolve(res);
        } else {
            call.reject(result.error);
        }
    }

    @PluginMethod()
    public void insert(PluginCall call) {
        String name = call.getString("name", "app.db");
        String table = call.getString("table");
        JSObject values = call.getObject("values");
        if (table == null || values == null) {
            call.reject("Missing 'table' or 'values' parameter");
            return;
        }

        LongResult result = null;
        try {
            result = implementation.insert(name, table, values);
        } catch (Exception ex) {
            call.reject("rawQuery failed: " + ex.getMessage(), ex);
            return;
        }
        if (result.error == null) {
            JSObject res = new JSObject();
            res.put("id", result.value);
            call.resolve(res);
        } else {
            call.reject(result.error);
        }
    }

    @PluginMethod()
    public void getLastInsertRowId(PluginCall call) {
        String name = call.getString("name", "app.db");

        LongResult result = null;
        try {
            result = implementation.getLastInsertRowId(name);
        } catch (Exception ex) {
            call.reject("getLastInsertRowId failed: " + ex.getMessage(), ex);
            return;
        }
        if (result.error == null) {
            JSObject res = new JSObject();
            res.put("rowId", result.value);
            call.resolve(res);
        } else {
            call.reject(result.error);
        }
    }

    @PluginMethod()
    public void update(PluginCall call) {
        String name = call.getString("name", "app.db");
        String table = call.getString("table");
        JSObject values = call.getObject("values");
        String whereClause = call.getString("whereClause");
        JSArray whereArgs = call.getArray("whereArgs");
        if (table == null || values == null) {
            call.reject("Missing 'table' or 'values' parameter");
            return;
        }

        LongResult result = null;
        try {
            result = implementation.update(name, table, values, whereClause, whereArgs);
        } catch (Exception ex) {
            call.reject("update failed: " + ex.getMessage(), ex);
            return;
        }
        if (result.error == null) {
            JSObject res = new JSObject();
            res.put("rowsAffected", result.value);
            call.resolve(res);
        } else {
            call.reject(result.error);
        }
    }

    @PluginMethod()
    public void delete(PluginCall call) {
        String name = call.getString("name", "app.db");
        String table = call.getString("table");
        String whereClause = call.getString("whereClause");
        JSArray whereArgs = call.getArray("whereArgs");
        if (table == null) {
            call.reject("Missing 'table' parameter");
            return;
        }

        LongResult result = null;
        try {
            result = implementation.delete(name, table, whereClause, whereArgs);
        } catch (Exception ex) {
            call.reject("delete failed: " + ex.getMessage(), ex);
            return;
        }
        if (result.error == null) {
            JSObject res = new JSObject();
            res.put("rowsAffected", result.value);
            call.resolve(res);
        } else {
            call.reject(result.error);
        }
    }

    @PluginMethod()
    public void beginTransaction(PluginCall call) {
        String name = call.getString("name", "app.db");

        String error = null;
        try {
            error = implementation.beginTransaction(name);
        } catch (Exception ex) {
            call.reject("beginTransaction failed: " + ex.getMessage(), ex);
            return;
        }
        if (error == null) {
            call.resolve();
        } else {
            call.reject(error);
        }
    }

    @PluginMethod()
    public void setTransactionSuccessful(PluginCall call) {
        String name = call.getString("name", "app.db");

        String error = null;
        try {
            error = implementation.setTransactionSuccessful(name);
        } catch (Exception ex) {
            call.reject("setTransactionSuccessful failed: " + ex.getMessage(), ex);
            return;
        }
        if (error == null) {
            call.resolve();
        } else {
            call.reject(error);
        }
    }

    @PluginMethod()
    public void endTransaction(PluginCall call) {
        String name = call.getString("name", "app.db");

        String error = null;
        try {
            error = implementation.endTransaction(name);
        } catch (Exception ex) {
            call.reject("endTransaction failed: " + ex.getMessage(), ex);
            return;
        }
        if (error == null) {
            call.resolve();
        } else {
            call.reject(error);
        }
    }

    @PluginMethod()
    public void getVersion(PluginCall call) {
        String name = call.getString("name", "app.db");

        LongResult result = null;
        try {
            result = implementation.getVersion(name);
        } catch (Exception ex) {
            call.reject("getVersion failed: " + ex.getMessage(), ex);
            return;
        }
        if (result.error == null) {
            JSObject res = new JSObject();
            res.put("version", result.value);
            call.resolve(res);
        } else {
            call.reject(result.error);
        }
    }

    @PluginMethod()
    public void setVersion(PluginCall call) {
        String name = call.getString("name", "app.db");
        Integer version = call.getInt("version");
        if (version == null) {
            call.reject("Missing 'version' parameter");
            return;
        }

        String error = null;
        try {
            error = implementation.setVersion(name, version);
        } catch (Exception ex) {
            call.reject("setVersion failed: " + ex.getMessage(), ex);
            return;
        }
        if (error == null) {
            call.resolve();
        } else {
            call.reject(error);
        }
    }
}

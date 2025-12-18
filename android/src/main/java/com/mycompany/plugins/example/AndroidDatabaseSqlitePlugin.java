package com.sepodo.software.plugins.android.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.getcapacitor.Logger;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CapacitorPlugin(name = "AndroidDatabaseSqlite")
public class AndroidDatabaseSqlitePlugin extends Plugin {

    private AndroidDatabaseSqlite implementation = new AndroidDatabaseSqlite();

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
        JSArray args = call.getArray("args");
        if (sql == null) {
            call.reject("Missing 'sql' parameter");
            return;
        }

        String error = null;
        try {
            error = implementation.execSQL(name, sql, args);
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
        JSArray args = call.getArray("args");
        if (sql == null) {
            call.reject("Missing 'sql' parameter");
            return;
        }

        ArrayResult result = null;
        try {
            result = implementation.execSQL(name, sql, args);
        } catch (Exception ex) {
            call.reject("rawQuery failed: " + ex.getMessage(), ex);
            return;
        }
        if (result.error == null) {
            JSObject res = new JSObject();
            res.put("values", result.value);
            call.resolve(res);
        } else {
            call.reject(result.error);
        }
    }
}

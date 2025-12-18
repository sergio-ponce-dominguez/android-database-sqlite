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

public class AndroidDatabaseSqlite {
   
    private final Map<String, SQLiteDatabase> databases = Collections.synchronizedMap(new HashMap<>());

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }

    public void openOrCreateDatabase(String name) {
        SQLiteDatabase db = databases.get(name);
        if (db == null) {
            Context ctx = getContext();
            db = ctx.openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
            databases.put(name, db);
        } else if (!db.isOpen()) {
            db.open();
        }        
    }

    public Boolean isOpen(String name) {
        SQLiteDatabase db = databases.get(name);
        return db == null || db.isOpen();
    }

    public String close(String name) {
        SQLiteDatabase db = databases.get(name);
        if (db == null) {
            return "Database not open: " + name;
        } else if (!db.isOpen()) {
            return "Database already closed: " + name;
        }
        db.close();
        return null;
    }

    public String execSQL(String name, String sql, JSArray args) {
        SQLiteDatabase db = databases.get(name);
        if (db == null) {
            return "Database not open: " + name;
        } else if (!db.isOpen()) {
            return "Database already closed: " + name;
        }
        if (args != null && args.length() > 0) {
            Object[] bindArgs = jsArrayToObjectArray(args);
            db.execSQL(sql, bindArgs);
        } else {
            db.execSQL(sql);
        }
        return null;
    }

    public ArrayResult rawQuery(String name, String sql, JSArray args) throws Exception {
        SQLiteDatabase db = databases.get(name);
        ArrayResult result = new ArrayResult();
        if (db == null) {
            result.error = "Database not open: " + name;
            return result;
        } else if (!db.isOpen()) {
            result.error = "Database already closed: " + name;
            return result;
        }
        Cursor cursor = null;
        try {
            String[] selectionArgs = jsArrayToStringArray(args);
            cursor = db.rawQuery(sql, selectionArgs);
            result.value = cursorToJsArray(cursor);
            cursor.close();
            return result;
        } catch (Exception ex) {
            if (cursor != null) {
                cursor.close();
            }
            throw ex;
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
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            ContentValues cv = jsObjectToContentValues(values);
            long id = db.insertOrThrow(table, null, cv);
            JSObject res = new JSObject();
            res.put("id", id);
            call.resolve(res);
        } catch (Exception ex) {
            call.reject("insert failed: " + ex.getMessage(), ex);
        }
    }

    @PluginMethod()
    public void update(PluginCall call) {
        String name = call.getString("name", "app.db");
        String table = call.getString("table");
        JSObject values = call.getObject("values");
        String where = call.getString("where");
        JSArray whereArgs = call.getArray("whereArgs");
        if (table == null || values == null) {
            call.reject("Missing 'table' or 'values' parameter");
            return;
        }
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            ContentValues cv = jsObjectToContentValues(values);
            String[] whereArgsArr = jsArrayToStringArray(whereArgs);
            int count = db.update(table, cv, where, whereArgsArr);
            JSObject res = new JSObject();
            res.put("rowsAffected", count);
            call.resolve(res);
        } catch (Exception ex) {
            call.reject("update failed: " + ex.getMessage(), ex);
        }
    }

    @PluginMethod()
    public void delete(PluginCall call) {
        String name = call.getString("name", "app.db");
        String table = call.getString("table");
        String where = call.getString("where");
        JSArray whereArgs = call.getArray("whereArgs");
        if (table == null) {
            call.reject("Missing 'table' parameter");
            return;
        }
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            String[] whereArgsArr = jsArrayToStringArray(whereArgs);
            int count = db.delete(table, where, whereArgsArr);
            JSObject res = new JSObject();
            res.put("rowsAffected", count);
            call.resolve(res);
        } catch (Exception ex) {
            call.reject("delete failed: " + ex.getMessage(), ex);
        }
    }

    @PluginMethod()
    public void beginTransaction(PluginCall call) {
        String name = call.getString("name", "app.db");
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            db.beginTransaction();
            call.resolve();
        } catch (Exception ex) {
            call.reject("beginTransaction failed: " + ex.getMessage(), ex);
        }
    }

    @PluginMethod()
    public void setTransactionSuccessful(PluginCall call) {
        String name = call.getString("name", "app.db");
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            db.setTransactionSuccessful();
            call.resolve();
        } catch (Exception ex) {
            call.reject("setTransactionSuccessful failed: " + ex.getMessage(), ex);
        }
    }

    @PluginMethod()
    public void endTransaction(PluginCall call) {
        String name = call.getString("name", "app.db");
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            db.endTransaction();
            call.resolve();
        } catch (Exception ex) {
            call.reject("endTransaction failed: " + ex.getMessage(), ex);
        }
    }

    @PluginMethod()
    public void getVersion(PluginCall call) {
        String name = call.getString("name", "app.db");
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            int v = db.getVersion();
            JSObject res = new JSObject();
            res.put("version", v);
            call.resolve(res);
        } catch (Exception ex) {
            call.reject("getVersion failed: " + ex.getMessage(), ex);
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
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            call.reject("Database not open: " + name);
            return;
        }
        try {
            db.setVersion(version);
            call.resolve();
        } catch (Exception ex) {
            call.reject("setVersion failed: " + ex.getMessage(), ex);
        }
    }

    // Helpers

    private String[] jsArrayToStringArray(JSArray array) throws JSONException {
        if (array == null) return null;
        int len = array.length();
        String[] out = new String[len];
        for (int i = 0; i < len; i++) {
            Object o = array.get(i);
            out[i] = o == null || o == JSONObject.NULL ? null : o.toString();
        }
        return out;
    }

    private Object[] jsArrayToObjectArray(JSArray array) throws JSONException {
        if (array == null) return null;
        int len = array.length();
        Object[] out = new Object[len];
        for (int i = 0; i < len; i++) {
            Object o = array.get(i);
            if (o == JSONObject.NULL) {
                out[i] = null;
            } else {
                out[i] = o;
            }
        }
        return out;
    }

    private ContentValues jsObjectToContentValues(JSObject obj) {
        ContentValues cv = new ContentValues();
        if (obj == null) return cv;
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                Object v = obj.get(key);
                if (v == JSONObject.NULL) {
                    cv.putNull(key);
                } else if (v instanceof Integer) {
                    cv.put(key, (Integer) v);
                } else if (v instanceof Long) {
                    cv.put(key, (Long) v);
                } else if (v instanceof Double) {
                    cv.put(key, (Double) v);
                } else if (v instanceof Float) {
                    cv.put(key, (Float) v);
                } else if (v instanceof Boolean) {
                    cv.put(key, ((Boolean) v) ? 1 : 0);
                } else if (v instanceof byte[]) {
                    cv.put(key, (byte[]) v);
                } else {
                    cv.put(key, v.toString());
                }
            } catch (JSONException e) {
                // ignore this key
            }
        }
        return cv;
    }

    private JSArray cursorToJsArray(Cursor cursor) {
        JSArray rows = new JSArray();
        if (cursor == null) return rows;
        int colCount = cursor.getColumnCount();
        String[] colNames = cursor.getColumnNames();

        while (cursor.moveToNext()) {
            JSObject row = new JSObject();
            for (int i = 0; i < colCount; i++) {
                String col = colNames[i];
                int type = cursor.getType(i);
                switch (type) {
                    case Cursor.FIELD_TYPE_INTEGER:
                        row.put(col, cursor.getLong(i));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        row.put(col, cursor.getDouble(i));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        row.put(col, cursor.getString(i));
                        break;
                    case Cursor.FIELD_TYPE_BLOB:
                        byte[] blob = cursor.getBlob(i);
                        if (blob != null) {
                            row.put(col, Base64.encodeToString(blob, Base64.NO_WRAP));
                        } else {
                            row.put(col, (Object) null);
                        }
                        break;
                    case Cursor.FIELD_TYPE_NULL:
                    default:
                        row.put(col, (Object) null);
                        break;
                }
            }
            rows.put(row);
        }
        return rows;
    }

}

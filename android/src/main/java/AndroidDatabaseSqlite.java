package com.sepodo.software.plugins.android.database.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Base64;

import com.getcapacitor.Logger;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AndroidDatabaseSqlite {
    private final Context context;
    private final Map<String, SQLiteDatabase> databases = Collections.synchronizedMap(new HashMap<>());

    public AndroidDatabaseSqlite(Context context) {
        this.context = context;
    }

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }

    public void openOrCreateDatabase(String name) {
        SQLiteDatabase db = context.openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        databases.put(name, db);
    }

    public Boolean isOpen(String name) {
        SQLiteDatabase db = databases.get(name);
        return db != null && db.isOpen();
    }

    public String close(String name) {
        SQLiteDatabase db = databases.remove(name);
        if (db == null || !db.isOpen()) {
            return "Database not open: " + name;
        }
        db.close();
        return null;
    }

    public String execSQL(String name, String sql, JSArray bindArgs) throws JSONException {
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            return "Database not open: " + name;
        }
        if (bindArgs != null && bindArgs.length() > 0) {
            Object[] _bindArgs = jsArrayToObjectArray(bindArgs);
            db.execSQL(sql, _bindArgs);
        } else {
            db.execSQL(sql);
        }
        return null;
    }

    public ArrayResult rawQuery(String name, String sql, JSArray selectionArgs) throws Exception {
        SQLiteDatabase db = databases.get(name);
        ArrayResult result = new ArrayResult();
        if (db == null || !db.isOpen()) {
            result.error = "Database not open: " + name;
            return result;
        }
        Cursor cursor = null;
        try {
            String[] _selectionArgs = jsArrayToStringArray(selectionArgs);
            cursor = db.rawQuery(sql, _selectionArgs);
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

    public LongResult insert(String name, String table, JSObject values) {
        SQLiteDatabase db = databases.get(name);
        LongResult result = new LongResult();
        if (db == null || !db.isOpen()) {
            result.error = "Database not open: " + name;
            return result;
        }
        
        ContentValues _values = jsObjectToContentValues(values);
        result.value = db.insertOrThrow(table, null, _values);
        return result;
    }

     public LongResult getLastInsertRowId(String name) {
        SQLiteDatabase db = databases.get(name);
        LongResult result = new LongResult();
        if (db == null || !db.isOpen()) {
            result.error = "Database not open: " + name;
            return result;
        }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        result.value = db.getLastInsertRowId();
      } else {
        Logger.warn("getLastInsertRowId is implemented on API level 35 or above, current is " + Build.VERSION.SDK_INT);
      }
      return result;
    }

    public LongResult getLastChangedRowCount(String name) {
        SQLiteDatabase db = databases.get(name);
        LongResult result = new LongResult();
        if (db == null || !db.isOpen()) {
            result.error = "Database not open: " + name;
            return result;
        }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        result.value = db.getLastChangedRowCount();
      } else {
        Logger.warn("getLastChangedRowCount is implemented on API level 35 or above, current is " + Build.VERSION.SDK_INT);
      }
      return result;
    }

    public LongResult update(String name, String table, JSObject values, String whereClause, JSArray whereArgs) throws JSONException {
        SQLiteDatabase db = databases.get(name);
        LongResult result = new LongResult();
        if (db == null || !db.isOpen()) {
            result.error = "Database not open: " + name;
            return result;
        }

        ContentValues _values = jsObjectToContentValues(values);
        String[] _whereArgs = jsArrayToStringArray(whereArgs);
        result.value = db.update(table, _values, whereClause, _whereArgs);
        return result;
    }

    public LongResult delete(String name, String table, String whereClause, JSArray whereArgs) throws JSONException {
        SQLiteDatabase db = databases.get(name);
        LongResult result = new LongResult();
        if (db == null || !db.isOpen()) {
            result.error = "Database not open: " + name;
            return result;
        }

        String[] _whereArgs = jsArrayToStringArray(whereArgs);
        result.value = db.delete(table, whereClause, _whereArgs);
        return result;
    }

    public String beginTransaction(String name) {
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            return "Database not open: " + name;
        }
        
        db.beginTransaction();
        return null;
    }

    public String setTransactionSuccessful(String name) {
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            return "Database not open: " + name;
        }
        
        db.setTransactionSuccessful();
        return null;
    }

    public String endTransaction(String name) {
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            return "Database not open: " + name;
        }
        
        db.endTransaction();
        return null;
    }

    public LongResult getVersion(String name) {
        SQLiteDatabase db = databases.get(name);
        LongResult result = new LongResult();
        if (db == null || !db.isOpen()) {
            result.error = "Database not open: " + name;
            return result;
        }

        result.value = db.getVersion();
        return result;
    }

    public String setVersion(String name, Integer version) {
        SQLiteDatabase db = databases.get(name);
        if (db == null || !db.isOpen()) {
            return "Database not open: " + name;
        }
        
        db.setVersion(version);
        return null;
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

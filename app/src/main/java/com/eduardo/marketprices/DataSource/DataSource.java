package com.eduardo.marketprices.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataSource extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public DataSource(Context context) {
        super(context, "market_prices.sqlite", null, 1);
        db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseStructure.createTables());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void create(ContentValues values, String table){
            db.insert(table, null, values);
    }

    public void update(ContentValues values, String table){
        Integer id = values.getAsInteger("id");
        db.update(table, values, "id=" + id, null);
    }

    public void delete(ContentValues values, String table){
        Integer id = values.getAsInteger("id");
        db.delete(table, "id=" + id, null);
    }



    public Cursor search(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }
}

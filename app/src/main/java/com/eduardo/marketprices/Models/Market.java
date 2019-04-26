package com.eduardo.marketprices.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.eduardo.marketprices.DataSource.DataSource;

import java.util.ArrayList;

public class Market {
    private static String TABLE_NAME = "markets";
    String name;
    Integer id;

    public Market(String name) {
        this.name = name;
    }

    public Market(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void save(Context ctx) {
        ContentValues values = new ContentValues();
        values.put("id", this.id);
        values.put("name", this.name);

        DataSource ds = new DataSource(ctx);
        if (isNewRecord()){
            ds.create(values, TABLE_NAME);
        } else {
            ds.update(values, TABLE_NAME);
        }
    }

    public static ArrayList<Market> all(Context ctx){
        DataSource ds = new DataSource(ctx);
        Cursor cursor = ds.search(TABLE_NAME, null, null, null, null, null, null, null);
        ArrayList<Market> results = new ArrayList<>();

        if (cursor.getCount() > 0 ){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                Market market = Market.buildFromDatabase(cursor);
                results.add(market);
                cursor.moveToNext();
            }
        }

        return results;
    }

    private static Market buildFromDatabase(Cursor cursor){
        Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        Market market = new Market(id, name);
        return market;
    }

    private boolean isNewRecord(){
        return this.id  == null;
    }
}

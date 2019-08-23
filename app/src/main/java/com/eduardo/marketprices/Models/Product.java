package com.eduardo.marketprices.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.Editable;

import com.eduardo.marketprices.DataSource.DataSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Product implements Serializable {
    private static String TABLE_NAME = "products";
    Integer id;
    Integer market_id;
    String name;
    Double price;

    public Product(Integer market_id, String name, Double price) {
        this.market_id = market_id;
        this.name = name;
        this.price = price;
    }

    public Product(Integer id, Integer market_id, String name, Double price){
        this.id = id;
        this.market_id = market_id;
        this.name = name;
        this.price = price;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Double getPrice(){
        return price;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public void save(Context ctx) {
        ContentValues values = new ContentValues();
        values.put("id", this.id);
        values.put("market_id", this.market_id);
        values.put("name", this.name);
        values.put("price", this.price);

        DataSource ds = new DataSource(ctx);
        if (isNewRecord()){
            ds.create(values, TABLE_NAME);
        } else {
            ds.update(values, TABLE_NAME);
        }
    }

    public void delete(Context ctx){
        DataSource ds = new DataSource(ctx);
        if (!isNewRecord()){
            ContentValues values = new ContentValues();
            values.put("id", this.id);
            ds.delete(values, TABLE_NAME);
        }
    }

    public static ArrayList<Product> all(Context ctx){
        DataSource ds = new DataSource(ctx);
        Cursor cursor = ds.search(TABLE_NAME, null, null, null, null, null, null, null);
        ArrayList<Product> results = new ArrayList<>();

        if (cursor.getCount() > 0 ){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                Product market = Product.buildFromDatabase(cursor);
                results.add(market);
                cursor.moveToNext();
            }
        }

        return results;
    }

    public static ArrayList<Product> search(Context ctx, HashMap<String, String> query){
        String[] selectionArgs = null;
        String selection = "";

        selectionArgs = query.values().toArray(new String[0]);
        for (String key : query.keySet()){
            selection += key + " = ?";
        }

        DataSource ds = new DataSource(ctx);
        Cursor cursor = ds.search(TABLE_NAME, null, selection, selectionArgs, null, null, null, null);
        ArrayList<Product> results = new ArrayList<>();

        if (cursor.getCount() > 0 ){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                Product market = Product.buildFromDatabase(cursor);
                results.add(market);
                cursor.moveToNext();
            }
        }

        return results;
    }

    private static Product buildFromDatabase(Cursor cursor){
        Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        Integer market_id = cursor.getInt(cursor.getColumnIndex("market_id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        Double price = cursor.getDouble(cursor.getColumnIndex("price"));
        return new Product(id, market_id, name, price);
    }

    private boolean isNewRecord(){
        return this.id  == null;
    }
}

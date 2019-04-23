package com.eduardo.marketprices.Models;

import android.content.ContentValues;
import android.content.Context;

import com.eduardo.marketprices.DataSource.DataSource;

public class Market {
    String name;

    public Market(String name){
        this.name = name;
    }

    public void save(Context ctx) {
        ContentValues values = new ContentValues();
        values.put("name", this.name);

        DataSource ds = new DataSource(ctx);
        ds.save(values, "markets");
    }
}

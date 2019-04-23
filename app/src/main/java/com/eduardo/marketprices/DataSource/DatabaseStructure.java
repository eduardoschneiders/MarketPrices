package com.eduardo.marketprices.DataSource;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseStructure {
    public static String createTables(){
        return createMarketsTable();
    }

    private static String createMarketsTable(){
        HashMap<String, String> fields = new HashMap<>();
        fields.put("id", "INTEGER PRIMARY KEY");
        fields.put("name", "TEXT");

        return createTable("markets", fields);
    }

    private static String createTable(String tableName, HashMap<String, String> fields){
        return "CREATE TABLE " + tableName + " (" + joinFields(fields) + ");";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String joinFields(HashMap<String, String> fields){
        ArrayList<String> result = new ArrayList<>();

        for (Map.Entry field : fields.entrySet()){
            result.add(field.getKey().toString() + " " + field.getValue());
        }

        return String.join(",", result);
    }
}

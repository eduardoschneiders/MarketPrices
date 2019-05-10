package com.eduardo.marketprices.DataSource;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseStructure {
    public static String createTables(){
        return createMarketsTable() + " " + createProductsTable();
    }

    public static String createMarketsTable(){
        HashMap<String, String> fields = new HashMap<>();
        fields.put("id", "INTEGER PRIMARY KEY");
        fields.put("name", "TEXT");
        fields.put("created_at", "DATE");
        fields.put("updated_at", "DATE");

        return createTable("markets", fields);
    }

    public static String createProductsTable(){
        HashMap<String, String> fields = new HashMap<>();
        fields.put("id", "INTEGER PRIMARY KEY");
        fields.put("market_id", "INTEGER");
        fields.put("name", "TEXT");
        fields.put("price", "INTEGER");
        fields.put("created_at", "DATE");
        fields.put("updated_at", "DATE");

        ArrayList<String> references = new ArrayList<>();
        references.add("market_id");
        references.add("market");
        references.add("id");

        return createTable("products", fields, references);
    }

    private static String createTable(String tableName, HashMap<String, String> fields){
        return "CREATE TABLE " + tableName + " (" + joinFields(fields) + ");";
    }

    private static String createTable(String tableName, HashMap<String, String> fields, ArrayList<String> references){
        return "CREATE TABLE " + tableName + " (" + joinFields(fields) + ", " + foreignReference(references) + ");";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String joinFields(HashMap<String, String> fields){
        ArrayList<String> result = new ArrayList<>();

        for (Map.Entry field : fields.entrySet()){
            result.add(field.getKey().toString() + " " + field.getValue());
        }

        return String.join(",", result);
    }

    private static String foreignReference( ArrayList<String> references){
        return "FOREIGN KEY(" + references.get(0) + ") REFERENCES "+references.get(1)+"("+references.get(2)+")";
    }
}

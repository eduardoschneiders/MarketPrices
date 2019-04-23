package com.eduardo.marketprices;

import android.content.Context;
import android.widget.Toast;

public class Util {
    public static void popUp(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }
}

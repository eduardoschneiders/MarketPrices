package com.eduardo.marketprices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eduardo.marketprices.Models.Market;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button createMarket = (Button) findViewById(R.id.createMarket);
        final EditText marketName = (EditText) findViewById(R.id.marketName);

        createMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = marketName.getText().toString();
                if (!name.equals("")){
                    Market market = new Market(name);
                    market.save(getApplicationContext());
                    Util.popUp(getApplicationContext(), "Market " + name + " created with success.");
                } else {
                    Util.popUp(getApplicationContext(), "Error to create market " + name + "!");
                }
            }
        });
    }
}

package com.eduardo.marketprices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eduardo.marketprices.Models.Market;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button createMarket = (Button) findViewById(R.id.createMarket);
        final EditText marketName = (EditText) findViewById(R.id.marketName);

        updateList();

        createMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = marketName.getText().toString();
                if (!name.equals("")){
                    Market market = new Market(name);
                    market.save(getApplicationContext());

                    updateList();
                    marketName.setText("");

                    Util.popUp(getApplicationContext(), "Market " + name + " created with success.");
                } else {
                    Util.popUp(getApplicationContext(), "Error to create market " + name + "!");
                }
            }
        });
    }

    private void updateList(){
        ArrayList<Market> markets = Market.all(getApplicationContext());
        ArrayList<String> results = new ArrayList<>();

        for (Market market : markets){
            results.add(market.getId() + " => " + market.getName());
        }

        ListView mainListView = (ListView) findViewById(R.id.mainListView);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.row_list_view, results);
        mainListView.setAdapter(listAdapter);
    }
}

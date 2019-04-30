package com.eduardo.marketprices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eduardo.marketprices.Models.Market;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView mainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainListView = (ListView) findViewById(R.id.mainListView);
        final Button createMarket = (Button) findViewById(R.id.createMarket);
        final EditText marketName = (EditText) findViewById(R.id.marketName);
        final EditText marketId = (EditText) findViewById(R.id.marketId);

        updateList();

        createMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = null;
                if (!marketId.getText().toString().equals("") ){
                    id = Integer.parseInt(marketId.getText().toString());
                }

                String name = marketName.getText().toString();

                if (!name.equals("")){
                    Market market = new Market(id, name);
                    market.save(getApplicationContext());

                    updateList();
                    marketName.setText("");
                    marketId.setText("");

                    Util.popUp(getApplicationContext(), "Market " + name + " created with success.");
                } else {
                    Util.popUp(getApplicationContext(), "Error to create market " + name + "!");
                }
            }
        });
        final Intent marketIntent;
        marketIntent = new Intent(this, MarketActivity.class);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Market market = findMarketByPosition((int) id);

                marketIntent.putExtra("market", market);
                startActivity(marketIntent);
            }
        });
    }

    private Market findMarketByPosition(Integer position){
        ArrayList<Market> markets = getMarkets();
        return markets.get(position);
    }

    private ArrayList<Market> getMarkets(){
        return Market.all(getApplicationContext());
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

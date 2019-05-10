package com.eduardo.marketprices;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eduardo.marketprices.Models.Market;
import com.eduardo.marketprices.Models.Product;

import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        Intent marketIntent = getIntent();
        final Market market = (Market) marketIntent.getSerializableExtra("market");
        final Intent mainIntent = new Intent(this, MainActivity.class);
        final Intent productIntent = new Intent(this, ProductActivity.class);

        final Button updateMarket = (Button) findViewById(R.id.updateMarket);
        final Button deleteMarket = (Button) findViewById(R.id.deleteMarket);
        final Button goProductIntent = (Button) findViewById(R.id.goProductIntent);
        final EditText marketName = (EditText) findViewById(R.id.marketName);

        marketName.setText(market.getName());


        ArrayList<Product> products = Product.all(getApplicationContext()); //TODO get only products from current Market
        ArrayList<String> results = new ArrayList<>();

        for (Product product: products){
            results.add(product.getId() + " => " + product.getName() + " => " + product.getPrice());
        }

        ListView mainListView = (ListView) findViewById(R.id.mainListView);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.row_list_view, results);
        mainListView.setAdapter(listAdapter);


        updateMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = marketName.getText().toString();

                if (!name.equals("")){
                    market.setName(name);
                    market.save(getApplicationContext());

                    Util.popUp(getApplicationContext(), "Market " + name + " udpdated with success.");
                    startActivity(mainIntent);
                } else {
                    Util.popUp(getApplicationContext(), "Error to update market " + name + "!");
                }
            }
        });

        deleteMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MarketActivity.this);
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Sure to delete the market " + market.getName() + "?");
                alertDialog.setIcon(R.drawable.ic_launcher_foreground);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    market.delete(getApplicationContext());

                    Util.popUp(getApplicationContext(), "Market " + market.getName() + " deleted with success.");
                    startActivity(mainIntent);
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alertDialog.show();
            }
        });

        goProductIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productIntent.putExtra("market", market);
                startActivity(productIntent);
            }
        });
    }

}

package com.eduardo.marketprices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eduardo.marketprices.Models.Market;
import com.eduardo.marketprices.Models.Product;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final Button createProduct = (Button) findViewById(R.id.createProduct);
        final Intent productIntent = getIntent();
        final Market market = (Market) productIntent.getSerializableExtra("market");
        final Intent marketIntent;
        marketIntent = new Intent(this, MarketActivity.class);



        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText productName = (EditText) findViewById(R.id.productName);
                final EditText productPrice = (EditText) findViewById(R.id.productPrice);
                Product product = new Product(market.getId(), productName.getText().toString(), Double.parseDouble(productPrice.getText().toString()));
                product.save(getApplicationContext());
                Util.popUp(getApplicationContext(), "Product " + productName.getText() + " created with success.");

                marketIntent.putExtra("market", market);
                startActivity(marketIntent);
            }
        });
    }

}

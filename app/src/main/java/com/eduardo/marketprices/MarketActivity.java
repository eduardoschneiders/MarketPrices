package com.eduardo.marketprices;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eduardo.marketprices.Models.Market;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        Intent marketIntent = getIntent();
        final Market market = (Market) marketIntent.getSerializableExtra("market");
        final Intent mainIntent = new Intent(this, MainActivity.class);

        final Button updateMarket = (Button) findViewById(R.id.updateMarket);
        final Button deleteMarket = (Button) findViewById(R.id.deleteMarket);
        final EditText marketName = (EditText) findViewById(R.id.marketName);

        marketName.setText(market.getName());

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
    }

}

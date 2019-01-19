package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ChooseLaundryShop extends AppCompatActivity {
    TextView name, location;
    String isname, islocation;
    Button btnviewclients, btnviewlocation, btnbookrequest;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselaundryshop);
        name = findViewById(R.id.lsname);
        location = findViewById(R.id.lslocation);
        btnviewclients = findViewById(R.id.btnviewclients);
        btnviewlocation = findViewById(R.id.btnlocation);
        btnbookrequest = findViewById(R.id.btnbookrequest);
        isname = getIntent().getStringExtra("name");
        islocation = getIntent().getStringExtra("location");

        name.setText(isname);
        location.setText(islocation);
        btnviewlocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("shop_name",isname);
                extras.putString("shop_location", islocation);
                Intent intent = new Intent(context, LaundryShopLocation.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        btnbookrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

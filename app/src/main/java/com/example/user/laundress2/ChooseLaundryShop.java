package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ChooseLaundryShop extends AppCompatActivity {
    Button btnviewclients, btnviewlocation, btnbookrequest;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselaundryshop);
        btnviewclients = findViewById(R.id.btnviewclients);
        btnviewlocation = findViewById(R.id.btnlocation);
        btnbookrequest = findViewById(R.id.btnbookrequest);

        btnviewlocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LaundryShopLocation.class);
                startActivity(intent);
            }
        });
    }

}

package com.example.user.laundress2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class ClientLaundryDetails extends AppCompatActivity {
    GridView androidGridView;

    String[] laundryDetailName = {
            "T-shirt and Polo", "Sleeveless", "Long Sleeve", "Pants", "Shorts", "Skirt",
            "Dress", "Blankets, Curtains, etc.", "Socks, Gloves, etc.", "Towels", "Others", "Done"
    };

    int[] laundryDetailId = {
            R.drawable.logo,  R.drawable.logo, R.drawable.logo,  R.drawable.logo,  R.drawable.logo,  R.drawable.logo,
            R.drawable.logo,  R.drawable.logo, R.drawable.logo,  R.drawable.logo,  R.drawable.logo,  R.drawable.logo

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientlaundrydet);
        LaundryDetailsAdapter laundryDetailsAdapter = new LaundryDetailsAdapter(ClientLaundryDetails.this, laundryDetailName, laundryDetailId);
        androidGridView = findViewById(R.id.gridview);
        androidGridView.setAdapter(laundryDetailsAdapter);

    }
}

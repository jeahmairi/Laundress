package com.example.user.laundress2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClientLaundryDetails extends AppCompatActivity {
    GridView androidGridView;
    Spinner laundrytag;

    String[] laundryDetailName = {
            "T-shirt and Polo", "Sleeveless", "Long Sleeve", "Pants", "Shorts", "Skirt",
            "Dress", "Blankets, Curtains, etc.", "Socks, Gloves, etc.", "Towels", "Others", "",
    };

    int[] laundryDetailId = {
            R.drawable.tshirt, R.drawable.sleeveless, R.drawable.polo, R.drawable.pants, R.drawable.shorts,
            R.drawable.skirt, R.drawable.dress, R.drawable.towel, R.drawable.socks, R.drawable.towel, R.drawable.add,
            R.drawable.buttondone

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientlaundrydet);
        LaundryDetailsAdapter laundryDetailsAdapter = new LaundryDetailsAdapter(ClientLaundryDetails.this, laundryDetailName, laundryDetailId);
        androidGridView = findViewById(R.id.gridview);
        androidGridView.setAdapter(laundryDetailsAdapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),AddLaundryDetails.class);
                intent.putExtra("name",laundryDetailName[position]);
                //intent.putExtra("image",laundryDetailId[position]);
                startActivity(intent);

               /* AlertDialog.Builder builder = new AlertDialog.Builder(ClientLaundryDetails.this);
                View mView = getLayoutInflater().inflate(R.layout.addclientlaundrydet, null);
                final TextView title = findViewById(R.id.title);
                builder.setTitle(laundryDetailName[position]);
                builder.setView(mView);
                final Dialog dialog = builder.create();
                dialog.show();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                int dialogWindowWidth = (int) (displayWidth * 0.99f);
                int dialogWindowHeight = (int) (displayHeight * 0.99f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                dialog.getWindow().setAttributes(layoutParams);*/
            }
        });

    }
}

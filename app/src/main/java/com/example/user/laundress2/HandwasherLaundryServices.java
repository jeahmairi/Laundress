package com.example.user.laundress2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class HandwasherLaundryServices extends AppCompatActivity {

    ArrayList<String> serviceoffered = new ArrayList<>();
    ArrayList<String> extraservice = new ArrayList<>();
    private String servicetype;
    CheckBox servofferedlaundry, servofferedlaundryanddry, extrairon, extrafold;
    RadioButton homeservice, pickup;
    Button btnset;
    private static String URL_HANDWASHER_SERVICES = "http://192.168.254.117/laundress/addhandwasherservices.php";

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

        // Write your code here

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handwasher_laundry_services);
        servofferedlaundry = findViewById(R.id.laundry);
        servofferedlaundryanddry = findViewById(R.id.laundryanddry);
        extrairon = findViewById(R.id.iron);
        extrafold = findViewById(R.id.fold);
        homeservice = findViewById(R.id.homeservice);
        pickup = findViewById(R.id.pickup);
        btnset = findViewById(R.id.btnset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servofferedlaundry.isChecked()){
                    String laundry = servofferedlaundry.getText().toString();
                    if(serviceoffered.indexOf(laundry)< 0 ){
                        serviceoffered.add(laundry);
                    }
                } else {
                    String laundry = servofferedlaundry.getText().toString();
                    serviceoffered.remove(laundry);
                }
                if(servofferedlaundryanddry.isChecked()){
                    String laundryanddry = servofferedlaundryanddry.getText().toString();
                    if(serviceoffered.indexOf(laundryanddry)< 0 ){
                        serviceoffered.add(laundryanddry);
                    }
                }else {
                    String laundryanddry = servofferedlaundryanddry.getText().toString();
                    serviceoffered.remove(laundryanddry);
                }
                if(extrairon.isChecked()){
                    String iron = extrairon.getText().toString();
                    if(extraservice.indexOf(iron)< 0 ){
                        extraservice.add(iron);
                    }
                }else {
                    String iron = extrairon.getText().toString();
                    extraservice.remove(iron);
                }
                if(extrafold.isChecked()){
                    String fold = extrafold.getText().toString();
                    if(extraservice.indexOf(fold)< 0 ){
                        extraservice.add(fold);
                    }
                }else {
                    String fold = extrafold.getText().toString();
                    extraservice.remove(fold);
                }

              for(int i = 0; i<serviceoffered.size(); i++){
                  Toast.makeText(HandwasherLaundryServices.this, "services offered: " +serviceoffered.get(i), Toast.LENGTH_SHORT).show();
              }
              for(int i = 0; i<extraservice.size(); i++){
                  Toast.makeText(HandwasherLaundryServices.this, "extra services: " +extraservice.get(i), Toast.LENGTH_SHORT).show();
              }
                Toast.makeText(HandwasherLaundryServices.this, "service type: " +servicetype, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.pickup:
                if(checked)
                    servicetype = pickup.getText().toString();
                break;
            case R.id.homeservice:
                if(checked)
                    servicetype = homeservice.getText().toString();
                break;
        }
    }
}



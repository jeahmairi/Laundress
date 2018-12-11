package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddLaundryDetails extends AppCompatActivity {
    TextView title;
    Spinner laundryTag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclientlaundrydet);
        title = findViewById(R.id.title);
        Intent intent = getIntent();
        String receivedName =  intent.getStringExtra("name");
        title.setText(receivedName);
        if(receivedName.equals("T-shirt and Polo")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Sleeveless")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Long Sleeve")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Pants")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Shorts")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Skirt")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Dress")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        }  else if(receivedName.equals("Blankets, Curtains, etc.")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Socks, Gloves, etc.")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Towels")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else if(receivedName.equals("Others")) {
            laundryTag = (Spinner) findViewById(R.id.laundrytag);
            List<String> list = new ArrayList<String>();
            list.add("T-shirt");
            list.add("Polo");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            laundryTag.setAdapter(dataAdapter);
        } else {
            Toast.makeText(this, "Invalid Laundry Details", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this,ClientLaundryDetails.class);
            startActivity(intent1);

        }
    }
}

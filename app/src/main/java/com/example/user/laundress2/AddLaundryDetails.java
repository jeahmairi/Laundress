package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddLaundryDetails extends AppCompatActivity {
    private TextView title;
    Spinner laundryTag;
    private LinearLayout parent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclientlaundrydet);
        parent = findViewById(R.id.linearLayout);
        title = findViewById(R.id.title);
        Intent intent = getIntent();
        String receivedName =  intent.getStringExtra("name");
        title.setText(receivedName);
        if(receivedName.equals(" ")) {
            Intent intent1 = new Intent(AddLaundryDetails.this,ClientHomepage.class);
            startActivity(intent1);
        }
    }
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.addclientlaundrydet2, null);
        // Add the new row before the add field button.
        parent.addView(rowView, parent.getChildCount() - 2);
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
        }/*else if(receivedName.equals(" ")) {
            Intent intent1 = new Intent(AddLaundryDetails.this,ClientLaundryDetails.class);
            startActivity(intent1);
        } else {
            Toast.makeText(this, "Invalid Laundry Details", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this,ClientLaundryDetails.class);
            startActivity(intent1);

        }*/
    }
}

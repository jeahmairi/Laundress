package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddLaundryDetails extends AppCompatActivity {
    private TextView title;
    Button addnewfield;
    Spinner laundryTag;
    private LinearLayout parent;
    String receivedName;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclientlaundrydet);
        parent = findViewById(R.id.linearLayout);
        addnewfield = findViewById(R.id.additemcateg);
        title = findViewById(R.id.title);
        Intent intent = getIntent();
        receivedName =  intent.getStringExtra("name");
        id = intent.getIntExtra("id", 0);
        Toast.makeText(AddLaundryDetails.this, "id: " +id, Toast.LENGTH_SHORT).show();
        title.setText(receivedName);
        addnewfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.addclientlaundrydet2, null);
                // Add the new row before the add field button.
                parent.addView(rowView, parent.getChildCount() - 2);
               // Toast.makeText(AddLaundryDetails.this, "value" +receivedName, Toast.LENGTH_SHORT).show();
               /* if(receivedName.equals("T-shirt and Polo")) {
                    Toast.makeText(AddLaundryDetails.this, "sulod", Toast.LENGTH_SHORT).show();
                    laundryTag = (Spinner) findViewById(R.id.laundrytag);
                    List<String> list = new ArrayList<String>();
                    list.add("T-shirt");
                    list.add("Polo");
                    list.add("list 3");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddLaundryDetails.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    laundryTag.setAdapter(dataAdapter);
                    //dataAdapter.notifyDataSetChanged();
                }*/
            }
        });
        if(receivedName.equals(" ")) {
            Intent intent1 = new Intent(AddLaundryDetails.this,ClientHomepage.class);
            startActivity(intent1);
        }
    }
   /* public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.addclientlaundrydet2, null);
        // Add the new row before the add field button.
        parent.addView(rowView, parent.getChildCount() - 2);

    }*/
}

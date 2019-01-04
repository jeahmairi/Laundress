package com.example.user.laundress2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ChooseHandwasher extends AppCompatActivity {
    TextView name, contact;
    RatingBar setRatingBar;
    final Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosehandwasher);
        name = findViewById(R.id.hwname);
        contact = findViewById(R.id.hwcontact);
        setRatingBar = findViewById(R.id.setRatings);
        String isname = getIntent().getStringExtra("name");
        String iscontact = getIntent().getStringExtra("contact");

        name.setText(isname);
        contact.setText(iscontact);
        setRatingBar.setRating((float) 2.5);
    }}

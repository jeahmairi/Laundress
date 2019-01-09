package com.example.user.laundress2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MakePost extends AppCompatActivity {
    int client_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makepost);
        TextView name;
        name = findViewById(R.id.name);
        String isname = getIntent().getStringExtra("client_name");
        client_id = getIntent().getIntExtra("client_id", 0);
        name.setText(isname);
        Toast.makeText(MakePost.this, "client id:" +client_id, Toast.LENGTH_SHORT).show();
    }
}

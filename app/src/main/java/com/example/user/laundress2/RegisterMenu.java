package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterMenu extends AppCompatActivity {

    Button lcreg, lhwreg, lsreg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registermenu);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        final Context context = this;

        lcreg = (Button) findViewById(R.id.lc_register);
        lhwreg = findViewById(R.id.lhw_register);
        lsreg = findViewById(R.id.ls_register);

        lcreg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, RegisterLc.class);
                startActivity(intent);

            }

        });

        lhwreg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, RegisterLhw.class);
                startActivity(intent);

            }

        });

        lsreg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {

                Toast.makeText(RegisterMenu.this, "Available only in web.", Toast.LENGTH_LONG).show();

            }


        });
    }
}

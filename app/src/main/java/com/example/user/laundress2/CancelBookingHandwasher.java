package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CancelBookingHandwasher extends AppCompatActivity {
    EditText reason;
    Button btncancel, btnback;
    private int trans_No, client_id;
    private String client_name;
    //    private static final String URL_CANCEL="http://192.168.254.113/laundress/cancelbookinghandwasher.php";
    private static final String URL_CANCEL="http://192.168.254.117/laundress/cancelbookinghandwasher.php";
    private String reasons, name;
    private int handwasher_id,lsp_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelbooking);
        reason = findViewById(R.id.reason);
        btncancel = findViewById(R.id.btncancel);
        btnback = findViewById(R.id.btnback);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        trans_No = extras.getInt("trans_No");
        handwasher_id = extras.getInt("id");
        lsp_id = extras.getInt("lspid");
        name = extras.getString("name");

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasons = reason.getText().toString();
                cancelbooking();

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("name",name);
                extras.putInt("id", handwasher_id);
                extras.putInt("lspid", lsp_id);
                Intent intent = new Intent(CancelBookingHandwasher.this, HandwasherHomepage.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void cancelbooking() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CANCEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Bundle extras = new Bundle();
                                extras.putString("name",name);
                                extras.putInt("id", handwasher_id);
                                extras.putInt("lspid", lsp_id);
                                Intent intent = new Intent(CancelBookingHandwasher.this, HandwasherHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(CancelBookingHandwasher.this, "Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CancelBookingHandwasher.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("reason", reasons);
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

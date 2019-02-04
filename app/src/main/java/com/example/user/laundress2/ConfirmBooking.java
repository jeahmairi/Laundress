package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ConfirmBooking extends AppCompatActivity {
    String name;
    int trans_No, lsp_id, handwasher_id;
    private static final String URL_TRANS ="http://192.168.254.117/laundress/updatetransaction.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        name = extras.getString("name");
        trans_No = extras.getInt("trans_No");
        lsp_id = extras.getInt("lsp_id");
        handwasher_id = extras.getInt("handwasher_id");
        Toast.makeText(ConfirmBooking.this, "Updated trans_no " +trans_No, Toast.LENGTH_SHORT).show();
        updateTransStatus(trans_No);
    }

    private void updateTransStatus(final int trans_Nos) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(ConfirmBooking.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putString("name",name);
                                extras.putInt("id", handwasher_id);
                                extras.putInt("lspid", lsp_id);
                                Intent intent = new Intent(ConfirmBooking.this, HandwasherHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ConfirmBooking.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConfirmBooking.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_Nos));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

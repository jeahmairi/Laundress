package com.example.user.laundress2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClientLaundryDetails extends AppCompatActivity {


    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<Integer> arrid = new ArrayList<>();
    String client_name; int client_id;
    GridView androidGridView;
    Button btnaddcategory;
    //  ListView listview;
    private Context context;
    //private static final String URL_ALL ="http://192.168.254.117/laundress/detailscategory.php";
    private static final String URL_ALL ="http://192.168.1.12/laundress/detailscategory.php";
    //private static final String URL_ALL ="http://192.168.254.100/laundress/detailscategory.php";
    //private static final String URL_ALL ="http://192.168.1.2/laundress/detailscategory.php";
    ArrayList<LaundryDetailList> laundryDetailLists = new ArrayList<LaundryDetailList>();
    LaundryDetailsAdapter laundryDetailsAdapter;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientlaundrydet);
        btnaddcategory = findViewById(R.id.btnaddcategory);
        androidGridView = findViewById(R.id.gridview);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        client_id = extras.getInt("client_id");

/*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
*/
        btnaddcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClientLaundryDetails.this,AddLaundryDetails.class);
                intent.putExtra("categ_name",laundryDetailLists.get(position).getName());
                intent.putExtra("categ_id",laundryDetailLists.get(position).getId());
                intent.putExtra("client_id",client_id);
                intent.putExtra("client_name",client_name);
                startActivity(intent);
            }
        });
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            getJsonResponse(response);
                            System.out.println("RESPONSEesponse");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(ClientLaundryDetails.this);
            requestQueue.add(stringRequest);
        }
        catch (Exception e)
        {

        }
        /*laundryDetailsAdapter = new LaundryDetailsAdapter(ClientLaundryDetails.this,laundryDetailLists);
        androidGridView.setAdapter(laundryDetailsAdapter);*/
    }


    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("category");
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {
                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                    int id= Integer.parseInt(jsonArray.getJSONObject(i).getString("id").toString());
                    //Toast.makeText(ClientLaundryDetails.this, " " +name, Toast.LENGTH_LONG).show();
                    arrname.add(name);
                    arrid.add(id);
                    LaundryDetailList laundryDetailList = new LaundryDetailList();
                    laundryDetailList.setName(name);
                    laundryDetailList.setId(id);
                    laundryDetailLists.add(laundryDetailList);
                }
                laundryDetailsAdapter = new LaundryDetailsAdapter(ClientLaundryDetails.this,laundryDetailLists);
                androidGridView.setAdapter(laundryDetailsAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ClientLaundryDetails.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
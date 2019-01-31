package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class HandwasherLaundryServices extends AppCompatActivity {

    ArrayList<String> serviceoffered = new ArrayList<>();
    ArrayList<String> extraservice = new ArrayList<>();
    ArrayList<String> servicetypecb = new ArrayList<>();
    ArrayList<Integer> priceservicetype = new ArrayList<>();
    ArrayList<String> umitofmeasure = new ArrayList<>();
    private boolean selected;
    private boolean selected2;
    private String servicetype;
    CheckBox servofferedlaundry, servofferedlaundryanddry, extrairon, extrafold, pickdeliv, homeserv;
    //RadioButton homeservice, pickup;
    String uompicdel, uomhomeserv;
    Spinner uompic, uomhome;
    EditText pricepick, pricehome;
    Button btnset, btnedit;
    String handwasher_name;
    String pickdel;
    String home;
    int pricepd, pricehs;
    int handwasher_id, handwasher_lspid;
    private static String URL_HANDWASHER_SERVICES = "http://192.168.254.117/laundress/addhandwasherservices.php";
    private static String URL_HANDWASHER_SERVICESTYPE = "http://192.168.254.117/laundress/addhandwasherservicetype.php";
    private static String URL_ALL_HANDWASHER_SERVICES = "http://192.168.254.117/laundress/allhandwasherservices.php";

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
        pickdeliv = findViewById(R.id.pickupdeliv);
        homeserv = findViewById(R.id.homeservice);
        pricepick = findViewById(R.id.pricepickdeliv);
        pricehome = findViewById(R.id.pricehome);
        uompic = findViewById(R.id.unitofmeasurepick);
        uomhome = findViewById(R.id.unitofmeasurehome);


        btnedit = findViewById(R.id.btnedit);
        btnset = findViewById(R.id.btnset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        handwasher_name = extras.getString("handwasher_name");
        handwasher_id = extras.getInt("handwasher_id");
        handwasher_lspid = extras.getInt("handwasher_lspid");
        allHandwasherServices();

        pickdeliv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(pickdeliv.isChecked());
            }
        });
        homeserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected2(homeserv.isChecked());
            }
        });



        uompic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uompicdel = parent.getItemAtPosition(position).toString();
               // umitofmeasure.add(uompicdel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uomhome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uomhomeserv = parent.getItemAtPosition(position).toString();
                //umitofmeasure.add(uomhomeserv);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelected()){
                    try{
                        pickdel = pickdeliv.getText().toString();
                        pricepd = Integer.parseInt(String.valueOf(pricepick.getText()));
                        if(servicetypecb.indexOf(pickdel)< 0 ){
                            servicetypecb.add(pickdel);
                        }

                        if(priceservicetype.indexOf(pricepd)<0){
                            priceservicetype.add(pricepd);
                        }
                        if(umitofmeasure.indexOf(uompicdel)<0){
                            umitofmeasure.add(uompicdel);
                        }

                    }catch (NumberFormatException e){
                        pricepick.setError("Error");
                    }
                }

                if(isSelected2()){
                    try{
                        home = homeserv.getText().toString();
                        pricehs = Integer.parseInt(String.valueOf(pricehome.getText()));
                        if(servicetypecb.indexOf(home)< 0 ){
                            servicetypecb.add(home);
                        }
                        if(priceservicetype.indexOf(pricehs)<0){
                            priceservicetype.add(pricehs);
                        }
                        if(umitofmeasure.indexOf(uomhomeserv)<0){
                            umitofmeasure.add(uomhomeserv);
                        }
                    }catch (NumberFormatException e){
                        pricehome.setError("Error");
                    }
                }/*else {
                    String home = homeserv.getText().toString();
                    //servicetypecb.remove(home);
                    *//*umitofmeasure.remove(uomhomeserv);
                    priceservicetype.remove(String.valueOf(pricehs));*//*
                }*/

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
       addServicesOffered();
       addServicesType();
           }
        });
    }

    private void addServicesType() {
       // final JSONObject all = new JSONObject();
        final JSONArray servtype = new JSONArray();
        final JSONArray prices = new JSONArray();
        final JSONArray uomeasure = new JSONArray();
        for(int i = 0; i < servicetypecb.size(); i++)
        {
            JSONObject srvtype = new JSONObject();
            try {
                srvtype.put("servtype", servicetypecb.get(i).toString());
                servtype.put(srvtype);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < priceservicetype.size(); i++)
        {
            JSONObject prcesrve = new JSONObject();
            try {
                prcesrve.put("priceserve", priceservicetype.get(i).toString());
                prices.put(prcesrve);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < umitofmeasure.size(); i++)
        {
            JSONObject uomeas = new JSONObject();
            try {
                uomeas.put("unitmeasure", umitofmeasure.get(i).toString());
                uomeasure.put(uomeas);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /*try {
            all.put("servctype", servtype);
            all.put("prc", prices);
            all.put("unitomeasure", uomeasure);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HANDWASHER_SERVICESTYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(HandwasherLaundryServices.this, "Added Successfully ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HandwasherLaundryServices.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(HandwasherLaundryServices.this, "Adding Services Failed ed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherLaundryServices.this, "Adding Services Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                // params.put("fname", fname);
                params.put("servicetype", servtype.toString());
                params.put("pricesve", prices.toString());
                params.put("measure", uomeasure.toString());
                //params.put("all", all.toString());
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void allHandwasherServices() {
        final JSONArray serviceoff = new JSONArray();
        final JSONArray xtraserv = new JSONArray();
        for(int i = 0; i < serviceoffered.size(); i++)
        {
            JSONObject servoffered = new JSONObject();
            try {
                servoffered.put("serviceoffered", serviceoffered.get(i).toString());
                serviceoff.put(servoffered);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < extraservice.size(); i++)
        {
            JSONObject extraserv = new JSONObject();
            try {
                extraserv.put("extraservices", extraservice.get(i).toString());
                xtraserv.put(extraserv);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_HANDWASHER_SERVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasherservice");
                            if (success.equals("1")){
                                btnedit.setVisibility(View.VISIBLE);
                                btnset.setVisibility(View.GONE);
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String name=jsonArray2.getJSONObject(i).getString("name").toString();
                                    if(name.equals(pickdeliv.getText().toString())){
                                        pickdeliv.setChecked(true);
                                    }
                                    if(name.equals(homeserv.getText().toString())){
                                        homeserv.setChecked(true);
                                    }
                                    if(name.equals(servofferedlaundry.getText().toString())){
                                        servofferedlaundry.setChecked(true);
                                    }
                                    if(name.equals(servofferedlaundryanddry.getText().toString())){
                                        servofferedlaundryanddry.setChecked(true);
                                    }
                                    if(name.equals(extrairon.getText().toString())){
                                        extrairon.setChecked(true);
                                    }
                                    if(name.equals(extrafold.getText().toString())){
                                        extrafold.setChecked(true);
                                    }
                                }
                            } else if(success.equals("0")){

                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(HandwasherLaundryServices.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherLaundryServices.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                // params.put("fname", fname);
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addServicesOffered() {
        final JSONArray serviceoff = new JSONArray();
        final JSONArray xtraserv = new JSONArray();
        final JSONArray servtype = new JSONArray();
        final JSONArray prices = new JSONArray();
        final JSONArray uomeasure = new JSONArray();
        for(int i = 0; i < serviceoffered.size(); i++)
        {
            JSONObject servoffered = new JSONObject();
            try {
                servoffered.put("serviceoffered", serviceoffered.get(i).toString());
                serviceoff.put(servoffered);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < extraservice.size(); i++)
        {
            JSONObject extraserv = new JSONObject();
            try {
                extraserv.put("extraservices", extraservice.get(i).toString());
                xtraserv.put(extraserv);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < servicetypecb.size(); i++)
        {
            JSONObject srvtype = new JSONObject();
            try {
                srvtype.put("servtype", servicetypecb.get(i).toString());
                servtype.put(srvtype);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < priceservicetype.size(); i++)
        {
            JSONObject prcesrve = new JSONObject();
            try {
                prcesrve.put("priceserve", priceservicetype.get(i).toString());
                prices.put(prcesrve);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < umitofmeasure.size(); i++)
        {
            JSONObject uomeas = new JSONObject();
            try {
                uomeas.put("unitmeasure", umitofmeasure.get(i).toString());
                uomeasure.put(uomeas);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HANDWASHER_SERVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(HandwasherLaundryServices.this, "Added Successfully ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(HandwasherLaundryServices.this, "Adding Services Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherLaundryServices.this, "Adding Services Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
               // params.put("fname", fname);
                params.put("serviceoffereds", serviceoff.toString());
                params.put("extraservice", xtraserv.toString());
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected2() {
        return selected2;
    }

    public void setSelected2(boolean selected2) {
        this.selected2 = selected2;
    }
}



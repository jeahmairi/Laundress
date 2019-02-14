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
    ArrayList<Integer> priceserviceoff = new ArrayList<>();
    ArrayList<Integer> priceextraservice = new ArrayList<>();
    ArrayList<String> umitofmeasureoff = new ArrayList<>();
    ArrayList<String> umitofmeasureextra = new ArrayList<>();
    private boolean selected;
    private boolean selected2;
    private String servicetype;
    CheckBox servofferedlaundry, servofferedlaundryanddry,servoffereddry, extrairon, extrafold, pickdeliv, homeserv;
    //RadioButton homeservice, pickup;
    Spinner unitofmeasurelaundry, unitofmeasuredry, unitofmeasurelaundryanddry, unitofmeasureiron, unitofmeasurefold;
    EditText pricelaundry, pricedry, pricelaundryanddry, priceironing, pricefolding;
    Button btnset, btnedit;
    String handwasher_name;
    String pickdel;
    String home;
    int pricelaund, price_dry, pricelaundanddry, priceiron, pricefold;
    int handwasher_id, handwasher_lspid;
    private static String URL_HANDWASHER_SERVICES = "http://192.168.254.117/laundress/addhandwasherextraservices.php";
    private static String URL_HANDWASHER_SERVICESTYPE = "http://192.168.254.117/laundress/addhandwasherservicetype.php";
    private static String URL_HANDWASHER_SERVICEOFFERED = "http://192.168.254.117/laundress/addhandwasherserviceoffered.php";
    private static String URL_ALL_HANDWASHER_SERVICES = "http://192.168.254.117/laundress/allhandwasherservices.php";
    String uomlaundry;
    String uomdry;
    String uomdryanddry;
    String uomiron;
    String uomfold;

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
        servoffereddry = findViewById(R.id.dry);
        servofferedlaundryanddry = findViewById(R.id.laundryanddry);
        extrairon = findViewById(R.id.iron);
        extrafold = findViewById(R.id.fold);
        pickdeliv = findViewById(R.id.pickupdeliv);
        homeserv = findViewById(R.id.homeservice);

        unitofmeasurelaundry = findViewById(R.id.unitofmeasurelaundry);
        unitofmeasuredry = findViewById(R.id.unitofmeasuredry);
        unitofmeasurelaundryanddry = findViewById(R.id.unitofmeasurelaundryanddry);
        unitofmeasureiron = findViewById(R.id.unitofmeasureiron);
        unitofmeasurefold = findViewById(R.id.unitofmeasurefold);

        pricelaundry = findViewById(R.id.pricelaundry);
        pricedry = findViewById(R.id.pricedry);
        pricelaundryanddry = findViewById(R.id.pricelaundryanddry);
        priceironing = findViewById(R.id.priceironing);
        pricefolding = findViewById(R.id.pricefolding);

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

        unitofmeasurelaundry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uomlaundry = parent.getItemAtPosition(position).toString();
                umitofmeasureoff.add(uomlaundry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitofmeasuredry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uomdry = parent.getItemAtPosition(position).toString();
                umitofmeasureoff.add(uomdry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitofmeasurelaundryanddry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uomdryanddry = parent.getItemAtPosition(position).toString();
                umitofmeasureoff.add(uomdryanddry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitofmeasureiron.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uomiron = parent.getItemAtPosition(position).toString();
                umitofmeasureextra.add(uomiron);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitofmeasurefold.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uomfold = parent.getItemAtPosition(position).toString();
                umitofmeasureextra.add(uomfold);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickdeliv.isChecked()){
                        pickdel = pickdeliv.getText().toString();
                        if(servicetypecb.indexOf(pickdel)< 0 ){
                            servicetypecb.add(pickdel);
                        }
                        else {
                            String laundry = pickdeliv.getText().toString();
                            servicetypecb.remove(laundry);
                        }
                }

                if(homeserv.isChecked()){
                        home = homeserv.getText().toString();
                        if(servicetypecb.indexOf(home)< 0 ){
                            servicetypecb.add(home);
                        }else {
                            String laundry = homeserv.getText().toString();
                            servicetypecb.remove(laundry);
                        }
                }
                //start of service offered
                if(servofferedlaundry.isChecked()){
                    String laundry = servofferedlaundry.getText().toString();
                    pricelaund = Integer.parseInt(String.valueOf(pricelaundry.getText()));
                    if(serviceoffered.indexOf(laundry)< 0 ){
                        serviceoffered.add(laundry);
                    }
                    if(priceserviceoff.indexOf(pricelaund)<0){
                        priceserviceoff.add(pricelaund);
                    }
                    if(umitofmeasureoff.indexOf(uomlaundry)<0){
                        umitofmeasureoff.add(uomlaundry);
                    }
                } else {
                    String laundry = servofferedlaundry.getText().toString();
                    serviceoffered.remove(laundry);
                }
                if(servoffereddry.isChecked()){
                    String laundry = servoffereddry.getText().toString();
                    price_dry = Integer.parseInt(String.valueOf(pricedry.getText()));
                    if(serviceoffered.indexOf(laundry)< 0 ){
                        serviceoffered.add(laundry);
                    }
                    if(priceserviceoff.indexOf(price_dry)<0){
                        priceserviceoff.add(price_dry);
                    }
                    if(umitofmeasureoff.indexOf(uomdry)<0){
                        umitofmeasureoff.add(uomdry);
                    }
                } else {
                    String laundry = servoffereddry.getText().toString();
                    serviceoffered.remove(laundry);
                }
                if(servofferedlaundryanddry.isChecked()){
                    String laundryanddry = servofferedlaundryanddry.getText().toString();
                    pricelaundanddry = Integer.parseInt(String.valueOf(pricelaundryanddry.getText()));
                    if(serviceoffered.indexOf(laundryanddry)< 0 ){
                        serviceoffered.add(laundryanddry);
                    }
                    if(priceserviceoff.indexOf(pricelaundanddry)<0){
                        priceserviceoff.add(pricelaundanddry);
                    }
                    if(umitofmeasureoff.indexOf(uomdryanddry)<0){
                        umitofmeasureoff.add(uomdryanddry);
                    }
                }else {
                    String laundryanddry = servofferedlaundryanddry.getText().toString();
                    serviceoffered.remove(laundryanddry);
                }
                //end of service offered

                //start of extra service
                if(extrairon.isChecked()){
                    String iron = extrairon.getText().toString();
                    priceiron = Integer.parseInt(String.valueOf(priceironing.getText()));
                    if(extraservice.indexOf(iron)< 0 ){
                        extraservice.add(iron);
                    }
                    if(priceextraservice.indexOf(priceiron)<0){
                        priceextraservice.add(priceiron);
                    }
                    if(umitofmeasureextra.indexOf(uomiron)<0){
                        umitofmeasureextra.add(uomiron);
                    }
                }else {
                    String iron = extrairon.getText().toString();
                    extraservice.remove(iron);
                }
                if(extrafold.isChecked()){
                    String fold = extrafold.getText().toString();
                    pricefold = Integer.parseInt(String.valueOf(pricefolding.getText()));
                    if(extraservice.indexOf(fold)< 0 ){
                        extraservice.add(fold);
                    }
                    if(priceextraservice.indexOf(pricefold)<0){
                        priceextraservice.add(pricefold);
                    }
                    if(umitofmeasureextra.indexOf(uomfold)<0){
                        umitofmeasureextra.add(uomfold);
                    }
                }else {
                    String fold = extrafold.getText().toString();
                    extraservice.remove(fold);
                }
                //end of extra service
                for(int i = 0; i < servicetypecb.size(); i++){
                    String servetype = servicetypecb.get(i).toString();
                    addServicesType(servetype);
                }
                addServicesOffered();
                addExtraServices();
           }
        });
    }

    private void addServicesType(final String servetype) {
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
                params.put("servetype", servetype);
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void allHandwasherServices() {
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
                                    String price= jsonArray2.getJSONObject(i).getString("price").toString();
                                    String uom=jsonArray2.getJSONObject(i).getString("uom").toString();
                                    if(name.equals(pickdeliv.getText().toString())){
                                        pickdeliv.setChecked(true);
                                    }
                                    if(name.equals(homeserv.getText().toString())){
                                        homeserv.setChecked(true);
                                    }
                                    //offered
                                    if(name.equals(servofferedlaundry.getText().toString())){
                                        servofferedlaundry.setChecked(true);
                                        pricelaundry.setText(price);
                                        for(int j= 0; j < unitofmeasurelaundry.getAdapter().getCount(); j++)
                                        {
                                            if(unitofmeasurelaundry.getAdapter().getItem(j).toString().equals(uom))
                                            {
                                                unitofmeasurelaundry.setSelection(j);
                                            }
                                        }
                                    }
                                    if(name.equals(servoffereddry.getText().toString())){
                                        servoffereddry.setChecked(true);
                                        pricedry.setText(price);
                                        for(int j= 0; j < unitofmeasuredry.getAdapter().getCount(); j++)
                                        {
                                            if(unitofmeasuredry.getAdapter().getItem(j).toString().equals(uom))
                                            {
                                                unitofmeasuredry.setSelection(j);
                                            }
                                        }
                                    }
                                    if(name.equals(servofferedlaundryanddry.getText().toString())){
                                        servofferedlaundryanddry.setChecked(true);
                                        pricelaundryanddry.setText(price);
                                        for(int j= 0; j < unitofmeasurelaundryanddry.getAdapter().getCount(); j++)
                                        {
                                            if(unitofmeasurelaundryanddry.getAdapter().getItem(j).toString().equals(uom))
                                            {
                                                unitofmeasurelaundryanddry.setSelection(j);
                                            }
                                        }
                                    }
                                    //extra
                                    if(name.equals(extrairon.getText().toString())){
                                        extrairon.setChecked(true);
                                        priceironing.setText(price);
                                        for(int j= 0; j < unitofmeasureiron.getAdapter().getCount(); j++)
                                        {
                                            if(unitofmeasureiron.getAdapter().getItem(j).toString().equals(uom))
                                            {
                                                unitofmeasureiron.setSelection(j);
                                            }
                                        }
                                    }
                                    if(name.equals(extrafold.getText().toString())){
                                        extrafold.setChecked(true);
                                        pricefolding.setText(price);
                                        for(int j= 0; j < unitofmeasurefold.getAdapter().getCount(); j++)
                                        {
                                            if(unitofmeasurefold.getAdapter().getItem(j).toString().equals(uom))
                                            {
                                                unitofmeasurefold.setSelection(j);
                                            }
                                        }
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
        for(int i = 0; i < priceserviceoff.size(); i++)
        {
            JSONObject prcesrve = new JSONObject();
            try {
                prcesrve.put("priceserve", priceserviceoff.get(i).toString());
                prices.put(prcesrve);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < umitofmeasureoff.size(); i++)
        {
            JSONObject uomeas = new JSONObject();
            try {
                uomeas.put("unitmeasure", umitofmeasureoff.get(i).toString());
                uomeasure.put(uomeas);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HANDWASHER_SERVICEOFFERED,
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
                params.put("serviceoff", serviceoff.toString());
                params.put("prices", prices.toString());
                params.put("uomeasure", uomeasure.toString());
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addExtraServices() {
        final JSONArray serviceoff = new JSONArray();
        final JSONArray prices = new JSONArray();
        final JSONArray uomeasure = new JSONArray();
        for(int i = 0; i < extraservice.size(); i++)
        {
            JSONObject servoffered = new JSONObject();
            try {
                servoffered.put("serviceoffered", extraservice.get(i).toString());
                serviceoff.put(servoffered);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < priceextraservice.size(); i++)
        {
            JSONObject prcesrve = new JSONObject();
            try {
                prcesrve.put("priceserve", priceextraservice.get(i).toString());
                prices.put(prcesrve);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < umitofmeasureextra.size(); i++)
        {
            JSONObject uomeas = new JSONObject();
            try {
                uomeas.put("unitmeasure", umitofmeasureextra.get(i).toString());
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
                params.put("extraserv", serviceoff.toString());
                params.put("prices", prices.toString());
                params.put("uomeasure", uomeasure.toString());
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}



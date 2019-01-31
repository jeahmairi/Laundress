package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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


public class ChooseLaundryShop extends AppCompatActivity {
    ArrayList<String> allServiceOffered = new ArrayList<>();
    ArrayList<String> allExtraServices = new ArrayList<>();
    TextView name, location, lscpnum, lsopenhours, lsserviceprice;
    String isname, islocation, iscontact, isopenhours, isclosehours;
    EditText estdateandtime;
    int isid, lsp_id;
    Button btnviewclients, btnviewlocation, btnbookrequest;
    LinearLayout llextras, llservice, llserviceoff;
    RadioGroup servicetyperadio;
    final Context context = this;
    private static final String URL_ALL_SERVICE_TYPE="http://192.168.254.117/laundress/allhandwasherservtype.php";
    private static final String URL_ALL_SERVICES="http://192.168.254.117/laundress/allhandwasherservices.php";
    //private static final String URL_ALL_HANDWASHER="http://192.168.254.117/laundress/allhandwasherLSP.php";
    private static final String URL_ALL_SERVICE_OFFERED ="http://192.168.254.117/laundress/allhandwasherservoff.php";
    private static final String URL_ALL_EXTRA_SERVICES ="http://192.168.254.117/laundress/allhandwasherextserv.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselaundryshop);
        name = findViewById(R.id.lsname);
        location = findViewById(R.id.lslocation);
        lscpnum = findViewById(R.id.lscpnum);
        lsopenhours = findViewById(R.id.lsopenhours);
        btnviewclients = findViewById(R.id.btnviewclients);
        btnviewlocation = findViewById(R.id.btnlocation);
        btnbookrequest = findViewById(R.id.btnbookrequest);
        lsserviceprice = findViewById(R.id.lsserviceprice);
        llextras = findViewById(R.id.linearlayoutextra);
        estdateandtime = findViewById(R.id.estdateandtime);
        llservice = findViewById(R.id.linearlayoutservices);
        llserviceoff= findViewById(R.id.servicesoffered);
        servicetyperadio = findViewById(R.id.servicetyperadio);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        isname =  extras.getString("name");
        islocation =  extras.getString("location");
        iscontact =  extras.getString("contact");
        isid = extras.getInt("id", 0);
        lsp_id = extras.getInt("lspid", 0);
        isopenhours = extras.getString("openhours");
        isclosehours = extras.getString("closehours");

        Toast.makeText(ChooseLaundryShop.this, "lsip: "+lsp_id, Toast.LENGTH_SHORT).show();

        name.setText(isname);
        location.setText(islocation);
        lscpnum.setText(iscontact);
        lsopenhours.setText(isopenhours+" - "+isclosehours);
        btnviewlocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("shop_name",isname);
                extras.putString("shop_location", islocation);
                extras.putString("shop_contact", iscontact);
                Intent intent = new Intent(context, LaundryShopLocation.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        btnbookrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<allServiceOffered.size(); i++){
                    Toast.makeText(ChooseLaundryShop.this, "services offered checked  " +allServiceOffered.get(i),Toast.LENGTH_SHORT).show();
                }
                for(int i = 0; i<allExtraServices.size(); i++){
                    Toast.makeText(ChooseLaundryShop.this, "all services checked  " +allExtraServices.get(i),Toast.LENGTH_SHORT).show();
                }

            }
        });

        allServices();
        allServiceOffered();
        allServiceType();
        allExtraService();
    }
    private void allServices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_SERVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasherservice");
                            if (success.equals("1")){
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String names=jsonArray2.getJSONObject(i).getString("name").toString();
                                    TextView tv = new TextView(ChooseLaundryShop.this);
                                    tv.setText(names);
                                    llserviceoff.addView(tv);
                                    //Toast.makeText(ChooseHandwasher.this, "service offered" + name,  Toast.LENGTH_SHORT).show();
                                }
                            } else if(success.equals("0")){
                                Toast.makeText(ChooseLaundryShop.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseLaundryShop.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseLaundryShop.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(lsp_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void allServiceOffered() {
        //final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_SERVICE_OFFERED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasherservicesoffered");
                            if (success.equals("1")){
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String name=jsonArray2.getJSONObject(i).getString("service_offered_name").toString();
                                    CheckBox cb = new CheckBox(ChooseLaundryShop.this);
                                    cb.setText(name);
                                    cb.setChecked(false);
                                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked){
                                                String servicesOffered = buttonView.getText().toString();
                                                //Toast.makeText(ChooseLaundryShop.this, "service offered" + servicesOffered,  Toast.LENGTH_SHORT).show();
                                                if(allServiceOffered.indexOf(servicesOffered)< 0 ){
                                                    allServiceOffered.add(servicesOffered);
                                                }
                                            } else{
                                                String servicesOffered = buttonView.getText().toString();
                                                allServiceOffered.remove(servicesOffered);
                                            }
                                            /*String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                                            Toast.makeText(ChooseLaundryShop.this, msg, Toast.LENGTH_SHORT).show();*/
                                        }
                                    });
                                    llservice.addView(cb);
                                    /*if(cb.isChecked()){
                                        String servicesOffered = cb.getText().toString();
                                        Toast.makeText(ChooseLaundryShop.this, "service offered" + servicesOffered,  Toast.LENGTH_SHORT).show();
                                        if(allServiceOffered.indexOf(servicesOffered)< 0 ){
                                            allServiceOffered.add(servicesOffered);
                                        }
                                    }else {
                                        String servicesOffered = cb.getText().toString();
                                        allServiceOffered.remove(servicesOffered);
                                    }*/


                                }
                            } else if(success.equals("0")){
                                Toast.makeText(ChooseLaundryShop.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseLaundryShop.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseLaundryShop.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(lsp_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void allServiceType() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_SERVICE_TYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasherservicetype");
                            if (success.equals("1")){
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String name=jsonArray2.getJSONObject(i).getString("service_Type").toString();
                                    String price=jsonArray2.getJSONObject(i).getString("service_Price").toString();
                                    lsserviceprice.setText(price);
                                    final RadioButton button = new RadioButton(ChooseLaundryShop.this);
                                    button.setId(i);
                                    button.setText(name);
                                    servicetyperadio.addView(button);
                                    //Toast.makeText(ChooseLaundryShop.this, "service offered" + name,  Toast.LENGTH_SHORT).show();

                                }
                            } else if(success.equals("0")){
                                Toast.makeText(ChooseLaundryShop.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseLaundryShop.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseLaundryShop.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(lsp_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void allExtraService() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_EXTRA_SERVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasherextraservice");
                            if (success.equals("1")){
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String name=jsonArray2.getJSONObject(i).getString("extra_service_name").toString();
                                    CheckBox cb = new CheckBox(ChooseLaundryShop.this);
                                    cb.setText(name);
                                    cb.setChecked(false);
                                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked){
                                                String extraservices = buttonView.getText().toString();
                                                //Toast.makeText(ChooseLaundryShop.this, "service offered" + servicesOffered,  Toast.LENGTH_SHORT).show();
                                                if(allExtraServices.indexOf(extraservices)< 0 ){
                                                    allExtraServices.add(extraservices);
                                                }
                                            } else{
                                                String extraservices = buttonView.getText().toString();
                                                allExtraServices.remove(extraservices);
                                            }
                                            /*String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                                            Toast.makeText(ChooseLaundryShop.this, msg, Toast.LENGTH_SHORT).show();*/
                                        }
                                    });
                                    llextras.addView(cb);
                                    //Toast.makeText(ChooseLaundryShop.this, "service offered" + name,  Toast.LENGTH_SHORT).show();

                                }
                            } else if(success.equals("0")){
                                Toast.makeText(ChooseLaundryShop.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseLaundryShop.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseLaundryShop.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(lsp_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

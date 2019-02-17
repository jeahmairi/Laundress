package com.example.user.laundress2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ChooseLaundryShop extends AppCompatActivity {
    ArrayList<String> allServiceOffered = new ArrayList<>();
    ArrayList<String> allExtraServices = new ArrayList<>();
    TextView name, location, lscpnum, lsopenhours, lsserviceprice;
    String isname, islocation, iscontact, isopenhours, isclosehours, client_name;
    EditText estdateandtime, lsweight;
    RatingBar setRatingBar;
    int isid, lsp_id, client_id;
    private DatePickerDialog datepicker;
    private TimePickerDialog timepicker;
    Button btnviewclients, btnviewlocation, btnbookrequest;
    LinearLayout llextras, llservice, llserviceoff;
    String estdate, esttime, service, estdatetime, weight;
    String allservice ="";
    String allxtraservice ="";
    float average = 0;
    RadioGroup servicetyperadio;
    final Context context = this;
    private int servicesOffered;

    private static final String URL_ALL_SERVICE_TYPE="http://192.168.254.113/laundress/allhandwasherservtype.php";
    private static final String URL_ALL_SERVICES="http://192.168.254.113/laundress/allhandwasherservices.php";
    private static final String URL_ALL_HANDWASHER="http://192.168.254.113/laundress/allshoplsp.php";
    private static final String URL_ALL_SERVICE_OFFERED ="http://192.168.254.113/laundress/allhandwasherservoff.php";
    private static final String URL_ALL_EXTRA_SERVICES ="http://192.168.254.113/laundress/allhandwasherextserv.php";
    private static final String URL_ADD_LAUND_TRANS ="http://192.168.254.113/laundress/addtransactionshop.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselaundryshop);
        name = findViewById(R.id.lsname);
        location = findViewById(R.id.lslocation);
        lscpnum = findViewById(R.id.lscpnum);
        lsopenhours = findViewById(R.id.lsopenhours);
        btnviewclients = findViewById(R.id.btnviewclients);
        setRatingBar = findViewById(R.id.setRatings);
        btnviewlocation = findViewById(R.id.btnlocation);
        btnbookrequest = findViewById(R.id.btnbookrequest);
        llextras = findViewById(R.id.linearlayoutextra);
        estdateandtime = findViewById(R.id.estdateandtime);
        llservice = findViewById(R.id.linearlayoutservices);
        llserviceoff= findViewById(R.id.servicesoffered);
        servicetyperadio = findViewById(R.id.servicetyperadio);
        lsweight = findViewById(R.id.lsweight);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        isname =  extras.getString("name");
        client_name =  extras.getString("client_name");
        islocation =  extras.getString("location");
        iscontact =  extras.getString("contact");
        isid = extras.getInt("id", 0);
        lsp_id = extras.getInt("lspid", 0);
        client_id = extras.getInt("client_id", 0);
        isopenhours = extras.getString("openhours");
        isclosehours = extras.getString("closehours");

        Toast.makeText(ChooseLaundryShop.this, "lsip: "+lsp_id+" client_id: "+client_id, Toast.LENGTH_SHORT).show();
        allhandwasher();
        name.setText(isname);
        location.setText(islocation);
        lscpnum.setText(iscontact);
        lsopenhours.setText(isopenhours+" - "+isclosehours);
        btnviewclients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",isname);
                extras.putString("handwasher_location", islocation);
                extras.putString("handwasher_contact", iscontact);
                extras.putInt("lsp_id", lsp_id);
                Intent intent = new Intent(context, ViewClients.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        btnviewlocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",isname);
                extras.putString("handwasher_location", islocation);
                extras.putString("handwasher_contact", iscontact);
                Intent intent = new Intent(context, LaundryShopLocation.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        btnbookrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = lsweight.getText().toString();
                /*Toast.makeText(ChooseLaundryShop.this, "service  " +servicesOffered,Toast.LENGTH_SHORT).show();
                for(int i = 0; i<allServiceOffered.size(); i++){
                   // allservice = allServiceOffered.get(i)+","+allservice;
                    Toast.makeText(ChooseLaundryShop.this, "services offered checked  " +allServiceOffered.get(i),Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(ChooseLaundryShop.this, "all services checked  " +allservice,Toast.LENGTH_SHORT).show();
                for(int i = 0; i<allExtraServices.size(); i++){
                    // allxtraservice = allExtraServices.get(i)+","+allxtraservice;
                    Toast.makeText(ChooseLaundryShop.this, "allExtraServices checked  " +allExtraServices.get(i),Toast.LENGTH_SHORT).show();
                }*/
                //Toast.makeText(ChooseLaundryShop.this, "all services checked  " +allxtraservice,Toast.LENGTH_SHORT).show();
                //Toast.makeText(ChooseLaundryShop.this, "weight  " +weight,Toast.LENGTH_SHORT).show();
                /*Toast.makeText(ChooseLaundryShop.this, "service  " +service,Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseLaundryShop.this, "estdatetime  " +estdatetime,Toast.LENGTH_SHORT).show();*/
                addLaundryTransaction( );
                Intent intent = new Intent(ChooseLaundryShop.this, ClientHomepage.class);
                intent.putExtra("id", client_id);
                intent.putExtra("name", client_name);
                //ClientMyLaundry.newInstance(client_id, client_name);
                startActivity(intent);

            }
        });
        estdateandtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(ChooseLaundryShop.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                estdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                //estdateandtime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);


                final Calendar cldr1 = Calendar.getInstance();
                int hour = cldr1.get(Calendar.HOUR_OF_DAY);
                int minute = cldr1.get(Calendar.MINUTE);
                // date picker dialog
                timepicker = new TimePickerDialog(ChooseLaundryShop.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                esttime = String.valueOf(hourOfDay) + " : " + String.valueOf(minute);
                                estdatetime = estdate+" "+esttime;
                                estdateandtime.setText(estdatetime);
                            }
                        }, hour, minute, DateFormat.is24HourFormat(ChooseLaundryShop.this));
                timepicker.show();
                datepicker.show();

            }
        });

        allServices();
        allServiceOffered();
        allServiceType();
        allExtraService();
    }

    private void allhandwasher(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_HANDWASHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasher");
                            if (success.equals("1")){
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    average= Float.parseFloat(jsonArray2.getJSONObject(i).getString("rate").toString());
                                    setRatingBar.setRating(Float.parseFloat(String.format("%.2f", new Float(average).floatValue())));
                                    //Toast.makeText(ChooseHandwasher.this, "service offered" + name,  Toast.LENGTH_SHORT).show();

                                }
                            } else if(success.equals("0")){
                                // Toast.makeText(ChooseHandwasher.this, "No data",  Toast.LENGTH_SHORT).show();
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
                                    int seroffer_ID= Integer.parseInt(jsonArray2.getJSONObject(i).getString("id").toString());
                                    String name=jsonArray2.getJSONObject(i).getString("service_offered_name").toString();
                                    String price=jsonArray2.getJSONObject(i).getString("service_offered_price").toString();
                                    String uom=jsonArray2.getJSONObject(i).getString("service_offered_uom").toString();
                                    LinearLayout llhori = new LinearLayout(ChooseLaundryShop.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                                    llhori.setOrientation(LinearLayout.HORIZONTAL);
                                    llhori.setPadding(5, 5, 5, 5);
                                    CheckBox cb = new CheckBox(ChooseLaundryShop.this);
                                    cb.setText(name);
                                    cb.setChecked(false);
                                    cb.setTag(seroffer_ID);
                                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            servicesOffered = Integer.parseInt(buttonView.getTag().toString());
                                            if(allServiceOffered.indexOf(servicesOffered)< 0 ){
                                                allServiceOffered.add(String.valueOf(servicesOffered));
                                            } else{
                                                int servid = Integer.parseInt(buttonView.getTag().toString());
                                                allServiceOffered.remove(servid);
                                            }
                                            /*String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                                            Toast.makeText(ChooseLaundryShop.this, msg, Toast.LENGTH_SHORT).show();*/
                                        }
                                    });
                                    llhori.addView(cb);
                                    TextView tv2 = new TextView(ChooseLaundryShop.this);
                                    tv2.setText(price+" "+uom);
                                    tv2.setLayoutParams(params);
                                    tv2.setGravity(Gravity.CENTER);
                                    llhori.addView(tv2);
                                    llservice.addView(llhori);
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
                                    int service_No= Integer.parseInt(jsonArray2.getJSONObject(i).getString("service_No").toString());
                                    String name=jsonArray2.getJSONObject(i).getString("service_Type").toString();
                                    //String price=jsonArray2.getJSONObject(i).getString("service_Price").toString();
                                    //lsserviceprice.setText(price);
                                    RadioButton button = new RadioButton(ChooseLaundryShop.this);
                                    button.setId(i);
                                    button.setText(name);
                                    button.setTag(service_No);
                                    button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked){
                                                service = buttonView.getTag().toString();
                                            }
                                        }
                                    });
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
                                    int extraserv_ID= Integer.parseInt(jsonArray2.getJSONObject(i).getString("id").toString());
                                    String name=jsonArray2.getJSONObject(i).getString("extra_service_name").toString();
                                    String price=jsonArray2.getJSONObject(i).getString("extra_service_price").toString();
                                    String uom=jsonArray2.getJSONObject(i).getString("extra_service_uom").toString();
                                    LinearLayout llhori = new LinearLayout(ChooseLaundryShop.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                                    llhori.setOrientation(LinearLayout.HORIZONTAL);
                                    llhori.setPadding(5, 5, 5, 5);
                                    CheckBox cb = new CheckBox(ChooseLaundryShop.this);
                                    cb.setText(name);
                                    cb.setChecked(false);
                                    cb.setTag(extraserv_ID);
                                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked){
                                                int extraid = Integer.parseInt(buttonView.getTag().toString());
                                                //Toast.makeText(ChooseLaundryShop.this, "service offered" + servicesOffered,  Toast.LENGTH_SHORT).show();
                                                if(allExtraServices.indexOf(extraid)< 0 ){
                                                    allExtraServices.add(String.valueOf(extraid));
                                                } else{
                                                    int extraids = Integer.parseInt(buttonView.getTag().toString());
                                                    allExtraServices.remove(extraids);
                                                }
                                            }
                                            /*String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                                            Toast.makeText(ChooseLaundryShop.this, msg, Toast.LENGTH_SHORT).show();*/
                                        }
                                    });
                                    llhori.addView(cb);
                                    TextView tv2 = new TextView(ChooseLaundryShop.this);
                                    tv2.setText(price+" "+uom);
                                    tv2.setLayoutParams(params);
                                    tv2.setGravity(Gravity.CENTER);
                                    llhori.addView(tv2);
                                    llextras.addView(llhori);
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

    private void addLaundryTransaction( ) {
        final JSONArray allservice = new JSONArray();
        final JSONArray allxtraservice = new JSONArray();
        for(int i = 0; i < allServiceOffered.size(); i++)
        {
            JSONObject servoffered = new JSONObject();
            try {
                servoffered.put("serviceoffered", allServiceOffered.get(i).toString());
                allservice.put(servoffered);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < allExtraServices.size(); i++)
        {
            JSONObject xtraserve = new JSONObject();
            try {
                xtraserve.put("xtraserve", allExtraServices.get(i).toString());
                allxtraservice.put(xtraserve);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_LAUND_TRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ChooseLaundryShop.this, "Please wait for the approval of your chosen laundry service provider ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChooseLaundryShop.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseLaundryShop.this, "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("serviceoff", allservice.toString());
                params.put("extraserve", allxtraservice.toString());
                params.put("service", service);
                params.put("estdatetime", estdatetime);
                params.put("weight", weight);
                params.put("lsp_id", String.valueOf(lsp_id));
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

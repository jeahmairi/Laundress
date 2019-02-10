package com.example.user.laundress2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
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

import static org.json.JSONObject.NULL;

public class ChooseHandwasher extends AppCompatActivity {
    ArrayList<String> allServiceOffered = new ArrayList<>();
    ArrayList<String> allExtraServices = new ArrayList<>();
    String names, address, contacts, age, civilstatus;
    String isname, iscontact, location;
    EditText estdateandtime, lsweight;
    TextView name, contact, ageval, civilstat, lsserviceprice;
    RatingBar setRatingBar;
    RadioGroup servicetyperadio;
    Button btnLocation , btnbookrequest, btnviewclients;
    private DatePickerDialog datepicker;
    private TimePickerDialog timepicker;
    LinearLayout llextras, llservice, llserviceoff;
    String client_name, estdate, esttime, service,estdatetime, allservice, allxtraservice, weight;
    int lsp_id, client_id ;
    float average = 0;
    final Context context = this;
    private static final String URL_ALL_SERVICE_TYPE="http://192.168.254.117/laundress/allhandwasherservtype.php";
    private static final String URL_ALL_SERVICES="http://192.168.254.117/laundress/allhandwasherservices.php";
    private static final String URL_ALL_HANDWASHER="http://192.168.254.117/laundress/allhandwasherLSP.php";
    private static final String URL_ALL_SERVICE_OFFERED ="http://192.168.254.117/laundress/allhandwasherservoff.php";
    private static final String URL_ALL_EXTRA_SERVICES ="http://192.168.254.117/laundress/allhandwasherextserv.php";
    private static final String URL_ADD_LAUND_TRANS ="http://192.168.254.117/laundress/addlaundrytransaction.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosehandwasher);
        name = findViewById(R.id.hwname);
        contact = findViewById(R.id.hwcontact);
        setRatingBar = findViewById(R.id.setRatings);
        llextras = findViewById(R.id.linearlayoutextra);
        llservice = findViewById(R.id.linearlayoutservices);
        llserviceoff= findViewById(R.id.servicesoffered);
        servicetyperadio = findViewById(R.id.servicetyperadio);
        lsserviceprice = findViewById(R.id.lsserviceprice);
        ageval = findViewById(R.id.ageval);
        btnbookrequest = findViewById(R.id.btnbookrequest);
        estdateandtime = findViewById(R.id.estdateandtime);
        civilstat = findViewById(R.id.civilstat);
        lsweight = findViewById(R.id.lsweight);
        btnviewclients = findViewById(R.id.btnviewclients);
        btnLocation = findViewById(R.id.btnlocation);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        isname = extras.getString("name");
        iscontact = extras.getString("contact");
        location = extras.getString("location");
        lsp_id = extras.getInt("lsp_id");
        client_id = extras.getInt("client_id");
        btnviewclients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",isname);
                extras.putString("handwasher_location", location);
                extras.putString("handwasher_contact", iscontact);
                extras.putInt("lsp_id", lsp_id);
                Intent intent = new Intent(context, ViewClients.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",isname);
                extras.putString("handwasher_location", location);
                extras.putString("handwasher_contact", iscontact);
                Intent intent = new Intent(context, HandwasherLocation.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        btnbookrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = lsweight.getText().toString();

                for(int i = 0; i<allServiceOffered.size(); i++){

                    allservice = allServiceOffered.get(i)+","+allservice;
                    // Toast.makeText(ChooseLaundryShop.this, "services offered checked  " +allServiceOffered.get(i),Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(ChooseLaundryShop.this, "all services checked  " +allservice,Toast.LENGTH_SHORT).show();
                for(int i = 0; i<allExtraServices.size(); i++){
                    allxtraservice = allExtraServices.get(i)+","+allxtraservice;

                }
                addLaundryTransaction(allservice, allxtraservice);
                Intent intent = new Intent(ChooseHandwasher.this, ClientHomepage.class);
                intent.putExtra("id", client_id);
                intent.putExtra("name", isname);
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
                datepicker = new DatePickerDialog(ChooseHandwasher.this,
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
                timepicker = new TimePickerDialog(ChooseHandwasher.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                esttime = String.valueOf(hourOfDay) + " : " + String.valueOf(minute);
                                estdatetime = estdate+" "+esttime;
                                estdateandtime.setText(estdatetime);
                            }
                        }, hour, minute, DateFormat.is24HourFormat(ChooseHandwasher.this));
                timepicker.show();
                datepicker.show();

            }
        });
        /*CheckBox cb = new CheckBox(this);
        cb.setText("Tutlane");
        cb.setChecked(true);
        llextras.addView(cb);
        CheckBox cb2 = new CheckBox(this);
        cb2.setText("Tutlane2");
        cb2.setChecked(true);
        llextras.addView(cb2);*/

        /*CheckBox cb = new CheckBox(ChooseHandwasher.this);
        cb.setText(isname);
        cb.setChecked(false);
        llservice.addView(cb);*/
        name.setText(isname);
        contact.setText(iscontact);

        //Toast.makeText(ChooseHandwasher.this, "lsp_id: "+lsp_id+"  client_id "+client_id, Toast.LENGTH_SHORT).show();

        allhandwasher();
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
                                    TextView tv = new TextView(ChooseHandwasher.this);
                                    tv.setText(names);
                                    llserviceoff.addView(tv);
                                    //Toast.makeText(ChooseHandwasher.this, "service offered" + name,  Toast.LENGTH_SHORT).show();
                                }
                            } else if(success.equals("0")){
                                //Toast.makeText(ChooseHandwasher.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseHandwasher.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseHandwasher.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
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
                                    names=jsonArray2.getJSONObject(i).getString("name").toString();
                                    address=jsonArray2.getJSONObject(i).getString("address").toString();
                                    contacts=jsonArray2.getJSONObject(i).getString("contact").toString();
                                    age=jsonArray2.getJSONObject(i).getString("age").toString();
                                    civilstatus=jsonArray2.getJSONObject(i).getString("civilstat").toString();
                                    average= Float.parseFloat(jsonArray2.getJSONObject(i).getString("rate").toString());
                                    name.setText(names);
                                    contact.setText(contacts);
                                    ageval.setText(age);
                                    civilstat.setText(civilstatus);
                                    setRatingBar.setRating(Float.parseFloat(String.format("%.2f", new Float(average).floatValue())));
                                    //Toast.makeText(ChooseHandwasher.this, "service offered" + name,  Toast.LENGTH_SHORT).show();

                                }
                            } else if(success.equals("0")){
                               // Toast.makeText(ChooseHandwasher.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseHandwasher.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseHandwasher.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
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
                                    RadioButton button = new RadioButton(ChooseHandwasher.this);
                                    button.setId(i);
                                    button.setText(name);
                                    button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked){
                                                service = buttonView.getText().toString();
                                            }
                                        }
                                    });
                                    servicetyperadio.addView(button);
                                    //Toast.makeText(ChooseHandwasher.this, "service offered" + name,  Toast.LENGTH_SHORT).show();

                                }
                            } else if(success.equals("0")){
                               // Toast.makeText(ChooseHandwasher.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseHandwasher.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseHandwasher.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
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
                                    CheckBox cb = new CheckBox(ChooseHandwasher.this);
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
                                    //Toast.makeText(ChooseHandwasher.this, "service offered" + name,  Toast.LENGTH_SHORT).show();

                                }
                            } else if(success.equals("0")){
                               // Toast.makeText(ChooseHandwasher.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseHandwasher.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseHandwasher.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
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
                                    CheckBox cb = new CheckBox(ChooseHandwasher.this);
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
                                    //Toast.makeText(ChooseHandwasher.this, "service offered" + name,  Toast.LENGTH_SHORT).show();

                                }
                            } else if(success.equals("0")){
                               // Toast.makeText(ChooseHandwasher.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseHandwasher.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseHandwasher.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
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

    private void addLaundryTransaction(final String allservice, final String allxtraservice) {
        // final JSONObject all = new JSONObject();
        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_LAUND_TRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ChooseHandwasher.this, "Please wait for the approval of your chosen laundry service provider ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChooseHandwasher.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ChooseHandwasher.this, "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseHandwasher.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("serviceoff", ChooseHandwasher.this.allservice);
                params.put("extraservice", ChooseHandwasher.this.allxtraservice);
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

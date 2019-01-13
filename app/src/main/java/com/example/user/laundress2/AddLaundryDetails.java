package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

public class AddLaundryDetails extends AppCompatActivity {
    /*ArrayList<String> arritemtag = new ArrayList<>();
    ArrayList<String> arritembrand = new ArrayList<>();
    ArrayList<String> arritemcolor = new ArrayList<>();
    ArrayList<Integer> arritemnoofpieces = new ArrayList<>();
    ArrayList<Integer> arrclientid = new ArrayList<>();
    ArrayList<Integer> arrcategid = new ArrayList<>();
    private Context context;*/
    ViewHolder viewHolder;
    private static final String URL_ALL ="http://192.168.254.117/laundress/alllaundrydetails.php";
   // private static final String URL_ALL ="http://192.168.254.100/laundress/alllaundrydetails.php";
    //private static final String URL_ALL ="http://192.168.1.2/laundress/alllaundrydetails.php";
   // private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.1.2/laundress/addlaundrydetails.php";
    //private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.117/laundress/addlaundrydetails.php";
    private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.100/laundress/addlaundrydetails.php";

    /*ArrayList<AddLaundryDetailList> addLaundryDetailLists = new ArrayList<AddLaundryDetailList>();*/
    EditText itemtag, itembrand, itemcolor, itemnoofpieces;

    private TextView title;
    Button addnewfield, done;
    Spinner laundryTag;
    private LinearLayout parent;
    String category_Name;
    String client_Name;
    int categ_id;
    int client_id;
    int count=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclientlaundrydet);
        parent = findViewById(R.id.linearLayout);
        addnewfield = findViewById(R.id.additemcateg);
        done = findViewById(R.id.done);
        title = findViewById(R.id.title);
        Intent intent = getIntent();
        category_Name =  intent.getStringExtra("categ_name");
        categ_id = intent.getIntExtra("categ_id", 0);
        client_id = intent.getIntExtra("client_id", 0);
        client_Name = intent.getStringExtra("client_name");
        Toast.makeText(AddLaundryDetails.this, "categ_id: " +categ_id+ "client_id"+client_id+ "client_name" +client_Name, Toast.LENGTH_SHORT).show();
        title.setText(category_Name);

        addnewfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
                /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.addclientlaundrydet3, null);*/
/*
                viewHolder = new ViewHolder();
                        viewHolder.itemtagstore = rowView.findViewById(R.id.tag);
                        viewHolder.itembrandstore = rowView.findViewById(R.id.brand);
                        viewHolder.itemcolorstore = rowView.findViewById(R.id.color);
                        viewHolder.itemnoofpiecesstore = rowView.findViewById(R.id.noofpieces);*/
                        final String itemTag = viewHolder.itemtagstore.getText().toString().trim();
                        final String itemBrand = viewHolder.itembrandstore.getText().toString().trim();
                        final String itemColor = viewHolder.itemcolorstore.getText().toString().trim();
                        final int itemNoofPieces = Integer.parseInt(String.valueOf(viewHolder.itemnoofpiecesstore.getText()));
                        addInputtedLaundryDetails(itemTag, itemBrand, itemColor, itemNoofPieces);
                        /*final String itemTag = viewHolder.itemtagstore.getText().toString().trim();*/
                        //Toast.makeText(AddLaundryDetails.this, "" +itemTag, Toast.LENGTH_SHORT ).show();
                //parent.addView(rowView, parent.getChildCount()-2);
                count++;
                    //Toast.makeText(AddLaundryDetails.this, "" +count, Toast.LENGTH_SHORT).show();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    onBackPressed();

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.toString(),

                            Toast.LENGTH_LONG).show();


                }

                /*Intent intent = new Intent(AddLaundryDetails.this, ClientMyLaundry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);*/
              /*  LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.addclientlaundrydet3, null);
                *//*itemtagstore = view.findViewById(R.id.tag);
                itembrandstore = view.findViewById(R.id.brand);
                itemcolorstore = view.findViewById(R.id.color);*//*
                *//*for(int j = 0; j < count; j++){
                    final String itemTag = viewHolder.itemtagstore.getText().toString().trim();
                    final String itemBrand = viewHolder.itembrandstore.getText().toString().trim();
                    final String itemColor = viewHolder.itemcolorstore.getText().toString().trim();
                    addInputtedLaundryDetails(itemTag, itemBrand, itemColor);
                   // Toast.makeText(AddLaundryDetails.this,"" +count, Toast.LENGTH_SHORT).show();
                }*//*
                *//*Intent intent = new Intent(AddLaundryDetails.this, ClientLaundryDetails.class);
                startActivity(intent);*/
            }
        });

        //display all laundry list -- start --
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
                            Toast.makeText(AddLaundryDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("categ_id", String.valueOf(categ_id));
                    params.put("client_id", String.valueOf(client_id));
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AddLaundryDetails.this);
            requestQueue.add(stringRequest);
        }
        catch (Exception e)
        {
            Toast.makeText(AddLaundryDetails.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {

        // Write your code here

        super.onBackPressed();
    }

    // add laundry details -- start --
    private void addInputtedLaundryDetails(final String itemTag, final String itemBrand, final String itemColor, final int itemNoofPieces) {
        final Context context = this;
       /* final String itemTag = this.itemtagstore.getText().toString().trim();
        final String itemBrand = this.itembrandstore.getText().toString().trim();
        final String itemColor = this.itemcolorstore.getText().toString().trim();*/
        //final int itemNoofPieces = Integer.parseInt(itemnoofpiecesstore.getText().toString());
        //Toast.makeText(AddLaundryDetails.this, "Added Successfully " +itemTag+ ""+itemBrand+""+itemColor, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URl_ADD_LAUNDRY_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(AddLaundryDetails.this, "Added Successfully ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(AddLaundryDetails.this, "Add Laundry Details Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddLaundryDetails.this, "Add Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("itemTag", itemTag);
                params.put("itemBrand", itemBrand);
                params.put("itemColor", itemColor);
                params.put("itemNoofPieces", String.valueOf(itemNoofPieces));
                params.put("categ_id", String.valueOf(categ_id));
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    // add laundry details -- end --

    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("alllaundrydetails");
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (success.equals("1")){
                LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = inflater1.inflate(R.layout.addclientlaundrydet3, null);
                parent.addView(view1, parent.getChildCount()-2);
                viewHolder = new ViewHolder();
                viewHolder.itemtagstore = view1.findViewById(R.id.tag);
                viewHolder.itembrandstore = view1.findViewById(R.id.brand);
                viewHolder.itemcolorstore = view1.findViewById(R.id.color);
                viewHolder.itemnoofpiecesstore = view1.findViewById(R.id.noofpieces);
                for (int i =0;i<jsonArray.length();i++)
                {
                    String cinv_itemTag=jsonArray.getJSONObject(i).getString("cinv_itemTag").toString();
                    String cinv_itemBrand=jsonArray.getJSONObject(i).getString("cinv_itemBrand").toString();
                    String cinv_itemColor=jsonArray.getJSONObject(i).getString("cinv_itemColor").toString();
                    int cinv_noOfPieces= Integer.parseInt(jsonArray.getJSONObject(i).getString("cinv_noOfPieces"));
                    Toast.makeText(AddLaundryDetails.this, "cinv_itemTag: " +cinv_itemTag+ "cinv_itemBrand"+cinv_itemBrand+ "cinv_itemColor" +cinv_itemColor+"cinv_noOfPieces" +cinv_noOfPieces, Toast.LENGTH_SHORT).show();


                    View view = inflater.inflate(R.layout.addclientlaundrydet2, null);
                    // Assume you want to add your view in linear layout
                    itemtag = view.findViewById(R.id.tag);
                    itembrand = view.findViewById(R.id.brand);
                    itemcolor = view.findViewById(R.id.color);
                    itemnoofpieces = view.findViewById(R.id.noofpieces);
                    itemtag.setEnabled(false);
                    itembrand.setEnabled(false);
                    itemcolor.setEnabled(false);
                    itemnoofpieces.setEnabled(false);
                    itemtag.setText(cinv_itemTag);
                    itembrand.setText(cinv_itemBrand);
                    itemcolor.setText(cinv_itemColor);
                    itemnoofpieces.setText(Integer.toString(cinv_noOfPieces));
                    //itemnoofpieces.setText(cinv_noOfPieces);
                    parent.addView(view,  parent.getChildCount() - 2);
                }
            } else if(success.equals("0")) {
                LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater1.inflate(R.layout.addclientlaundrydet3, null);
                viewHolder = new ViewHolder();
                viewHolder.itemtagstore = view.findViewById(R.id.tag);
                viewHolder.itembrandstore = view.findViewById(R.id.brand);
                viewHolder.itemcolorstore = view.findViewById(R.id.color);
                viewHolder.itemnoofpiecesstore = view.findViewById(R.id.noofpieces);
                parent.addView(view, parent.getChildCount() - 2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(AddLaundryDetails.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewHolder {
        EditText itemtagstore, itembrandstore, itemcolorstore, itemnoofpiecesstore;
    }
    //display all laundry list --end--
}

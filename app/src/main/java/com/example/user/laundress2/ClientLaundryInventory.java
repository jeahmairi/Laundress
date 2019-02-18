package com.example.user.laundress2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class ClientLaundryInventory extends AppCompatActivity {
    /*private static final String URL_ALL ="http://192.168.254.113/laundress/allinventory.php";
    private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.113/laundress/addlaunddetails.php";*/

    private static final String URL_ALL ="http://192.168.254.117/laundress/allinventory.php";
    private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.117/laundress/addlaunddetails.php";

    String client_name;
    Button btnselect, btnskip;
    int client_id;
    ListView llall;
    ArrayList<String> arritemdescription= new ArrayList<>();
    ArrayList<String> arrcategoryname= new ArrayList<>();
    ArrayList<Integer> arritemnoofpieces = new ArrayList<>();
    ArrayList<Integer> arrclientid = new ArrayList<>();
    ArrayList<Integer> arrcategid = new ArrayList<>();
    ArrayList<Integer> arrcinvno = new ArrayList<>();
    ArrayList<Integer> arritemnoofpieces1 = new ArrayList<>();
    ArrayList<Integer> arrclientid1 = new ArrayList<>();
    ArrayList<Integer> arrcinvno1 = new ArrayList<>();
    ArrayList<AddLaundryDetailList> addLaundryDetailLists = new ArrayList<AddLaundryDetailList>();
    LaundryInventoryAdapter laundryInventoryAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allinventory);
        llall = findViewById(R.id.llall);
        btnselect = findViewById(R.id.select);
        btnskip = findViewById(R.id.skip);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        client_id = extras.getInt("client_id");
        allHandwasherServices();
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                for(int i=0;i<addLaundryDetailLists.size();i++)
                {
                    AddLaundryDetailList addLaundryDetailList = addLaundryDetailLists.get(i);

                    if(addLaundryDetailList.isSelected())
                    {
                        //responseText.append("\n" + addLaundryDetailList.getCinv_no());
                        if(arrcinvno1.indexOf(addLaundryDetailList.getCinv_no())< 0 ){
                            arrcinvno1.add(addLaundryDetailList.getCinv_no());
                        }else{
                            arrcinvno1.remove(addLaundryDetailList.getCinv_no());
                        }
                        if(arritemnoofpieces1.indexOf(addLaundryDetailList.getSelectitemNoofPieces())< 0 ){
                            arritemnoofpieces1.add(addLaundryDetailList.getSelectitemNoofPieces());
                        }
                        //arrcinvno1.add(addLaundryDetailList.getCinv_no());
                        //arrclientid1.add(addLaundryDetailList.getClientId());
                       // arritemnoofpieces1.add(addLaundryDetailList.getSelectitemNoofPieces());

                        //addLaundryDetail();
                        //responseText.append("\n" + addLaundryDetailList.getClientId());
                        //responseText.append("\n Item " + addLaundryDetailList.getSelectitemNoofPieces());

                    }
                }
                addLaundryDetail();
                Bundle extras = new Bundle();
                extras.putString("client_name",client_name);
                extras.putInt("client_id", client_id);
                Intent intent = new Intent(ClientLaundryInventory.this, FindLaundryServiceProv.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialogSkip();
            }
        });

    }

    private void addLaundryDetail() {
        final JSONArray cinvid = new JSONArray();
        final JSONArray noofpieces = new JSONArray();
        for(int i = 0; i < arrcinvno1.size(); i++)
        {
            JSONObject invid = new JSONObject();
            try {
                invid.put("cinvid", arrcinvno1.get(i).toString());
                cinvid.put(invid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < arritemnoofpieces1.size(); i++)
        {
            JSONObject numofpieces = new JSONObject();
            try {
                numofpieces.put("noofpieces", arritemnoofpieces1.get(i).toString());
                noofpieces.put(numofpieces);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URl_ADD_LAUNDRY_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ClientLaundryInventory.this, "Added Successfully ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClientLaundryInventory.this, "Add Laundry Details Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientLaundryInventory.this, "Add Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                params.put("cinv_id", cinvid.toString());
                params.put("noofpiecess", noofpieces.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showChangeLangDialogSkip() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setTitle("Warning!!!");
        dialogBuilder.setMessage("If you proceed, this means that the laundry service provider is no longer responsible for any lost items during the transaction.");
        dialogBuilder.setPositiveButton("Proceed Anyway", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //AddLaundryDetails.this.recreate();
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void allHandwasherServices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("alllaundrydetails");
                            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int cinv_id = Integer.parseInt(jsonArray.getJSONObject(i).getString("cinv_id"));
                                    String cinv_itemDescription=jsonArray.getJSONObject(i).getString("cinv_itemDescription").toString();
                                    String category_Name=jsonArray.getJSONObject(i).getString("category_Name").toString();
                                    int cinv_noOfPieces= Integer.parseInt(jsonArray.getJSONObject(i).getString("cinv_noOfPieces"));
                                    int cinv_category_No= Integer.parseInt(jsonArray.getJSONObject(i).getString("cinv_noOfPieces"));


                                    arritemdescription.add(cinv_itemDescription);
                                    arrcategoryname.add(category_Name);
                                    arritemnoofpieces.add(cinv_noOfPieces);
                                    arrclientid.add(client_id);
                                    arrcategid.add(cinv_category_No);
                                    arrcinvno.add(cinv_id);

                                    AddLaundryDetailList addLaundryDetailList = new AddLaundryDetailList();
                                    addLaundryDetailList.setAllNameDet(cinv_itemDescription);
                                    addLaundryDetailList.setCategoryname(category_Name);
                                    addLaundryDetailList.setItemNoofPieces(cinv_noOfPieces);
                                    addLaundryDetailList.setClientId(client_id);
                                    addLaundryDetailList.setCategoryId(cinv_category_No);
                                    addLaundryDetailList.setCinv_no(cinv_id);

                                    addLaundryDetailLists.add(addLaundryDetailList);

                                }
                                laundryInventoryAdapter = new LaundryInventoryAdapter(ClientLaundryInventory.this,addLaundryDetailLists);
                                llall.setAdapter(laundryInventoryAdapter);

                            } else if(success.equals("0")) {
                                Toast.makeText(ClientLaundryInventory.this, "No data " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClientLaundryInventory.this, "Failed" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientLaundryInventory.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                /*params.put("categ_id", String.valueOf(categ_id));*/
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

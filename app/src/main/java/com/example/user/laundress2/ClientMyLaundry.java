package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

public class ClientMyLaundry extends Fragment {
    Button laundrydetails, findlsp;
    private String client_name, trans_Status, name, address, contact, table;
    private int client_id, trans_No, lsp_ID;
    //TextView section_label, section_label2, section_label3, aname, number, address, textView31, statusname, timeleft, time, timename;
    //ImageView picture, bgtime;
    ListView llmylaundry;
    ArrayList<ClientMyList> clientMyLists = new ArrayList<>();
    ClientMyAdapter clientMyAdapter;
    /*private static final String URL_ALL ="http://192.168.254.113/laundress/allbooking.php";
    private static final String URL_UPDATE ="http://192.168.254.113/laundress/updatetranscancel.php";*/
    private static final String URL_ALL ="http://192.168.254.117/laundress/allbooking.php";
    private static final String URL_UPDATE ="http://192.168.254.117/laundress/updatetranscancel.php";
    // newInstance constructor for creating fragment with arguments
    public static ClientMyLaundry newInstance(int client_id, String client_name) {
        ClientMyLaundry clientMyLaundry = new ClientMyLaundry();
        Bundle args = new Bundle();
        args.putInt("client_id", client_id);
        args.putString("client_name", client_name);
        clientMyLaundry.setArguments(args);
        return clientMyLaundry;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client_id = getArguments().getInt("client_id", 0);
        client_name = getArguments().getString("client_name");
        allbooking();
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //allbooking();
        View rootView = inflater.inflate(R.layout.client_mylaundry, container, false);
        llmylaundry = rootView.findViewById(R.id.llmylaundry);
        laundrydetails = rootView.findViewById(R.id.laundrydetails);
        findlsp = rootView.findViewById(R.id.findlsp);

        //Toast.makeText(getContext(),"Name MyLaundry" +client_name+ "ID " +client_id, Toast.LENGTH_SHORT).show();
            laundrydetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("client_name", client_name);
                    extras.putInt("client_id", client_id);

                    Intent intent = new Intent(getActivity(), ClientLaundryInventory.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });

            findlsp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("client_name", client_name);
                    extras.putInt("client_id", client_id);
                    Intent intent = new Intent(getActivity(), FindLaundryServiceProv.class);
                    /*intent.putExtra("client_name", getClientName());
                    intent.putExtra("client_id", getClientId());*/
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });

        return rootView;
    }
    private void allbooking(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allbooking");
                            //Toast.makeText(Login.this, "sud" + success, Toast.LENGTH_SHORT).show();
                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    trans_Status = object.getString("trans_Status").trim();
                                    table = object.getString("table").trim();
                                    trans_No = Integer.parseInt(object.getString("trans_No").trim());
                                    lsp_ID = Integer.parseInt(object.getString("lsp_ID").trim());
                                    String shop_id = object.getString("shop_ID");
                                    String handwasher_id = object.getString("handwasher_ID");
                                    name = object.getString("name");
                                    address = object.getString("address");
                                    contact = object.getString("contact");
                                    if(!trans_Status.equals("Cancelled")) {
                                        ClientMyList clientMyList = new ClientMyList();
                                        clientMyList.setName(name);
                                        clientMyList.setClient_name(client_name);
                                        clientMyList.setAddress(address);
                                        clientMyList.setContact(contact);
                                        clientMyList.setTable(table);
                                        clientMyList.setTrans_status(trans_Status);
                                        clientMyList.setTransNo(trans_No);
                                        clientMyList.setLspID(lsp_ID);
                                        clientMyList.setClientID(client_id);
                                        clientMyLists.add(clientMyList);
                                    }
                                    //Toast.makeText(getActivity(), "shop_id " +shop_id, Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getActivity(), "handwasher_id " +handwasher_id, Toast.LENGTH_SHORT).show();
                                    /*if(!handwasher_id.isEmpty()){
                                         handwasher_Name = object.getString("handwasher_LName").trim();
                                         handwasher_Address = object.getString("handwasher_Address").trim();
                                         handwasher_Contact = object.getString("handwasher_Contact").trim();
                                        if(handwasher_Name.isEmpty() && handwasher_Address.isEmpty() && handwasher_Contact.isEmpty()){
                                            if(!shop_id.isEmpty()){
                                                shop_Name = object.getString("shop_Name").trim();
                                                shop_Address = object.getString("shop_Address").trim();
                                                shop_Contact = object.getString("shop_ContactNo1").trim();
                                                    *//*aname.setText(shop_Name);
                                                    address.setText(shop_Address);
                                                    number.setText(shop_Contact);
                                                    time.setText(trans_Status);*//*
                                            }
                                        } else {
                                            *//*aname.setText(handwasher_Name);
                                            address.setText(handwasher_Address);
                                            number.setText(handwasher_Contact);
                                            time.setText(trans_Status);*//*
                                        }
                                    }else
                                    if(!shop_id.isEmpty()){
                                         shop_Name = object.getString("shop_Name").trim();
                                         shop_Address = object.getString("shop_Address").trim();
                                         shop_Contact = object.getString("shop_ContactNo1").trim();
                                        if(shop_Name.isEmpty() && shop_Address.isEmpty() && shop_Contact.isEmpty()){
                                            Toast.makeText(getActivity(), "Sud ", Toast.LENGTH_SHORT).show();
                                            if(!handwasher_id.isEmpty()){
                                                handwasher_Name = object.getString("handwasher_LName").trim();
                                                handwasher_Address = object.getString("handwasher_Address").trim();
                                                handwasher_Contact = object.getString("handwasher_Contact").trim();
                                                    *//*aname.setText(handwasher_Name);
                                                    address.setText(handwasher_Address);
                                                    number.setText(handwasher_Contact);
                                                    time.setText(trans_Status);*//*
                                            }
                                        } else{
                                            *//*aname.setText(shop_Name);
                                            address.setText(shop_Address);
                                            number.setText(shop_Contact);
                                            time.setText(trans_Status);*//*
                                        }


                                    }*/

                                        //Toast.makeText(getActivity(), "trans_Status " + trans_Status, Toast.LENGTH_SHORT).show();
                                }
                                clientMyAdapter = new ClientMyAdapter(getActivity(), clientMyLists);
                                llmylaundry.setAdapter(clientMyAdapter);
                            } else if (success.equals("0")) {
                                Toast.makeText(getActivity(), "No Boookings", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Login failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}

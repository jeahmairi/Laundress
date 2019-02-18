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

import java.util.HashMap;
import java.util.Map;

public class ClientMyLaundry extends Fragment {
    Button laundrydetails, findlsp, btnvielaunddetails, btnviewreqdet, btncancelreq, btnviewdet, btnviewreq;
    private String client_name, trans_Status, handwasher_Name, handwasher_Address, handwasher_Contact, shop_Name, shop_Address, shop_Contact;
    private int client_id, trans_No;
    TextView section_label, section_label2, section_label3, aname, number, address, textView31, statusname, timeleft, time, timename;
    ImageView picture, bgtime;

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
        laundrydetails = rootView.findViewById(R.id.laundrydetails);
        findlsp = rootView.findViewById(R.id.findlsp);
        section_label = rootView.findViewById(R.id.section_label);
        section_label2 = rootView.findViewById(R.id.section_label2);
        section_label3 = rootView.findViewById(R.id.section_label3);
        picture = rootView.findViewById(R.id.picture);
        bgtime = rootView.findViewById(R.id.bgtime);
        aname = rootView.findViewById(R.id.name);
        address = rootView.findViewById(R.id.address);
        number = rootView.findViewById(R.id.number);
        textView31 = rootView.findViewById(R.id.textView31);
        time = rootView.findViewById(R.id.time);
        btncancelreq = rootView.findViewById(R.id.btncancelreq);
        btnviewreqdet = rootView.findViewById(R.id.btnviewreqdet);
        btnvielaunddetails = rootView.findViewById(R.id.btnvielaunddetails);
        btnviewdet = rootView.findViewById(R.id.btnviewdet);
        btnviewreq = rootView.findViewById(R.id.btnviewreq);

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
                   /* intent.putExtra("client_name", getClientName());
                    intent.putExtra("client_id", getClientId());*/
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
            //Pending buttons
            btncancelreq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTransaction();
                }
            });
            btnvielaunddetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putInt("trans_No",trans_No);
                    Intent intent = new Intent(getActivity(), ViewLaundryDetails.class);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
            });
            btnviewreqdet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putInt("trans_No",trans_No);
                    Intent intent = new Intent(getActivity(), ViewRequestDetails.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
            //End pending buttons
        btnviewdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No",trans_No);
                Intent intent = new Intent(getActivity(), ViewLaundryDetails.class);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
        btnviewreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No",trans_No);
                Intent intent = new Intent(getActivity(), ViewRequestDetails.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void updateTransaction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                               // Toast.makeText(ClientM.this, "Post Updated Successfully", Toast.LENGTH_SHORT).show();
                                getActivity().recreate();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
                                    trans_No = Integer.parseInt(object.getString("trans_No").trim());
                                    String shop_id = object.getString("shop_ID");
                                    String handwasher_id = object.getString("handwasher_ID");
                                    //Toast.makeText(getActivity(), "shop_id " +shop_id, Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getActivity(), "handwasher_id " +handwasher_id, Toast.LENGTH_SHORT).show();
                                    if(!handwasher_id.isEmpty()){
                                         handwasher_Name = object.getString("handwasher_LName").trim();
                                         handwasher_Address = object.getString("handwasher_Address").trim();
                                         handwasher_Contact = object.getString("handwasher_Contact").trim();
                                        if(handwasher_Name.isEmpty() && handwasher_Address.isEmpty() && handwasher_Contact.isEmpty()){
                                            if(!shop_id.isEmpty()){
                                                shop_Name = object.getString("shop_Name").trim();
                                                shop_Address = object.getString("shop_Address").trim();
                                                shop_Contact = object.getString("shop_ContactNo1").trim();
                                                    aname.setText(shop_Name);
                                                    address.setText(shop_Address);
                                                    number.setText(shop_Contact);
                                                    time.setText(trans_Status);
                                            }
                                        } else {
                                            aname.setText(handwasher_Name);
                                            address.setText(handwasher_Address);
                                            number.setText(handwasher_Contact);
                                            time.setText(trans_Status);
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
                                                    aname.setText(handwasher_Name);
                                                    address.setText(handwasher_Address);
                                                    number.setText(handwasher_Contact);
                                                    time.setText(trans_Status);
                                            }
                                        } else{
                                            aname.setText(shop_Name);
                                            address.setText(shop_Address);
                                            number.setText(shop_Contact);
                                            time.setText(trans_Status);
                                        }


                                    }
                                    if(trans_Status.equals("Pending")){
                                        section_label.setVisibility(View.GONE);
                                        section_label2.setVisibility(View.GONE);
                                        section_label3.setVisibility(View.GONE);
                                        laundrydetails.setVisibility(View.GONE);
                                        findlsp.setVisibility(View.GONE);

                                        picture.setVisibility(View.VISIBLE);
                                        aname.setVisibility(View.VISIBLE);
                                        address.setVisibility(View.VISIBLE);
                                        number.setVisibility(View.VISIBLE);
                                        textView31.setVisibility(View.VISIBLE);
                                        bgtime.setVisibility(View.VISIBLE);
                                        time.setVisibility(View.VISIBLE);
                                        btnvielaunddetails.setVisibility(View.VISIBLE);
                                        btnviewreqdet.setVisibility(View.VISIBLE);
                                        btncancelreq.setVisibility(View.VISIBLE);
                                        time.setText(trans_Status);

                                    } else if(trans_Status.equals("Accepted")){
                                        btnvielaunddetails.setVisibility(View.GONE);
                                        btnviewreqdet.setVisibility(View.GONE);
                                        btncancelreq.setVisibility(View.GONE);

                                        section_label.setVisibility(View.GONE);
                                        section_label2.setVisibility(View.GONE);
                                        section_label3.setVisibility(View.GONE);
                                        laundrydetails.setVisibility(View.GONE);
                                        findlsp.setVisibility(View.GONE);

                                        picture.setVisibility(View.VISIBLE);
                                        aname.setVisibility(View.VISIBLE);
                                        address.setVisibility(View.VISIBLE);
                                        number.setVisibility(View.VISIBLE);
                                        textView31.setVisibility(View.VISIBLE);
                                        bgtime.setVisibility(View.VISIBLE);
                                        time.setVisibility(View.VISIBLE);

                                        btnviewdet.setVisibility(View.VISIBLE);
                                        btnviewreq.setVisibility(View.VISIBLE);
                                        time.setText(trans_Status);
                                    } else if(trans_Status.equals("Confirmed")){
                                        btnvielaunddetails.setVisibility(View.GONE);
                                        btnviewreqdet.setVisibility(View.GONE);
                                        btncancelreq.setVisibility(View.GONE);

                                        section_label.setVisibility(View.GONE);
                                        section_label2.setVisibility(View.GONE);
                                        section_label3.setVisibility(View.GONE);
                                        laundrydetails.setVisibility(View.GONE);
                                        findlsp.setVisibility(View.GONE);

                                        picture.setVisibility(View.VISIBLE);
                                        aname.setVisibility(View.VISIBLE);
                                        address.setVisibility(View.VISIBLE);
                                        number.setVisibility(View.VISIBLE);
                                        textView31.setVisibility(View.VISIBLE);
                                        bgtime.setVisibility(View.VISIBLE);
                                        time.setVisibility(View.VISIBLE);

                                        btnviewdet.setVisibility(View.VISIBLE);
                                        btnviewreq.setVisibility(View.VISIBLE);
                                        time.setText(trans_Status);
                                    }
                                        //Toast.makeText(getActivity(), "trans_Status " + trans_Status, Toast.LENGTH_SHORT).show();
                                }
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

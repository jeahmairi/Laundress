package com.example.user.laundress2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HandwasherBookings extends Fragment {
    ListView lvallbookings;
    private String handwasher_name;
    private int handwasher_id;
    private int handwasher_lspid;
    private Context context;
    ArrayList<HandwasherBookingsList> handwasherBookingsLists = new ArrayList<HandwasherBookingsList>();
    HandwasherBookingsAdapter handwasherBookingsAdapter;
    private static final String URL_ALL ="http://192.168.254.117/laundress/allbookingapprove.php";
    private String name, services, extraservices, servicetype, weight, datetime, client_Photo ;
    private int trans_No;
    private String xtraserve, serve;

    public static HandwasherBookings newInstance(int handwasher_id, String handwasher_name, int handwasher_lspid) {
        HandwasherBookings handwasherBookings = new HandwasherBookings();
        Bundle args = new Bundle();
        args.putInt("handwasher_id", handwasher_id);
        args.putInt("handwasher_lspid", handwasher_lspid);
        args.putString("handwasher_name", handwasher_name);
        handwasherBookings.setArguments(args);
        return handwasherBookings;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handwasher_id = getArguments().getInt("handwasher_id", 0);
        handwasher_lspid = getArguments().getInt("handwasher_lspid", 0);
        handwasher_name = getArguments().getString("handwasher_name");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.handwasher_bookings, container, false);
        lvallbookings = rootView.findViewById(R.id.lvallbookings);
        context = getActivity();
        allCategory();
        return rootView;
    }

    private void allCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allbooking");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                     name=jsonArray2.getJSONObject(i).getString("name").toString();
                                     services = jsonArray2.getJSONObject(i).getString("trans_Service").toString();
                                     extraservices = jsonArray2.getJSONObject(i).getString("trans_ExtService").toString();
                                     servicetype = jsonArray2.getJSONObject(i).getString("trans_ServiceType").toString();
                                     weight = jsonArray2.getJSONObject(i).getString("trans_EstWeight").toString();
                                     datetime = jsonArray2.getJSONObject(i).getString("trans_EstDateTime").toString();
                                     client_Photo = jsonArray2.getJSONObject(i).getString("client_Photo").toString();
                                     trans_No = Integer.parseInt(jsonArray2.getJSONObject(i).getString("trans_No").toString());
                                    int lsp_id = Integer.parseInt(jsonArray2.getJSONObject(i).getString("lsp_ID").toString());
                                    int handwasher_id = Integer.parseInt(jsonArray2.getJSONObject(i).getString("handwasher_ID").toString());
                                    xtraserve = xtraserve + " " +extraservices;
                                    serve = serve + " " +services;
                                }
                                HandwasherBookingsList handwasherBookingsList = new HandwasherBookingsList();
                                handwasherBookingsList.setName(name);
                                handwasherBookingsList.setLsp_id(handwasher_lspid);
                                handwasherBookingsList.setHandwasher_id(handwasher_id);
                                handwasherBookingsList.setTrans_no(trans_No);
                                handwasherBookingsList.setServices(serve);
                                handwasherBookingsList.setExtraservices(xtraserve);
                                handwasherBookingsList.setServicetype(servicetype);
                                handwasherBookingsList.setWeight(weight);
                                handwasherBookingsList.setDatetime(datetime);
                                handwasherBookingsList.setImage(client_Photo);
                                handwasherBookingsLists.add(handwasherBookingsList);
                                handwasherBookingsAdapter = new HandwasherBookingsAdapter(context,handwasherBookingsLists);
                                lvallbookings.setAdapter(handwasherBookingsAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}

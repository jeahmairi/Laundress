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

public class ShopBookings extends Fragment {
    ArrayList<Integer> arrID = new ArrayList<>();
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrReqService = new ArrayList<>();
    ArrayList<String> arrExtService = new ArrayList<>();
    ArrayList<String> arrServiceType = new ArrayList<>();
    ArrayList<String> arrWeight = new ArrayList<>();
    ArrayList<String> arrDateTime = new ArrayList<>();
    ArrayList<String> arrStatus = new ArrayList<>();
    ArrayList<Integer> arrTransNo = new ArrayList<>();
    ArrayList<String> arrShopName = new ArrayList<>();
    ArrayList<Integer> arrShopID = new ArrayList<>();
    private Context context;
    ListView listView;
//    private static final String URL_ALL = "http://192.168.254.113/laundress/shop_bookings.php";
    private static final String URL_ALL = "http://192.168.254.117/laundress/shop_bookings.php";
    ArrayList<ShopBookingsList> shopBookingsLists = new ArrayList<>();
    ShopBookingsAdapter shopBookingsAdapter;
    private RequestQueue requestQueue;
    String shop_name;
    int shop_id;
    private String booking;
    private int client_id;
    private int transNo;

    public static ShopBookings newInstance(int shop_id, String shop_name) {
        ShopBookings shopBookings = new ShopBookings();
        Bundle args = new Bundle();
        args.putInt("shop_id", shop_id);
        args.putString("name", shop_name);
        shopBookings.setArguments(args);

        return shopBookings;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());
        shop_id = getArguments().getInt("shop_id");
        shop_name = getArguments().getString("name");
        client_id = getArguments().getInt("client_id");
        booking = getArguments().getString("booking");

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shop_booking, container, false);
        listView = rootView.findViewById(R.id.lv_booking);
        /*shopBookingsAdapter = new ShopBookingsAdapter(context,shopBookingsLists);
        listView.setAdapter(shopBookingsAdapter);*/
       // bookings(shop_id, shop_name);
        context = getActivity();
        bookings(shop_id, shop_name);
        return rootView;
    }

    private void bookings(final int shop_id, final String shop_name){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("shopBookings");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length(); i++)
                                {
                                    int shopID = jsonArray.getJSONObject(i).getInt("shop_id");
                                    int id = jsonArray.getJSONObject(i).getInt("clientID");
                                    String name = jsonArray.getJSONObject(i).getString("clientName");
                                    String transServ = jsonArray.getJSONObject(i).getString("transService");
                                    String client_Address = jsonArray.getJSONObject(i).getString("client_Address");
                                    String client_Contact = jsonArray.getJSONObject(i).getString("client_Contact");
                                    String transExt = jsonArray.getJSONObject(i).getString("transExtra");
                                    String transServType = jsonArray.getJSONObject(i).getString("transServType");
                                    String transWeight = jsonArray.getJSONObject(i).getString("transWeight");
                                    String transDateTime = jsonArray.getJSONObject(i).getString("transDateTime");
                                    String transStat = jsonArray.getJSONObject(i).getString("transStat");
                                    String client_Photo = jsonArray.getJSONObject(i).getString("client_Photo");
                                    int lsp_id = Integer.parseInt(jsonArray.getJSONObject(i).getString("lsp_ID").toString());
                                    int transNo = jsonArray.getJSONObject(i).getInt("transNo");
                                    arrID.add(id);
                                    arrName.add(name);
                                    arrReqService.add(transServ);
                                    arrExtService.add(transExt);
                                    arrServiceType.add(transServType);
                                    arrWeight.add(transWeight);
                                    arrDateTime.add(transDateTime);
                                    arrStatus.add(transStat);
                                    arrTransNo.add(transNo);
                                    arrShopID.add(shopID);
                                    ShopBookingsList shopBookingsList = new ShopBookingsList();
                                    shopBookingsList.setShopID(shopID);
                                    shopBookingsList.setShopName(shop_name);
                                    shopBookingsList.setTransNo(transNo);
                                    shopBookingsList.setId(lsp_id);
                                    shopBookingsList.setName(name);
                                    shopBookingsList.setContact(client_Contact);
                                    shopBookingsList.setLocation(client_Address);
                                    shopBookingsList.setPhoto(client_Photo);
                                    shopBookingsList.setTransServ1(transServ);
                                    shopBookingsList.setTransExtra1(transExt);
                                    shopBookingsList.setTransServType(transServType);
                                    shopBookingsList.setTransWeight(transWeight);
                                    shopBookingsList.setTransDateTime(transDateTime);
                                    shopBookingsList.setTransStat(transStat);
                                    shopBookingsLists.add(shopBookingsList);
                                }
                                shopBookingsAdapter = new ShopBookingsAdapter(context,shopBookingsLists);
                                listView.setAdapter(shopBookingsAdapter);
                                shopBookingsAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failed Bookings 2 " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "failedBookings 3", Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("shop_id", String.valueOf(shop_id));
                //first 'email' nga kay mao pangan para kuha nimo para sa php
                params.put("shop_name", shop_name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}

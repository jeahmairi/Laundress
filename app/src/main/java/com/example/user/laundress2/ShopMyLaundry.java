package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class ShopMyLaundry extends Fragment {

    ArrayList<Integer> arrTransNo = new ArrayList<>();
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<Integer> arrShopID = new ArrayList<>();
    ArrayList<String> arrShopName = new ArrayList<>();
    ArrayList<Integer> arrClientID = new ArrayList<>();
    ArrayList<String> arrContact = new ArrayList<>();
    ArrayList<String> arrAddress = new ArrayList<>();
    ArrayList<String> arrDate = new ArrayList<>();
    private Context context;
    ListView listView;
//    private static final String URL_ALL = "http://192.168.254.113/laundress/shop_mylaundry.php";
    private static final String URL_ALL = "http://192.168.254.117/laundress/shop_mylaundry.php";
    ArrayList<ShopMyLaundryList> shopMyLaundryLists = new ArrayList<>();
    ShopMyLaundryAdapter shopMyLaundryAdapter;
    private RequestQueue requestQueue;
    private String shop_name;
    private int shop_id;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
// newInstance constructor for creating fragment with arguments
    public static ShopMyLaundry newInstance(int shop_id, String shop_name) {
        ShopMyLaundry shopMyLaundry = new ShopMyLaundry();
        Bundle args = new Bundle();
        args.putInt("shop_id", shop_id);
        args.putString("shop_name", shop_name);
        shopMyLaundry.setArguments(args);

        return shopMyLaundry;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shop_laundries, container, false);
        listView = rootView.findViewById(R.id.lv_laundries);
        shopMyLaundryAdapter = new ShopMyLaundryAdapter(context,shopMyLaundryLists);
        listView.setAdapter(shopMyLaundryAdapter);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        requestQueue = Volley.newRequestQueue(getContext());

        shop_id = getArguments().getInt("shop_id");
        shop_name = getArguments().getString("shop_name");

        //Toast.makeText(getActivity(), "shop id " +shop_id, Toast.LENGTH_SHORT).show();
        shop(shop_id, shop_name);
    }

    private void shop(final int shop_id, final String shop_name){

        //final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("shopMyLaundry");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int transNo = jsonArray.getJSONObject(i).getInt("transNo");
                                    int clientID = jsonArray.getJSONObject(i).getInt("clientID");
                                    int lsp_ID = jsonArray.getJSONObject(i).getInt("lspID");
                                    Toast.makeText(getActivity(), "lsp_ID " +lsp_ID, Toast.LENGTH_SHORT).show();
                                    String name = jsonArray.getJSONObject(i).getString("clientName");
                                    String address = jsonArray.getJSONObject(i).getString("clientAddress");
                                    String contact = jsonArray.getJSONObject(i).getString("clientContact");
                                    String date = jsonArray.getJSONObject(i).getString("transDate");
                                    String client_Photo = jsonArray.getJSONObject(i).getString("client_Photo");
                                    arrClientID.add(clientID);
                                    arrTransNo.add(transNo);
                                    arrName.add(name);
                                    arrShopID.add(shop_id);
                                    arrShopName.add(shop_name);
                                    arrAddress.add(address);
                                    arrContact.add(contact);
                                    arrDate.add(date);
                                    ShopMyLaundryList shopMyLaundryList = new ShopMyLaundryList();
                                    shopMyLaundryList.setShopID(shop_id);
                                    shopMyLaundryList.setLspID(lsp_ID);
                                    shopMyLaundryList.setShopName(shop_name);
                                    shopMyLaundryList.setTransNo(transNo);
                                    shopMyLaundryList.setClientID(clientID);
                                    shopMyLaundryList.setName(name);
                                    shopMyLaundryList.setAddress(address);
                                    shopMyLaundryList.setContact(contact);
                                    shopMyLaundryList.setDate(date);
                                    shopMyLaundryList.setPhoto(client_Photo);
                                    shopMyLaundryLists.add(shopMyLaundryList);
                                }
                                shopMyLaundryAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "failedLaundry", Toast.LENGTH_SHORT).show();
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

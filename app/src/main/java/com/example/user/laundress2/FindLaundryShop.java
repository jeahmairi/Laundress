package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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


public class FindLaundryShop extends Fragment{
    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<String> arrmeter = new ArrayList<>();
    ArrayList<String> arrcontact = new ArrayList<>();
    private Context context;
    //private static final String URL_ALL ="http://192.168.254.117/laundress/alllaundryshop.php";
    private static final String URL_ALL ="http://192.168.1.12/laundress/alllaundryshop.php";
   // private static final String URL_ALL ="http://192.168.254.100/laundress/alllaundryshop.php";
   // private static final String URL_ALL ="http://192.168.1.2/laundress/alllaundryshop.php";
    ArrayList<LaundryShopList> laundryShopLists = new ArrayList<LaundryShopList>();
    LaundryShopAdapter laundryShopAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.findlaundryshop, container, false);
        ListView listView = rootView.findViewById(R.id.lvlaundryshop);
        context = getActivity();
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
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
        catch (Exception e)
        {

        }
        laundryShopAdapter = new LaundryShopAdapter(context,laundryShopLists);
        listView.setAdapter(laundryShopAdapter);
        return rootView;
    }

    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("alllaundryshop");
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {

                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();;
                    arrname.add(name);
                    arrmeter.add(meter);
                    arrcontact.add(contact);
                    LaundryShopList laundryShopList = new LaundryShopList();
                    laundryShopList.setName(name);
                    laundryShopList.setLocation(meter);
                    laundryShopList.setMeter(contact);
                    laundryShopLists.add(laundryShopList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

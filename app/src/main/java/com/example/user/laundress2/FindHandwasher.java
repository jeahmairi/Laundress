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

public class FindHandwasher extends Fragment {
    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<String> arrmeter = new ArrayList<>();
    ArrayList<String> arrcontact = new ArrayList<>();
    //  ListView listview;
    private Context context;
    private static final String URL_ALL ="http://192.168.254.117/laundress/allhandwasher.php";
    ArrayList<HandwasherList> handwasherLists = new ArrayList<HandwasherList>();
    HandwasherAdapter handwasherAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.findhandwasher, container, false);
        ListView listView = rootView.findViewById(R.id.lvhandwashers);
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
        handwasherAdapter = new HandwasherAdapter(context,handwasherLists);
        listView.setAdapter(handwasherAdapter);
        return rootView;
    }

    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("allhandwasher");
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {

                    String name=jsonArray.getJSONObject(i).getString("fname").toString();
                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();;
                    arrname.add(name);
                    arrmeter.add(meter);
                    arrcontact.add(contact);
                    HandwasherList handwasherList = new HandwasherList();
                    handwasherList.setHandwasherName(name);
                    handwasherList.setHwmeter(meter);
                    handwasherList.setContact(contact);
                    handwasherLists.add(handwasherList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

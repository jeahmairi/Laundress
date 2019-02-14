package com.example.user.laundress2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindHandwasher extends Fragment {
    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<String> arrmeter = new ArrayList<>();
    ArrayList<String> arrcontact = new ArrayList<>();
    //  ListView listview;
    private Context context;
   private static final String URL_ALL ="http://192.168.254.117/laundress/allhandwasher.php";
   private static final String URL_ALL_CLIENT ="http://192.168.254.117/laundress/client.php";
   private static final String URL_ALL_CHEAP ="http://192.168.254.117/laundress/allhandwashercheap.php";
   private static final String URL_ALL_REC ="http://192.168.254.117/laundress/allhandwasherrecom.php";
   //private static final String URL_ALL ="http://192.168.1.12/laundress/allhandwasher.php";
   //private static final String URL_ALL ="http://192.168.254.100/laundress/allhandwasher.php";
   // private static final String URL_ALL ="http://192.168.1.2/laundress/allhandwasher.php";
    ArrayList<HandwasherList> handwasherLists = new ArrayList<HandwasherList>();
    HandwasherAdapter handwasherAdapter;

    private String client_name, filterby;
    private int client_id;
    Spinner spinner;
    String client_Address;
    ListView listView, lvcheap, lvrecommended;
    double lat;
    double lng;
    double lat2;
    double lng2;
    //private double client_latlang;

    // newInstance constructor for creating fragment with arguments
    public static FindHandwasher newInstance(int client_id, String client_name) {
        FindHandwasher findHandwasher = new FindHandwasher();
        Bundle args = new Bundle();
        args.putInt("client_id", client_id);
        args.putString("client_name", client_name);
        findHandwasher.setArguments(args);
        return findHandwasher;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client_id = getArguments().getInt("client_id", 0);
        client_name = getArguments().getString("client_name");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.findhandwasher, container, false);
         listView = rootView.findViewById(R.id.lvhandwashers);
         lvcheap = rootView.findViewById(R.id.lvcheap);
         lvrecommended = rootView.findViewById(R.id.lvrecommended);
         spinner = rootView.findViewById(R.id.spinner);
        context = getActivity();

        allClient();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterby = parent.getItemAtPosition(position).toString();
                if(filterby.equals("Nearest Location")){
                    listView.setVisibility(View.VISIBLE);
                    allHandwasher();
                } else if(filterby.equals("Cheapest Laundry Handwasher")){
                    listView.setVisibility(View.GONE);
                    lvcheap.setVisibility(View.VISIBLE);
                    allHandwasherCheapeast();
                } else
                if(filterby.equals("Recommended Laundry Handwasher")){
                    listView.setVisibility(View.GONE);
                    lvcheap.setVisibility(View.GONE);
                    lvrecommended.setVisibility(View.VISIBLE);
                    allHandwasherRecommend();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //allHandwasher();
        return rootView;
    }
    private void allClient() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_CLIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("client");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    client_Address=jsonArray.getJSONObject(i).getString("client_Address").toString();
                                   // Toast.makeText(getActivity(), "client_Address " +client_Address, Toast.LENGTH_SHORT).show();
                                    getLocationFromAddress(client_Address);
                                }
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
    private void allHandwasher() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("allhandwasher");
                            if (success.equals("1")){
                                handwasherLists.clear();
                                for (int i =0;i<jsonArray.length();i++)
                                {

                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();
                                    int lsp_id = Integer.parseInt(jsonArray.getJSONObject(i).getString("lspid").toString());
                                    arrname.add(name);
                                    arrmeter.add(getDistance(lat, lng, lat2, lng2));
                                    arrcontact.add(contact);
                                    //Collections.sort(arrmeter);
                                    //System.out.println(arrmeter);
                                    HandwasherList handwasherList = new HandwasherList();
                                    handwasherList.setClient_id(client_id);
                                    handwasherList.setHandwasherName(name);
                                    handwasherList.setContact(contact);
                                    handwasherList.setLsp_id(lsp_id);
                                    getLocationToAddress(meter);
                                    handwasherList.setHwmeter(getDistance(lat, lng, lat2, lng2));
                                    handwasherLists.add(handwasherList);

                                   // Toast.makeText(getActivity(), " " + getDistance(lat, lng, lat2, lng2),Toast.LENGTH_SHORT).show();
                                }
                                handwasherAdapter = new HandwasherAdapter(context,handwasherLists);
                                listView.setAdapter(handwasherAdapter);
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void allHandwasherCheapeast() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_CHEAP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("allhandwasher");
                            if (success.equals("1")){
                                handwasherLists.clear();
                                for (int i =0;i<jsonArray.length();i++)
                                {

                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();
                                    int lsp_id = Integer.parseInt(jsonArray.getJSONObject(i).getString("lspid").toString());
                                    arrname.add(name);
                                    arrmeter.add(meter);
                                    arrcontact.add(contact);
                                    HandwasherList handwasherList = new HandwasherList();
                                    handwasherList.setClient_id(client_id);
                                    handwasherList.setHandwasherName(name);
                                    handwasherList.setContact(contact);
                                    handwasherList.setLsp_id(lsp_id);
                                    getLocationToAddress(meter);
                                    handwasherList.setHwmeter(getDistance(lat, lng, lat2, lng2));
                                    handwasherList.setHwmeterdouble(getDistanceDouble(lat, lng, lat2, lng2));
                                    handwasherLists.add(handwasherList);
                                }
                                handwasherAdapter = new HandwasherAdapter(context,handwasherLists);
                                lvcheap.setAdapter(handwasherAdapter);
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void allHandwasherRecommend() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_REC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("allhandwasher");
                            if (success.equals("1")){
                                handwasherLists.clear();
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();
                                    String photo = jsonArray.getJSONObject(i).getString("photo").toString();
                                    int lsp_id = Integer.parseInt(jsonArray.getJSONObject(i).getString("lspid").toString());
                                    arrname.add(name);
                                    arrmeter.add(meter);
                                    arrcontact.add(contact);
                                    HandwasherList handwasherList = new HandwasherList();
                                    handwasherList.setClient_id(client_id);
                                    handwasherList.setHandwasherName(name);
                                    handwasherList.setContact(contact);
                                    handwasherList.setLsp_id(lsp_id);
                                    handwasherList.setPhoto(photo);
                                    handwasherList.setHwlocation(meter);
                                    getLocationToAddress(meter);
                                    handwasherList.setHwmeter(getDistance(lat, lng, lat2, lng2));
                                    handwasherList.setHwmeterdouble(getDistanceDouble(lat, lng, lat2, lng2));
                                    handwasherLists.add(handwasherList);
                                }
                                handwasherAdapter = new HandwasherAdapter(context,handwasherLists);
                                lvrecommended.setAdapter(handwasherAdapter);
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    public static String getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        // earth radius is in mile
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        double kmConvertion = 1.6093;
        // return new Float(distance * meterConversion).floatValue();
        return String.format("%.2f", new Float(distance * kmConvertion).floatValue()) + " km";
        // return String.format("%.2f", distance)+" m";
    }

    public static double getDistanceDouble(double lat_a, double lng_a, double lat_b, double lng_b) {
        // earth radius is in mile
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        double kmConvertion = 1.6093;
        // return new Float(distance * meterConversion).floatValue();
        return Double.parseDouble(String.format("%.2f", new Float(distance * kmConvertion).floatValue()));
        // return String.format("%.2f", distance)+" m";
    }

    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            lat = location.getLatitude();
            lng = location.getLongitude();

            return lat + "," + lng;
        } catch (Exception e) {
            return null;
        }
    }

    public String getLocationToAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            lat2 = location.getLatitude();
            lng2 = location.getLongitude();

            return lat + "," + lng;
        } catch (Exception e) {
            return null;
        }
    }

}
